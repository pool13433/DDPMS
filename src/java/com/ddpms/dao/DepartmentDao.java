package com.ddpms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.ddpms.db.DbConnection;
import com.ddpms.model.Department;
import com.ddpms.util.CharacterUtil;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDao {

    final static Logger logger = Logger.getLogger(DepartmentDao.class);

    private Connection conn = null;
    private final String STR_TO_DATE = " '%d-%m-%Y' ";
    private final String DATE_TO_STR = " '%d-%m-%Y' ";

    public Department getDepartment(String depId) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Department department = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT `dep_id`, `dep_name`,dep_account, `modified_date`, `modified_by` FROM `department`");
            sql.append(" WHERE dep_id = ?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, depId);

            rs = pstm.executeQuery();
            if (rs.next()) {
                department = this.getEntityDepartment(rs);
            }
        } catch (Exception e) {
            logger.error("getDepartment error", e);
        } finally {
            this.close(pstm, rs);
        }
        return department;
    }

    public List<Department> getDepartmentList(int limit, int offset,String sqlCondition) {
        ResultSet rs = null;
        PreparedStatement pstm = null;
        List<Department> departmentList = null;
        try {
            conn = new DbConnection().open();
            String sql = "SELECT `dep_id`, `dep_name`, `dep_account`, `modified_by` ";
            sql += " ,DATE_FORMAT(modified_date," + DATE_TO_STR + ") as modified_date  FROM department c ";
            sql += sqlCondition;
            sql += " ORDER BY c.dep_id limit " + limit + " offset " + offset;
            //logger.info("sql ::=="+sql);
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            departmentList = new ArrayList<Department>();
            while (rs.next()) {
                departmentList.add(this.getEntityDepartment(rs));
            }
        } catch (Exception e) {
            logger.error("getDepartmentList error", e);
        } finally {
            this.close(pstm, rs);
        }
        return departmentList;
    }

    public int createDepartment(Department department) {
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO `department` ");
            sql.append(" ( `dep_name`, `dep_account`, modified_date,modified_by ) ");
            sql.append(" VALUES ");
            sql.append(" (?,?,NOW(),?)");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, department.getDepName());
            pstm.setString(2, department.getDepAccount());
            pstm.setString(3, department.getModifiedBy());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            logger.error("createDepartment error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public int updateDepartment(Department department) {
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE `department` SET ");
            sql.append("  `dep_name`=?,`dep_account`=?,");
            sql.append(" `modified_date`=NOW(),`modified_by`=? WHERE `dep_id`=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, department.getDepName());
            pstm.setString(2, department.getDepAccount());
            pstm.setString(3, department.getModifiedBy());
            pstm.setString(4, department.getDepId());

            exe = pstm.executeUpdate();
        } catch (Exception e) {
            logger.error("updateDepartment error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public int deleteDepartment(int depId) {
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" DELETE FROM `department` WHERE dep_id=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setInt(1, depId);
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            logger.error("deleteDepartment error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
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
            logger.error("getUser error", ex);
        }
    }

    public int getCountDepartment(String sqlCondition) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int countRecord = 0;
        try {
            conn = new DbConnection().open();
            String sql = " SELECT COUNT(*) as cnt FROM department c "+sqlCondition;
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            if (rs.next()) {
                countRecord = rs.getInt("cnt");
            }
        } catch (Exception e) {
            logger.error("getCountDepartment error", e);
        } finally {
            this.close(pstm, rs);
        }
        return countRecord;
    }

    public List<Department> getDepartmentAll() {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Department> departmentList = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT `dep_id`, `dep_name`,dep_account, `modified_date`, `modified_by` FROM `department`");

            pstm = conn.prepareStatement(sql.toString());
            rs = pstm.executeQuery();

            departmentList = new ArrayList<Department>();
            while (rs.next()) {
                departmentList.add(this.getEntityDepartment(rs));
            }
        } catch (Exception e) {
            logger.error("getDepartmentAll error", e);
        } finally {
            this.close(pstm, rs);
        }
        return departmentList;
    }

    private Department getEntityDepartment(ResultSet rs) throws SQLException {
        Department department = new Department();
        department.setDepId(rs.getString("dep_id"));
        department.setDepName(rs.getString("dep_name"));
        department.setDepAccount(rs.getString("dep_account"));
        department.setModifiedBy(rs.getString("modified_by"));
        department.setModifiedDate(rs.getString("modified_date"));
        return department;
    }

    public String getConditionBuilder(String depName) {
        String sql = " WHERE 1=1 ";
        if (!CharacterUtil.removeNull(depName).equals("")) {
            sql += " AND dep_name LIKE '%" + depName + "%' ";
        }
        return sql;
    }

}
