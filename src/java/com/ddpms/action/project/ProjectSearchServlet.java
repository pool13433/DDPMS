
package com.ddpms.action.project;

import com.ddpms.dao.BudgetPlanDao;
import com.ddpms.dao.ConfigDao;
import com.ddpms.dao.PlanDao;
import com.ddpms.dao.ProjectDao;
import com.ddpms.dao.ProjectTypeDao;
import com.ddpms.model.BudgetPlan;
import com.ddpms.model.Employee;
import com.ddpms.model.Pagination;
import com.ddpms.model.Plan;
import com.ddpms.model.Project;
import com.ddpms.model.ProjectExpense;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;


public class ProjectSearchServlet extends HttpServlet {
    
    final static Logger logger = Logger.getLogger(ProjectSearchServlet.class);
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...doGet ProjectSearchServlet");
        try {
            Employee emp = (Employee) request.getSession().getAttribute("EMPLOYEE");
            Project p = new Project();
            ProjectDao projectDao = new ProjectDao();
            int limit = CharacterUtil.removeNullTo(request.getParameter("limit"), 5);
            int offset = CharacterUtil.removeNullTo(request.getParameter("offset"), 0);
            String proj_name = CharacterUtil.removeNull(request.getParameter("proj_name"));
            request.setAttribute("proj_name", proj_name);
            String proj_details = CharacterUtil.removeNull(request.getParameter("proj_details"));
            request.setAttribute("proj_details", proj_details);
            String proj_status = CharacterUtil.removeNull(request.getParameter("proj_status"));
            request.setAttribute("proj_status", proj_status);
            String plan_id = CharacterUtil.removeNull(request.getParameter("plan_id"));
            request.setAttribute("plan_id", plan_id);
            String budgetPlan = CharacterUtil.removeNull(request.getParameter("budgetPlan"));
            request.setAttribute("budgetPlan", budgetPlan);
            String prot_id = CharacterUtil.removeNull(request.getParameter("prot_id"));
            request.setAttribute("prot_id", prot_id);  
            request.setAttribute("statusList", new ConfigDao().getConfigList("PROJECT_STATUS"));
            p.setProjName(proj_name);
            p.setProjDetail(proj_details);
            p.setProjStatus(proj_status);
            p.setPlanId(plan_id);
            p.setBudpId(budgetPlan);    
            p.setProtId(prot_id);  
            p.setModifiedBy(String.valueOf(emp.getEmpId()));
            int notificationCnt = CharacterUtil.removeNullTo(request.getParameter("notification"), 0);
            if(notificationCnt > 0){
                p.setNotification(notificationCnt);
                request.getSession().removeAttribute("NOTI_PROJECT_WAITING");
            }   
            request.setAttribute("projectList", projectDao.getProject(p, limit, offset));   
            String pageUrl = request.getContextPath() + "/ProjectSearchServlet?" + request.getQueryString();
            String sqlConditionBuilder = projectDao.getConditionBuilder(p);
            int countRecordAll = projectDao.getCountProject(sqlConditionBuilder);
            Pagination pagination = new Pagination(pageUrl, countRecordAll, limit, offset);
            request.setAttribute("pagination", pagination);
            PlanDao planDao = new PlanDao();
            request.setAttribute("planList", planDao.getPlan(new Plan(), 0, 0));
            ProjectTypeDao dao = new ProjectTypeDao();
            countRecordAll = dao.getCountProjectType("");
            request.setAttribute("projectTypeList", dao.getProjectTypeAll(countRecordAll, 0,""));
            BudgetPlanDao bpDao = new BudgetPlanDao();
            request.setAttribute("budgetPlanList", bpDao.getBudgetPlan(new BudgetPlan(), 0, 0));
            request.setAttribute("planList", planDao.getPlan(new Plan(), 0, 0)); 
            
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/project/project-search.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            logger.error("ProjectSearchServlet Error : "+e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       logger.debug("...doPost ProjectSearchServlet");
        try {
            
        } catch (Exception e) {
             logger.error("ProjectSearchServlet Error : "+e.getMessage());
        }
    }

}
