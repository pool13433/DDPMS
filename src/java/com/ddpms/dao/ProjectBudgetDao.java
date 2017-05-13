
package com.ddpms.dao;

import com.ddpms.db.DbConnection;
import com.ddpms.model.ProjectBudget;
import com.ddpms.util.CharacterUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class ProjectBudgetDao {
    final static Logger logger = Logger.getLogger(ProjectBudgetDao.class);

    private Connection conn = null;

    public List<ProjectBudget> getProjectBudget(ProjectBudget pb, int limit, int offset) {
        logger.debug("..getProjectBudget");
        List<ProjectBudget> list = new ArrayList<ProjectBudget>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT `prob_id`, `proj_id`, `budp_id`, `prob_req_budg`, `prob_appr_budg` ");
            sql.append(" FROM  `project_budget` ");
            sql.append(getConditionBuilder(pb));
            if (offset != 0) {
                sql.append(" limit ").append(limit).append(" offset ").append(offset);
            }

            pstm = conn.prepareStatement(sql.toString());
            logger.info("pstm ::==" + pstm.toString());
            rs = pstm.executeQuery();

            while (rs.next()) {
                list.add(getEntityProjectBudget(rs));
            }
        } catch (Exception e) {
            logger.error("Error getProjectBudget :" + e.getMessage());
        }
        return list;
    }

    public List<ProjectBudget> getProjectBudgetAll() {
        logger.debug("..getProjectBudgetAll");
        List<ProjectBudget> list = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT  `proj_id`, `budp_id`, `prob_req_budg`, `prob_appr_budg` ");
            sql.append(" FROM `project_budget`");

            pstm = conn.prepareStatement(sql.toString());
            logger.info("pstm ::==" + pstm.toString());
            rs = pstm.executeQuery();
            list = new ArrayList<ProjectBudget>();
            while (rs.next()) {
                list.add(getEntityProjectBudget(rs));
            }
        } catch (Exception e) {
            logger.error("Error getProjectBudgetAll :", e);
        }
        return list;
    }

    public int createProjectBudget(ProjectBudget pb) {
        logger.debug("..createProjectBudget");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT  `project_budget` INTO  ");
            sql.append(" ( `proj_id`, `budp_id`, `prob_req_budg`, `prob_appr_budg`) ");
            sql.append(" VALUES (?,?,?,?)");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, pb.getProjId());
            pstm.setString(2, pb.getBudpId());
            pstm.setString(3, pb.getProbReqBudg());
            pstm.setString(4, pb.getProbApprBudg());
            logger.info("pstm ::==" + pstm.toString());
            exe = pstm.executeUpdate();

        } catch (Exception e) {
            logger.error("Error saveProjectBudget:" + e.getMessage());
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public int updateProjectBudget(ProjectBudget pb) {
        logger.debug("..updateProjectBudget");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE `project_budget` SET ");
            sql.append(" `prob_appr_budg`=? ");
            sql.append(" WHERE =?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, pb.getProbApprBudg());
            pstm.setString(2, pb.getProjId());
            logger.info("pstm ::==" + pstm.toString());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("updateProjectBudget error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public String getConditionBuilder(ProjectBudget pb) {
        logger.debug("..getConditionBuilder");
        StringBuilder sql = new StringBuilder(" WHERE 1=1 ");
        try {
            if (!"".equals(CharacterUtil.removeNull(pb.getProjId()))) {
                sql.append(" and proj_id='" + pb.getProjId() + "'");
            }
            if (!"".equals(CharacterUtil.removeNull(pb.getBudpId()))) {
                sql.append(" and budp_id='" + pb.getBudpId() + "'");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sql.toString();
    }

    private ProjectBudget getEntityProjectBudget(ResultSet rs) throws SQLException {
        ProjectBudget pb = new ProjectBudget();

        pb.setBudpId(rs.getString("budp_id"));
        pb.setProbId(rs.getString("prob_id"));
        pb.setProjId(rs.getString("proj_id"));
        pb.setProbReqBudg(rs.getString("prob_req_budg"));
        pb.setProbApprBudg(rs.getString("prob_appr_budg"));

        return pb;
    }

    public int getCountProjectBudget(String conditionBuilder) {
        logger.debug("..getCountProjectBudget");
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int countProjectBudget = 0;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder("SELECT COUNT(*) as cnt FROM `project_budget` pb");
            if (conditionBuilder != null) {
                sql.append(conditionBuilder);
            }
            pstm = conn.prepareStatement(sql.toString());
            rs = pstm.executeQuery();
            if (rs.next()) {
                countProjectBudget = rs.getInt("cnt");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getCountProjectBudget error", e);
        } finally {
            this.close(pstm, rs);
        }
        return countProjectBudget;
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
    
}
