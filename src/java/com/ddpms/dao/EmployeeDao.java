/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ddpms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.ddpms.db.DbConnection;
import com.ddpms.model.Employee;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author POOL_LAPTOP
 */
public class EmployeeDao {
    
    final static Logger logger = Logger.getLogger(EmployeeDao.class);
    
    private Connection conn = null;
    
    public Employee getEmployee(String username, String password) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Employee user = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT `emp_id`, `emp_code`,  `username`, `password`,`emp_fname`, `emp_lname`,  ");
            sql.append(" `emp_email`,`emp_mobile`, `gender`, `title`, `dept_id`, status, `modified_date`, `modified_by` ");
            sql.append(" FROM `employee`  ");
            sql.append(" WHERE username = ? and password = md5(?)");
            
            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, username);
            pstm.setString(2, password);
            
            rs = pstm.executeQuery();
            if(rs.next()){
                user = this.getEntityEmployee(rs);
            }
        } catch (Exception e) {
            logger.error("getUser error", e);
        } finally {
            this.close(pstm, rs);
        }
        return user;
    }
    
    public List<Employee> getEmployeeListByRole(String role) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Employee> employeeList = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT `emp_id`, `emp_code`,  `username`, `password`,`emp_fname`, `emp_lname`,  ");
            sql.append(" `emp_email`,`emp_mobile`, `gender`, `title`, `dept_id`, status, `modified_date`, `modified_by` ");
            sql.append(" FROM `employee`  ");
            sql.append(" WHERE status = ?");
            
            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, role);            
            rs = pstm.executeQuery();
            
            employeeList = new ArrayList<Employee>();
            while(rs.next()){
                employeeList.add(this.getEntityEmployee(rs));
            }
        } catch (Exception e) {
            logger.error("getUser error", e);
        } finally {
            this.close(pstm, rs);
        }
        return employeeList;
    }
    
    private Employee getEntityEmployee(ResultSet rs) throws SQLException{
        Employee employee = new Employee();
        employee.setDept_id(rs.getInt("dept_id"));
        employee.setEmpCode(rs.getString("emp_code"));
        employee.setEmpEmail(rs.getString("emp_email"));
        employee.setEmpFname(rs.getString("emp_fname"));
        employee.setEmpId(rs.getInt("emp_id"));
        employee.setEmpLname(rs.getString("emp_lname"));
        employee.setEmpMobile(rs.getString("emp_mobile"));
        employee.setGender(rs.getString("gender"));
        employee.setModified_by(rs.getString("modified_date"));
        employee.setModified_date(rs.getString("modified_date"));
        employee.setPassword(rs.getString("password"));
        employee.setTitle(rs.getString("title"));
        employee.setUsername(rs.getString("username"));
        employee.setStatus(rs.getString("status"));
        return employee;
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

