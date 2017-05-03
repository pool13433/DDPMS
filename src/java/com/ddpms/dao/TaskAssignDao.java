package com.ddpms.dao;

import com.ddpms.db.DbConnection;
import com.ddpms.model.TaskAssign;
import com.ddpms.model.TaskWork;
import com.ddpms.util.CharacterUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class TaskAssignDao {

    final static Logger logger = Logger.getLogger(TaskAssignDao.class);

    private Connection conn = null;

    private final String STR_TO_DATE = " '%d-%m-%Y' ";
    private final String DATE_TO_STR = " '%d-%m-%Y' ";

    public List<TaskAssign> getTaskAssignAll() {
        logger.info("..getTaskAssignAll");
        List<TaskAssign> taskAssignList = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT`taska_id`,`task_id`,(SELECT t.task_name FROM task t where t.task_id = ta.task_id) as task_name, ");
            sql.append(" `proj_id`,(SELECT p.proj_name FROM project p where p.proj_id = ta.proj_id) as proj_name, ");
            sql.append(" `task_userid`,(SELECT CONCAT(e.emp_fname,' ',e.emp_lname) FROM employee e where e.emp_id = ta.task_userid) as task_Username, ");
            sql.append(" DATE_FORMAT(taska_assign_date," + DATE_TO_STR + ") as taska_assign_date, ");
            sql.append(" DATE_FORMAT(taska_target_date," + DATE_TO_STR + ") as taska_target_date, ");
            sql.append(" DATE_FORMAT(modified_date," + DATE_TO_STR + ") as modified_date,`modified_by` ");
            sql.append(" FROM task_assign ta ORDER by ta.proj_id ASC");

            pstm = conn.prepareStatement(sql.toString());
            logger.info("pstm ::==" + pstm.toString());
            rs = pstm.executeQuery();

            taskAssignList = new ArrayList<TaskAssign>();
            while (rs.next()) {
                taskAssignList.add(getEntityTaskAssign(rs));
            }
        } catch (Exception e) {
            logger.error("error getTaskAssignAll ", e);
        } finally {
            this.close(pstm, rs);
        }
        return taskAssignList;
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

    private TaskAssign getEntityTaskAssign(ResultSet rs) throws SQLException {
        TaskAssign task = new TaskAssign();
        task.setModifiedBy(rs.getInt("modified_by"));
        task.setModifiedDate(rs.getString("modified_date"));
        task.setTaskaAssignDate(rs.getString("taska_assign_date"));
        task.setTaskaTargetDate(rs.getString("taska_target_date"));
        task.setProjId(rs.getInt("proj_id"));
        task.setProjName(rs.getString("proj_name"));
        task.setTaskId(rs.getInt("task_id"));
        task.setTaskName(rs.getString("task_name"));
        task.setTaskUserId(rs.getInt("task_userid"));
        task.setTaskUsername(rs.getString("task_username"));
        task.setTaskaId(rs.getString("taska_id"));
        return task;
    }

    public List<TaskAssign> getTaskAssignsListByProject(String projId) {
        logger.info("..getTaskAssignsListByProject");
        List<TaskAssign> taskAssignList = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT `taska_id`,`task_id`,(SELECT t.task_name FROM task t where t.task_id = ta.task_id) as task_name, ");
            sql.append(" `proj_id`,(SELECT p.proj_name FROM project p where p.proj_id = ta.proj_id) as proj_name, ");
            sql.append(" `task_userid`,(SELECT CONCAT(e.emp_fname,' ',e.emp_lname) FROM employee e where e.emp_id = ta.task_userid) as task_Username, ");
            sql.append(" DATE_FORMAT(taska_assign_date," + DATE_TO_STR + ") as taska_assign_date, ");
            sql.append(" DATE_FORMAT(taska_target_date," + DATE_TO_STR + ") as taska_target_date, ");
            sql.append(" DATE_FORMAT(modified_date," + DATE_TO_STR + ") as modified_date,`modified_by` ");
            sql.append(" FROM task_assign ta ");
            sql.append(" WHERE ta.proj_id = ? ");
            sql.append(" ORDER by ta.task_id ASC");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, projId);
            logger.info("pstm ::==" + pstm.toString());
            rs = pstm.executeQuery();
            taskAssignList = new ArrayList<TaskAssign>();
            while (rs.next()) {
                taskAssignList.add(getEntityTaskAssign(rs));
            }
        } catch (Exception e) {
            logger.error("error getTaskAssignsListByProject ", e);
        } finally {
            this.close(pstm, rs);
        }
        return taskAssignList;
    }
    
    public List<TaskAssign> getTaskAssignListByUser(int userId,int projId) {
        logger.info("..getTaskAssignListByUser");
        List<TaskAssign> taskAssignList = null;        
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT `taska_id`,`task_id`,(SELECT t.task_name FROM task t where t.task_id = ta.task_id) as task_name, ");
            sql.append(" `proj_id`,(SELECT p.proj_name FROM project p where p.proj_id = ta.proj_id) as proj_name, ");
            sql.append(" `task_userid`,(SELECT CONCAT(e.emp_fname,' ',e.emp_lname) FROM employee e where e.emp_id = ta.task_userid) as task_Username, ");
            sql.append(" DATE_FORMAT(taska_assign_date," + DATE_TO_STR + ") as taska_assign_date, ");
            sql.append(" DATE_FORMAT(taska_target_date," + DATE_TO_STR + ") as taska_target_date, ");
            sql.append(" DATE_FORMAT(modified_date," + DATE_TO_STR + ") as modified_date,`modified_by` ");
            sql.append(" FROM task_assign ta ");
            sql.append(" WHERE ta.task_userid = ? AND ta.proj_id = ?");
            sql.append(" ORDER by ta.proj_id ASC");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setInt(1, userId);
            pstm.setInt(2, projId);
            logger.info("pstm ::==" + sql.toString());
            rs = pstm.executeQuery();
            taskAssignList = new ArrayList<TaskAssign>();
            while (rs.next()) {
                taskAssignList.add(getEntityTaskAssign(rs));
            }
        } catch (Exception e) {
            logger.error("error getTaskAssignListByUser ", e);
        } finally {
            this.close(pstm, rs);
        }
        return taskAssignList;
    }

    public TaskAssign getTaskAssign(int taskaId) {
        logger.info("..getTaskAssign");
        TaskAssign taskAssign = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT`taska_id`,`task_id`,(SELECT t.task_name FROM task t where t.task_id = ta.task_id) as task_name, ");
            sql.append(" `proj_id`,(SELECT p.proj_name FROM project p where p.proj_id = ta.proj_id) as proj_name, ");
            sql.append(" `task_userid`,'' as task_Username, ");
            sql.append(" DATE_FORMAT(taska_assign_date," + DATE_TO_STR + ") as taska_assign_date, ");
            sql.append(" DATE_FORMAT(taska_target_date," + DATE_TO_STR + ") as taska_target_date, ");
            sql.append(" DATE_FORMAT(modified_date," + DATE_TO_STR + ") as modified_date,`modified_by` ");
            sql.append(" FROM task_assign ta WHERE taska_id = ?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setInt(1, taskaId);
            logger.info("pstm ::==" + pstm.toString());
            rs = pstm.executeQuery();
            if (rs.next()) {
                taskAssign = getEntityTaskAssign(rs);
            }
        } catch (Exception e) {
            logger.error("error getTaskAssign ", e);
        } finally {
            this.close(pstm, rs);
        }
        return taskAssign;
    }

    public int createTaskAssign(TaskAssign taskAssign) {
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO `task_assign`(`task_id`, `proj_id`, `task_userid`, `taska_assign_date`, `taska_target_date`, `modified_date`, `modified_by`) ");
            sql.append(" VALUES (?,?,?,STR_TO_DATE(?,"+STR_TO_DATE+"),STR_TO_DATE(?,"+STR_TO_DATE+"),NOW(),?)");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setInt(1, taskAssign.getTaskId());
            pstm.setInt(2, taskAssign.getProjId());
            pstm.setInt(3, taskAssign.getTaskUserId());
            pstm.setString(4, taskAssign.getTaskaAssignDate());
            pstm.setString(5, taskAssign.getTaskaTargetDate());
            pstm.setInt(6, taskAssign.getModifiedBy());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("createTaskAssign error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public int updateTaskAssign(TaskAssign taskAssign) {
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE `task_assign` SET ");
            sql.append(" `task_id`=?,`proj_id`=?,`task_userid`=?, ");
            sql.append(" `taska_assign_date`=STR_TO_DATE(?,"+STR_TO_DATE+"),`taska_target_date`=STR_TO_DATE(?,"+STR_TO_DATE+"), ");
            sql.append(" `modified_date`=NOW(),`modified_by`=? WHERE `taska_id`=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setInt(1, taskAssign.getTaskId());
            pstm.setInt(2, taskAssign.getProjId());
            pstm.setInt(3, taskAssign.getTaskUserId());
            pstm.setString(4, taskAssign.getTaskaAssignDate());
            pstm.setString(5, taskAssign.getTaskaTargetDate());
            pstm.setInt(6, taskAssign.getModifiedBy());
            pstm.setString(7, taskAssign.getTaskaId());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("updateTaskAssign error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public int deleteTaskAssign(int tasksId) {
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" DELETE FROM `task_assign` WHERE taska_id=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setInt(1, tasksId);
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("deleteTaskAssign error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

}
