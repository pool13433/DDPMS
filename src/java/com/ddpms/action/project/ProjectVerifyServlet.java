package com.ddpms.action.project;

import com.ddpms.dao.ProjectDao;
import com.ddpms.dao.ProjectWorkingDao;
import com.ddpms.model.Project;
import com.ddpms.model.ProjectWorking;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ProjectVerifyServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(ProjectVerifyServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String projId = CharacterUtil.removeNull(request.getParameter("id"));
            int projectId = Integer.parseInt(projId);
            Project project = new ProjectDao().getProject(projectId);
            List<ProjectWorking> projectWorkingList = new ProjectWorkingDao().getProjectWorkingByProject(projectId);
            request.setAttribute("projectWorkingList", projectWorkingList);
            request.setAttribute("project", project);
        } catch (Exception e) {
            logger.error("ProjectVerifyServlet Error", e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/project/project-verify.jsp");
        dispatcher.forward(request, response);
    }

}
