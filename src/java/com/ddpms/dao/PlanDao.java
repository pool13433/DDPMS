/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ddpms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.ddpms.db.DbConnection;
import com.ddpms.model.Employee;
import com.ddpms.model.Plan;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author POOL_LAPTOP
 */
public class PlanDao {

    final static Logger logger = Logger.getLogger(PlanDao.class);

    private Connection conn = null;

    public Plan getPlan(String planId) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Plan plan = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT `plan_id`, `plan_name`, `modified_date`, `modified_by` FROM `plan` WHERE plan_id = ?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, planId);

            rs = pstm.executeQuery();
            if (rs.next()) {
                plan = this.getEntityPlan(rs);
            }
        } catch (Exception e) {
            logger.error("getUser error", e);
        } finally {
            this.close(pstm, rs);
        }
        return plan;
    }

    public List<Plan> getPlanAll() {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Plan> planList = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT `plan_id`, `plan_name`, `modified_date`, `modified_by` FROM `plan` ORDER BY plan_name ASC");

            pstm = conn.prepareStatement(sql.toString());
            rs = pstm.executeQuery();
            planList = new ArrayList<Plan>();
            while (rs.next()) {
                planList.add(this.getEntityPlan(rs));
            }
        } catch (Exception e) {
            logger.error("getPlanAll error", e);
        } finally {
            this.close(pstm, rs);
        }
        return planList;
    }

    private Plan getEntityPlan(ResultSet rs) throws SQLException {
        Plan plan = new Plan();
        plan.setModifiedBy(rs.getString("modified_date"));
        plan.setModifiedDate(rs.getString("modified_date"));
        plan.setPlanId(rs.getString("plan_id"));
        plan.setPlanName(rs.getString("plan_name"));
        return plan;
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
            logger.error("close db error", ex);
        }
    }

}
