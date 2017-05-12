package com.ddpms.dao;

import com.ddpms.db.DbConnection;
import com.ddpms.model.Project;
import com.ddpms.util.CharacterUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class ProjectDao {

    final static Logger logger = Logger.getLogger(ProjectDao.class);

    private Connection conn = null;

    public List<Project> getProject(Project p, int limit, int offset) {
        logger.debug("..getProject");
        List<Project> list = new ArrayList<Project>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT  `proj_id`, `proj_name`, `proj_details`, `proj_status`, ");
            sql.append(" (SELECT `plan_name`FROM `plan` p  WHERE p.plan_id=pj.plan_id ) as `plan_id`, (SELECT `budp_name` FROM `budget_plan` b WHERE b.budp_id=pj.budp_id) as `budp_id`, ");
            sql.append("  `modified_date`, `modified_by`,");
            sql.append(" (SELECT `prot_name` FROM `project_type` pt WHERE pt.prot_id=pj.prot_id) as `prot_id`, `proj_remark`, `proj_verify_date`, `proj_verify_by`, account_code ");
            sql.append(" FROM `project` pj");
            sql.append(getConditionBuilder(p));
            if (offset != 0) {
                sql.append(" limit ").append(limit).append(" offset ").append(offset);
            }

            pstm = conn.prepareStatement(sql.toString());
            logger.info("pstm ::==" + pstm.toString());
            rs = pstm.executeQuery();

            while (rs.next()) {
                list.add(getEntityProject(rs));
            }

        } catch (Exception e) {
            logger.error("getProject Error : " + e.getMessage());
        } finally {
            this.close(pstm, rs);
        }
        return list;
    }
    
    public List<Project> getProjectNormal(Project p, int limit, int offset) {
        logger.debug("..getProject");
        List<Project> list = new ArrayList<Project>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT  `proj_id`, `proj_name`, `proj_details`, `proj_status`, ");
            sql.append(" `plan_id`, `budp_id`, `modified_date`, `modified_by`,");
            sql.append(" `prot_id`, `proj_remark`, `proj_verify_date`, `proj_verify_by`, account_code ");
            sql.append(" FROM `project` pj");
            sql.append(getConditionBuilder(p));
            if (offset != 0) {
                sql.append(" limit ").append(limit).append(" offset ").append(offset);
            }

            pstm = conn.prepareStatement(sql.toString());
            logger.info("pstm ::==" + pstm.toString());
            rs = pstm.executeQuery();

            while (rs.next()) {
                list.add(getEntityProject(rs));
            }

        } catch (Exception e) {
            logger.error("getProject Error : " + e.getMessage());
        } finally {
            this.close(pstm, rs);
        }
        return list;
    }

    public List<Project> getProjectAll() {
        logger.debug("..getProjectAll");
        List<Project> list = new ArrayList<Project>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT  `proj_id`, `proj_name`, `proj_details`, `proj_status`, ");
            sql.append(" plan_id,budp_id, account_code, ");
            sql.append(" DATE_FORMAT(modified_date,'%d-%m-%Y') as modified_date, `modified_by` ");
            sql.append(" FROM `project` p ORDER BY proj_name ASC");
            pstm = conn.prepareStatement(sql.toString());
            logger.info("pstm ::==" + pstm.toString());
            rs = pstm.executeQuery();

            while (rs.next()) {
                list.add(getEntityProject(rs));
            }
        } catch (Exception e) {
            logger.error("getProjectAll Error", e);
        } finally {
            this.close(pstm, rs);
        }
        return list;
    }

    public int createProject(Project p) {
        logger.debug("..createProject");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO project ");
            sql.append(" (`proj_name`, `proj_details`, `proj_status`,`plan_id`, `budp_id`, prot_id, account_code, `modified_date`, `modified_by` ) ");
            sql.append(" VALUES (?,?,?,?,?,?,?,NOW(),?)");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, p.getProjName());
            pstm.setString(2, p.getProjDetails());
            pstm.setString(3, p.getProjStatus());
            pstm.setString(4, p.getPlanId());
            pstm.setString(5, p.getBudpId());
            pstm.setString(6, p.getProtId());
            pstm.setString(7, p.getAccountCode());
            pstm.setString(8, p.getModifiedBy());

            logger.info("pstm ::==" + pstm.toString());
            exe = pstm.executeUpdate();

        } catch (Exception e) {
            logger.error("Error saveProject:" + e.getMessage());
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public int updateProject(Project p) {
        logger.debug("..updateProject");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE `project` SET ");
            sql.append(" `proj_name`=?,`proj_details`=?,`proj_status`=?,`budp_id`=?,`plan_id`=?, ");
            sql.append(" `modified_by`=?, account_code=?, ");
            sql.append(" `modified_date`=NOW(), prot_id=? ");
            sql.append(" WHERE `proj_id`=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, p.getProjName());
            pstm.setString(2, p.getProjDetails());
            pstm.setString(3, p.getProjStatus());
            pstm.setString(4, p.getPlanId());
            pstm.setString(5, p.getBudpId());
            pstm.setString(6, p.getModifiedBy());
            pstm.setString(7, p.getAccountCode());
            pstm.setString(8, p.getProtId());
            pstm.setString(9, p.getProjId());
            logger.info("pstm ::==" + pstm.toString());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("updateProject error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public int deleteProject(String id) {
        logger.debug("..deleteProject");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" DELETE FROM `project` WHERE proj_id=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, id);
            logger.info("pstm ::==" + pstm.toString());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("deleteProject error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public String getConditionBuilder(Project p) {
        logger.debug("..getConditionBuilder");
        StringBuilder sql = new StringBuilder(" WHERE 1=1 ");
        try {
            if (!"".equals(CharacterUtil.removeNull(p.getProjName()))) {
                sql.append(" and proj_name LIKE '%" + p.getProjName() + "%'");
            }
            if (!"".equals(CharacterUtil.removeNull(p.getProjId()))) {
                sql.append(" and proj_id='" + p.getProjId() + "'");
            }
            if (!"".equals(CharacterUtil.removeNull(p.getProjDetails()))) {
                sql.append(" and proj_details='" + p.getProjDetails() + "'");
            }
            if (!"".equals(CharacterUtil.removeNull(p.getProjStatus()))) {
                sql.append(" and proj_status='" + p.getProjStatus() + "'");
            }
            if (!"".equals(CharacterUtil.removeNull(p.getPlanId()))) {
                sql.append(" and plan_id='" + p.getPlanId() + "'");
            }
            if (!"".equals(CharacterUtil.removeNull(p.getBudpId()))) {
                sql.append(" and budp_id='" + p.getBudpId() + "'");
            }
            if (!"".equals(CharacterUtil.removeNull(p.getProtId()))) {
                sql.append(" and prot_id='" + p.getProtId() + "'");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sql.toString();
    }

    private Project getEntityProject(ResultSet rs) throws SQLException {
        logger.debug("..getEntityProject");
        Project p = new Project();
        p.setProjId(rs.getString("proj_id"));
        p.setProjName(rs.getString("proj_name"));
        p.setProjDetails(rs.getString("proj_details"));
        p.setProjStatus(rs.getString("proj_status"));
        p.setPlanId(rs.getString("plan_id"));
        p.setBudpId(rs.getString("budp_id"));
        p.setModifiedDate(rs.getString("modified_date"));
        p.setModifiedBy(rs.getString("modified_by"));
        
        p.setProtId(rs.getString("prot_id"));
        p.setProjRemark(rs.getString("proj_remark"));
        p.setProjVerifyBy(rs.getString("proj_verify_by"));
        p.setProjVerifyDate(rs.getString("proj_verify_date"));
        p.setAccountCode(rs.getString("account_code"));

        return p;
    }

    public int getCountProject(String conditionBuilder) {
        logger.debug("..getCountProject");
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int countProject = 0;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder("SELECT COUNT(*) as cnt FROM Project p");
            if (conditionBuilder != null) {
                sql.append(conditionBuilder);
            }
            pstm = conn.prepareStatement(sql.toString());
            rs = pstm.executeQuery();
            if (rs.next()) {
                countProject = rs.getInt("cnt");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getCountProject error", e);
        } finally {
            this.close(pstm, rs);
        }
        return countProject;
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

    public Project getProject(int projId) {
        logger.debug("..getProject by projId");
        Project project = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT  `proj_id`, `proj_name`, `proj_details`, `proj_status`, ");
            sql.append(" plan_id,budp_id, ");
            sql.append(" DATE_FORMAT(modified_date,'%d-%m-%Y') as modified_date, `modified_by` ");
            sql.append(" FROM `project` p WHERE proj_id = ?");
            pstm = conn.prepareStatement(sql.toString());
            pstm.setInt(1, projId);
            logger.info("pstm ::==" + pstm.toString());
            rs = pstm.executeQuery();

            if (rs.next()) {
                project = getEntityProject(rs);
            }
        } catch (Exception e) {
            logger.error("getProject Error", e);
        } finally {
            this.close(pstm, rs);
        }
        return project;
    }

    public List<Project> getProjectByCriteria(Project criteria) {
        logger.debug("..getProjectByCriteria");
        List<Project> list = new ArrayList<Project>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT  `proj_id`, `proj_name`, `proj_details`, `proj_status`, ");
            sql.append(" `plan_id`, `budp_id`, `modified_date`, `modified_by`, ");
            sql.append(" `prot_id`, `proj_remark`, `proj_verify_date`, `proj_verify_by` , account_code ");
            sql.append(" FROM `project` ");
            sql.append(getConditionBuilder(criteria));

            pstm = conn.prepareStatement(sql.toString());
            logger.info("pstm ::==" + pstm.toString());
            rs = pstm.executeQuery();

            while (rs.next()) {
                list.add(getEntityProject(rs));
            }

        } catch (Exception e) {
            logger.error("getProjectByCriteria Error : " + e.getMessage());
        } finally {
            this.close(pstm, rs);
        }
        return list;
    }

    public List<Project> getProjectListHaveTaskAssign(Integer empId) {
        logger.debug("..getProjectListHaveTaskAssign");
        List<Project> list = new ArrayList<Project>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT  `proj_id`, `proj_name`, `proj_details`, `proj_status`, ");
            sql.append(" plan_id,budp_id, ");
            sql.append(" `prot_id`, `proj_remark`, `proj_verify_date`, `proj_verify_by`, ");
            sql.append(" DATE_FORMAT(modified_date,'%d-%m-%Y') as modified_date, `modified_by` ");
            sql.append(" FROM `project` p ");
            sql.append(" WHERE p.proj_id IN (SELECT proj_id  FROM `task_assign` WHERE task_userid = ?)");
            sql.append(" ORDER BY p.proj_name ASC");
            pstm = conn.prepareStatement(sql.toString());
            pstm.setInt(1, empId);
            logger.info("pstm ::==" + pstm.toString());
            rs = pstm.executeQuery();

            while (rs.next()) {
                list.add(getEntityProject(rs));
            }
        } catch (Exception e) {
            logger.error("getProjectListHaveTaskAssign Error", e);
        } finally {
            this.close(pstm, rs);
        }
        return list;
    }
}
