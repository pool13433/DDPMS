package com.ddpms.dao;

import com.ddpms.db.DbConnection;
import com.ddpms.model.TaskAssign;
import com.ddpms.model.TaskWork;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class TaskWorkDao {

    final static Logger logger = Logger.getLogger(TaskWorkDao.class);

    private Connection conn = null;

    private final String STR_TO_DATE = " '%d-%m-%Y' ";
    private final String DATE_TO_STR = " '%d-%m-%Y' ";

    public List<TaskWork> getTaskAssignAll() {
        logger.info("..getTaskAssignAll");
        List<TaskWork> taskWorkList = null;
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

            taskWorkList = new ArrayList<TaskWork>();
            while (rs.next()) {
                taskWorkList.add(getEntityTaskWork(rs));
            }
        } catch (Exception e) {
            logger.error("error getTaskAssignAll ", e);
        } finally {
            this.close(pstm, rs);
        }
        return taskWorkList;
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

    private TaskWork getEntityTaskWork(ResultSet rs) throws SQLException {
        TaskWork task = new TaskWork();
        task.setModifiedBy(rs.getInt("modified_by"));
        task.setModifiedDate(rs.getString("modified_date"));
        task.setTaskaId(rs.getInt("taska_id"));
        task.setTaskName(rs.getString("task_name"));
        task.setTaskwDate(rs.getString("taskw_date"));
        task.setTaskwDetail(rs.getString("taskw_detail"));
        task.setTaskwId(rs.getString("taskw_id"));
        task.setTaskwManday(rs.getInt("taskw_manday"));
        task.setProjName(rs.getString("proj_name"));
        return task;
    }

    public List<TaskWork> getTaskWorkListByUser(int userId) {
        logger.info("..getTaskAssignsByTask");
        List<TaskWork> taskWorkList = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT `taskw_id`, `taskw_detail`, `taskw_manday`, tw.taska_id, ");
            sql.append(" DATE_FORMAT(taskw_date," + DATE_TO_STR + ") as taskw_date, DATE_FORMAT(tw.modified_date," + DATE_TO_STR + ") as modified_date, tw.modified_by, ");
            sql.append(" (SELECT task_name FROM task t WHERE t.task_id = ta.task_id) as task_name,");
            sql.append(" (SELECT proj_name FROM project p WHERE p.proj_id = ta.proj_id) as proj_name");
            sql.append(" FROM task_work tw ");
            sql.append(" LEFT JOIN task_assign ta ON ta.taska_id =  tw.taska_id");
            sql.append(" LEFT JOIN task t ON t.task_id =  ta.task_id");
            sql.append("  WHERE ta.task_userid = ?");
            sql.append(" ORDER BY taskw_date ASC ");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setInt(1, userId);
            logger.info("pstm ::==" + pstm.toString());
            rs = pstm.executeQuery();
            taskWorkList = new ArrayList<TaskWork>();
            while (rs.next()) {
                taskWorkList.add(getEntityTaskWork(rs));
            }
        } catch (Exception e) {
            logger.error("error getTaskAssignAll ", e);
        } finally {
            this.close(pstm, rs);
        }
        return taskWorkList;
    }

    public TaskWork getTaskWork(int taskaId) {
        logger.info("..getTaskAssign");
        TaskWork taskWork = null;
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
                taskWork = getEntityTaskWork(rs);
            }
        } catch (Exception e) {
            logger.error("error getTaskAssign ", e);
        } finally {
            this.close(pstm, rs);
        }
        return taskWork;
    }

    public int createTaskWork(TaskWork taskWork) {
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO `task_work`(`taskw_detail`, `taskw_manday`, `taska_id`, `taskw_date`, ");
            sql.append(" `modified_date`, `modified_by`) VALUES");
            sql.append(" (?,?,?,STR_TO_DATE(?," + STR_TO_DATE + "),NOW(),?)");

            pstm = conn.prepareStatement(sql.toString());
            logger.info("sql ::==" + sql.toString());
            pstm.setString(1, taskWork.getTaskwDetail());
            pstm.setInt(2, taskWork.getTaskwManday());
            pstm.setInt(3, taskWork.getTaskaId());
            pstm.setString(4, taskWork.getTaskwDate());
            pstm.setInt(5, taskWork.getModifiedBy());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("createTaskWork error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public int updateTaskWork(TaskWork taskWork) {
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE `task_work` SET ");
            sql.append(" `taskw_detail`=?,`taskw_manday`=?,`taska_id`=?,");
            sql.append(" `taskw_date`=STR_TO_DATE(?," + STR_TO_DATE + "),`modified_date`=NOW(),`modified_by`=? ");
            sql.append(" WHERE `taskw_id`=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, taskWork.getTaskwDetail());
            pstm.setInt(2, taskWork.getTaskwManday());
            pstm.setInt(3, taskWork.getTaskaId());
            pstm.setString(4, taskWork.getTaskwDate());
            pstm.setInt(5, taskWork.getModifiedBy());
            pstm.setString(6, taskWork.getTaskwId());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("updateTaskWork error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public int deleteTaskWork(int tasksId) {
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" DELETE FROM `task_work` WHERE taskw_id=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setInt(1, tasksId);
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            logger.error("deleteTaskWork error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

}
