
package com.ddpms.dao;

import com.ddpms.db.DbConnection;
import com.ddpms.model.ProjectExpense;
import com.ddpms.util.CharacterUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class ProjectExpenseDao {
    final static Logger logger = Logger.getLogger(ProjectExpenseDao.class);
    
    private Connection conn = null;
    public List<ProjectExpense> getProjectExpense(ProjectExpense pe, int limit, int offset){
        logger.debug("..getProjectExpense");
        List<ProjectExpense> bpList = new ArrayList<ProjectExpense>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT `exp_id`, `proj_id`, `exp_desc`, `exp_amount`, `exp_voch`, `exp_pr`, ");
            sql.append(" `receipt`, `exp_date`, `vender`, `modified_date`, `modified_by` ");
            sql.append(" FROM `project_expense` ");
            sql.append(getConditionBuilder(pe));   
            if(offset != 0){
                sql.append(" limit ").append(limit).append(" offset ").append(offset);
            }            
            
            pstm = conn.prepareStatement(sql.toString());
            logger.info("pstm ::=="+pstm.toString());
            rs = pstm.executeQuery();
            
            while (rs.next()) {                
                bpList.add(getEntityProjectExpense(rs));
            }
        } catch (Exception e) {
            logger.error("Error getProjectExpense :"+e.getMessage());
        }
        return bpList;
    }
    public int createProjectExpense(ProjectExpense pe){
         logger.debug("..createProjectExpense");
         int exe = 0;
         PreparedStatement pstm = null;
         try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO project_expense ");
            sql.append(" (`proj_id`, `exp_desc`, `exp_amount`, `exp_voch`, `exp_pr`, "
                    + "`receipt`, `exp_date`, `vender`, `modified_date`, `modified_by` ) ");
            sql.append(" VALUES (?,?,?,?,?,?,?,?,NOW(),?)");
            
            pstm = conn.prepareStatement(sql.toString());     
            pstm.setString(1, pe.getProjId());
            pstm.setString(2, pe.getExpDesc());
            pstm.setString(3, pe.getExpAmount());
            pstm.setString(4, pe.getExpVoch());
            pstm.setString(5, pe.getExpPr());
            pstm.setString(6, pe.getReceipt());
            pstm.setString(7, pe.getExpDate());
            pstm.setString(8, pe.getVender());
            pstm.setString(9, pe.getModifiedBy());
            logger.info("pstm ::=="+pstm.toString());
            exe = pstm.executeUpdate();
             
         } catch (Exception e) {
             logger.error("Error saveProjectExpense:"+e.getMessage());
         }finally {
            this.close(pstm, null);
        }
        return exe;
    }
     
    public int updateProjectExpense(ProjectExpense pe){
        logger.debug("..updateProjectExpense");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE `project_expense` SET ");            
            sql.append(" `proj_id`=?,`exp_desc`=?,`exp_amount`=?,`exp_voch`=? ");
            sql.append(" `exp_pr`=?,`receipt`=?,`exp_date`=?,`vender`=?, `modified_by`=?, ");
            sql.append(" `modified_date`=NOW() ");
            sql.append(" WHERE `exp_id`=?");
            
            pstm = conn.prepareStatement(sql.toString());     
            pstm.setString(1, pe.getProjId());
            pstm.setString(2, pe.getExpDesc());
            pstm.setString(3, pe.getExpAmount());
            pstm.setString(4, pe.getExpVoch());
            pstm.setString(5, pe.getExpPr());
            pstm.setString(6, pe.getReceipt());
            pstm.setString(7, pe.getExpDate());
            pstm.setString(8, pe.getVender());
            pstm.setString(9, pe.getModifiedBy());
            pstm.setString(10, pe.getExpId());
            logger.info("pstm ::=="+pstm.toString());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("updateProjectExpense error", e);
        }finally {
            this.close(pstm, null);
        }
        return exe;
    }
     
    public int deleteProjectExpense(String id) {
        logger.debug("..deleteProjectExpense");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" DELETE FROM `project_expense` WHERE exp_id=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, id);
            logger.info("pstm ::=="+pstm.toString());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("deleteProjectExpense error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }
    
    public String getConditionBuilder(ProjectExpense pe){
        logger.debug("..getConditionBuilder");
        StringBuilder sql = new StringBuilder(" WHERE 1=1 ");
        try {            
            if (!"".equals(CharacterUtil.removeNull(pe.getProjId()))) {
                sql.append(" and proj_id='" + pe.getProjId() + "'");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sql.toString();
    }
    
    private ProjectExpense getEntityProjectExpense(ResultSet rs) throws SQLException {
        logger.debug("..getEntityProjectExpense");
        ProjectExpense pe = new ProjectExpense();
        
        pe.setExpId(rs.getString("exp_id"));
        pe.setProjId(rs.getString("proj_id"));
        pe.setExpDesc(rs.getString("exp_desc"));
        pe.setExpAmount(rs.getString("exp_amount"));
        pe.setExpDate(rs.getString("exp_date"));
        pe.setExpVoch(rs.getString("exp_voch"));
        pe.setExpPr(rs.getString("exp_pr"));
        pe.setReceipt(rs.getString("receipt"));
        pe.setVender(rs.getString("vender"));
        pe.setModifiedDate(rs.getString("modified_date"));
        pe.setModifiedBy(rs.getString("modified_by"));
        
        return pe;
    }
    
    public int getCountProjectExpense(String conditionBuilder) {
        logger.debug("..getCountProjectExpense");
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int countProjectExpense = 0;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder("SELECT COUNT(*) as cnt FROM project_expense pe");
            if (conditionBuilder != null) {
                sql.append(conditionBuilder);
            }
            pstm = conn.prepareStatement(sql.toString());
            rs = pstm.executeQuery();
            if (rs.next()) {
                countProjectExpense = rs.getInt("cnt");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getCountProjectExpense error", e);
        } finally {
            this.close(pstm, rs);
        }
        return countProjectExpense;
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
