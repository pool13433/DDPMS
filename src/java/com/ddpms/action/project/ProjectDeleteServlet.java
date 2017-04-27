
package com.ddpms.action.project;

import com.ddpms.dao.ProjectDao;
import com.ddpms.model.MessageUI;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ProjectDeleteServlet extends HttpServlet {
final static Logger logger = Logger.getLogger(ProjectDeleteServlet.class);
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...doGet ProjectDelectServlet");
        try {
            String id = CharacterUtil.removeNull(request.getParameter("id"));
            ProjectDao projectDao = new ProjectDao();
            int exe = 0;
            exe = projectDao.deleteProject(id);
            MessageUI message = null;
            if (exe == 0) {
                message = new MessageUI(true, "สถานะการลบข้อมูล", "เกิดข้อผิดพลาดในขั้นตอนการลบข้อมูล", "danger");
            } else {
                message = new MessageUI(true, "สถานะการลบข้อมูล", "ลบข้อมูลสำเร็จ", "info");
            }       
            request.getSession().setAttribute("MessageUI", message);
            response.sendRedirect(request.getContextPath() + "/ProjectSearchServlet");
        } catch (Exception e) {
            logger.error("ProjectDelectServlet Error : "+e.getMessage());
        }
        
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }


}
