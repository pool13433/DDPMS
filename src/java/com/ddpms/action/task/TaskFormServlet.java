
package com.ddpms.action.task;

import com.ddpms.dao.TaskDao;
import com.ddpms.model.Task;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class TaskFormServlet extends HttpServlet {

   final static Logger logger = Logger.getLogger(TaskFormServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {            
            String taskId = CharacterUtil.removeNull(request.getParameter("taskId"));
            Task task = null;
            if(taskId.equals("")){ // NEW 
                task = new Task();
            }else{
                task = new TaskDao().getTask(taskId);
            }
            request.setAttribute("task", task);
        } catch (Exception e) {
            logger.error("TaskFormServlet Error", e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/task/task-form.jsp");
        dispatcher.forward(request, response);
    }
}
