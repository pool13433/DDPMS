
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
    public int createProjectExpense(ProjectExpense bp){
         logger.debug("..createProjectExpense");
         int exe = 0;
         PreparedStatement pstm = null;
         try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO budget_plan ");
            sql.append(" (`budp_name`, `budp_year_begin`, `budp_year_end`, `modified_date`, `modified_by` ) ");
            sql.append(" VALUES (?,?,?,NOW(),?)");
            
            pstm = conn.prepareStatement(sql.toString());     
            //pstm.setString(1, bp.getBudpName());
            logger.info("pstm ::=="+pstm.toString());
            exe = pstm.executeUpdate();
             
         } catch (Exception e) {
             logger.error("Error saveProjectExpense:"+e.getMessage());
         }finally {
            this.close(pstm, null);
        }
        return exe;
    }
     
    public int updateProjectExpense(ProjectExpense bp){
        logger.debug("..updateProjectExpense");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE `budget_plan` SET ");            
            sql.append(" `budp_name`=?,`budp_year_begin`=?,`budp_year_end`=?, ");
            sql.append(" `modified_by`=?, ");
            sql.append(" `modified_date`=NOW() ");
            sql.append(" WHERE `budp_id`=?");
            
            pstm = conn.prepareStatement(sql.toString());     
            //pstm.setString(1, bp.getBudpName());
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
            sql.append(" DELETE FROM `budget_plan` WHERE budp_id=?");

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
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sql.toString();
    }
    
    private ProjectExpense getEntityProjectExpense(ResultSet rs) throws SQLException {
        logger.debug("..getEntityProjectExpense");
        ProjectExpense bp = new ProjectExpense();
        /*
        bp.setBudpId(rs.getString("budp_id"));
        bp.setBudpName(rs.getString("budp_name"));
        bp.setBudpYearBegin(rs.getString("budp_year_begin"));
        bp.setBudpYearEnd(rs.getString("budp_year_end"));
        bp.setModifiedDate(rs.getString("modified_date"));
        bp.setModifiedBy(rs.getString("modified_by"));
        */
        
        
        return bp;
    }
    
    public int getCountProjectExpense(String conditionBuilder) {
        logger.debug("..getCountProjectExpense");
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int countProjectExpense = 0;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder("SELECT COUNT(*) as cnt FROM budget_plan bp");
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
