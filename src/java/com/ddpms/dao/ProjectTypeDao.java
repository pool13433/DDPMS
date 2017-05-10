package com.ddpms.dao;

import com.ddpms.db.DbConnection;
import com.ddpms.model.ProjectType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class ProjectTypeDao {

    final static Logger logger = Logger.getLogger(ProjectTypeDao.class);

    private Connection conn = null;
    private final String STR_TO_DATE = " '%d-%m-%Y' ";
    private final String DATE_TO_STR = " '%d-%m-%Y' ";

    public ProjectType getProjectType(String protId) {
        ResultSet rs = null;
        PreparedStatement pstm = null;
        ProjectType projectType = null;
        try {
            conn = new DbConnection().open();
            String sql = "SELECT c.* FROM project_type c WHERE c.prot_id = ? ";
            //logger.info("sql ::=="+sql);
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, protId);
            rs = pstm.executeQuery();
            if (rs.next()) {
                projectType = this.getEntityProjectType(rs);
            }
        } catch (Exception e) {
            logger.error("getProjectType error", e);
        } finally {
            this.close(pstm, rs);
        }
        return projectType;
    }

    public List<ProjectType> getProjectTypeAll(int limit, int offset) {
        ResultSet rs = null;
        PreparedStatement pstm = null;
        List<ProjectType> projectTypeList = null;
        try {
            conn = new DbConnection().open();
            String sql = "SELECT `prot_id`, `prot_code`, `prot_name`, `prot_type`, dep_id, `modified_by` ";
            sql += " ,DATE_FORMAT(modified_date,"+DATE_TO_STR+") as modified_date  FROM project_type c ORDER BY c.prot_id limit " + limit + " offset " + offset;
            //logger.info("sql ::=="+sql);
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            projectTypeList = new ArrayList<ProjectType>();
            while (rs.next()) {
                projectTypeList.add(this.getEntityProjectType(rs));
            }
        } catch (Exception e) {
            logger.error("getProjectTypeAll error", e);
        } finally {
            this.close(pstm, rs);
        }
        return projectTypeList;
    }

    public int createProjectType(ProjectType projectType) {
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO `project_type` ");
            sql.append(" ( `prot_code`, `prot_name`, `prot_type`, dep_id, modified_date,modified_by ) ");
            sql.append(" VALUES ");
            sql.append(" (?,?,?,?,NOW(),?)");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, projectType.getProtCode());
            pstm.setString(2, projectType.getProtName());
            pstm.setString(3, projectType.getProtType());
            pstm.setString(4, projectType.getDepId());
            pstm.setString(5, projectType.getModifiedBy());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            logger.error("createProjectType error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public int updateProjectType(ProjectType projectType ){
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE `project_type` SET ");
            sql.append(" `prot_code`=?,`prot_name`=?,`prot_type`=?, dep_id=?, modified_date=NOW(),modified_by=? ");
            sql.append(" WHERE `prot_id`=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, projectType.getProtCode());
            pstm.setString(2, projectType.getProtName());
            pstm.setString(3, projectType.getProtType());
            pstm.setString(4, projectType.getDepId());
            pstm.setString(5, projectType.getModifiedBy());
            pstm.setString(6, projectType.getProtId());

            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("updateProjectType error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public int deleteProjectType(int protId) {
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" DELETE FROM `project_type` WHERE prot_id=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setInt(1, protId);
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("deleteProjectType error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
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
            logger.error("getUser error", ex);
        }
    }

    public int getCountProjectType() {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int countRecord = 0;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder("SELECT COUNT(*) as cnt FROM project_type c");
            pstm = conn.prepareStatement(sql.toString());
            rs = pstm.executeQuery();
            if (rs.next()) {
                countRecord = rs.getInt("cnt");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getCountProjectType error", e);
        } finally {
            this.close(pstm, rs);
        }
        return countRecord;
    }

    private ProjectType getEntityProjectType(ResultSet rs) throws SQLException {
        ProjectType projectType = new ProjectType();
        projectType.setProtCode(rs.getString("prot_code"));
        projectType.setProtId(rs.getString("prot_id"));
        projectType.setProtName(rs.getString("prot_name"));
        projectType.setProtType(rs.getString("prot_type"));       
        projectType.setDepId(rs.getString("dep_id")); 
        projectType.setModifiedDate(rs.getString("modified_date"));      
        projectType.setModifiedBy(rs.getString("modified_by"));      
        return projectType;
    }
}
