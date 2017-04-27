
package com.ddpms.action.budgetplan;

import com.ddpms.dao.BudgetPlanDao;
import com.ddpms.model.MessageUI;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class BudgetplanDeleteServlet extends HttpServlet {
final static Logger logger = Logger.getLogger(BudgetplanDeleteServlet.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...doGet BudgetplanDeleteServlet");
        try {
            String id = CharacterUtil.removeNull(request.getParameter("id"));
            BudgetPlanDao bpDao = new BudgetPlanDao();
            int exe = 0;
            exe = bpDao.deleteBudgetPlan(id);
            MessageUI message = null;
            if (exe == 0) {
                message = new MessageUI(true, "สถานะการลบข้อมูล", "เกิดข้อผิดพลาดในขั้นตอนการลบข้อมูล", "danger");
            } else {
                message = new MessageUI(true, "สถานะการลบข้อมูล", "ลบข้อมูลสำเร็จ", "info");
            }       
            request.getSession().setAttribute("MessageUI", message);      
            response.sendRedirect(request.getContextPath() + "/BudgetplanSearchServlet");
        } catch (Exception e) {
            logger.error("BudgetplanDeleteServlet Error :"+e.getMessage());
        }
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            
        } catch (Exception e) {
        }
    }

    
}
