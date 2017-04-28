package com.ddpms.action.task;

import com.ddpms.dao.EmployeeDao;
import com.ddpms.dao.ProjectDao;
import com.ddpms.dao.TaskAssignDao;
import com.ddpms.dao.TaskDao;
import com.ddpms.model.Employee;
import com.ddpms.model.Project;
import com.ddpms.model.Task;
import com.ddpms.model.TaskAssign;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class TaskAssignFormServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(TaskAssignFormServlet.class);
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            TaskAssign taskAssign = null;
            String projId = CharacterUtil.removeNull(request.getParameter("projId"));            
            String taskaId = CharacterUtil.removeNull(request.getParameter("taskaId"));   
            
            if(taskaId.equals("")){ //NEW
                taskAssign = new TaskAssign();
            }else{ // UPDATE
                taskAssign = new TaskAssignDao().getTaskAssign(Integer.parseInt(taskaId));
                projId = String.valueOf(taskAssign.getProjId());
            }
            logger.info("projId ::=="+projId);
            List<Employee> employeeList = new EmployeeDao().getEmployeeListByRole("STAFF");
            Project project = new ProjectDao().getProject(Integer.parseInt(projId));
            List<Task> taskList = new TaskDao().getTaskAll();
            request.setAttribute("employeeList", employeeList);
            request.setAttribute("project", project);
            request.setAttribute("taskList", taskList);
            request.setAttribute("taskAssign", taskAssign);
        } catch (Exception e) {
            logger.error("TaskAssignFormServlet Error", e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/task/taskAssign-form.jsp");
        dispatcher.forward(request, response);
    }

}
