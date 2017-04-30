package com.ddpms.action.task;

import com.ddpms.dao.TaskWorkDao;
import com.ddpms.model.Employee;
import com.ddpms.model.TaskWork;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class TaskWorkListServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(TaskWorkListServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Employee employee = (Employee) request.getSession().getAttribute("EMPLOYEE");
            
            List<TaskWork> taskWorkingList = new TaskWorkDao().getTaskWorkListByUser(employee.getEmpId());
            request.setAttribute("taskWorkingList", taskWorkingList);
        } catch (Exception e) {
            logger.error("TaskWorkingListServlet error", e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/task/taskWork-search.jsp");
        dispatcher.forward(request, response);
    }

}
