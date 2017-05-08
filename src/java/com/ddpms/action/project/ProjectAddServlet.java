
package com.ddpms.action.project;

import com.ddpms.dao.ConfigDao;
import com.ddpms.dao.PlanDao;
import com.ddpms.dao.ProjectDao;
import com.ddpms.dao.ProjectExpenseDao;
import com.ddpms.model.MessageUI;
import com.ddpms.model.Plan;
import com.ddpms.model.Project;
import com.ddpms.model.ProjectExpense;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ProjectAddServlet extends HttpServlet {
    final static Logger logger = Logger.getLogger(ProjectAddServlet.class);
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...doGet ProjectAddServlet");
        try {
            PlanDao planDao = new PlanDao();
            request.setAttribute("planList", planDao.getPlan(new Plan(), 0, 0));
            request.setAttribute("months", new ConfigDao().getConfigUnique("MONTHS").getConfValue());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/project/project-form.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            logger.error("ProjectAddServlet Error : "+e.getMessage());
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...doPost ProjectAddServlet");
                
        try {
            Project p = new Project();
            ProjectDao projectDao = new ProjectDao();
            //Profile profile = (Profile)request.getSession().getAttribute("USER_PROFILE");
            String id = CharacterUtil.removeNull(request.getParameter("id"));
            String proj_name = CharacterUtil.removeNull(request.getParameter("proj_name"));
            request.setAttribute("proj_name", proj_name);
            String proj_details = CharacterUtil.removeNull(request.getParameter("proj_details"));
            request.setAttribute("proj_details", proj_details);
            //String proj_status = CharacterUtil.removeNull(request.getParameter("proj_status"));
            //request.setAttribute("proj_status", proj_status);
            String plan_id = CharacterUtil.removeNull(request.getParameter("plan_id"));
            request.setAttribute("plan_id", plan_id);
            String budp_id = CharacterUtil.removeNull(request.getParameter("budp_id"));
            request.setAttribute("budp_id", budp_id);
            
            //new parameter
            String account = CharacterUtil.removeNull(request.getParameter("account"));
            request.setAttribute("account", account);
            String department = CharacterUtil.removeNull(request.getParameter("department"));
            request.setAttribute("department", department);
            String prot_id = CharacterUtil.removeNull(request.getParameter("prot_id"));
            request.setAttribute("prot_id", prot_id);
            String budget_year1 = CharacterUtil.removeNull(request.getParameter("budget_year1"));
            request.setAttribute("budget_yea1r", budget_year1);
            String budget_request1 = CharacterUtil.removeNull(request.getParameter("budget_request1"));
            request.setAttribute("budget_request1", budget_request1);
            String budget_year2 = CharacterUtil.removeNull(request.getParameter("budget_year2"));
            request.setAttribute("budget_year2", budget_year2);
            String budget_request2 = CharacterUtil.removeNull(request.getParameter("budget_request2"));
            request.setAttribute("budget_request2", budget_request2);
            String budget_year3 = CharacterUtil.removeNull(request.getParameter("budget_year3"));
            request.setAttribute("budget_year3", budget_year3);
            String budget_request3 = CharacterUtil.removeNull(request.getParameter("budget_request3"));
            request.setAttribute("budget_request3", budget_request3);
            
            p.setProjId(id);
            p.setProjName(proj_name);
            p.setProjDetails(proj_details);
            p.setProjStatus("WAITING");
            p.setPlanId(plan_id);
            p.setBudpId(budp_id);  
            p.setModifiedBy("1");
            
            //new parameter
            p.setProjRemark("");
            p.setProjVerifyBy("");
            p.setProjVerifyDate("");//NOW
            p.setProtId("");
            
            ProjectExpenseDao peDao = new ProjectExpenseDao();
            int exe = 0;
            if(id.equals("")){
                exe = projectDao.createProject(p);          
                try {
                    if (exe != 0) {
                        p = new Project();
                        p.setProjName(proj_name);
                        List<Project> pj = projectDao.getProject(p, 1, 0);
                        
                        ProjectExpense pe = new ProjectExpense();
                        if(!pj.isEmpty()){
                           pe.setProjId(pj.get(0).getProjId());                           
                            pe.setExpAmount("0");
                            pe.setModifiedBy("1");
                            peDao.createProjectExpense(pe);  
                        }
                                
                    }                              
                } catch (Exception e) {
                    logger.error("createProjectExpense Error : "+e.getMessage());
                }
                
            }else{
                exe = projectDao.updateProject(p);                
            }
            MessageUI message = null;
            if (exe == 0) {
                message = new MessageUI(true, "สถานะการบันทีกข้อมูล", "เกิดข้อผิดพลาดในขั้นตอนการบันทีกข้อมูล", "danger");
            } else {
                message = new MessageUI(true, "สถานะการบันทีกข้อมูล", "บันทีกข้อมูลสำเร็จ", "info");
            }       
            request.getSession().setAttribute("MessageUI", message);
            response.sendRedirect(request.getContextPath() + "/ProjectSearchServlet");
        } catch (Exception e) {
            logger.error("ProjectAddServlet Error : "+e.getMessage());
        }
    }

}
