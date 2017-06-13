
package com.ddpms.action.project.expense;

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
            String expId = CharacterUtil.removeNull(request.getParameter("expId"));
            ProjectExpenseDao dao = new ProjectExpenseDao();
            int exe = 0;
            exe = dao.deleteProjectExpense(expId);
            MessageUI message = null;
            if (exe == 0) {
                message = new MessageUI(false, "สถานะการลบข้อมูล", "เกิดข้อผิดพลาดในขั้นตอนการลบข้อมูล", "danger");
            } else {
                message = new MessageUI(true, "สถานะการลบข้อมูล", "ลบข้อมูลสำเร็จ", "info");
            }       
            request.getSession().setAttribute("MessageUI", message);                  
        } catch (Exception e) {
            logger.error("ProjectExpenseDeleteServlet Error : ",e);
        }
        response.sendRedirect(request.getContextPath() + "/ProjectExpenseSearchServlet?menu=project_expense");
    }
}
