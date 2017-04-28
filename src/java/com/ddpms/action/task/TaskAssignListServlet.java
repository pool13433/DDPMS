package com.ddpms.action.task;

import com.ddpms.dao.BudgetPlanDao;
import com.ddpms.dao.PlanDao;
import com.ddpms.dao.ProjectDao;
import com.ddpms.dao.TaskAssignDao;
import com.ddpms.model.BudgetPlan;
import com.ddpms.model.Plan;
import com.ddpms.model.Project;
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

public class TaskAssignListServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(TaskAssignListServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String budpId = CharacterUtil.removeNull(request.getParameter("budpId"));
            String planId = CharacterUtil.removeNull(request.getParameter("planId"));
            Project criteria = new Project();
            criteria.setBudpId(budpId);
            criteria.setPlanId(planId);
            List<Project> projectList = new ProjectDao().getProjectByCriteria(criteria);
            for (Project project : projectList) {
                List<TaskAssign> taskAssignList = new TaskAssignDao().getTaskAssignsListByProject(project.getProjId());
                project.setTaskAssignList(taskAssignList);
            }

            List<BudgetPlan> budgetPlanList = new BudgetPlanDao().getBudgetAll();
            List<Plan> planList = new PlanDao().getPlanAll();
            request.setAttribute("budgetPlanList", budgetPlanList);
            request.setAttribute("projectList", projectList);
            request.setAttribute("planList", planList);
            request.setAttribute("criteria", criteria);
        } catch (Exception e) {
            logger.error("TaskAssignListServlet Error", e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/task/taskAssign-search.jsp");
        dispatcher.forward(request, response);
    }

}
