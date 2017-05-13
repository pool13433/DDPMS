
package com.ddpms.dao;

import com.ddpms.db.DbConnection;
import com.ddpms.model.Strategic;
import com.ddpms.util.CharacterUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class StrategicDao {
final static Logger logger = Logger.getLogger(StrategicDao.class);
    
    private Connection conn = null;
    
    public List<Strategic> getStrategic(Strategic s, int limit, int offset){
        logger.debug("..getStrategic");
        List<Strategic> list = new ArrayList<Strategic>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT `stra_id`, `stra_name`, ");
            sql.append(" `modified_date`, (select e.username from employee e where e.emp_id=modified_by) as `modified_by` ");
            sql.append(" FROM `strategic` ");
            sql.append(getConditionBuilder(s));   
            if(offset != 0){
                sql.append(" limit ").append(limit).append(" offset ").append(offset);
            }            
            
            pstm = conn.prepareStatement(sql.toString());
            logger.info("pstm ::=="+pstm.toString());
            rs = pstm.executeQuery();
            
            while (rs.next()) {                
                list.add(getEntityStrategic(rs));
            }
        } catch (Exception e) {
            logger.error("Error getStrategic :"+e.getMessage());
        }
        return list;
    }
    public int createStrategic(Strategic s){
         logger.debug("..createStrategic");
         int exe = 0;
         PreparedStatement pstm = null;
         try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO strategic ");
            sql.append(" (`stra_name`, `modified_date`, `modified_by` ) ");
            sql.append(" VALUES (?,NOW(),?)");
            
            pstm = conn.prepareStatement(sql.toString()); 
            pstm.setString(1, s.getStraName());
            pstm.setString(2, s.getModifiedBy());
            logger.info("pstm ::=="+pstm.toString());
            exe = pstm.executeUpdate();
             
         } catch (Exception e) {
             logger.error("Error saveStrategic:"+e.getMessage());
         }finally {
            this.close(pstm, null);
        }
        return exe;
    }
     
    public int updateStrategic(Strategic s){
        logger.debug("..updateStrategic");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE `strategic` SET ");      
            sql.append(" `stra_name`=?, `modified_by`=?, ");
            sql.append(" `modified_date`=NOW() ");
            sql.append(" WHERE `stra_id`=?");
            
            pstm = conn.prepareStatement(sql.toString());     
            pstm.setString(1, s.getStraName());
            pstm.setString(2, s.getModifiedBy());
            pstm.setString(3, s.getStraId());
            logger.info("pstm ::=="+pstm.toString());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("updateStrategic error", e);
        }finally {
            this.close(pstm, null);
        }
        return exe;
    }
     
    public int deleteStrategic(String id) {
        logger.debug("..deleteStrategic");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" DELETE FROM `strategic` WHERE stra_id=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, id);
            logger.info("pstm ::=="+pstm.toString());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("deleteStrategic error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }
    
    public String getConditionBuilder(Strategic s){
        logger.debug("..getConditionBuilder");
        StringBuilder sql = new StringBuilder(" WHERE 1=1 ");
        try {            
            if(!"".equals(CharacterUtil.removeNull(s.getStraName()))){
               sql.append(" and stra_name LIKE '%"+s.getStraName()+"%'"); 
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sql.toString();
    }
    
    private Strategic getEntityStrategic(ResultSet rs) throws SQLException {        
        Strategic s = new Strategic();
        
        s.setStraId(rs.getString("stra_id"));
        s.setStraName(rs.getString("stra_name"));
        s.setModifiedDate(rs.getString("modified_date"));
        s.setModifiedBy(rs.getString("modified_by"));
        
        return s;
    }
    
    public int getCountStrategic(String conditionBuilder) {
        logger.debug("..getCountStrategic");
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int countStrategic = 0;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder("SELECT COUNT(*) as cnt FROM strategic s");
            if (conditionBuilder != null) {
                sql.append(conditionBuilder);
            }
            pstm = conn.prepareStatement(sql.toString());
            rs = pstm.executeQuery();
            if (rs.next()) {
                countStrategic = rs.getInt("cnt");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getCountStrategic error", e);
        } finally {
            this.close(pstm, rs);
        }
        return countStrategic;
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
