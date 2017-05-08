package com.ddpms.dao;

import com.ddpms.db.DbConnection;
import com.ddpms.model.Plan;
import com.ddpms.util.CharacterUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class PlanDao {

    final static Logger logger = Logger.getLogger(PlanDao.class);

    private Connection conn = null;

    public List<Plan> getPlan(Plan p, int limit, int offset) {
        logger.debug("..getPlan");
        List<Plan> list = new ArrayList<Plan>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT `plan_id`, `plan_name`, ");
            sql.append(" `modified_date`, (select e.username from employee e where e.emp_id=modified_by) as `modified_by` ");
            sql.append(" FROM `Plan` ");
            sql.append(getConditionBuilder(p));
            if (offset != 0) {
                sql.append(" limit ").append(limit).append(" offset ").append(offset);
            }

            pstm = conn.prepareStatement(sql.toString());
            logger.info("pstm ::==" + pstm.toString());
            rs = pstm.executeQuery();

            while (rs.next()) {
                list.add(getEntityPlan(rs));
            }
        } catch (Exception e) {
            logger.error("Error getPlan :" + e.getMessage());
        }
        return list;
    }

    public int createPlan(Plan p) {
        logger.debug("..createPlan");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO Plan ");
            sql.append(" (`plan_name`, `modified_date`, `modified_by` ) ");
            sql.append(" VALUES (?,NOW(),?)");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, p.getPlanName());
            pstm.setString(2, p.getModifiedBy());
            logger.info("pstm ::==" + pstm.toString());
            exe = pstm.executeUpdate();

        } catch (Exception e) {
            logger.error("Error savePlan :" + e.getMessage());
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public int updatePlan(Plan p) {
        logger.debug("..updatePlan");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE `Plan` SET ");
            sql.append(" `plan_name`=?, `modified_by`=?, ");
            sql.append(" `modified_date`=NOW() ");
            sql.append(" WHERE `plan_id`=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, p.getPlanName());
            pstm.setString(2, p.getModifiedBy());
            pstm.setString(3, p.getPlanId());
            logger.info("pstm ::==" + pstm.toString());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("updatePlan error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public int deletePlan(String id) {
        logger.debug("..deletePlan");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" DELETE FROM `Plan` WHERE plan_id=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, id);
            logger.info("pstm ::==" + pstm.toString());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("deletePlan error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public String getConditionBuilder(Plan p) {
        logger.debug("..getConditionBuilder");
        StringBuilder sql = new StringBuilder(" WHERE 1=1 ");
        try {
            if (!"".equals(CharacterUtil.removeNull(p.getPlanId()))) {
                sql.append(" and plan_id ='" + p.getPlanId() + "'");
            }
            if (!"".equals(CharacterUtil.removeNull(p.getPlanName()))) {
                sql.append(" and plan_name LIKE '%" + p.getPlanName() + "%'");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sql.toString();
    }


    public int getCountPlan(String conditionBuilder) {
        logger.debug("..getCountPlan");
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int countPlan = 0;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder("SELECT COUNT(*) as cnt FROM Plan p");
            if (conditionBuilder != null) {
                sql.append(conditionBuilder);
            }
            pstm = conn.prepareStatement(sql.toString());
            rs = pstm.executeQuery();
            if (rs.next()) {
                countPlan = rs.getInt("cnt");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getCountPlan error", e);
        } finally {
            this.close(pstm, rs);
        }
        return countPlan;
    }

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
            logger.error("Close PreparedStatement error", ex);
            logger.error("close db error", ex);
        }
    }

}

