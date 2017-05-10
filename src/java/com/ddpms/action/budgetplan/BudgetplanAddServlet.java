
package com.ddpms.action.budgetplan;

import com.ddpms.dao.BudgetPlanDao;
import com.ddpms.model.BudgetPlan;
import com.ddpms.model.Employee;
import com.ddpms.model.MessageUI;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class BudgetplanAddServlet extends HttpServlet {
final static Logger logger = Logger.getLogger(BudgetplanAddServlet.class);
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("....doGet BudgetplanAddServlet");
        try {
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/budget-plan/budp-form.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            logger.error("BudgetplanAddServlet Error :"+e.getMessage());
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("....doPost BudgetplanAddServlet");
        try {
            BudgetPlan bp = new BudgetPlan();
            BudgetPlanDao bpDao = new BudgetPlanDao();
            String id = CharacterUtil.removeNull(request.getParameter("id"));
            String budp_name = CharacterUtil.removeNull(request.getParameter("budp_name"));
            request.setAttribute("budp_name", budp_name);
            String budp_begin = CharacterUtil.removeNull(request.getParameter("budp_begin"));
            request.setAttribute("budp_begin", budp_begin);
            String budp_end = CharacterUtil.removeNull(request.getParameter("budp_end"));
            request.setAttribute("budp_end", budp_end);
            Employee employee = (Employee) request.getSession().getAttribute("EMPLOYEE");  
            
            bp.setBudpId(id);
            bp.setBudpName(budp_name);
            bp.setBudpYearBegin(budp_begin);
            bp.setBudpYearEnd(budp_end);
            bp.setModifiedBy(String.valueOf(employee.getEmpId()));
            int exe = 0;
            if(id.equals("")){
                exe = bpDao.createBudgetPlan(bp);
            }else{
                exe = bpDao.updateBudgetPlan(bp);
            }
            MessageUI message = null;
            if (exe == 0) {
                message = new MessageUI(true, "สถานะการบันทีกข้อมูล", "เกิดข้อผิดพลาดในขั้นตอนการบันทีกข้อมูล", "danger");
            } else {
                message = new MessageUI(true, "สถานะการบันทีกข้อมูล", "บันทีกข้อมูลสำเร็จ", "info");
            }        
            request.getSession().setAttribute("MessageUI", message);
            response.sendRedirect(request.getContextPath() + "/BudgetplanSearchServlet");
        } catch (Exception e) {
            logger.error("BudgetplanAddServlet Error: "+e.getMessage());
        }
    }

   
}
