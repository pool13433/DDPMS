package com.ddpms.action.project.type;

import com.ddpms.dao.ConfigDao;
import com.ddpms.dao.ProjectTypeDao;
import com.ddpms.model.ProjectType;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ProjectTypeFormServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(ProjectTypeFormServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String protId = CharacterUtil.removeNull(request.getParameter("protId"));
            ProjectType projectType = null;
            if (protId.equals("")) { // new 
                projectType = new ProjectType();
            } else {
                projectType = new ProjectTypeDao().getProjectType(protId);
            }
            request.setAttribute("projectType", projectType);
            request.setAttribute("projectGroupList", new ConfigDao().getConfigList("PROJECT_GROUP"));
        } catch (Exception e) {
            logger.error("ProjectTypeFormServlet error", e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/project-type/projectType-form.jsp");
        dispatcher.forward(request, response);
    }
}
