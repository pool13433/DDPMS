/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ddpms.dao;

import static com.ddpms.dao.PlanDao.logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.ddpms.db.DbConnection;
import com.ddpms.model.Employee;
import com.ddpms.model.Plan;
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
        logger.info(" getEmployee ..");
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
            logger.info("sql ::=="+sql.toString());
            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, username);
            pstm.setString(2, password);
            
            rs = pstm.executeQuery();
            if(rs.next()){
                user = this.getEntityEmployee(rs);
            }
        } catch (Exception e) {
            logger.error("getEmployee error", e);
        } finally {
            this.close(pstm, rs);
        }
        return user;
    }
    
    public Employee getEmployee(int empId) {
        logger.info(" getEmployee ..");
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Employee user = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT `emp_id`, `emp_code`,  `username`, `password`,`emp_fname`, `emp_lname`,  ");
            sql.append(" `emp_email`,`emp_mobile`, `gender`, `title`, `dept_id`, status, `modified_date`, `modified_by` ");
            sql.append(" FROM `employee`  ");
            sql.append(" WHERE emp_id = ?");
            logger.info("sql ::=="+sql.toString());
            pstm = conn.prepareStatement(sql.toString());
            pstm.setInt(1, empId);
            
            rs = pstm.executeQuery();
            if(rs.next()){
                user = this.getEntityEmployee(rs);
            }
        } catch (Exception e) {
            logger.error("getEmployee error", e);
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
    
    public int updatePassword(String passsword,int modifiedBy,int id) {
        logger.debug("..updatePassword");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE `employee` SET ");
            sql.append(" `password`=md5(?), `modified_by`=?, ");
            sql.append(" `modified_date`=NOW() ");
            sql.append(" WHERE `emp_id`=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, passsword);
            pstm.setInt(2,modifiedBy);
            pstm.setInt(3, id);
            logger.info("pstm ::==" + pstm.toString());
            exe = pstm.executeUpdate();
        } catch (Exception e) {            
            logger.error("updatePassword error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }
    
    private Employee getEntityEmployee(ResultSet rs) throws SQLException{
        Employee employee = new Employee();
        employee.setDepId(rs.getInt("dept_id"));
        employee.setEmpCode(rs.getString("emp_code"));
        employee.setEmpEmail(rs.getString("emp_email"));
        employee.setEmpFname(rs.getString("emp_fname"));
        employee.setEmpId(rs.getInt("emp_id"));
        employee.setEmpLname(rs.getString("emp_lname"));
        employee.setEmpMobile(rs.getString("emp_mobile"));
        employee.setGender(rs.getString("gender"));
        employee.setModifiedBy(rs.getString("modified_date"));
        employee.setModifiedDate(rs.getString("modified_date"));
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

    public int updateProfile(Employee employee) {
        logger.debug("..updateProfile");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE `employee` SET ");
            sql.append(" `emp_fname`=?,`emp_lname`=?,`emp_email`=?,");
            sql.append(" `emp_mobile`=?,`gender`=?,`title`=?,");
            sql.append(" `dept_id`=?,`modified_date`=NOW(),`modified_by`=? ");
            sql.append(" WHERE `emp_id`=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, employee.getEmpFname());
            pstm.setString(2, employee.getEmpLname());
            pstm.setString(3, employee.getEmpEmail());
            pstm.setString(4, employee.getEmpMobile());
            pstm.setString(5, employee.getGender());
            pstm.setString(6, employee.getTitle());
            pstm.setInt(7, employee.getDepId());
            pstm.setString(8, employee.getModifiedBy());
            pstm.setInt(9, employee.getEmpId());
            logger.info("pstm ::==" + pstm.toString());
            exe = pstm.executeUpdate();
        } catch (Exception e) {            
            logger.error("updateProfile error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }
    
}

