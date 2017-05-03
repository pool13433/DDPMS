package com.ddpms.action.task;

import com.ddpms.dao.ProjectDao;
import com.ddpms.dao.TaskAssignDao;
import com.ddpms.dao.TaskWorkDao;
import com.ddpms.model.Employee;
import com.ddpms.model.Project;
import com.ddpms.model.TaskAssign;
import com.ddpms.model.TaskWork;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class TaskWorkFormServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(TaskWorkFormServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            
            String taskwId = CharacterUtil.removeNull(request.getParameter("taskwId"));
            TaskWork taskWork = null;
            if(taskwId.equals("")){ // NEW 
                taskWork = new TaskWork();
            }else{
                taskWork = new TaskWorkDao().getTaskWork(Integer.parseInt(taskwId));
            }
            
            Employee employee = (Employee) request.getSession().getAttribute("EMPLOYEE");             
            List<Project> projectList = new ProjectDao().getProjectListHaveTaskAssign(employee.getEmpId());
            for (Project project : projectList) {                
                List<TaskAssign> taskAssignList = new TaskAssignDao().getTaskAssignListByUser(employee.getEmpId(),Integer.parseInt(project.getProjId()));
                project.setTaskAssignList(taskAssignList);
            }            
            request.setAttribute("projectList", projectList);
            request.setAttribute("taskWork", taskWork);
        } catch (Exception e) {
            logger.error("TaskAssignFormServlet Error", e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/task/taskWork-form.jsp");
        dispatcher.forward(request, response);
    }
}
