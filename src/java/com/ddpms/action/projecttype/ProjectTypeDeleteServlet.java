
package com.ddpms.action.projecttype;

import com.ddpms.dao.ProjectTypeDao;
import com.ddpms.model.MessageUI;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ProjectTypeDeleteServlet extends HttpServlet {
    final static Logger logger = Logger.getLogger(ProjectTypeDeleteServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int exec = new ProjectTypeDao().deleteProjectType(Integer.parseInt(CharacterUtil.removeNull(request.getParameter("protId"))));
            MessageUI message = null;
            if (exec == 0) {
                message = new MessageUI(true, "สถานะการลบข้อมูล", "เกิดข้อผิดพลาดในขั้นตอนการลบข้อมูล", "danger");
            } else {
                message = new MessageUI(true, "สถานะการลบข้อมูล", "ลบข้อมูลสำเร็จ", "info");
            }
            request.getSession().setAttribute("MessageUI", message);
        } catch (Exception e) {
            logger.error("ProjectTypeDeleteServlet error", e);
            request.setAttribute("message", "delete project type error");
        }
        response.sendRedirect(request.getContextPath() + "/ProjectTypeListServlet?menu=project-type");
    }
}
