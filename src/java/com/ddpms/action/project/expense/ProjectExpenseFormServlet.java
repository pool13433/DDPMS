package com.ddpms.action.project.expense;

import com.ddpms.dao.ProjectDao;
import com.ddpms.dao.ProjectExpenseDao;
import com.ddpms.model.ProjectExpense;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ProjectExpenseFormServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(ProjectExpenseFormServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String expId = CharacterUtil.removeNull(request.getParameter("expId"));
            ProjectExpense expense = null;
            if (expId.equals("")) { // NEW
                expense = new ProjectExpense();
            } else {
                expense = new ProjectExpenseDao().getProjectExpense(expId);
            }
            request.setAttribute("projectList", new ProjectDao().getProjectAll());
            request.setAttribute("expense", expense);
        } catch (Exception e) {
            logger.error("ProjectExpenseFormServlet error", e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/project-expense/expense-form.jsp");
        dispatcher.forward(request, response);
    }
}
