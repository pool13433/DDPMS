
package com.ddpms.action.expense;

import com.ddpms.dao.ProjectExpenseDao;
import com.ddpms.model.ProjectExpense;
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

public class ProjectExpenseEditServlet extends HttpServlet {

final static Logger logger = Logger.getLogger(ProjectExpenseEditServlet.class);
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...goGet ProjectExpenseEditServlet");
        try {
            ProjectExpenseDao dao = new ProjectExpenseDao();
            String id = CharacterUtil.removeNull(request.getParameter("id"));
            ProjectExpense pe = new  ProjectExpense();
            pe.setExpId(id);
            List<ProjectExpense> list = new ArrayList<ProjectExpense>();
            list = dao.getProjectExpense(pe, 0, 0);
            if(!list.isEmpty()){
                request.setAttribute("exp_id", id);
                request.setAttribute("proj_id", list.get(0).getProjId());
                request.setAttribute("exp_desc", list.get(0).getExpDesc());
                request.setAttribute("exp_date", list.get(0).getExpDate());
                request.setAttribute("exp_amount", list.get(0).getExpAmount());
                request.setAttribute("exp_pr", list.get(0).getExpPr());
                request.setAttribute("exp_voch", list.get(0).getExpVoch());
                request.setAttribute("receipt", list.get(0).getReceipt());
                request.setAttribute("vender", list.get(0).getVender());                
            }
                        
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/expense/expense-form.jsp");
            dispatcher.forward(request, response);
            
        } catch (Exception e) {
            logger.error("ProjectExpenseEditServlet Error : "+e.getMessage());
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...goPost ProjectExpenseEditServlet");
        try {
            
        } catch (Exception e) {
            logger.error("ProjectExpenseEditServlet Error : "+e.getMessage());
        }
    }
}
