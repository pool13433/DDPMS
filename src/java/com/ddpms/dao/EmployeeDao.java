/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ddpms.dao;

import static com.ddpms.dao.ProjectTypeDao.logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.ddpms.db.DbConnection;
import com.ddpms.model.Employee;
import com.ddpms.model.ProjectType;
import com.ddpms.util.CharacterUtil;
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
            logger.info("sql ::==" + sql.toString());
            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, username);
            pstm.setString(2, password);

            rs = pstm.executeQuery();
            if (rs.next()) {
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
            logger.info("sql ::==" + sql.toString());
            pstm = conn.prepareStatement(sql.toString());
            pstm.setInt(1, empId);

            rs = pstm.executeQuery();
            if (rs.next()) {
                user = this.getEntityEmployee(rs);
            }
        } catch (Exception e) {
            logger.error("getEmployee error", e);
        } finally {
            this.close(pstm, rs);
        }
        return user;
    }

    public List<Employee> getEmployeeList(int limit, int offset, String sqlCondition) {
        logger.info(" getEmployeeList ..");
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Employee> userList = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT `emp_id`, `emp_code`,  `username`, `password`,`emp_fname`, `emp_lname`,  ");
            sql.append(" `emp_email`,`emp_mobile`, `gender`, `title`, `dept_id`, ");
            sql.append(" (SELECT dep_name FROM department d WHERE d.dep_id = e.dept_id ) as dep_name,");
            sql.append(" (SELECT emp_fname  FROM employee d WHERE d.emp_id = e.modified_by ) as modified_by,");
            sql.append(" status, `modified_date`, `modified_by` ");
            sql.append(" FROM `employee`  e");
            sql.append(sqlCondition);
            sql.append(" ORDER BY emp_fname limit " + limit + " offset " + offset);
            logger.info("sql ::==" + sql.toString());
            pstm = conn.prepareStatement(sql.toString());

            rs = pstm.executeQuery();
            userList = new ArrayList<>();
            while (rs.next()) {
                Employee emp = this.getEntityEmployee(rs);
                emp.setDepName(rs.getString("dep_name"));
                userList.add(emp);
            }
        } catch (Exception e) {
            logger.error("getEmployeeList error", e);
        } finally {
            this.close(pstm, rs);
        }
        return userList;
    }

    public String getConditionBuilder(Employee condition) {
        String sql = " WHERE 1=1 ";
        if (condition.getDepId() != -99) {
            sql += " AND dept_id = " + condition.getDepId();
        }
        if (!CharacterUtil.removeNull(condition.getEmpCode()).equals("")) {
            sql += " AND emp_code LIKE '%" + condition.getEmpCode() + "%' ";
        }
        if (!CharacterUtil.removeNull(condition.getEmpEmail()).equals("")) {
            sql += " AND emp_email LIKE '%" + condition.getEmpEmail() + "%' ";
        }
        if (!CharacterUtil.removeNull(condition.getEmpFname()).equals("")) {
            sql += " AND emp_fname LIKE '%" + condition.getEmpFname() + "%' ";
        }
        if (!CharacterUtil.removeNull(condition.getEmpMobile()).equals("")) {
            sql += " AND emp_mobile LIKE '%" + condition.getEmpMobile() + "%' ";
        }
        if (!CharacterUtil.removeNull(condition.getGender()).equals("")) {
            sql += " AND gender = '" + condition.getGender() + "' ";
        }
        if (!CharacterUtil.removeNull(condition.getStatus()).equals("")) {
            sql += " AND status = '" + condition.getStatus() + "' ";
        }

        return sql;
    }

    public int getCountEmployee(String sqlCondition) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int countRecord = 0;
        try {
            conn = new DbConnection().open();
            String sql = " SELECT COUNT(*) as cnt FROM employee c " + sqlCondition;
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            if (rs.next()) {
                countRecord = rs.getInt("cnt");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getCountEmployee error", e);
        } finally {
            this.close(pstm, rs);
        }
        return countRecord;
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
            while (rs.next()) {
                employeeList.add(this.getEntityEmployee(rs));
            }
        } catch (Exception e) {
            logger.error("getUser error", e);
        } finally {
            this.close(pstm, rs);
        }
        return employeeList;
    }

    public int updatePassword(String passsword, int modifiedBy, int id) {
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
            pstm.setInt(2, modifiedBy);
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

    private Employee getEntityEmployee(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setDepId(rs.getInt("dept_id"));
        employee.setEmpCode(rs.getString("emp_code"));
        employee.setEmpEmail(rs.getString("emp_email"));
        employee.setEmpFname(rs.getString("emp_fname"));
        employee.setEmpId(rs.getString("emp_id"));
        employee.setEmpLname(rs.getString("emp_lname"));
        employee.setEmpMobile(rs.getString("emp_mobile"));
        employee.setGender(rs.getString("gender"));
        employee.setModifiedBy(rs.getString("modified_by"));
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
            pstm.setString(9, employee.getEmpId());
            logger.info("pstm ::==" + pstm.toString());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            logger.error("updateProfile error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public int deleteEmployee(int empId) {
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" DELETE FROM `employee` WHERE emp_id=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setInt(1, empId);
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            logger.error("deleteEmployee error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public int creteEmployee(Employee employee) {
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO `employee` ");
            sql.append(" (`emp_code`, `username`, `password`, `emp_fname`, ");
            sql.append(" `emp_lname`, `emp_email`, `emp_mobile`, `gender`, `title`, `dept_id`, ");
            sql.append(" `status`, `modified_date`, `modified_by`) VALUES ");
            sql.append(" (?,?,md5(?),?, ");
            sql.append(" ?,?,?,?,?,?, ");
            sql.append(" ?,NOW(),?) ");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, employee.getEmpCode());
            pstm.setString(2, employee.getUsername());
            pstm.setString(3, employee.getPassword());
            pstm.setString(4, employee.getEmpFname());
            pstm.setString(5, employee.getEmpLname());
            pstm.setString(6, employee.getEmpEmail());
            pstm.setString(7, employee.getEmpMobile());
            pstm.setString(8, employee.getGender());
            pstm.setString(9, employee.getTitle());
            pstm.setInt(10, employee.getDepId());
            pstm.setString(11, employee.getStatus());
            pstm.setString(12, employee.getModifiedBy());

            exe = pstm.executeUpdate();
        } catch (Exception e) {
            logger.error("createProjectType error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public int updateEmployee(Employee employee) {
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE `employee` SET ");
            sql.append(" `emp_code`=?,");
            sql.append(" `emp_fname`=?,`emp_lname`=?,`emp_email`=?, ");
            sql.append(" `emp_mobile`=?,`gender`=?,`title`=?, ");
            sql.append(" `dept_id`=?,`status`=?,`modified_date`=NOW(), ");
            sql.append(" `modified_by`=? WHERE emp_id = ?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, employee.getEmpCode());
            pstm.setString(2, employee.getEmpFname());
            pstm.setString(3, employee.getEmpLname());
            pstm.setString(4, employee.getEmpEmail());
            pstm.setString(5, employee.getEmpMobile());
            pstm.setString(6, employee.getGender());
            pstm.setString(7, employee.getTitle());
            pstm.setInt(8, employee.getDepId());
            pstm.setString(9, employee.getStatus());
            pstm.setString(10, employee.getModifiedBy());
            pstm.setString(11, employee.getEmpId());

            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("updateProjectType error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

}
