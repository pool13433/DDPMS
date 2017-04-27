package com.ddpms.dao;

import com.ddpms.db.DbConnection;
import com.ddpms.model.ProjectWorking;
import com.ddpms.model.TaskAssign;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
            sql.append(" DATE_FORMAT(modified_date,'%d-%m-%Y') as modified_date,`modified_by` ");
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
        task.setModifiedBy(rs.getString("modified_by"));
        task.setModifiedDate(rs.getString("modified_date"));
        task.setProjId(rs.getInt("proj_id"));
        task.setProjName(rs.getString("proj_name"));
        task.setTaskId(rs.getInt("task_id"));
        task.setTaskName(rs.getString("task_name"));
        task.setTaskUserId(rs.getString("task_userid"));
        task.setTaskUsername(rs.getString("task_username"));
        task.setTaskaId(rs.getInt("taska_id"));
        return task;
    }

    public List<TaskAssign> getTaskAssignsByTask(int taskId) {
        logger.info("..getTaskAssignsByTask");
        List<TaskAssign> taskAssignList = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT `taska_id`,`task_id`,(SELECT t.task_name FROM task t where t.task_id = ta.task_id) as task_name, ");
            sql.append(" `proj_id`,(SELECT p.proj_name FROM project p where p.proj_id = ta.proj_id) as proj_name, ");
            sql.append(" `task_userid`,(SELECT CONCAT(e.emp_fname,' ',e.emp_lname) FROM employee e where e.emp_id = ta.task_userid) as task_Username, ");
            sql.append(" DATE_FORMAT(modified_date,'%d-%m-%Y') as modified_date,`modified_by` ");
            sql.append(" FROM task_assign ta ");
            sql.append(" WHERE ta.task_id = ? ");
            sql.append(" ORDER by ta.task_id ASC");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setInt(1, taskId);
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

}
