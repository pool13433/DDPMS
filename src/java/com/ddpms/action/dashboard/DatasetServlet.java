package com.ddpms.action.dashboard;

import com.ddpms.dao.ProjectDao;
import com.ddpms.dao.ProjectShiftDao;
import com.ddpms.model.Config;
import com.ddpms.util.CharacterUtil;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class DatasetServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(DatasetServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object data = null;
        try {
            String chartId = CharacterUtil.removeNull(request.getParameter("chartId"));
            if (chartId.equals("chartBudgetInYear")) {
                String year = "2017";
                Map<String, Integer> months = new ProjectDao().getCountProjectInMonth(year);
                data = months;
            } else if (chartId.equals("chartGroupPlan")) {
                List<Config> groupPlanList = new ProjectShiftDao().getSumGroupByPlan();
                data = groupPlanList;
            }
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(new Gson().toJson(data));
            out.flush();
        } catch (Exception e) {
            logger.error("DatasetServlet error", e);
        }
    }

}
