package com.ddpms.dao;

import com.ddpms.db.DbConnection;
import com.ddpms.model.Task;
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
        task.setTaskId(rs.getInt("task_id"));
        task.setTaskName(rs.getString("task_name"));
        task.setModifiedBy(rs.getString("modified_by"));
        task.setModifiedDate(rs.getString("modified_date"));
        return task;
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
