package com.ddpms.action.project.type;

import com.ddpms.dao.ProjectTypeDao;
import com.ddpms.model.Employee;
import com.ddpms.model.MessageUI;
import com.ddpms.model.ProjectType;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ProjectTypeSaveServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(ProjectTypeSaveServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Employee employee = (Employee) request.getSession().getAttribute("EMPLOYEE");
            String protId=  CharacterUtil.removeNull(request.getParameter("protId"));
            String protName = CharacterUtil.removeNull(request.getParameter("protName"));
            String protCode = CharacterUtil.removeNull(request.getParameter("protCode"));
            String protType = CharacterUtil.removeNull(request.getParameter("protType"));
            ProjectType projectType = new ProjectType();
            projectType.setProtCode(protCode);
            projectType.setProtType(protType);
            projectType.setProtName(protName);
            projectType.setModifiedBy(String.valueOf(employee.getEmpId()));
            ProjectTypeDao dao = new ProjectTypeDao();
            int exec = 0;
            if (protId.equals("")) {
                exec = dao.createProjectType(projectType);
            } else {
                projectType.setProtId(protId);
                exec = dao.updateProjectType(projectType);
            }
            MessageUI message = null;
            if (exec == 0) {
                message = new MessageUI(true, "สถานะการบันทึกข้อมูล", "เกิดข้อผิดพลาดในขั้นตอนการบันทึกข้อมูล", "danger");
            } else {
                message = new MessageUI(true, "สถานะการบันทึกข้อมูล", "บันทึกข้อมูลสำเร็จ", "info");
            }
            request.getSession().setAttribute("MessageUI", message);
        } catch (Exception e) {
            logger.error("ProjectTypeSaveServlet", e);
        }
        response.sendRedirect(request.getContextPath() + "/ProjectTypeListServlet?menu=project-type");
    }

}
