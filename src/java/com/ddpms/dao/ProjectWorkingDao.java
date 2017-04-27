
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
    
    public List<ProjectWorking> getProjectWorking(ProjectWorking pw, int limit, int offset){
        logger.debug("..getProjectWorking");
        List<ProjectWorking> pwList = new ArrayList<ProjectWorking>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT `projw_id`, (select p.proj_name from project p where pw.proj_id=p.proj_id) as `proj_id`, `budget_year`, `budget_request`,  ");
            sql.append(" `budget_response`, `budget_usage`, `modified_date`, `modified_by` ");
            sql.append(" FROM `project_working` pw ");
            sql.append(getConditionBuilder(pw));   
            if(offset != 0){
                sql.append(" limit ").append(limit).append(" offset ").append(offset);
            }            
            
            pstm = conn.prepareStatement(sql.toString());
            logger.info("pstm ::=="+pstm.toString());
            rs = pstm.executeQuery();
            
            while (rs.next()) {                
                pwList.add(getEntityProjectWorking(rs));
            }
        } catch (Exception e) {
            logger.error("Error getProjectWorking :"+e.getMessage());
        }
        return pwList;
    }
    public int createProjectWorking(ProjectWorking pw){
         logger.debug("..createProjectWorking");
         int exe = 0;
         PreparedStatement pstm = null;
         try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO project_working ");
            sql.append(" (`proj_id`, `budget_year`, `budget_request`, `budget_response`, `budget_usage`, `modified_date`, `modified_by` ) ");
            sql.append(" VALUES (?,?,?,?,?,NOW(),?)");
            
            pstm = conn.prepareStatement(sql.toString());     
            pstm.setString(1, pw.getProjId());
            pstm.setString(2, pw.getBudgetYear());
            pstm.setString(3, pw.getBudgetRequest());
            pstm.setString(4, pw.getBudgetResponse());
            pstm.setString(5, pw.getBudgetUsage());
            pstm.setString(6, pw.getModifiedBy());
            logger.info("pstm ::=="+pstm.toString());
            exe = pstm.executeUpdate();
             
         } catch (Exception e) {
             logger.error("Error saveProjectWorking:"+e.getMessage());
         }finally {
            this.close(pstm, null);
        }
        return exe;
    }
     
    public int updateProjectWorking(ProjectWorking pw){
        logger.debug("..updateProjectWorking");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE `project_working` SET ");            
            sql.append(" `proj_id`=?, `budget_year`=?,`budget_request`=?,`budget_response`=?, ");
            sql.append(" `budget_usage`=?, `modified_by`=?, ");
            sql.append(" `modified_date`=NOW() ");
            sql.append(" WHERE `projw_id`=?");
            
            pstm = conn.prepareStatement(sql.toString());     
            pstm.setString(1, pw.getProjId());
            pstm.setString(2, pw.getBudgetYear());
            pstm.setString(3, pw.getBudgetRequest());
            pstm.setString(4, pw.getBudgetResponse());
            pstm.setString(5, pw.getBudgetUsage());
            pstm.setString(6, pw.getModifiedBy());
            pstm.setString(7, pw.getProjwId());   
            logger.info("pstm ::=="+pstm.toString());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("updateProjectWorking error", e);
        }finally {
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
            logger.info("pstm ::=="+pstm.toString());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("deleteProjectWorking error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }
    
    public String getConditionBuilder(ProjectWorking pw){
        logger.debug("..getConditionBuilder");
        StringBuilder sql = new StringBuilder(" WHERE 1=1 ");
        try {  
            if(!"".equals(CharacterUtil.removeNull(pw.getProjwId()))){
               sql.append(" and projw_id='"+pw.getProjwId()+"'"); 
            }
            if(!"".equals(CharacterUtil.removeNull(pw.getProjId()))){
               sql.append(" and proj_id='"+pw.getProjId()+"'"); 
            }
            if(!"".equals(CharacterUtil.removeNull(pw.getBudgetYear()))){
               sql.append(" and budget_year='"+pw.getBudgetYear()+"'"); 
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sql.toString();
    }
    
    private ProjectWorking getEntityProjectWorking(ResultSet rs) throws SQLException {
        logger.debug("..getEntityProjectWorking");
        ProjectWorking pw = new ProjectWorking();
        
        pw.setProjwId(rs.getString("projw_id"));
        pw.setProjId(rs.getString("proj_id"));
        pw.setBudgetYear(rs.getString("budget_year"));
        pw.setBudgetRequest(rs.getString("budget_request"));
        pw.setBudgetResponse(rs.getString("budget_response"));
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
    
    
}
