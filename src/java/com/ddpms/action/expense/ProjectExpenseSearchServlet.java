
package com.ddpms.action.expense;

import com.ddpms.dao.ProjectDao;
import com.ddpms.dao.ProjectExpenseDao;
import com.ddpms.model.Pagination;
import com.ddpms.model.Project;
import com.ddpms.model.ProjectExpense;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ProjectExpenseSearchServlet extends HttpServlet {
final static Logger logger = Logger.getLogger(ProjectExpenseSearchServlet.class);
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...goGet ProjectExpenseSearchServlet");
        try {
            String menu = CharacterUtil.removeNull(request.getParameter("menu"));
            ProjectExpenseDao dao = new ProjectExpenseDao();
            ProjectExpense pe = new  ProjectExpense();
            ProjectDao pjDao = new ProjectDao();
            request.setAttribute("projectList", pjDao.getProject(new Project(), 0, 0));
            int limit = CharacterUtil.removeNullTo(request.getParameter("limit"), 5);
            int offset = CharacterUtil.removeNullTo(request.getParameter("offset"), 0);
            String proj_id = CharacterUtil.removeNull(request.getParameter("proj_id"));
            request.setAttribute("proj_id", proj_id);
            String pageUrl = request.getContextPath() + "/ProjectExpenseSearchServlet?" + request.getQueryString();
            String sqlConditionBuilder = dao.getConditionBuilder(pe);
            int countRecordAll = dao.getCountProjectExpense(sqlConditionBuilder);
            
             if("searching".equals(menu)){
                pe.setProjId(proj_id);
                
                request.setAttribute("expenseList", dao.getProjectExpense(pe, limit, offset));                
            }else{
                request.setAttribute("expenseList", dao.getProjectExpense(new ProjectExpense(), limit, offset));
            }
            Pagination pagination = new Pagination(pageUrl, countRecordAll, limit, offset);
            request.setAttribute("pagination", pagination);
                
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/expense/expense-search.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            logger.error("ProjectExpenseSearchServlet Error : "+e.getMessage());
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...goPost ProjectExpenseSearchServlet");
        try {
            
        } catch (Exception e) {
            logger.error("ProjectExpenseSearchServlet Error : "+e.getMessage());
        }
    }
}
