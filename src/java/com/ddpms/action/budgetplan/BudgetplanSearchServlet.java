
package com.ddpms.action.budgetplan;

import com.ddpms.dao.BudgetPlanDao;
import com.ddpms.model.BudgetPlan;
import com.ddpms.model.Pagination;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class BudgetplanSearchServlet extends HttpServlet {
final static Logger logger = Logger.getLogger(BudgetplanSearchServlet.class);
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("doGet BudgetplanSearchServlet");
        try {
            String menu = CharacterUtil.removeNull(request.getParameter("menu"));
            BudgetPlan bp = new BudgetPlan();
            BudgetPlanDao bpDao = new BudgetPlanDao();
            int limit = CharacterUtil.removeNullTo(request.getParameter("limit"), 5);
            int offset = CharacterUtil.removeNullTo(request.getParameter("offset"), 0);
            String budp_name = CharacterUtil.removeNull(request.getParameter("budp_name"));
            request.setAttribute("budp_name", budp_name);
            String budp_begin = CharacterUtil.removeNull(request.getParameter("budp_begin"));
            request.setAttribute("budp_begin", budp_begin);
            String budp_end = CharacterUtil.removeNull(request.getParameter("budp_end"));
            request.setAttribute("budp_end", budp_end);
            String pageUrl = request.getContextPath() + "/BudgetplanSearchServlet?" + request.getQueryString();
            String sqlConditionBuilder = bpDao.getConditionBuilder(bp);
            int countRecordAll = bpDao.getCountBudgetPlan(sqlConditionBuilder);
            
            if("searching".equals(menu)){
                bp.setBudpName(budp_name);
                bp.setBudpYearBegin(budp_begin);
                bp.setBudpYearEnd(budp_end);                
                
                request.setAttribute("budgetPlanList", bpDao.getBudgetPlan(bp, limit, offset));                
            }else{
                request.setAttribute("budgetPlanList", bpDao.getBudgetPlan(new BudgetPlan(), limit, offset));
            }
            Pagination pagination = new Pagination(pageUrl, countRecordAll, limit, offset);
            request.setAttribute("pagination", pagination);
                
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/budget-plan/budp-search.jsp");
            dispatcher.forward(request, response);
            
        } catch (Exception e) {
         logger.error("Error BudgetplanSearchServlet : "+e.getMessage());
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            
        } catch (Exception e) {
            
        }
    }

    
}
