
package com.ddpms.action.project_working;

import com.ddpms.dao.DepartmentDao;
import com.ddpms.dao.ProjectDao;
import com.ddpms.dao.ProjectWorkingDao;
import com.ddpms.model.MessageUI;
import com.ddpms.model.Project;
import com.ddpms.model.ProjectWorking;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ProjectWorkingAddServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(ProjectWorkingAddServlet.class);
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...doGet ProjectWorkingAddServlet");
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/project-working/project-working-form.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            logger.error("ProjectWorkingAddServlet Error : "+e.getMessage());
        }
        
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...doPost ProjectWorkingAddServlet");
        try {
            ProjectWorking pw = new ProjectWorking();
            ProjectWorkingDao dao = new ProjectWorkingDao();
            String id = CharacterUtil.removeNull(request.getParameter("id"));
            String proj_id = CharacterUtil.removeNull(request.getParameter("proj_id"));
            request.setAttribute("proj_id", proj_id);
            String budget_year = CharacterUtil.removeNull(request.getParameter("budget_year"));
            request.setAttribute("budget_year", budget_year);            
            String budget_usage = CharacterUtil.removeNull(request.getParameter("budget_usage"));
            request.setAttribute("budget_usage", budget_usage);
            ProjectDao pjDao = new ProjectDao();
            request.setAttribute("projectList", pjDao.getProject(new Project(), 0, 0));
            String[] budget_request = request.getParameterValues("budget_request");
            String budget_request_m="";
            for(int i=0 ; i<budget_request.length ; i++){
                budget_request_m = CharacterUtil.removeNull(request.getParameter("budget_request"));
            }
            //String budget_request = CharacterUtil.removeNull(request.getParameter("budget_request"));
            request.setAttribute("budget_request", budget_request);
            String budget_response = CharacterUtil.removeNull(request.getParameter("budget_response"));
            request.setAttribute("budget_response", budget_response);
            
            pw.setProjwId(id);
            pw.setProjId(proj_id);
            pw.setBudgetYear(budget_year);
            //pw.setBudgetRequest(budget_request);
            //pw.setBudgetResponse(budget_response);
            pw.setBudgetUsage(budget_usage);
            pw.setModifiedBy("1");
            
            int exe = 0;
            if(id.equals("")){
                //exe = dao.createProjectWorking(pw);
            }else{
                //exe = dao.updateProjectWorking(pw);
            }
            MessageUI message = null;
            if (exe == 0) {
                message = new MessageUI(true, "สถานะการบันทีกข้อมูล", "เกิดข้อผิดพลาดในขั้นตอนการบันทีกข้อมูล", "danger");
            } else {
                message = new MessageUI(true, "สถานะการบันทีกข้อมูล", "บันทีกข้อมูลสำเร็จ", "info");
            }        
            request.getSession().setAttribute("MessageUI", message);
            response.sendRedirect(request.getContextPath() + "/ProjectWorkingSearchServlet");
        } catch (Exception e) {
            logger.error("ProjectWorkingAddServlet Error : "+e.getMessage());
        }
    }

}
