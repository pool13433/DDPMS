
package com.ddpms.action.project;

import static com.ddpms.action.project.ProjectVerifyServlet.logger;
import com.ddpms.dao.BudgetPlanDao;
import com.ddpms.dao.PlanDao;
import com.ddpms.dao.ProjectDao;
import com.ddpms.dao.ProjectHistoryDao;
import com.ddpms.dao.ProjectTypeDao;
import com.ddpms.dao.ProjectWorkingDao;
import com.ddpms.dao.StrategicDao;
import com.ddpms.model.BudgetPlan;
import com.ddpms.model.Employee;
import com.ddpms.model.MessageUI;
import com.ddpms.model.Plan;
import com.ddpms.model.Project;
import com.ddpms.model.ProjectHistory;
import com.ddpms.model.ProjectWorking;
import com.ddpms.model.Strategic;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import java.util.Arrays;
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
            String yearStart = CharacterUtil.removeNull(request.getParameter("yearStart"));
            request.setAttribute("yearStart", yearStart);    
            StrategicDao stDao = new StrategicDao();
            countRecordAll = stDao.getCountStrategic("");
            request.setAttribute("strategicList", stDao.getStrategic(new Strategic(), countRecordAll, 0));
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
            String status = CharacterUtil.removeNull(request.getParameter("proj_status"));
            String proj_name = CharacterUtil.removeNull(request.getParameter("proj_name"));
            request.setAttribute("proj_name", proj_name);
            String plan_id = CharacterUtil.removeNull(request.getParameter("plan_id"));
            request.setAttribute("plan_id", plan_id);
            String budp_id = CharacterUtil.removeNull(request.getParameter("budp_id"));
            request.setAttribute("budp_id", budp_id);
            String details = CharacterUtil.removeNull(request.getParameter("details"));
            request.setAttribute("details", details);
            
            //new parameter 
            String[] strategic = request.getParameterValues("strategic");
            request.setAttribute("strategic", strategic);
            String account = CharacterUtil.removeNull(request.getParameter("account"));
            request.setAttribute("account", account);
            String department = CharacterUtil.removeNull(request.getParameter("department"));
            request.setAttribute("department", department);
            String prot_id = CharacterUtil.removeNull(request.getParameter("prot_id"));
            request.setAttribute("prot_id", prot_id);
            String yearStart = CharacterUtil.removeNull(request.getParameter("yearStart"));
            request.setAttribute("yearStart", yearStart);
            String remarks = CharacterUtil.removeNull(request.getParameter("remarks"));
            
            Employee employee = (Employee) request.getSession().getAttribute("EMPLOYEE");  
            
            p.setProjId(id);
            p.setProjName(proj_name);
            p.setProjDetail(details);
            if(status != null && !"".equals(status) && !"CANCEL".equals(status) && !"WAITING".equals(status)){                
                p.setProjStatus("PROCESSING");
            }else{      
                //After Approve Or Shift project
                p.setProjStatus("WAITING");
            }      
            p.setPlanId(plan_id);
            p.setBudpId(budp_id);  
            p.setModifiedBy(String.valueOf(employee.getEmpId()));
            
            //new parameter
            p.setProjRemark(remarks);
            p.setProjVerifyBy("");
            p.setProjVerifyDate("");//NOW
            p.setProtId(prot_id);
            p.setAccountCode(account);
            p.setStraId(Arrays.toString(strategic));
            
            ProjectWorkingDao pwDao = new ProjectWorkingDao();
            int exe = 0;
            if(id.equals("")){
                exe = projectDao.createProject(p); 
            }else{
                exe = projectDao.updateProject(p);  
            }
            
            if (exe != 0) {
                ProjectWorking pw = new ProjectWorking();
                if("".equals(id)){
                    p.setProjName(proj_name);
                    List<Project> pj = projectDao.getProject(p, 1, 0);
                     if(!pj.isEmpty()){
                        pw.setProjId(pj.get(0).getProjId());
                     }
                }else{
                    pw.setProjId(id);
                }
                 //Project History log
                ProjectHistoryDao histDao = new ProjectHistoryDao();
                ProjectHistory ph = new ProjectHistory();
                ph.setProjId(pw.getProjId());
                ph.setStatus(p.getProjStatus());
                ph.setRemarks(p.getProjRemark());
                ph.setModifiedBy(p.getModifiedBy());
                    try {
                        histDao.createProjectHistory(ph);
                    } catch (Exception e) {
                        logger.error("createProjectHistory Error "+e);
                    }
                
                String[] budget_request = request.getParameterValues("budget_request");
                String[] _yearStart = request.getParameterValues("yearStart_edit");
                int loop = budget_request.length / 12;
                int y = 0;
                int index = 0;
                    for(int i=1 ; i <= loop ; i++){  
                        
                        if(_yearStart == null){
                            y = (Integer.parseInt(yearStart)+i)-1;
                        }else{
                            y = Integer.parseInt(_yearStart[i-1]);
                        }
                           
                        pw.setBudgetYear(String.valueOf(y));
                        pw.setBudgetRequestM1(budget_request[(index+1)-1]);
                        pw.setBudgetRequestM2(budget_request[(index+2)-1]);
                        pw.setBudgetRequestM3(budget_request[(index+3)-1]);
                        pw.setBudgetRequestM4(budget_request[(index+4)-1]);
                        pw.setBudgetRequestM5(budget_request[(index+5)-1]);
                        pw.setBudgetRequestM6(budget_request[(index+6)-1]);
                        pw.setBudgetRequestM7(budget_request[(index+7)-1]);
                        pw.setBudgetRequestM8(budget_request[(index+8)-1]);
                        pw.setBudgetRequestM9(budget_request[(index+9)-1]);
                        pw.setBudgetRequestM10(budget_request[(index+10)-1]);
                        pw.setBudgetRequestM11(budget_request[(index+11)-1]);
                        pw.setBudgetRequestM12(budget_request[(index+12)-1]);
                        index=index+12; 
                        pw.setModifiedBy(p.getModifiedBy());

                        try {
                            if(id.equals("")){
                                pw.setIsFirstApprove(Boolean.TRUE);
                                pwDao.createProjectWorking(pw); 
                            }else{
                                if(!"WAITING".equals(status) && !"CANCEL".equals(status)){
                                    ProjectWorking pwk = new ProjectWorking();
                                    pwk.setProjId(id);
                                    pwk.setIsFirstApprove(Boolean.FALSE);
                                    //find firstApprove
                                    List<ProjectWorking> chkIsFirstApproveList = pwDao.getProjectWorking(pwk, 1, 0);
                                    if(!chkIsFirstApproveList.isEmpty()){
                                        pw.setIsFirstApprove(Boolean.FALSE);
                                        pwDao.updateProjectWorking(pw);
                                    }else{
                                        pw.setIsFirstApprove(Boolean.FALSE);
                                        pwDao.createProjectWorking(pw); 
                                    }  
                                }else{
                                    pw.setIsFirstApprove(Boolean.TRUE);
                                    pwDao.updateProjectWorking(pw);
                                }                                                              
                            }
                            
                        } catch (Exception e) {
                            logger.error("createProjectWorking error :"+e.getMessage());
                        }  
                    }                
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
