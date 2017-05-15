package com.ddpms.dao;

import com.ddpms.db.DbConnection;
import com.ddpms.model.Config;
import com.ddpms.model.ProjectShift;
import com.ddpms.util.CharacterUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class ProjectShiftDao {

    final static Logger logger = Logger.getLogger(ProjectShiftDao.class);

    private Connection conn = null;

    public List<ProjectShift> getProjectShift(ProjectShift ps, int limit, int offset) {
        logger.debug("..getProjectShift");
        List<ProjectShift> psList = new ArrayList<ProjectShift>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT  `projs_id`, `proj_id`, `projs_reason`, `projs_plan`, ");
            sql.append(" `modified_date`, `modified_by` ");
            sql.append(" FROM `project_shift` ");
            sql.append(getConditionBuilder(ps));
            if (offset != 0) {
                sql.append(" limit ").append(limit).append(" offset ").append(offset);
            }

            pstm = conn.prepareStatement(sql.toString());
            logger.info("pstm ::==" + pstm.toString());
            rs = pstm.executeQuery();

            while (rs.next()) {
                psList.add(getEntityProjectShift(rs));
            }
        } catch (Exception e) {
            logger.error("Error getProjectShift :" + e.getMessage());
        }
        return psList;
    }

    public int createProjectShift(ProjectShift ps) {
        logger.debug("..createProjectShift");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO prject_shift ");
            sql.append(" (`proj_id`, `projs_reason`, `projs_plan`, `modified_date`, `modified_by` ) ");
            sql.append(" VALUES (?,?,?,NOW(),?)");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, ps.getProjId());
            pstm.setString(2, ps.getProjsReason());
            pstm.setString(3, ps.getProjsPlan());
            pstm.setString(4, ps.getModifiedBy());
            logger.info("pstm ::==" + pstm.toString());
            exe = pstm.executeUpdate();

        } catch (Exception e) {
            logger.error("Error saveProjectShift:" + e.getMessage());
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public int updateProjectShift(ProjectShift ps) {
        logger.debug("..updateProjectShift");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE `prject_shift` SET ");
            sql.append(" `proj_id`=?,`projs_reason`=?,`projs_plan`=?, ");
            sql.append(" `modified_by`=?, ");
            sql.append(" `modified_date`=NOW() ");
            sql.append(" WHERE `projs_id`=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, ps.getProjId());
            pstm.setString(2, ps.getProjsReason());
            pstm.setString(3, ps.getProjsPlan());
            pstm.setString(4, ps.getModifiedBy());
            pstm.setString(5, ps.getProjsId());
            logger.info("pstm ::==" + pstm.toString());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("updateProjectShift error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public int deleteProjectShift(String id) {
        logger.debug("..deleteProjectShift");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" DELETE FROM `project_shift` WHERE projs_id=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, id);
            logger.info("pstm ::==" + pstm.toString());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("deleteProjectShift error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public String getConditionBuilder(ProjectShift ps) {
        logger.debug("..getConditionBuilder");
        StringBuilder sql = new StringBuilder(" WHERE 1=1 ");
        try {
            if (!"".equals(CharacterUtil.removeNull(ps.getProjId()))) {
                sql.append(" and proj_id='" + ps.getProjId() + "'");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sql.toString();
    }

    private ProjectShift getEntityProjectShift(ResultSet rs) throws SQLException {
        ProjectShift ps = new ProjectShift();

        ps.setProjsId(rs.getString("projs_id"));
        ps.setProjId(rs.getString("proj_id"));
        ps.setProjsReason(rs.getString("projs_reason"));
        ps.setProjsPlan(rs.getString("projs_plan"));
        ps.setModifiedDate(rs.getString("modified_date"));
        ps.setModifiedBy(rs.getString("modified_by"));

        return ps;
    }

    public int getCountProjectShift(String conditionBuilder) {
        logger.debug("..getCountProjectShift");
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int countProjectShift = 0;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder("SELECT COUNT(*) as cnt FROM project_shift ps");
            if (conditionBuilder != null) {
                sql.append(conditionBuilder);
            }
            pstm = conn.prepareStatement(sql.toString());
            rs = pstm.executeQuery();
            if (rs.next()) {
                countProjectShift = rs.getInt("cnt");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getCountProjectShift error", e);
        } finally {
            this.close(pstm, rs);
        }
        return countProjectShift;
    }

    private void close(PreparedStatement pstm, ResultSet rs) {
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
        }
    }

    public List<Config> getSumGroupByPlan() {
        logger.info(" ... getSumGroupByPlan ");
        List<Config> groupPlanList = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT (SELECT plan_name FROM plan pl where pl.plan_id = p.plan_id) as name,count(*) cnt FROM `project` p group by p.plan_id order by count(*) DESC";
            conn = new DbConnection().open();
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            groupPlanList = new ArrayList<>();
            while (rs.next()) {
                Config data = new Config();
                data.setConfName(rs.getString("name"));
                data.setConfValue(rs.getString("cnt"));
                groupPlanList.add(data);
            }
        } catch (Exception e) {
            logger.error("getSumGroupByPlan error", e);
        } finally {
            this.close(pstm, rs);
        }
        return groupPlanList;
    }

}
