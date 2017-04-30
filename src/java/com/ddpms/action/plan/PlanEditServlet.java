
package com.ddpms.action.plan;

import com.ddpms.dao.PlanDao;
import com.ddpms.model.Plan;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;


public class PlanEditServlet extends HttpServlet {

final static Logger logger = Logger.getLogger(PlanEditServlet.class);
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...goGet PlanEditServlet");
        try {
            PlanDao dao = new PlanDao();
            String id = CharacterUtil.removeNull(request.getParameter("id"));
            Plan p = new Plan();
            p.setPlanId(id);
            
            List<Plan> list = new ArrayList<Plan>();
            list = dao.getPlan(p, 0, 0);
            
            if(!list.isEmpty()){
                request.setAttribute("plan_id", id);
                request.setAttribute("plan_name", list.get(0).getPlanName()); 
            }
                        
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/plan/plan-form.jsp");
            dispatcher.forward(request, response);
            
        } catch (Exception e) {
            logger.error("PlanEditServlet Error : "+e.getMessage());
        }
    }
}
