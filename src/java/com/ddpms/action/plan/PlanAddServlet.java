
package com.ddpms.action.plan;

import com.ddpms.dao.PlanDao;
import com.ddpms.model.Employee;
import com.ddpms.model.MessageUI;
import com.ddpms.model.Plan;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class PlanAddServlet extends HttpServlet {
final static Logger logger = Logger.getLogger(PlanAddServlet.class);
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...goGet PlanAddServlet");
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/plan/plan-form.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            logger.error("PlanAddServlet Error : "+e.getMessage());
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...goPost PlanAddServlet");
        try {
            PlanDao dao = new PlanDao();
            Plan p = new  Plan();
            String id = CharacterUtil.removeNull(request.getParameter("id"));
            String plan_name = CharacterUtil.removeNull(request.getParameter("plan_name"));
            request.setAttribute("plan_name", plan_name);
            Employee employee = (Employee) request.getSession().getAttribute("EMPLOYEE");  
            
            p.setPlanId(id);
            p.setPlanName(plan_name);
            p.setModifiedBy(String.valueOf(employee.getEmpId()));
            
            int exe = 0;
            if(id.equals("")){
                exe = dao.createPlan(p);
            }else{
                exe = dao.updatePlan(p);
            }
            MessageUI message = null;
            if (exe == 0) {
                message = new MessageUI(true, "สถานะการบันทีกข้อมูล", "เกิดข้อผิดพลาดในขั้นตอนการบันทีกข้อมูล", "danger");
            } else {
                message = new MessageUI(true, "สถานะการบันทีกข้อมูล", "บันทีกข้อมูลสำเร็จ", "info");
            }        
            request.getSession().setAttribute("MessageUI", message);
            response.sendRedirect(request.getContextPath() + "/PlanSearchServlet");
            
        } catch (Exception e) {
            logger.error("PlanAddServlet Error : "+e.getMessage());
        }
    }
}
