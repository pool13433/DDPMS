package com.ddpms.action.project.expense;

import com.ddpms.dao.ProjectDao;
import com.ddpms.dao.ProjectExpenseDao;
import com.ddpms.model.Pagination;
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

public class ProjectExpenseSearchServlet extends HttpServlet {
    
    final static Logger logger = Logger.getLogger(ProjectExpenseSearchServlet.class);
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("...goGet ProjectExpenseSearchServlet");
        try {
            int limit = CharacterUtil.removeNullTo(request.getParameter("limit"), 10);
            int offset = CharacterUtil.removeNullTo(request.getParameter("offset"), 0);
            String pageUrl = request.getContextPath() + "/ProjectExpenseSearchServlet?" + request.getQueryString();
            
            String projId = CharacterUtil.removeNull(request.getParameter("projId"));
            String expPr = CharacterUtil.removeNull(request.getParameter("expPr"));
            String expVoch = CharacterUtil.removeNull(request.getParameter("expVoch"));
            String receipt = CharacterUtil.removeNull(request.getParameter("receipt"));
            String expAmount = CharacterUtil.removeNull(request.getParameter("expAmount"));
            String expDateBegin = CharacterUtil.removeNull(request.getParameter("expDateBegin"));
            String expDateEnd = CharacterUtil.removeNull(request.getParameter("expDateEnd"));
            
            ProjectExpense criteria = new ProjectExpense();
            criteria.setProjId(projId);
            criteria.setExpAmount(expAmount);
            criteria.setExpDateBegin(expDateBegin);
            criteria.setExpDateEnd(expDateEnd);
            criteria.setExpPr(expPr);
            criteria.setExpVoch(expVoch);
            criteria.setReceipt(receipt);
            
            ProjectExpenseDao dao = new ProjectExpenseDao();
            String sqlCondition = dao.getConditionBuilder(criteria);
            int countRecordAll = dao.getCountProjectExpense(sqlCondition);
            List<ProjectExpense> expenseList = dao.getProjectExpenseList(limit, offset, sqlCondition);
            request.setAttribute("expenseList", expenseList);
            Pagination pagination = new Pagination(pageUrl, countRecordAll, limit, offset);
            request.setAttribute("pagination", pagination);
            request.setAttribute("projectList", new ProjectDao().getProjectAll());
            request.setAttribute("criteria", criteria);
        } catch (Exception e) {
            logger.error("ProjectExpenseSearchServlet Error : ", e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/project-expense/expense-search.jsp");
        dispatcher.forward(request, response);
    }
    
}
