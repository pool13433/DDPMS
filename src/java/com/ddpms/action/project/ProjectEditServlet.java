
package com.ddpms.action.project;

import com.ddpms.dao.BudgetPlanDao;
import com.ddpms.dao.PlanDao;
import com.ddpms.dao.ProjectDao;
import com.ddpms.dao.ProjectExpenseDao;
import com.ddpms.dao.ProjectTypeDao;
import com.ddpms.dao.ProjectWorkingDao;
import com.ddpms.model.BudgetPlan;
import com.ddpms.model.Plan;
import com.ddpms.model.Project;
import com.ddpms.model.ProjectExpense;
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

public class ProjectEditServlet extends HttpServlet {
final static Logger logger = Logger.getLogger(ProjectEditServlet.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...doGet ProjectEditServlet");
        try {
            Project p = new Project();
            ProjectDao projectDao = new ProjectDao();
            //Profile profile = (Profile)request.getSession().getAttribute("USER_PROFILE");
            String id = CharacterUtil.removeNull(request.getParameter("id"));
            p.setProjId(id);
            List<Project> list = projectDao.getProjectNormal(p, 0, 0);
            PlanDao planDao = new PlanDao();
            request.setAttribute("planList", planDao.getPlan(new Plan(), 0, 0));
            ProjectTypeDao dao = new ProjectTypeDao();
            int countRecordAll = dao.getCountProjectType("");
            request.setAttribute("projectTypeList", dao.getProjectTypeAll(countRecordAll, 0,""));
            BudgetPlanDao bpDao = new BudgetPlanDao();
            request.setAttribute("budgetPlanList", bpDao.getBudgetPlan(new BudgetPlan(), 0, 0));
            request.setAttribute("planList", planDao.getPlan(new Plan(), 0, 0));
            
            if(!list.isEmpty()){
                request.setAttribute("proj_id", list.get(0).getProjId());
                request.setAttribute("proj_name",list.get(0).getProjName());
                request.setAttribute("proj_details",list.get(0).getProjDetail());
                request.setAttribute("proj_status",list.get(0).getProjStatus());
                request.setAttribute("plan_id",list.get(0).getPlanId());
                request.setAttribute("budp_id",list.get(0).getBudpId());
                request.setAttribute("account",list.get(0).getAccountCode());
                request.setAttribute("details",list.get(0).getProjDetail());
            }
            //check project have an expense
            ProjectExpenseDao peDao = new ProjectExpenseDao();
            ProjectExpense pe = new ProjectExpense();
            pe.setProjId(id);
            String condt = peDao.getConditionBuilder(pe);
            int isCancel = peDao.getCountProjectExpense(condt);
            request.setAttribute("isCancel", isCancel<=0?true:false);
            System.out.println("isCancel "+isCancel);
            
            ProjectWorkingDao projectWDao = new  ProjectWorkingDao();
            ProjectWorking pw = new ProjectWorking();
            pw.setProjId(id);
            List<ProjectWorking> projectWorkingList = projectWDao.getProjectWorking(pw, 0, 0);
            request.setAttribute("projectWorkingList",projectWorkingList);
                    
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/project/project-form.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            logger.error("ProjectEditServlet Error : "+e.getMessage());
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...doPost ProjectEditServlet");
        try {
            
        } catch (Exception e) {
        }
       
    }

}
