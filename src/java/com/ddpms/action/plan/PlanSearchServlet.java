
package com.ddpms.action.plan;

import com.ddpms.action.strategic.StrategicSearchServlet;
import com.ddpms.dao.PlanDao;
import com.ddpms.dao.ProjectDao;
import com.ddpms.dao.StrategicDao;
import com.ddpms.model.Pagination;
import com.ddpms.model.Plan;
import com.ddpms.model.Project;
import com.ddpms.model.Strategic;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class PlanSearchServlet extends HttpServlet {
final static Logger logger = Logger.getLogger(PlanSearchServlet.class);

@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...goGet PlanSearchServlet");
        try {
            String menu = CharacterUtil.removeNull(request.getParameter("menu"));
            PlanDao dao = new PlanDao();
            Plan p = new  Plan();
            ProjectDao pjDao = new ProjectDao();
            request.setAttribute("projectList", pjDao.getProject(new Project(), 0, 0));
            int limit = CharacterUtil.removeNullTo(request.getParameter("limit"), 5);
            int offset = CharacterUtil.removeNullTo(request.getParameter("offset"), 0);
            String plan_name = CharacterUtil.removeNull(request.getParameter("plan_name"));
            request.setAttribute("plan_name", plan_name);
            String pageUrl = request.getContextPath() + "/PlanSearchServlet?" + request.getQueryString();
            String sqlConditionBuilder = dao.getConditionBuilder(p);
            int countRecordAll = dao.getCountPlan(sqlConditionBuilder);
            
             if("searching".equals(menu)){
                p.setPlanName(plan_name);
                
                request.setAttribute("planList", dao.getPlan(p, limit, offset));                
            }else{
                request.setAttribute("planList", dao.getPlan(new Plan(), limit, offset));
            }
            Pagination pagination = new Pagination(pageUrl, countRecordAll, limit, offset);
            request.setAttribute("pagination", pagination);
                
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/plan/plan-search.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            logger.error("PlanSearchServlet Error : "+e.getMessage());
        }
    }
}
