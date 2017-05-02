
package com.ddpms.action.project;

import com.ddpms.dao.ProjectDao;
import com.ddpms.dao.ProjectExpenseDao;
import com.ddpms.model.MessageUI;
import com.ddpms.model.Project;
import com.ddpms.model.ProjectExpense;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
            String proj_status = CharacterUtil.removeNull(request.getParameter("proj_status"));
            request.setAttribute("proj_status", proj_status);
            String plan_id = CharacterUtil.removeNull(request.getParameter("plan_id"));
            request.setAttribute("plan_id", plan_id);
            String budp_id = CharacterUtil.removeNull(request.getParameter("budp_id"));
            request.setAttribute("budp_id", budp_id);
            
            p.setProjId(id);
            p.setProjName(proj_name);
            p.setProjDetails(proj_details);
            p.setProjStatus(proj_status);
            p.setPlanId(plan_id);
            p.setBudpId(budp_id);  
            p.setModifiedBy("1");
            
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
