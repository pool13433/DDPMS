
package com.ddpms.dao;

import com.ddpms.db.DbConnection;
import com.ddpms.model.Config;
import com.ddpms.model.ProjectHistory;
import com.ddpms.util.CharacterUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class ProjectHistoryDao {
    final static Logger logger = Logger.getLogger(ProjectHistoryDao.class);

    private Connection conn = null;

    public List<ProjectHistory> getProjectHistory(ProjectHistory ph) {
        List<ProjectHistory> list = new ArrayList<ProjectHistory>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT  `his_id`, (SELECT p.proj_name from project p where p.proj_id = h.proj_id) as `proj_id`, `status`, `modified_date`, `remarks`, ");
            sql.append(" (SELECT e.emp_fname FROM employee e WHERE e.emp_id = h.modified_by ) as `modified_by` ");
            sql.append(" FROM `project_history` h ");
            sql.append(getConditionBuilder(ph));
           
            pstm = conn.prepareStatement(sql.toString());
            logger.info("pstm ::==" + pstm.toString());
            rs = pstm.executeQuery();

            while (rs.next()) {
                list.add(getEntityProjectHistory(rs));
            }
        } catch (Exception e) {
            logger.error("getProjectHistory Error");
            e.printStackTrace();
        }finally {
            this.close(pstm, rs);
        }
        return list;
    }
    
    private ProjectHistory getEntityProjectHistory(ResultSet rs) throws SQLException {
        ProjectHistory ph = new ProjectHistory();
        ph.setHisId(rs.getString("his_id"));
        ph.setProjId(rs.getString("proj_id"));
        ph.setStatus(rs.getString("status"));
        ph.setRemarks(rs.getString("remarks"));
        ph.setModifiedDate(rs.getString("modified_date"));
        ph.setModifiedBy(rs.getString("modified_by"));

        return ph;
    }
    
    public String getConditionBuilder(ProjectHistory ph) {
        logger.debug("..getConditionBuilder");
        StringBuilder sql = new StringBuilder(" WHERE 1=1 ");
        try {
            if (!"".equals(CharacterUtil.removeNull(ph.getProjId()))) {
                sql.append(" and proj_id='" + ph.getProjId() + "'");
            }
            if (!"".equals(CharacterUtil.removeNull(ph.getStatus()))) {
                sql.append(" and status='" + ph.getStatus() + "'");
            }
            
            sql.append(" order by his_id ,`modified_date` desc ");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sql.toString();
    }
    
    public int createProjectHistory(ProjectHistory ph) {
        logger.debug("..createProjectHistory");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO project_history ");
            sql.append(" ( `proj_id`, `status`, `modified_date`, `modified_by`, `remarks`) ");
            sql.append(" VALUES (?,?,NOW(),?,?)");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, ph.getProjId());
            pstm.setString(2, ph.getStatus());
            pstm.setString(3, ph.getModifiedBy());
            pstm.setString(4, ph.getRemarks());

            logger.info("pstm ::==" + pstm.toString());
            exe = pstm.executeUpdate();

        } catch (Exception e) {
            logger.error("Error saveProjectHistory:", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }
    
    public List<ProjectHistory> getSumProjectStatus(){
        List<ProjectHistory> list = new ArrayList<ProjectHistory>();
        PreparedStatement pstm = null;
        ResultSet rs = null;        
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            ConfigDao confDao = new ConfigDao();
            List<Config> statusConfig = confDao.getConfigList("project_status");
            for(int i=0 ; i<statusConfig.size() ; i++){
                list.add(this.calSumProjectByStatus(statusConfig.get(i).getConfName()));
            }
        } catch (Exception e) {
            
        }
        return list;
    }
        
    public ProjectHistory calSumProjectByStatus(String statusValue){
        ProjectHistory history = new ProjectHistory();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            
            sql.append(" SELECT COUNT(`status`) as count from project_history WHERE `his_id` IN ( ");
            sql.append(" SELECT MAX(`his_id`) as historyID FROM `project_history` GROUP BY proj_id ");
            sql.append(" ) and `status`='"+statusValue+"'" );
            
            pstm = conn.prepareStatement(sql.toString());
            logger.info("pstm ::==" + pstm.toString());
            rs = pstm.executeQuery();

            while (rs.next()) {
                history.setSumProjectStatus(rs.getString("count"));
                history.setStatus(statusValue);
            }
        } catch (Exception e) {
            logger.error("calSumProjectByStatus"+e);
        }
        return history;
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
