package com.ddpms.dao;

import com.ddpms.db.DbConnection;
import com.ddpms.model.Task;
import com.ddpms.util.CharacterUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class TaskDao {

    final static Logger logger = Logger.getLogger(TaskDao.class);

    private Connection conn = null;

    private final String STR_TO_DATE = " '%d-%m-%Y' ";
    private final String DATE_TO_STR = " '%d-%m-%Y' ";

    public Task getTask(String taskId) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Task task = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT `task_id`, `task_name`,`modified_date`, `modified_by` FROM `task`");
            sql.append(" WHERE task_id = ?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, taskId);

            rs = pstm.executeQuery();
            if (rs.next()) {
                task = this.getEntityTask(rs);
            }
        } catch (Exception e) {
            logger.error("getDepartment error", e);
        } finally {
            this.close(pstm, rs);
        }
        return task;
    }

    public List<Task> getTaskList(int limit, int offset, String condition) {
        ResultSet rs = null;
        PreparedStatement pstm = null;
        List<Task> taskList = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT `task_id`, `task_name`,`modified_by` ");
            sql.append(" ,DATE_FORMAT(modified_date," + DATE_TO_STR + ") as modified_date  ");
            sql.append(" FROM task c  WHERE 1=1 ");
            sql.append(condition);
            sql.append(" ORDER BY c.task_id limit " + limit + " offset " + offset);
            logger.info("sql ::=="+sql.toString());
            pstm = conn.prepareStatement(sql.toString());
            rs = pstm.executeQuery();
            taskList = new ArrayList<Task>();
            while (rs.next()) {
                taskList.add(this.getEntityTask(rs));
            }
        } catch (Exception e) {
            logger.error("getTaskList error", e);
        } finally {
            this.close(pstm, rs);
        }
        return taskList;
    }

    public int createTask(Task task) {
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO `task` ");
            sql.append(" ( `task_name`, modified_date,modified_by ) ");
            sql.append(" VALUES ");
            sql.append(" (?,NOW(),?)");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, task.getTaskName());
            pstm.setString(2, task.getModifiedBy());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            logger.error("createTask error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public int updateTask(Task task) {
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE `task` SET ");
            sql.append("  `task_name`=?,");
            sql.append(" `modified_date`=NOW(),`modified_by`=? WHERE `task_id`=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, task.getTaskName());
            pstm.setString(2, task.getModifiedBy());
            pstm.setString(3, task.getTaskId());

            exe = pstm.executeUpdate();
        } catch (Exception e) {
            logger.error("updateTask error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public int deleteTask(int taskId) {
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" DELETE FROM `task` WHERE task_id=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setInt(1, taskId);
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            logger.error("deleteTask error", e);
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

    public int getCountTask(String condition) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int countRecord = 0;
        try {
            conn = new DbConnection().open();
            String sql = "SELECT COUNT(*) as cnt FROM task c WHERE 1=1 " + condition;

            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            if (rs.next()) {
                countRecord = rs.getInt("cnt");
            }
        } catch (Exception e) {
            logger.error("getCountTask error", e);
        } finally {
            this.close(pstm, rs);
        }
        return countRecord;
    }

    public String getConditionBuilder(String taskName) {
        String sql = " ";
        if (!CharacterUtil.removeNull(taskName).equals("")) {
            sql += " AND task_name LIKE '%" + taskName + "%' ";
        }
        return sql;
    }

    public List<Task> getTaskAll() {
        logger.info("..getTaskAll");
        List<Task> taskList = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT `task_id`, `task_name`, `modified_date`, `modified_by` FROM `task` ORDER BY task_name ASC");

            pstm = conn.prepareStatement(sql.toString());
            logger.info("pstm ::==" + pstm.toString());
            rs = pstm.executeQuery();

            taskList = new ArrayList<Task>();
            while (rs.next()) {
                taskList.add(getEntityTask(rs));
            }
        } catch (Exception e) {
            logger.error("error getTaskAll ", e);
        } finally {
            this.close(pstm, rs);
        }
        return taskList;
    }

    private Task getEntityTask(ResultSet rs) throws SQLException {
        Task task = new Task();
        task.setTaskId(rs.getString("task_id"));
        task.setTaskName(rs.getString("task_name"));
        task.setModifiedBy(rs.getString("modified_by"));
        task.setModifiedDate(rs.getString("modified_date"));
        return task;
    }

}
