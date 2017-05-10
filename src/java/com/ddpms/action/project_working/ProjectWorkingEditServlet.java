/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ddpms.action.project_working;

import com.ddpms.dao.ProjectDao;
import com.ddpms.dao.ProjectWorkingDao;
import com.ddpms.model.Project;
import com.ddpms.model.ProjectWorking;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ProjectWorkingEditServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(ProjectWorkingEditServlet.class);
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("..doGet ProjectWorkingEditServlet");
        try {
            ProjectDao pjDao = new ProjectDao();
            request.setAttribute("projectList", pjDao.getProject(new Project(), 0, 0));
            ProjectWorking pw = new ProjectWorking();
            ProjectWorkingDao dao = new ProjectWorkingDao();
            String id = CharacterUtil.removeNull(request.getParameter("id"));
            pw.setProjwId(id);
            List<ProjectWorking> list = new ArrayList<ProjectWorking>();
            list = dao.getProjectWorking(pw, 0, 0);
            if(!list.isEmpty()){
                request.setAttribute("projw_id", list.get(0).getProjwId());
                request.setAttribute("proj_id",list.get(0).getProjId());
                request.setAttribute("budget_year",list.get(0).getBudgetYear());
                //request.setAttribute("budget_request",list.get(0).getBudgetRequest());
                //request.setAttribute("budget_response",list.get(0).getBudgetResponse());
                request.setAttribute("budget_usage",list.get(0).getBudgetUsage());
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/project-working/project-working-form.jsp");
            dispatcher.forward(request, response);
            
        } catch (Exception e) {
            logger.error("ProjectWorkingEditServlet Error : "+e.getMessage());
        }
        
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...doPost ProjectWorkingEditServlet");
        try {
            
        } catch (Exception e) {
            logger.error(" ProjectWorkingEditServlet Error : "+e.getMessage());
        }
    }

}
