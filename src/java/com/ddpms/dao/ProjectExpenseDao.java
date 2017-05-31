package com.ddpms.dao;

import com.ddpms.db.DbConnection;
import com.ddpms.model.ProjectExpense;
import com.ddpms.util.CharacterUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class ProjectExpenseDao {

    final static Logger logger = Logger.getLogger(ProjectExpenseDao.class);

    private Connection conn = null;
    private final String STR_TO_DATE = " '%d-%m-%Y' ";
    private final String DATE_TO_STR = " '%d-%m-%Y' ";

    public List<ProjectExpense> getProjectExpenseList(int limit, int offset, String sqlCondition) {
        logger.debug("..getProjectExpense");
        List<ProjectExpense> expenseList = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT `exp_id`, (select p.proj_name from project p where p.proj_id=pe.proj_id) as `proj_id`, `exp_desc`, `exp_amount`, `exp_voch`, `exp_pr`, ");
            sql.append(" DATE_FORMAT(receipt_date,"+DATE_TO_STR+") as receipt_date, DATE_FORMAT(exp_date,"+DATE_TO_STR+") as exp_date, `vender`, `modified_date`, `modified_by` ");
            sql.append(" FROM `project_expense` pe ");
            sql.append(sqlCondition);
            sql.append(" limit ").append(limit).append(" offset ").append(offset);

            pstm = conn.prepareStatement(sql.toString());
            logger.info("pstm ::=="+pstm.toString());
            rs = pstm.executeQuery();
            expenseList = new ArrayList<ProjectExpense>();
            while (rs.next()) {
                expenseList.add(getEntityProjectExpense(rs));
            }
        } catch (Exception e) {
            logger.error("Error getProjectExpense :", e);
        }
        return expenseList;
    }

    public int createProjectExpense(ProjectExpense pe) {
        logger.debug("..createProjectExpense");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO project_expense ");
            sql.append(" (`proj_id`, `exp_desc`, `exp_amount`, `exp_voch`, `exp_pr`, "
                    + "`receipt_date`, `exp_date`, `vender`, `modified_date`, `modified_by` ) ");
            sql.append(" VALUES (?,?,?,?,?,STR_TO_DATE(?," + STR_TO_DATE + "),STR_TO_DATE(?," + STR_TO_DATE + "),?,NOW(),?)");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, pe.getProjId());
            pstm.setString(2, pe.getExpDesc());
            pstm.setString(3, pe.getExpAmount());
            pstm.setString(4, pe.getExpVoch());
            pstm.setString(5, pe.getExpPr());
            pstm.setString(6, pe.getReceiptDate());
            pstm.setString(7, pe.getExpDate());
            pstm.setString(8, pe.getVender());
            pstm.setString(9, pe.getModifiedBy());
            //logger.info("pstm ::=="+pstm.toString());
            exe = pstm.executeUpdate();

        } catch (Exception e) {
            logger.error("Error saveProjectExpense:" + e.getMessage());
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public int updateProjectExpense(ProjectExpense pe) {
        logger.debug("..updateProjectExpense");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE `project_expense` SET ");
            sql.append(" `proj_id`=?,`exp_desc`=?,`exp_amount`=?,`exp_voch`=?, ");
            sql.append(" `exp_pr`=?,`receipt_date`=STR_TO_DATE(?," + STR_TO_DATE + "),`exp_date`=STR_TO_DATE(?," + STR_TO_DATE + "),`vender`=?, `modified_by`=?, ");
            sql.append(" `modified_date`=NOW() ");
            sql.append(" WHERE `exp_id`=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, pe.getProjId());
            pstm.setString(2, pe.getExpDesc());
            pstm.setString(3, pe.getExpAmount());
            pstm.setString(4, pe.getExpVoch());
            pstm.setString(5, pe.getExpPr());
            pstm.setString(6, pe.getReceiptDate());
            pstm.setString(7, pe.getExpDate());
            pstm.setString(8, pe.getVender());
            pstm.setString(9, pe.getModifiedBy());
            pstm.setString(10, pe.getExpId());
            logger.info("pstm ::==" + pstm.toString());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            logger.error("updateProjectExpense error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public int deleteProjectExpense(String id) {
        logger.debug("..deleteProjectExpense");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" DELETE FROM `project_expense` WHERE exp_id=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, id);
            logger.info("pstm ::==" + pstm.toString());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("deleteProjectExpense error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public String getConditionBuilder(ProjectExpense pe) {
        StringBuilder sql = new StringBuilder(" WHERE 1=1 ");
        if (!CharacterUtil.removeNull(pe.getProjId()).equals("")) {
            sql.append(" and proj_id = '" + pe.getProjId() + "'");
        }
        if (!CharacterUtil.removeNull(pe.getExpAmount()).equals("")) {
            sql.append(" and exp_amount LIKE '%" + pe.getExpAmount() + "%' ");
        }
        if (!CharacterUtil.removeNull(pe.getExpPr()).equals("")) {
            sql.append(" and exp_pr LIKE '%" + pe.getExpPr() + "%' ");
        }
        if (!CharacterUtil.removeNull(pe.getExpVoch()).equals("")) {
            sql.append(" and exp_voch LIKE '%" + pe.getExpVoch() + "%' ");
        }
         if (!CharacterUtil.removeNull(pe.getReceiptDate()).equals("")) {
            sql.append(" and receipt_date LIKE '%" + pe.getReceiptDate() + "%' ");
        }
        if (!CharacterUtil.removeNull(pe.getExpDateBegin()).equals("") && !CharacterUtil.removeNull(pe.getExpDateEnd()).equals("")) {
            sql.append(" and (exp_date BETWEEN STR_TO_DATE('"+pe.getExpDateBegin()+"',"+STR_TO_DATE+") AND  ");
            sql.append(" STR_TO_DATE('"+pe.getExpDateEnd()+"',"+STR_TO_DATE+") )");
        }
        return sql.toString();
    }

    private ProjectExpense getEntityProjectExpense(ResultSet rs) throws SQLException {
        ProjectExpense pe = new ProjectExpense();

        pe.setExpId(rs.getString("exp_id"));
        pe.setProjId(rs.getString("proj_id"));
        pe.setExpDesc(rs.getString("exp_desc"));
        pe.setExpAmount(rs.getString("exp_amount"));
        pe.setExpDate(rs.getString("exp_date"));
        pe.setExpVoch(rs.getString("exp_voch"));
        pe.setExpPr(rs.getString("exp_pr"));
        pe.setReceiptDate(rs.getString("receipt_date"));
        pe.setVender(rs.getString("vender"));
        pe.setModifiedDate(rs.getString("modified_date"));
        pe.setModifiedBy(rs.getString("modified_by"));

        return pe;
    }

    private ProjectExpense getEntityListTotalSum(ResultSet rs) throws SQLException {        
        ProjectExpense pe = new ProjectExpense();

        pe.setProjId(rs.getString("proj_id"));
        pe.setProjName(rs.getString("proj_name"));
        pe.setExpAmount(rs.getString("exp_amount"));
        pe.setModifiedDate(rs.getString("modified_date"));

        return pe;
    }

    public int getCountProjectExpense(String conditionBuilder) {
        logger.debug("..getCountProjectExpense");
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int countProjectExpense = 0;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder("SELECT COUNT(*) as cnt FROM project_expense pe ");
            if (conditionBuilder != null) {
                sql.append(conditionBuilder);
            }
            pstm = conn.prepareStatement(sql.toString());
            rs = pstm.executeQuery();
            if (rs.next()) {
                countProjectExpense = rs.getInt("cnt");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getCountProjectExpense error", e);
        } finally {
            this.close(pstm, rs);
        }
        return countProjectExpense;
    }

    public List<ProjectExpense> listGroupProjectTotalSum(String conditionBuilder) {
        logger.debug("..listGroupProjectTotalSum");
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<ProjectExpense> listProjectGroupTotalSum = new ArrayList<ProjectExpense>();
        try {
            conn = new DbConnection().open();
            // SELECT `proj_id`, `modified_date` , (select sum(exp_amount) from project_expense p where p.proj_id=pe.proj_id) as total_amount FROM `project_expense` pe
            StringBuilder sql = new StringBuilder("SELECT (select p.proj_name from project p where p.proj_id=pe.proj_id) as proj_name, `proj_id`, `modified_date` , (select sum(exp_amount) from project_expense p where p.proj_id=pe.proj_id) as exp_amount FROM `project_expense` pe ");
            if (conditionBuilder != null) {
                sql.append(conditionBuilder);
                sql.append(" GROUP BY proj_id ");
            }
            pstm = conn.prepareStatement(sql.toString());
            //logger.debug("sql : "+pstm);
            rs = pstm.executeQuery();
            while (rs.next()) {
                listProjectGroupTotalSum.add(getEntityListTotalSum(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("listGroupProjectTotalSum error", e);
        } finally {
            this.close(pstm, rs);
        }
        return listProjectGroupTotalSum;
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

    public ProjectExpense getProjectExpense(String expId) {
        logger.debug("..getProjectExpense");
        ProjectExpense expense = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT `exp_id`,`proj_id`, `exp_desc`, `exp_amount`, `exp_voch`, `exp_pr`, ");
            sql.append(" DATE_FORMAT(receipt_date,"+DATE_TO_STR+") as receipt_date, DATE_FORMAT(exp_date," + DATE_TO_STR + ") as exp_date, `vender`, DATE_FORMAT(modified_date," + DATE_TO_STR + ") as modified_date, `modified_by` ");
            sql.append(" FROM `project_expense` pe WHERE exp_id = ?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, expId);
            logger.info("pstm ::==" + pstm.toString());
            rs = pstm.executeQuery();
            if (rs.next()) {
                expense = this.getEntityProjectExpense(rs);
            }
        } catch (Exception e) {
            logger.error("Error getProjectExpense :", e);
        }
        return expense;
    }
}
