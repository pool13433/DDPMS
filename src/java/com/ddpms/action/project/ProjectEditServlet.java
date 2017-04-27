
package com.ddpms.action.project;

import com.ddpms.dao.ProjectDao;
import com.ddpms.model.Project;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ProjectEditServlet extends HttpServlet {
final static Logger logger = Logger.getLogger(ProjectEditServlet.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...doGet ProjectEditServlet");
        try {
            Project p = new Project();
            ProjectDao projectDao = new ProjectDao();
            //Profile profile = (Profile)request.getSession().getAttribute("USER_PROFILE");
            String id = CharacterUtil.removeNull(request.getParameter("id"));
            p.setProjId(id);
            List<Project> list = projectDao.getProject(p, 0, 0);
            if(!list.isEmpty()){
                request.setAttribute("proj_id", list.get(0).getProjId());
                request.setAttribute("proj_name",list.get(0).getProjName());
                request.setAttribute("proj_details",list.get(0).getProjDetails());
                request.setAttribute("proj_status",list.get(0).getProjStatus());
                request.setAttribute("plan_id",list.get(0).getPlanId());
                request.setAttribute("budp_id",list.get(0).getBudpId());
            }
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/project/project-form.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            logger.error("ProjectEditServlet Error : "+e.getMessage());
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...doPost ProjectEditServlet");
        try {
            
        } catch (Exception e) {
        }
       
    }

}
