
package com.ddpms.action.project_working;

import com.ddpms.dao.ProjectWorkingDao;
import com.ddpms.model.MessageUI;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ProjectWorkingDeleteServlet extends HttpServlet {
final static Logger logger = Logger.getLogger(ProjectWorkingDeleteServlet.class);
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...doGet ProjectWorkingDeleteServlet");
        try {
            String id = CharacterUtil.removeNull(request.getParameter("id"));
            ProjectWorkingDao projectWorkingDao = new ProjectWorkingDao();
            int exe = 0;
            exe = projectWorkingDao.deleteProjectWorking(id);
            MessageUI message = null;
            if (exe == 0) {
                message = new MessageUI(false, "สถานะการลบข้อมูล", "เกิดข้อผิดพลาดในขั้นตอนการลบข้อมูล", "danger");
            } else {
                message = new MessageUI(true, "สถานะการลบข้อมูล", "ลบข้อมูลสำเร็จ", "info");
            }       
            request.getSession().setAttribute("MessageUI", message);
            response.sendRedirect(request.getContextPath() + "/ProjectWorkingSearchServlet");
            
        } catch (Exception e) {
            logger.error("ProjectWorkingDeleteServlet Error : ",e);
        }
        
    }
}
