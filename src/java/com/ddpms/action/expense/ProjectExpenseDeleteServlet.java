
package com.ddpms.action.expense;

import com.ddpms.dao.ProjectExpenseDao;
import com.ddpms.model.MessageUI;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ProjectExpenseDeleteServlet extends HttpServlet {
final static Logger logger = Logger.getLogger(ProjectExpenseDeleteServlet.class);
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...goGet ProjectExpenseDeleteServlet");
        try {
            String id = CharacterUtil.removeNull(request.getParameter("id"));
            ProjectExpenseDao dao = new ProjectExpenseDao();
            int exe = 0;
            exe = dao.deleteProjectExpense(id);
            MessageUI message = null;
            if (exe == 0) {
                message = new MessageUI(true, "สถานะการลบข้อมูล", "เกิดข้อผิดพลาดในขั้นตอนการลบข้อมูล", "danger");
            } else {
                message = new MessageUI(true, "สถานะการลบข้อมูล", "ลบข้อมูลสำเร็จ", "info");
            }       
            request.getSession().setAttribute("MessageUI", message);      
            response.sendRedirect(request.getContextPath() + "/ProjectExpenseSearchServlet");
        } catch (Exception e) {
            logger.error("ProjectExpenseDeleteServlet Error : "+e.getMessage());
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...goPost ProjectExpenseDeleteServlet");
        try {
            
        } catch (Exception e) {
            logger.error("ProjectExpenseDeleteServlet Error : "+e.getMessage());
        }
    }
}
