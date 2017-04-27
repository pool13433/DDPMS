
package com.ddpms.action.budgetplan;

import com.ddpms.dao.BudgetPlanDao;
import com.ddpms.model.BudgetPlan;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class BudgetplanEditServlet extends HttpServlet {
final static Logger logger = Logger.getLogger(BudgetplanEditServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...doGet BudgetplanEditServlet");
        try {
            BudgetPlan bp = new BudgetPlan();
            BudgetPlanDao bpDao = new BudgetPlanDao();
            String id = CharacterUtil.removeNull(request.getParameter("id"));
            bp.setBudpId(id);
            List<BudgetPlan> list = bpDao.getBudgetPlan(bp, 0, 0);
            if(!list.isEmpty()){
                request.setAttribute("budp_id", id);
                request.setAttribute("budp_name", list.get(0).getBudpName());
                request.setAttribute("budp_begin", list.get(0).getBudpYearBegin());
                request.setAttribute("budp_end", list.get(0).getBudpYearEnd());
            }
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/budget-plan/budp-form.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            logger.error("BudgetplanEditServlet Error : "+e.getMessage());
                    
        }
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            
            
        } catch (Exception e) {
            logger.error("BudgetplanEditServlet Error : "+e.getMessage());
        }
    }

    
}
