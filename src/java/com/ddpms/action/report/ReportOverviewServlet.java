package com.ddpms.action.report;

import com.ddpms.dao.ReportDao;
import com.ddpms.model.BudgetPlan;
import com.ddpms.model.BudgetProject;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ReportOverviewServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(ReportOverviewServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {        
        try {
            ReportDao dao = new ReportDao();
            BudgetPlan rangeYear = convertYearADToDC(dao.getRangeYearAllProject());
            int minYear = Integer.parseInt(rangeYear.getBudpYearBegin());
            int maxYear = Integer.parseInt(rangeYear.getBudpYearEnd());
            int lengthYear = maxYear - minYear;
            
            List<BudgetProject> budgetProjectList = dao.getBudgetInProjectList(minYear, lengthYear);
            request.setAttribute("budgetProjectList", budgetProjectList);
            request.setAttribute("rangeYear", rangeYear);
        } catch (Exception e) {
            logger.error("ReportOverviewServlet error", e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/report/report-overview.jsp");
        dispatcher.forward(request, response);
    }
    
    private BudgetPlan convertYearADToDC(BudgetPlan rangeYear){
        BudgetPlan temp = new BudgetPlan();
        temp.setBudpYearBegin(String.valueOf(Integer.parseInt(rangeYear.getBudpYearBegin()) - 543));
        temp.setBudpYearEnd(String.valueOf(Integer.parseInt(rangeYear.getBudpYearEnd()) - 543));
        return temp;
    }

}
