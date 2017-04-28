package com.ddpms.action.taskassign;

import com.ddpms.dao.ProjectDao;
import com.ddpms.dao.TaskAssignDao;
import com.ddpms.model.Project;
import com.ddpms.model.TaskAssign;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class TaskAssignListServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(TaskAssignListServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Project> projectList = new ProjectDao().getProjectAll();
            for (Project project : projectList) {
                List<TaskAssign> taskAssignList = new TaskAssignDao().getTaskAssignsByProject(project.getProjId());
                project.setTaskAssignList(taskAssignList);
            }
            request.setAttribute("projectList", projectList);
        } catch (Exception e) {
            logger.error("TaskAssignListServlet Error", e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/task-assign/taskAssign-search.jsp");
        dispatcher.forward(request, response);
    }

}
