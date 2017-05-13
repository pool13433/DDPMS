package com.ddpms.dao;

import com.ddpms.db.DbConnection;
import com.ddpms.model.ProjectWorking;
import com.ddpms.util.CharacterUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class ProjectWorkingDao {

    final static Logger logger = Logger.getLogger(ProjectWorkingDao.class);

    private Connection conn = null;

    private final String STR_TO_DATE = " '%d-%m-%Y' ";
    private final String DATE_TO_STR = " '%d-%m-%Y' ";

    public List<ProjectWorking> getProjectWorking(ProjectWorking pw, int limit, int offset) {
        logger.debug("..getProjectWorking");
        List<ProjectWorking> pwList = new ArrayList<ProjectWorking>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT `projw_id`, (select p.proj_name from project p where pw.proj_id=p.proj_id) as `proj_id`, `budget_year`,"
                    + " `budget_request_m1`, `budget_request_m2`, `budget_request_m3`, `budget_request_m4`,"
                    + " `budget_request_m5`, `budget_request_m6`, `budget_request_m7`, `budget_request_m8`, "
                    + " `budget_request_m9`, `budget_request_m10`, `budget_request_m11`, `budget_request_m12`,"
                    + " `budget_approve_m1`, `budget_approve_m2`, `budget_approve_m3`, `budget_approve_m4`, "
                    + " `budget_approve_m5`, `budget_approve_m6`, `budget_approve_m7`, `budget_approve_m8`, "
                    + " `budget_approve_m9`, `budget_approve_m10`, `budget_approve_m11`, `budget_approve_m12`, ");
            sql.append(" `budget_usage`, DATE_FORMAT(modified_date," + DATE_TO_STR + ") as modified_date, `modified_by` ");
            sql.append(" FROM `project_working` pw ");
            sql.append(getConditionBuilder(pw));
            if (offset != 0) {
                sql.append(" limit ").append(limit).append(" offset ").append(offset);
            }

            pstm = conn.prepareStatement(sql.toString());
            logger.info("pstm ::==" + pstm.toString());
            rs = pstm.executeQuery();

            while (rs.next()) {
                pwList.add(getEntityProjectWorking(rs));
            }
        } catch (Exception e) {
            logger.error("Error getProjectWorking :" + e.getMessage());
        } finally {
            this.close(pstm, rs);
        }
        return pwList;
    }

    public int createProjectWorking(ProjectWorking pw) {
        logger.debug("..createProjectWorking");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO project_working ");
            sql.append(" (`proj_id`, `budget_year`, `budget_usage`,"
                    + " `budget_request_m1`, `budget_request_m2`, `budget_request_m3`, `budget_request_m4`, "
                    + " `budget_request_m5`, `budget_request_m6`, `budget_request_m7`, `budget_request_m8`, "
                    + " `budget_request_m9`, `budget_request_m10`, `budget_request_m11`, `budget_request_m12`, "
                    + " `budget_approve_m1`, `budget_approve_m2`, `budget_approve_m3`, `budget_approve_m4`, "
                    + "`budget_approve_m5`, `budget_approve_m6`, `budget_approve_m7`, `budget_approve_m8`,"
                    + " `budget_approve_m9`, `budget_approve_m10`, `budget_approve_m11`, `budget_approve_m12`, "
                    + " `modified_date`, `modified_by` ) ");
            sql.append(" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?)");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, pw.getProjId());
            pstm.setString(2, pw.getBudgetYear());
            pstm.setString(3, pw.getBudgetUsage());

            pstm.setString(4, pw.getBudgetRequestM1());
            pstm.setString(5, pw.getBudgetRequestM2());
            pstm.setString(6, pw.getBudgetRequestM3());
            pstm.setString(7, pw.getBudgetRequestM4());
            pstm.setString(8, pw.getBudgetRequestM5());
            pstm.setString(9, pw.getBudgetRequestM6());
            pstm.setString(10, pw.getBudgetRequestM7());
            pstm.setString(11, pw.getBudgetRequestM8());
            pstm.setString(12, pw.getBudgetRequestM9());
            pstm.setString(13, pw.getBudgetRequestM10());
            pstm.setString(14, pw.getBudgetRequestM11());
            pstm.setString(15, pw.getBudgetRequestM12());
            pstm.setString(16, pw.getBudgetApproveM1());
            pstm.setString(17, pw.getBudgetApproveM2());
            pstm.setString(18, pw.getBudgetApproveM3());
            pstm.setString(19, pw.getBudgetApproveM4());
            pstm.setString(20, pw.getBudgetApproveM5());
            pstm.setString(21, pw.getBudgetApproveM6());
            pstm.setString(22, pw.getBudgetApproveM7());
            pstm.setString(23, pw.getBudgetApproveM8());
            pstm.setString(24, pw.getBudgetApproveM9());
            pstm.setString(25, pw.getBudgetApproveM10());
            pstm.setString(26, pw.getBudgetApproveM11());
            pstm.setString(27, pw.getBudgetApproveM12());
            pstm.setString(28, pw.getModifiedBy());
            logger.info("pstm ::==" + pstm.toString());
            exe = pstm.executeUpdate();

        } catch (Exception e) {
            logger.error("Error saveProjectWorking:", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public int updateProjectWorking(ProjectWorking pw) {
        logger.debug("..updateProjectWorking");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE `project_working` SET ");
            sql.append(" `budget_request_m1`=?,`budget_request_m2`=?,`budget_request_m3`=?,"
                     + " `budget_request_m4`=?,`budget_request_m5`=?,`budget_request_m6`=?,"
                     + " `budget_request_m7`=?,`budget_request_m8`=?,`budget_request_m9`=?,"
                     + " `budget_request_m10`=?,`budget_request_m11`=?,`budget_request_m12`=?,");
            sql.append(" `budget_usage`=?, `modified_by`=?, ");
            sql.append(" `modified_date`=NOW() ");
            sql.append(" WHERE `proj_id`=? and `budget_year`=?");

            pstm = conn.prepareStatement(sql.toString()); 
            
            pstm.setString(1, pw.getBudgetRequestM1());
            pstm.setString(2, pw.getBudgetRequestM2());
            pstm.setString(3, pw.getBudgetRequestM3());
            pstm.setString(4, pw.getBudgetRequestM4());
            pstm.setString(5, pw.getBudgetRequestM5());
            pstm.setString(6, pw.getBudgetRequestM6());
            pstm.setString(7, pw.getBudgetRequestM7());
            pstm.setString(8, pw.getBudgetRequestM8());
            pstm.setString(9, pw.getBudgetRequestM9());
            pstm.setString(10, pw.getBudgetRequestM10());
            pstm.setString(11, pw.getBudgetRequestM11());
            pstm.setString(12, pw.getBudgetRequestM12());
          
            pstm.setString(13, pw.getBudgetUsage());
            pstm.setString(14, pw.getModifiedBy());
            pstm.setString(15, pw.getProjId());
            pstm.setString(16, pw.getBudgetYear());

            logger.info("pstm ::==" + pstm.toString());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("updateProjectWorking error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public int deleteProjectWorking(String id) {
        logger.debug("..deleteProjectWorking");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" DELETE FROM `project_working` WHERE projw_id=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, id);
            logger.info("pstm ::==" + pstm.toString());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("deleteProjectWorking error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public String getConditionBuilder(ProjectWorking pw) {
        logger.debug("..getConditionBuilder");
        StringBuilder sql = new StringBuilder(" WHERE 1=1 ");
        try {
            if (!"".equals(CharacterUtil.removeNull(pw.getProjwId()))) {
                sql.append(" and projw_id='" + pw.getProjwId() + "'");
            }
            if (!"".equals(CharacterUtil.removeNull(pw.getProjId()))) {
                sql.append(" and proj_id='" + pw.getProjId() + "'");
            }
            if (!"".equals(CharacterUtil.removeNull(pw.getBudgetYear()))) {
                sql.append(" and budget_year='" + pw.getBudgetYear() + "'");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sql.toString();
    }

    private ProjectWorking getEntityProjectWorking(ResultSet rs) throws SQLException {        
        ProjectWorking pw = new ProjectWorking();

        pw.setProjwId(rs.getString("projw_id"));
        pw.setProjId(rs.getString("proj_id"));
        pw.setBudgetYear(rs.getString("budget_year"));

        pw.setBudgetRequestM1(rs.getString("budget_request_m1"));
        pw.setBudgetRequestM2(rs.getString("budget_request_m2"));
        pw.setBudgetRequestM3(rs.getString("budget_request_m3"));
        pw.setBudgetRequestM4(rs.getString("budget_request_m4"));
        pw.setBudgetRequestM5(rs.getString("budget_request_m5"));
        pw.setBudgetRequestM6(rs.getString("budget_request_m6"));
        pw.setBudgetRequestM7(rs.getString("budget_request_m7"));
        pw.setBudgetRequestM8(rs.getString("budget_request_m8"));
        pw.setBudgetRequestM9(rs.getString("budget_request_m9"));
        pw.setBudgetRequestM10(rs.getString("budget_request_m10"));
        pw.setBudgetRequestM11(rs.getString("budget_request_m11"));
        pw.setBudgetRequestM12(rs.getString("budget_request_m12"));
      
        pw.setBudgetApproveM1(rs.getString("budget_approve_m1"));
        pw.setBudgetApproveM2(rs.getString("budget_approve_m2"));
        pw.setBudgetApproveM3(rs.getString("budget_approve_m3"));
        pw.setBudgetApproveM4(rs.getString("budget_approve_m4"));
        pw.setBudgetApproveM5(rs.getString("budget_approve_m5"));
        pw.setBudgetApproveM6(rs.getString("budget_approve_m6"));
        pw.setBudgetApproveM7(rs.getString("budget_approve_m7"));
        pw.setBudgetApproveM8(rs.getString("budget_approve_m8"));
        pw.setBudgetApproveM9(rs.getString("budget_approve_m9"));
        pw.setBudgetApproveM10(rs.getString("budget_approve_m10"));
        pw.setBudgetApproveM11(rs.getString("budget_approve_m11"));
        pw.setBudgetApproveM12(rs.getString("budget_approve_m12"));
        pw.setBudgetUsage(rs.getString("budget_usage"));
        pw.setModifiedDate(rs.getString("modified_date"));
        pw.setModifiedBy(rs.getString("modified_by"));

        return pw;
    }

    public int getCountProjectWorking(String conditionBuilder) {
        logger.debug("..getCountProjectWorking");
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int countProjectWorking = 0;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder("SELECT COUNT(*) as cnt FROM project_working pw");
            if (conditionBuilder != null) {
                sql.append(conditionBuilder);
            }
            pstm = conn.prepareStatement(sql.toString());
            rs = pstm.executeQuery();
            if (rs.next()) {
                countProjectWorking = rs.getInt("cnt");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getCountProjectWorking error", e);
        } finally {
            this.close(pstm, rs);
        }
        return countProjectWorking;
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

    public List<ProjectWorking> getProjectWorkingAll() {
        logger.debug("..getProjectWorkingAll");
        List<ProjectWorking> pwList = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT `projw_id`, (select p.proj_name from project p where pw.proj_id=p.proj_id) as `proj_id`, `budget_year`,"
                    + " `budget_request_m1`, `budget_request_m2`, `budget_request_m3`, `budget_request_m4`,"
                    + " `budget_request_m5`, `budget_request_m6`, `budget_request_m7`, `budget_request_m8`, "
                    + "`budget_request_m9`, `budget_request_m10`, `budget_request_m11`, `budget_request_m12`, "
                    + " `budget_approve_m1`, `budget_approve_m2`, `budget_approve_m3`, `budget_approve_m4`, "
                    + "`budget_approve_m5`, `budget_approve_m6`, `budget_approve_m7`, `budget_approve_m8`,"
                    + " `budget_approve_m9`, `budget_approve_m10`, `budget_approve_m11`, `budget_approve_m12`, ");
            sql.append(" `budget_usage`, DATE_FORMAT(modified_date," + DATE_TO_STR + ") as modified_date, `modified_by` ");
            sql.append(" FROM `project_working` pw ");

            pstm = conn.prepareStatement(sql.toString());
            logger.info("pstm ::==" + pstm.toString());
            rs = pstm.executeQuery();

            pwList = new ArrayList<ProjectWorking>();
            while (rs.next()) {
                pwList.add(getEntityProjectWorking(rs));
            }
        } catch (Exception e) {
            logger.error("Error getProjectWorkingAll :", e);
        } finally {
            this.close(pstm, rs);
        }
        return pwList;
    }

    public List<ProjectWorking> getProjectWorkingByProject(int projId) {
        logger.debug("..getProjectWorkingByProject");
        List<ProjectWorking> projectWorkingList = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT `projw_id`, (select p.proj_name from project p where pw.proj_id=p.proj_id) as `proj_id`, `budget_year`,"
                    + " `budget_request_m1`, `budget_request_m2`, `budget_request_m3`, `budget_request_m4`,"
                    + " `budget_request_m5`, `budget_request_m6`, `budget_request_m7`, `budget_request_m8`, "
                    + "`budget_request_m9`, `budget_request_m10`, `budget_request_m11`, `budget_request_m12`, "
                    + " `budget_approve_m1`, `budget_approve_m2`, `budget_approve_m3`, `budget_approve_m4`, "
                    + "`budget_approve_m5`, `budget_approve_m6`, `budget_approve_m7`, `budget_approve_m8`,"
                    + " `budget_approve_m9`, `budget_approve_m10`, `budget_approve_m11`, `budget_approve_m12`, ");
            sql.append(" `budget_usage`, DATE_FORMAT(pw.modified_date," + DATE_TO_STR + ") as modified_date, pw.modified_by ");
            sql.append(" FROM `project_working` pw");
            sql.append(" LEFT JOIN project p ON p.proj_id = pw.proj_id ");
            sql.append(" WHERE  pw.proj_id = ? ORDER BY pw.budget_year ASC ");
            logger.info("sql.toString() ::==" + sql.toString());
            pstm = conn.prepareStatement(sql.toString());
            pstm.setInt(1, projId);
            logger.info("pstm ::==" + pstm.toString());
            rs = pstm.executeQuery();
            projectWorkingList = new ArrayList<ProjectWorking>();
            while (rs.next()) {
                projectWorkingList.add(this.getEntityProjectWorking(rs));
            }
        } catch (Exception e) {
            logger.error("Error getProjectWorkingByProject :", e);
        } finally {
            this.close(pstm, rs);
        }
        return projectWorkingList;
    }

    public int updateProjectWorkingBudgetApprove(ProjectWorking pw) {
        logger.debug("..updateProjectWorkingBudgetApprove");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE `project_working` SET ");
            sql.append(" `budget_approve_m1`=?,`budget_approve_m2`=?,`budget_approve_m3`=?,"
                    + " `budget_approve_m4`=?,`budget_approve_m5`=?,`budget_approve_m6`=?,"
                    + "`budget_approve_m7`=?,`budget_approve_m8`=?,`budget_approve_m9`=?,"
                    + " `budget_approve_m10`=?,`budget_approve_m11`=?,`budget_approve_m12`=?, ");            
            sql.append(" `modified_by`=?,`modified_date`=NOW() ");
            sql.append(" WHERE `budget_year`=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, pw.getBudgetApproveM1());
            pstm.setString(2, pw.getBudgetApproveM2());
            pstm.setString(3, pw.getBudgetApproveM3());
            pstm.setString(4, pw.getBudgetApproveM4());
            pstm.setString(5, pw.getBudgetApproveM5());
            pstm.setString(6, pw.getBudgetApproveM6());
            pstm.setString(7, pw.getBudgetApproveM7());
            pstm.setString(8, pw.getBudgetApproveM8());
            pstm.setString(9, pw.getBudgetApproveM9());
            pstm.setString(10, pw.getBudgetApproveM10());
            pstm.setString(11, pw.getBudgetApproveM11());
            pstm.setString(12, pw.getBudgetApproveM12());
            pstm.setString(13, pw.getModifiedBy());
            pstm.setString(14, pw.getBudgetYear());
            logger.info("pstm ::==" + pstm.toString());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("updateProjectWorkingBudgetApprove error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

}
