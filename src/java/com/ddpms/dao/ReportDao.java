package com.ddpms.dao;

import com.ddpms.db.DbConnection;
import com.ddpms.model.BudgetPlan;
import com.ddpms.model.BudgetProject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class ReportDao {

    final static Logger logger = Logger.getLogger(ReportDao.class);

    private Connection conn = null;

    public BudgetPlan getRangeYearAllProject() {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        BudgetPlan rangeYear = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT min(budp_year_begin) as min_year, max(budp_year_end) as max_year FROM `budget_plan`");
            logger.debug(" sql ::=="+sql.toString());
            pstm = conn.prepareStatement(sql.toString());
            rs = pstm.executeQuery();

            if (rs.next()) {
                rangeYear = new BudgetPlan();
                rangeYear.setBudpYearBegin(rs.getString("min_year"));
                rangeYear.setBudpYearEnd(rs.getString("max_year"));
            }
        } catch (Exception e) {
            logger.error("getRangeYearAllProject error", e);
        } finally {
            this.close(pstm, rs);
        }
        return rangeYear;
    }

    public List<BudgetProject> getBudgetInProjectList(int maxYear, int lengthYear) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<BudgetProject> budgetProjectList = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT p.proj_name,(SELECT e.emp_fname FROM employee e WHERE e.emp_id = p.modified_by ) as onwer, ");
            sql.append(" p.plan_id as plan_id,(SELECT pl.plan_name FROM plan pl WHERE pl.plan_id = p.plan_id) as plan_name, ");

            sql.append(" (SELECT SUM((budget_request_m1 + budget_request_m2 + budget_request_m3 + budget_request_m4 + ");
            sql.append(" budget_request_m5 + budget_request_m6 + budget_request_m7 + budget_request_m8 + ");
            sql.append(" budget_request_m9 + budget_request_m10 + budget_request_m11 + budget_request_m12)) ");
            sql.append(" FROM project_working pw WHERE pw.proj_id = p.proj_id ");
            sql.append(" ) as sum_request_all, ");

            sql.append(" (SELECT SUM((budget_approve_m1 + budget_approve_m2 + budget_approve_m3 + budget_approve_m4 + ");
            sql.append(" budget_approve_m5 + budget_approve_m6 + budget_approve_m7 + budget_approve_m8 + ");
            sql.append(" budget_approve_m9 + budget_approve_m10 + budget_approve_m11 + budget_approve_m12)) ");
            sql.append(" FROM project_working pw WHERE pw.proj_id = p.proj_id ");
            sql.append(" ) as sum_approve_all, ");

            sql.append(" ( SELECT SUM(pe.exp_amount) FROM project_expense pe WHERE pe.proj_id = p.proj_id ");
            sql.append(" ) as sum_actualuse_all ");

            for (int year = maxYear; year <= (maxYear + lengthYear); year++) {

                sql.append(" ,(SELECT SUM((budget_request_m1 + budget_request_m2 + budget_request_m3 + budget_request_m4 + ");
                sql.append(" budget_request_m5 + budget_request_m6 + budget_request_m7 + budget_request_m8 + ");
                sql.append(" budget_request_m9 + budget_request_m10 + budget_request_m11 + budget_request_m12)) ");
                sql.append(" FROM project_working pw WHERE pw.proj_id = p.proj_id ");
                sql.append(" AND (pw.budget_year-543) = '" + year + "' ");
                sql.append(" ) as sum_request_" + year + "  ");

                sql.append(" ,(SELECT SUM((budget_approve_m1 + budget_approve_m2 + budget_approve_m3 + budget_approve_m4 + ");
                sql.append(" budget_approve_m5 + budget_approve_m6 + budget_approve_m7 + budget_approve_m8 + ");
                sql.append(" budget_approve_m9 + budget_approve_m10 + budget_approve_m11 + budget_approve_m12)) ");
                sql.append(" FROM project_working pw WHERE pw.proj_id = p.proj_id ");
                sql.append(" AND (pw.budget_year-543) = '" + year + "' ");
                sql.append(" ) as sum_approve_" + year + "  ");

                sql.append(" ,( SELECT SUM(pe.exp_amount) FROM project_expense pe WHERE pe.proj_id = p.proj_id ");
                sql.append(" AND year(pe.exp_date) = '" + year + "' ");
                sql.append(" ) as sum_actualuse_" + year + "  ");
            }
            sql.append(" FROM project p  ");
            sql.append(" ORDER BY p.plan_id,p.proj_name DESC; ");
            logger.debug("sql ::==" + sql.toString());
            pstm = conn.prepareStatement(sql.toString());
            rs = pstm.executeQuery();

            budgetProjectList = new ArrayList<>();
            while (rs.next()) {
                BudgetProject projectMain = new BudgetProject();
                projectMain.setProject(rs.getString("proj_name"));
                projectMain.setOwner(rs.getString("onwer"));
                projectMain.setPlanId(rs.getString("plan_id"));
                projectMain.setPlanName(rs.getString("plan_name"));

                Map<String, Integer> projectDet = new HashMap<>();
                projectDet.put("sum_request_all", rs.getInt("sum_request_all"));
                projectDet.put("sum_approve_all", rs.getInt("sum_approve_all"));
                projectDet.put("sum_actualuse_all", rs.getInt("sum_actualuse_all"));

                for (int year = maxYear; year <= (maxYear + lengthYear); year++) {
                    projectDet.put("sum_request_" + String.valueOf(year), rs.getInt("sum_request_" + year));
                    projectDet.put("sum_approve_" + String.valueOf(year), rs.getInt("sum_approve_" + year));
                    projectDet.put("sum_actualuse_" + String.valueOf(year), rs.getInt("sum_actualuse_" + year));
                }
                projectMain.setBudgetProject(projectDet);
                budgetProjectList.add(projectMain);
            }
        } catch (Exception e) {
            logger.error("getBudgetInProjectList error", e);
        }
        return budgetProjectList;
    }

    public void close(PreparedStatement pstm, ResultSet rs) {
        try {
            if (this.conn != null) {
                this.conn.close();
            }
            if (pstm != null) {
                pstm.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            logger.error("Close PreparedStatement error", ex);
            logger.error("close db error", ex);
        }
    }

    public static void main(String[] args) {
        int minYear = 2560;
        int lengthYear = 4;
        List<BudgetProject> budgetProjectList = new ReportDao().getBudgetInProjectList(minYear, lengthYear);
        for (BudgetProject budgetProject : budgetProjectList) {
            logger.info("budgetProject ::==" + budgetProject.getProject());

            Map<String, Integer> map = budgetProject.getBudgetProject();

            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
            }

        }
    }
}
