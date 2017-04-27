
package com.ddpms.dao;

import com.ddpms.db.DbConnection;
import com.ddpms.model.BudgetPlan;
import com.ddpms.util.CharacterUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class BudgetPlanDao {
    final static Logger logger = Logger.getLogger(BudgetPlanDao.class);
    
    private Connection conn = null;
    
    public List<BudgetPlan> getBudgetPlan(BudgetPlan bp, int limit, int offset){
        logger.debug("..getBudgetPlan");
        List<BudgetPlan> bpList = new ArrayList<BudgetPlan>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT `budp_id`, `budp_name`, `budp_year_begin`, `budp_year_end`, ");
            sql.append(" `modified_date`, `modified_by` ");
            sql.append(" FROM `budget_plan` ");
            sql.append(getConditionBuilder(bp));   
            if(offset != 0){
                sql.append(" limit ").append(limit).append(" offset ").append(offset);
            }            
            
            pstm = conn.prepareStatement(sql.toString());
            logger.info("pstm ::=="+pstm.toString());
            rs = pstm.executeQuery();
            
            while (rs.next()) {                
                bpList.add(getEntityBudgetPlan(rs));
            }
        } catch (Exception e) {
            logger.error("Error getBudgetPlan :"+e.getMessage());
        }
        return bpList;
    }
    public int createBudgetPlan(BudgetPlan bp){
         logger.debug("..createBudgetPlan");
         int exe = 0;
         PreparedStatement pstm = null;
         try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO budget_plan ");
            sql.append(" (`budp_name`, `budp_year_begin`, `budp_year_end`, `modified_date`, `modified_by` ) ");
            sql.append(" VALUES (?,?,?,NOW(),?)");
            
            pstm = conn.prepareStatement(sql.toString());     
            pstm.setString(1, bp.getBudpName());
            pstm.setString(2, bp.getBudpYearBegin());
            pstm.setString(3, bp.getBudpYearEnd());
            pstm.setString(4, bp.getModifiedBy());
            logger.info("pstm ::=="+pstm.toString());
            exe = pstm.executeUpdate();
             
         } catch (Exception e) {
             logger.error("Error saveBudgetPlan:"+e.getMessage());
         }finally {
            this.close(pstm, null);
        }
        return exe;
    }
     
    public int updateBudgetPlan(BudgetPlan bp){
        logger.debug("..updateBudgetPlan");
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
            pstm.setString(1, bp.getBudpName());
            pstm.setString(2, bp.getBudpYearBegin());
            pstm.setString(3, bp.getBudpYearEnd());
            pstm.setString(4, bp.getModifiedBy());
            pstm.setString(5, bp.getBudpId());   
            logger.info("pstm ::=="+pstm.toString());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("updateBudgetPlan error", e);
        }finally {
            this.close(pstm, null);
        }
        return exe;
    }
     
    public int deleteBudgetPlan(String id) {
        logger.debug("..deleteBudgetPlan");
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
            logger.error("deleteBudgetPlan error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }
    
    public String getConditionBuilder(BudgetPlan bp){
        logger.debug("..getConditionBuilder");
        StringBuilder sql = new StringBuilder(" WHERE 1=1 ");
        try {            
            if(!"".equals(CharacterUtil.removeNull(bp.getBudpName()))){
               sql.append(" and budp_name LIKE '%"+bp.getBudpName()+"%'"); 
            }
            if(!"".equals(CharacterUtil.removeNull(bp.getBudpId()))){
               sql.append(" and budp_id='"+bp.getBudpId()+"'"); 
            }
            if(!"".equals(CharacterUtil.removeNull(bp.getBudpYearBegin()))){
               sql.append(" and budp_year_begin='"+bp.getBudpYearBegin()+"'"); 
            }
            if(!"".equals(CharacterUtil.removeNull(bp.getBudpYearEnd()))){
               sql.append(" and budp_year_end='"+bp.getBudpYearEnd()+"'"); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sql.toString();
    }
    
    private BudgetPlan getEntityBudgetPlan(ResultSet rs) throws SQLException {
        logger.debug("..getEntityBudgetPlan");
        BudgetPlan bp = new BudgetPlan();
        
        bp.setBudpId(rs.getString("budp_id"));
        bp.setBudpName(rs.getString("budp_name"));
        bp.setBudpYearBegin(rs.getString("budp_year_begin"));
        bp.setBudpYearEnd(rs.getString("budp_year_end"));
        bp.setModifiedDate(rs.getString("modified_date"));
        bp.setModifiedBy(rs.getString("modified_by"));
        
        return bp;
    }
    
    public int getCountBudgetPlan(String conditionBuilder) {
        logger.debug("..getCountBudgetPlan");
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int countBudgetPlan = 0;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder("SELECT COUNT(*) as cnt FROM budget_plan bp");
            if (conditionBuilder != null) {
                sql.append(conditionBuilder);
            }
            pstm = conn.prepareStatement(sql.toString());
            rs = pstm.executeQuery();
            if (rs.next()) {
                countBudgetPlan = rs.getInt("cnt");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getCountBudgetPlan error", e);
        } finally {
            this.close(pstm, rs);
        }
        return countBudgetPlan;
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
