package com.ddpms.action.task;

import com.ddpms.dao.ConfigDao;
import com.ddpms.dao.ProjectDao;
import com.ddpms.dao.ProjectShiftDao;
import com.ddpms.dao.TaskAssignDao;
import com.ddpms.dao.TaskWorkDao;
import com.ddpms.model.Config;
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

public class TaskWorkListServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(TaskWorkListServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int taskaId = CharacterUtil.removeNullTo(request.getParameter("taskaId"), -1);
            int taskwManday = CharacterUtil.removeNullTo(request.getParameter("taskwManday"),-1);
            String taskwMonth = CharacterUtil.removeNull(request.getParameter("taskwMonth"));
            logger.info(" taskaId ::=="+taskaId);
            logger.info(" taskwManday ::=="+taskwManday);
            logger.info(" taskwMonth ::=="+taskwMonth);
            TaskWork criteria = new TaskWork();
            criteria.setTaskaId(taskaId);
            criteria.setTaskwDate(taskwMonth);
            criteria.setTaskwManday(taskwManday);    
            
            Employee employee = (Employee) request.getSession().getAttribute("EMPLOYEE");            
            List<TaskWork> taskWorkingList = new TaskWorkDao().getTaskWorkListByUser(employee.getEmpId(),criteria);
            
            List<Project> projectList = new ProjectDao().getProjectListHaveTaskAssign(employee.getEmpId());
            for (Project project : projectList) {                
                List<TaskAssign> taskAssignList = new TaskAssignDao().getTaskAssignListByUser(employee.getEmpId(),Integer.parseInt(project.getProjId()));
                project.setTaskAssignList(taskAssignList);
            }            
            request.setAttribute("projectList", projectList);
            request.setAttribute("months", new ConfigDao().getConfigUnique("MONTHS").getConfValue());
            request.setAttribute("mandays", new ConfigDao().getConfigUnique("MANDAYS").getConfValue());
            request.setAttribute("taskWorkingList", taskWorkingList);
            request.setAttribute("criteria", criteria);
        } catch (Exception e) {
            logger.error("TaskWorkingListServlet error", e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/task/taskWork-search.jsp");
        dispatcher.forward(request, response);
    }

}
