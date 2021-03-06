package com.ddpms.dao;

import com.ddpms.db.DbConnection;
import com.ddpms.model.Employee;
import com.ddpms.model.Config;
import com.ddpms.model.Project;
import com.ddpms.model.ProjectWorking;
import com.ddpms.util.CharacterUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class ProjectDao {

    final static Logger logger = Logger.getLogger(ProjectDao.class);

    private Connection conn = null;

    public List<Project> getProject(Project p, int limit, int offset) {
        logger.debug("..getProject");
        List<Project> list = new ArrayList<Project>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT  `proj_id`, `proj_name`, `proj_details`, ");
            sql.append(" proj_status,(SELECT conf_value FROM config c WHERE c.conf_name = pj.proj_status) as proj_status_desc,");
            sql.append(" (SELECT `plan_name`FROM `plan` p  WHERE p.plan_id=pj.plan_id ) as `plan_id`, (SELECT `budp_name` FROM `budget_plan` b WHERE b.budp_id=pj.budp_id) as `budp_id`, ");
            sql.append("  `modified_date`, `modified_by`, (SELECT COUNT(*) as cnt FROM project_expense pe WHERE pe.proj_id=pj.proj_id) as delProject, ");
            sql.append(" (SELECT `prot_name` FROM `project_type` pt WHERE pt.prot_id=pj.prot_id) as `prot_id`, `proj_remark`, `proj_verify_date`, `proj_verify_by`, account_code, stra_id ");
            sql.append(" FROM `project` pj ");
            sql.append(getConditionBuilder(p));
            if (p.getNotification() > 0) {
                sql.append(" AND pj.proj_status = 'WAITING' AND pj.modified_date = curdate() ");
            }
            sql.append(" order by modified_date, proj_id desc ");
            if (offset != 0) {
                sql.append(" limit ").append(limit).append(" offset ").append(offset);
            }

            pstm = conn.prepareStatement(sql.toString());
            logger.info("pstm ::==" + pstm.toString());
            rs = pstm.executeQuery();

            while (rs.next()) {
                list.add(getEntityProject(rs));
            }

        } catch (Exception e) {
            logger.error("getProject Error : " + e.getMessage());
        } finally {
            this.close(pstm, rs);
        }
        return list;
    }

    public List<Project> getProjectNormal(Project p, int limit, int offset) {
        logger.debug("..getProject");
        List<Project> list = new ArrayList<Project>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT  `proj_id`, `proj_name`, `proj_details`, ");
            sql.append(" proj_status,(SELECT conf_value FROM config c WHERE c.conf_name = pj.proj_status) as proj_status_desc,");
            sql.append(" `plan_id`, `budp_id`, `modified_date`, `modified_by`, 0 as delProject, ");
            sql.append(" `prot_id`, `proj_remark`, `proj_verify_date`, `proj_verify_by`, account_code, stra_id ");
            sql.append(" FROM `project` pj");
            sql.append(getConditionBuilder(p));
            if (offset != 0) {
                sql.append(" limit ").append(limit).append(" offset ").append(offset);
            }

            pstm = conn.prepareStatement(sql.toString());
            logger.info("pstm ::==" + pstm.toString());
            rs = pstm.executeQuery();

            while (rs.next()) {
                list.add(getEntityProject(rs));
            }

        } catch (Exception e) {
            logger.error("getProject Error : " + e.getMessage());
        } finally {
            this.close(pstm, rs);
        }
        return list;
    }

    public List<Project> getProjectAll() {
        logger.debug("..getProjectAll");
        List<Project> list = new ArrayList<Project>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT  `proj_id`, `proj_name`, `proj_details`,0 as delProject, ");
            sql.append(" proj_status,(SELECT conf_value FROM config c WHERE c.conf_name = p.proj_status) as proj_status_desc,");
            sql.append(" (SELECT prot_name FROM project_type pt WHERE pt.prot_id = p.prot_id ) as prot_id, ");
            sql.append(" proj_remark,proj_verify_by,proj_verify_date,account_code,plan_id, budp_id, stra_id,  ");
            sql.append(" DATE_FORMAT(modified_date,'%d-%m-%Y') as modified_date, `modified_by` ");
            sql.append(" FROM `project` p ORDER BY proj_name ASC");
            pstm = conn.prepareStatement(sql.toString());
            logger.info("pstm ::==" + pstm.toString());
            rs = pstm.executeQuery();

            while (rs.next()) {
                list.add(getEntityProject(rs));
            }
        } catch (Exception e) {
            logger.error("getProjectAll Error", e);
        } finally {
            this.close(pstm, rs);
        }
        return list;
    }

    public List<Project> getProjectBudgetIsApproveAll() {
        logger.debug("..getProjectBudgetAll");
        List<Project> budgetProjectList = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT  `proj_id`, `proj_name`,");

            sql.append(" IFNULL((SELECT SUM((budget_approve_m1 + budget_approve_m2 + budget_approve_m3 + budget_approve_m4 + ");
            sql.append(" budget_approve_m5 + budget_approve_m6 + budget_approve_m7 + budget_approve_m8 + ");
            sql.append(" budget_approve_m9 + budget_approve_m10 + budget_approve_m11 + budget_approve_m12)) ");
            sql.append(" FROM project_working pw WHERE pw.proj_id = p.proj_id ");
            sql.append(" ),0) as sum_budget_all, ");

            sql.append(" IFNULL(( SELECT SUM(pe.exp_amount) FROM project_expense pe WHERE pe.proj_id = p.proj_id ");
            sql.append(" ),0) as sum_budget_actualuse ");

            sql.append(" FROM `project` p WHERE p.proj_status NOT IN('WAITING') ORDER BY proj_name ASC");
            pstm = conn.prepareStatement(sql.toString());
            logger.info("pstm ::==" + pstm.toString());
            rs = pstm.executeQuery();
            budgetProjectList = new ArrayList<>();
            while (rs.next()) {
                int budgetAll = rs.getInt("sum_budget_all");
                int budgetActualUser = rs.getInt("sum_budget_actualuse");
                Project project = new Project();
                project.setProjId(rs.getString("proj_id"));
                project.setProjName(rs.getString("proj_name"));
                project.setBudgetAll(budgetAll);
                project.setBudgetActualUse(budgetActualUser);
                project.setBudgetBalance(budgetAll - budgetActualUser);
                budgetProjectList.add(project);
            }
        } catch (Exception e) {
            logger.error("getProjectBudgetAll Error", e);
        } finally {
            this.close(pstm, rs);
        }
        return budgetProjectList;
    }

    public int createProject(Project p) {
        logger.debug("..createProject");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO project ");
            sql.append(" (`proj_name`, `proj_details`, `proj_status`,`plan_id`, `budp_id`, prot_id, account_code, `modified_date`, `modified_by`,stra_id ) ");
            sql.append(" VALUES (?,?,?,?,?,?,?,NOW(),?,?)");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, p.getProjName());
            pstm.setString(2, p.getProjDetail());
            pstm.setString(3, p.getProjStatus());
            pstm.setString(4, p.getPlanId());
            pstm.setString(5, p.getBudpId());
            pstm.setString(6, p.getProtId());
            pstm.setString(7, p.getAccountCode());
            pstm.setString(8, p.getModifiedBy());
            pstm.setString(9, p.getStraId());

            logger.info("pstm ::==" + pstm.toString());
            exe = pstm.executeUpdate();

        } catch (Exception e) {
            logger.error("Error saveProject:", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public int updateProject(Project p) {
        logger.debug("..updateProject");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE `project` SET ");
            sql.append(" `proj_name`=?,`proj_details`=?,`proj_status`=?,`plan_id`=?,`budp_id`=?, ");
            sql.append(" `modified_by`=?, account_code=?,stra_id=?, ");
            sql.append(" `modified_date`=NOW(), prot_id=? ");
            sql.append(" WHERE `proj_id`=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, p.getProjName());
            pstm.setString(2, p.getProjDetail());
            pstm.setString(3, p.getProjStatus());
            pstm.setString(4, p.getPlanId());
            pstm.setString(5, p.getBudpId());
            pstm.setString(6, p.getModifiedBy());
            pstm.setString(7, p.getAccountCode());
            pstm.setString(8, p.getStraId());
            pstm.setString(9, p.getProtId());
            pstm.setString(10, p.getProjId());
            logger.info("pstm ::==" + pstm.toString());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("updateProject error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public int updateProjectStatus(String pId) {
        logger.debug("..updateProjectStatus");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE `project` SET ");
            sql.append(" `proj_status`=?, ");
            sql.append(" `modified_date`=NOW() ");
            sql.append(" WHERE `proj_id`=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, "Processing");
            pstm.setString(2, pId);

            logger.info("pstm ::==" + pstm.toString());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("updateProjectStatus error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public int deleteProject(String id) {
        logger.debug("..deleteProject");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" DELETE FROM `project` WHERE proj_id=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, id);
            logger.info("pstm ::==" + pstm.toString());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("deleteProject error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public String getConditionBuilder(Project p) {
        logger.debug("..getConditionBuilder");
        StringBuilder sql = new StringBuilder(" WHERE 1=1 ");
        try {
            if (!"".equals(CharacterUtil.removeNull(p.getProjName()))) {
                sql.append(" and proj_name LIKE '%" + p.getProjName() + "%'");
            }
            if (!"".equals(CharacterUtil.removeNull(p.getProjId()))) {
                sql.append(" and proj_id='" + p.getProjId() + "'");
            }
            if (!"".equals(CharacterUtil.removeNull(p.getProjDetail()))) {
                sql.append(" and proj_details='" + p.getProjDetail() + "'");
            }
            if (!"".equals(CharacterUtil.removeNull(p.getProjStatus()))) {
                sql.append(" and proj_status='" + p.getProjStatus() + "'");
            }
            if (!"".equals(CharacterUtil.removeNull(p.getPlanId()))) {
                sql.append(" and plan_id='" + p.getPlanId() + "'");
            }
            if (!"".equals(CharacterUtil.removeNull(p.getBudpId()))) {
                sql.append(" and budp_id='" + p.getBudpId() + "'");
            }
            if (!"".equals(CharacterUtil.removeNull(p.getProtId()))) {
                sql.append(" and prot_id='" + p.getProtId() + "'");
            }
            if (!"".equals(CharacterUtil.removeNull(p.getStraId()))) {
                sql.append(" and stra_id='" + p.getStraId() + "'");
            }
			      if (!"".equals(CharacterUtil.removeNull(p.getModifiedBy()))) {
                EmployeeDao empDao = new EmployeeDao();
                Employee empObj = empDao.getEmployee(Integer.parseInt(p.getModifiedBy()));
                if(!"APPROVER".equals(empObj.getStatus()) && !"ADMIN".equals(empObj.getStatus())){
                    sql.append(" and modified_by='" + p.getModifiedBy() + "'");
                }                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sql.toString();
    }

    private Project getEntityProject(ResultSet rs) throws SQLException {
        Project p = new Project();
        p.setProjId(rs.getString("proj_id"));
        p.setProjName(rs.getString("proj_name"));
        p.setProjDetail(rs.getString("proj_details"));
        p.setProjStatus(rs.getString("proj_status"));
        p.setProjStatusDesc(rs.getString("proj_status_desc"));
        p.setPlanId(rs.getString("plan_id"));
        p.setBudpId(rs.getString("budp_id"));
        p.setModifiedDate(rs.getString("modified_date"));
        p.setModifiedBy(rs.getString("modified_by"));

        p.setProtId(rs.getString("prot_id"));
        p.setProjRemark(rs.getString("proj_remark"));
        p.setProjVerifyBy(rs.getString("proj_verify_by"));
        p.setProjVerifyDate(rs.getString("proj_verify_date"));
        p.setAccountCode(rs.getString("account_code"));
        p.setStraId(rs.getString("stra_id"));
        p.setDelProject(rs.getInt("delProject"));
        
        return p;
    }

    public int getCountProject(String conditionBuilder) {
        logger.debug("..getCountProject");
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int countProject = 0;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder("SELECT COUNT(*) as cnt FROM Project p");
            if (conditionBuilder != null) {
                sql.append(conditionBuilder);
            }
            pstm = conn.prepareStatement(sql.toString());
            rs = pstm.executeQuery();
            if (rs.next()) {
                countProject = rs.getInt("cnt");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getCountProject error", e);
        } finally {
            this.close(pstm, rs);
        }
        return countProject;
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

    public Project getProject(int projId) {
        logger.debug("..getProject by projId");
        Project project = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT  `proj_id`, `proj_name`, `proj_details`,");
            sql.append(" proj_status,(SELECT conf_value FROM config c WHERE c.conf_name = p.proj_status) as proj_status_desc,");
            sql.append(" (SELECT prot_name FROM project_type pt WHERE pt.prot_id = p.prot_id ) as prot_id, ");
            sql.append(" proj_remark,proj_verify_by,proj_verify_date,account_code,");
            sql.append(" (SELECT plan_name FROM plan pl WHERE pl.plan_id = p.plan_id) as plan_id, ");
            sql.append(" (SELECT budp_name FROM budget_plan bp WHERE bp.budp_id = p.budp_id ) as budp_id, stra_id, ");
            sql.append(" DATE_FORMAT(modified_date,'%d-%m-%Y') as modified_date, 0 as delProject, ");
            sql.append(" (SELECT CONCAT(emp_fname,' ',emp_lname) FROM employee e WHERE e.emp_id = p.modified_by) as modified_by");
            sql.append(" FROM `project` p WHERE p.proj_id = ?");
            pstm = conn.prepareStatement(sql.toString());
            pstm.setInt(1, projId);
            logger.info("pstm ::==" + pstm.toString());
            rs = pstm.executeQuery();

            if (rs.next()) {
                project = getEntityProject(rs);
            }
        } catch (Exception e) {
            logger.error("getProject Error", e);
        } finally {
            this.close(pstm, rs);
        }
        return project;
    }

    public List<Project> getProjectByCriteria(Project criteria) {
        logger.debug("..getProjectByCriteria");
        List<Project> list = new ArrayList<Project>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT  `proj_id`, `proj_name`, `proj_details`,");
            sql.append(" proj_status,(SELECT conf_value FROM config c WHERE c.conf_name = proj_status) as proj_status_desc,");
            sql.append(" `plan_id`, `budp_id`, `modified_date`, `modified_by`, 0 as delProject, ");
            sql.append(" `prot_id`, `proj_remark`, `proj_verify_date`, `proj_verify_by` , account_code, stra_id ");
            sql.append(" FROM `project`");            
            sql.append(getConditionBuilder(criteria));

            pstm = conn.prepareStatement(sql.toString());
            logger.info("pstm ::==" + pstm.toString());
            rs = pstm.executeQuery();

            while (rs.next()) {
                list.add(getEntityProject(rs));
            }

        } catch (Exception e) {
            logger.error("getProjectByCriteria Error : " + e.getMessage());
        } finally {
            this.close(pstm, rs);
        }
        return list;
    }

    public List<Project> getProjectListHaveTaskAssign(Integer empId) {
        logger.debug("..getProjectListHaveTaskAssign");
        List<Project> list = new ArrayList<Project>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT  `proj_id`, `proj_name`, `proj_details`, ");
            sql.append(" proj_status,(SELECT conf_value FROM config c WHERE c.conf_name = p.proj_status) as proj_status_desc,");
            sql.append(" plan_id,budp_id, 0 as delProject,  ");
            sql.append(" `prot_id`, `proj_remark`, `proj_verify_date`, `proj_verify_by`, ");
            sql.append(" DATE_FORMAT(modified_date,'%d-%m-%Y') as modified_date, `modified_by` ");
            sql.append(" FROM `project` p ");
            sql.append(" WHERE p.proj_id IN (SELECT proj_id  FROM `task_assign` WHERE task_userid = ?)");
            sql.append(" ORDER BY p.proj_name ASC");
            pstm = conn.prepareStatement(sql.toString());
            pstm.setInt(1, empId);
            logger.info("pstm ::==" + pstm.toString());
            rs = pstm.executeQuery();

            while (rs.next()) {
                list.add(getEntityProject(rs));
            }
        } catch (Exception e) {
            logger.error("getProjectListHaveTaskAssign Error", e);
        } finally {
            this.close(pstm, rs);
        }
        return list;
    }

    public int updateProjectVerifyStatus(Project param) {
        logger.debug("..updateProjectVerifyStatus");
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE `project` SET ");
            sql.append(" `proj_status`=?,proj_remark=?,proj_budg_approve=?, ");
            sql.append(" proj_verify_date=NOW(),`proj_verify_by`=?  ");
            sql.append(" WHERE `proj_id`=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, param.getProjStatus());
            pstm.setString(2, param.getProjRemark());
            pstm.setInt(3, param.getProjBudgApprove());
            pstm.setString(4, param.getProjVerifyBy());
            pstm.setString(5, param.getProjId());
            logger.info("pstm ::==" + pstm.toString());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            logger.error("updateProjectVerifyStatus error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public List<Config> getSumGroupByPlan() {
        logger.info(" ... getSumGroupByPlan ");
        List<Config> groupPlanList = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT (SELECT plan_name FROM plan pl where pl.plan_id = p.plan_id) as name,count(*) cnt FROM `project` p group by p.plan_id order by count(*) DESC";
            conn = new DbConnection().open();
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            groupPlanList = new ArrayList<>();
            while (rs.next()) {
                Config data = new Config();
                data.setConfName(rs.getString("name"));
                data.setConfValue(rs.getString("cnt"));
                groupPlanList.add(data);
            }
        } catch (Exception e) {
            logger.error("getSumGroupByPlan error", e);
        } finally {
            this.close(pstm, rs);
        }
        return groupPlanList;
    }

    public Map<String, Integer> getCountProjectInMonth(String year, String status) {
        logger.info("getCountProjectInMonth ...");
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Map<String, Integer> map = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT YEAR(p.modified_date) as year, ");
            sql.append(" COUNT(CASE WHEN MONTH(p.modified_date) = 1 THEN 0 END) AS Jan, COUNT(CASE WHEN MONTH(p.modified_date) = 2 THEN 0 END) AS Feb, ");
            sql.append(" COUNT(CASE WHEN MONTH(p.modified_date) = 3 THEN 0 END) AS Mar, COUNT(CASE WHEN MONTH(p.modified_date) = 4 THEN 0 END) AS Apr, ");
            sql.append(" COUNT(CASE WHEN MONTH(p.modified_date) = 5 THEN 0 END) AS May, COUNT(CASE WHEN MONTH(p.modified_date) = 6 THEN 0 END) AS Jun, ");
            sql.append(" COUNT(CASE WHEN MONTH(p.modified_date) = 7 THEN 0 END) AS Jul,  COUNT(CASE WHEN MONTH(p.modified_date) = 8 THEN 0 END) AS Aug, ");
            sql.append(" COUNT(CASE WHEN MONTH(p.modified_date) = 9 THEN 0 END) AS Sep, COUNT(CASE WHEN MONTH(p.modified_date) = 10 THEN 0 END) AS Oct, ");
            sql.append(" COUNT(CASE WHEN MONTH(p.modified_date) = 11 THEN 0 END) AS Nov, COUNT(CASE WHEN MONTH(p.modified_date) = 12 THEN 0 END) AS Decem ");
            sql.append(" FROM project p  ");
            sql.append(" WHERE YEAR(p.modified_date) = ? ");
            if (!CharacterUtil.removeNull(status).equals("")) {
                sql.append(" and p.proj_status = '" + status + "' ");
            }
            sql.append(" GROUP BY 1 ");
            logger.info(" sql ::==" + sql.toString());
            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, year);
            rs = pstm.executeQuery();

            if (rs.next()) {
                map = new HashMap<>();
                map.put("Year", rs.getInt("year"));
                map.put("Jan", rs.getInt("Jan"));
                map.put("Feb", rs.getInt("Feb"));
                map.put("Mar", rs.getInt("Mar"));
                map.put("Apr", rs.getInt("Apr"));
                map.put("May", rs.getInt("May"));
                map.put("Jun", rs.getInt("Jun"));
                map.put("Jul", rs.getInt("Jul"));
                map.put("Aug", rs.getInt("Aug"));
                map.put("Sep", rs.getInt("Sep"));
                map.put("Oct", rs.getInt("Oct"));
                map.put("Nov", rs.getInt("Nov"));
                map.put("Dec", rs.getInt("Decem"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getCountProjectInMonth error", e);
        } finally {
            this.close(pstm, rs);
        }
        return map;
    }
    
    public String getSumCostOfProject(ProjectWorking pw){
        logger.debug("getSumCostOfProject");
        PreparedStatement pstm = null;
        ResultSet rs = null;
        DecimalFormat formatter = new DecimalFormat("###,###");
        int total=0;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT SUM((budget_request_m1 + budget_request_m2 + budget_request_m3 + budget_request_m4 + ");
            sql.append(" budget_request_m5 + budget_request_m6 + budget_request_m7 + budget_request_m8 + ");
            sql.append(" budget_request_m9 + budget_request_m10 + budget_request_m11 + budget_request_m12)) as sum_budget_all ");
            sql.append(" FROM project_working pw WHERE pw.proj_id ='"+pw.getProjId()+"' and isFirstApprove is "+pw.getIsFirstApprove());
           
            pstm = conn.prepareStatement(sql.toString());
            logger.info("pstm ::==" + pstm.toString());
            
            rs = pstm.executeQuery();
            while (rs.next()) {
                total= total+(Integer.parseInt(rs.getString("sum_budget_all")));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            this.close(pstm, rs);
        }
        return formatter.format(total);
    }    
}
