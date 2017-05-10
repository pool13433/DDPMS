
package com.ddpms.action.project;

import com.ddpms.dao.BudgetPlanDao;
import com.ddpms.dao.ConfigDao;
import com.ddpms.dao.PlanDao;
import com.ddpms.dao.ProjectDao;
import com.ddpms.dao.ProjectTypeDao;
import com.ddpms.dao.ProjectWorkingDao;
import com.ddpms.model.BudgetPlan;
import com.ddpms.model.Employee;
import com.ddpms.model.MessageUI;
import com.ddpms.model.Plan;
import com.ddpms.model.Project;
import com.ddpms.model.ProjectWorking;
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
            ProjectTypeDao dao = new ProjectTypeDao();
            int countRecordAll = dao.getCountProjectType("");
            request.setAttribute("projectTypeList", dao.getProjectTypeAll(countRecordAll, 0,""));
            BudgetPlanDao bpDao = new BudgetPlanDao();
            request.setAttribute("budgetPlanList", bpDao.getBudgetPlan(new BudgetPlan(), 0, 0));
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
            String id = CharacterUtil.removeNull(request.getParameter("id"));
            String proj_name = CharacterUtil.removeNull(request.getParameter("proj_name"));
            request.setAttribute("proj_name", proj_name);
            String plan_id = CharacterUtil.removeNull(request.getParameter("plan_id"));
            request.setAttribute("plan_id", plan_id);
            String budp_id = CharacterUtil.removeNull(request.getParameter("budp_id"));
            request.setAttribute("budp_id", budp_id);
            String details = CharacterUtil.removeNull(request.getParameter("details"));
            request.setAttribute("details", details);
            
            //new parameter
            String account = CharacterUtil.removeNull(request.getParameter("account"));
            request.setAttribute("account", account);
            String department = CharacterUtil.removeNull(request.getParameter("department"));
            request.setAttribute("department", department);
            String prot_id = CharacterUtil.removeNull(request.getParameter("prot_id"));
            request.setAttribute("prot_id", prot_id);
            String yearStart = CharacterUtil.removeNull(request.getParameter("yearStart"));
            
            Employee employee = (Employee) request.getSession().getAttribute("EMPLOYEE");  
            
            p.setProjId(id);
            p.setProjName(proj_name);
            p.setProjDetails(details);
            p.setProjStatus("WAITING");
            p.setPlanId(plan_id);
            p.setBudpId(budp_id);  
            p.setModifiedBy(String.valueOf(employee.getEmpId()));
            
            //new parameter
            p.setProjRemark("");
            p.setProjVerifyBy("");
            p.setProjVerifyDate("");//NOW
            p.setProtId(prot_id);
            p.setAccountCode(account);
            
            ProjectWorkingDao pwDao = new ProjectWorkingDao();
            int exe = 0;
            if(id.equals("")){
                exe = projectDao.createProject(p);  
                    if (exe != 0) {
                        p.setProjName(proj_name);
                        List<Project> pj = projectDao.getProject(p, 1, 0);
                        
                        String[] budget_request = request.getParameterValues("budget_request");
                        int loop = budget_request.length / 12;
                        if(!pj.isEmpty()){
                            ProjectWorking pw = new ProjectWorking();
                            for(int i=1 ; i <= loop ; i++){   
                                int y = (Integer.parseInt(yearStart)+i)-1;
                                pw.setProjId(pj.get(0).getProjId());
                                pw.setBudgetYear(String.valueOf(y));
                                pw.setBudgetRequestM1(budget_request[(i*1)-1]);
                                pw.setBudgetRequestM2(budget_request[(i*2)-1]);
                                pw.setBudgetRequestM3(budget_request[(i*3)-1]);
                                pw.setBudgetRequestM4(budget_request[(i*4)-1]);
                                pw.setBudgetRequestM5(budget_request[(i*5)-1]);
                                pw.setBudgetRequestM6(budget_request[(i*6)-1]);
                                pw.setBudgetRequestM7(budget_request[(i*7)-1]);
                                pw.setBudgetRequestM8(budget_request[(i*8)-1]);
                                pw.setBudgetRequestM9(budget_request[(i*9)-1]);
                                pw.setBudgetRequestM10(budget_request[(i*10)-1]);
                                pw.setBudgetRequestM11(budget_request[(i*11)-1]);
                                pw.setBudgetRequestM12(budget_request[(i*12)-1]);
                                pw.setModifiedBy(p.getModifiedBy());
                                
                                try {
                                    pwDao.createProjectWorking(pw);
                                } catch (Exception e) {
                                    logger.error("createProjectWorking error :"+e.getMessage());
                                }  
                            }
                        }   
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
