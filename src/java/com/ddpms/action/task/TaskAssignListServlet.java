package com.ddpms.action.task;

import com.ddpms.dao.TaskAssignDao;
import com.ddpms.dao.TaskDao;
import com.ddpms.model.Task;
import com.ddpms.model.TaskAssign;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import static org.eclipse.jdt.core.compiler.IProblem.Task;

public class TaskAssignListServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(TaskAssignListServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Task> taskList = new TaskDao().getTaskAll();
            for (Task task : taskList) {
                List<TaskAssign> taskAssignList = new TaskAssignDao().getTaskAssignsByTask(task.getTaskId());
                task.setTaskAssignList(taskAssignList);
            }
            request.setAttribute("taskList", taskList);
        } catch (Exception e) {
            logger.error("TaskAssignListServlet Error", e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/task/task-search.jsp");
        dispatcher.forward(request, response);
    }

}
