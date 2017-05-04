package com.ddpms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.ddpms.db.DbConnection;
import com.ddpms.model.Department;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDao {
    
    final static Logger logger = Logger.getLogger(DepartmentDao.class);
    
    private Connection conn = null;
    
    public Department getDepartment(int depId) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Department department = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT `dep_id`, `dep_name`, `modified_date`, `modified_by` FROM `department`");
            sql.append(" WHERE dep_id = ?");
            
            pstm = conn.prepareStatement(sql.toString());
            pstm.setInt(1, depId);
            
            rs = pstm.executeQuery();
            if(rs.next()){
                department = this.getEntityDepartment(rs);
            }
        } catch (Exception e) {
            logger.error("getDepartment error", e);
        } finally {
            this.close(pstm, rs);
        }
        return department;
    }
    
    public List<Department> getDepartmentAll() {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Department> departmentList = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT `dep_id`, `dep_name`, `modified_date`, `modified_by` FROM `department`");
            
            pstm = conn.prepareStatement(sql.toString());            
            rs = pstm.executeQuery();
            
            departmentList = new ArrayList<Department>();
            while(rs.next()){
                departmentList.add(this.getEntityDepartment(rs));
            }
        } catch (Exception e) {
            logger.error("getDepartmentAll error", e);
        } finally {
            this.close(pstm, rs);
        }
        return departmentList;
    }
    
    private Department getEntityDepartment(ResultSet rs) throws SQLException{
        Department department = new Department();
        department.setDepId(rs.getString("dep_id"));
        department.setDepName(rs.getString("dep_name"));
        department.setModifiedBy(rs.getString("modified_by"));
        department.setModifiedDate(rs.getString("modified_date"));
        return department;
    }
    
    public void close(PreparedStatement pstm, ResultSet rs) {
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
            logger.error("close db error", ex);
        }
    }
    
}

