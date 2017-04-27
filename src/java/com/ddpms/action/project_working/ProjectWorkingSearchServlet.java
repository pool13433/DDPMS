/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ddpms.action.project_working;

import com.ddpms.dao.ProjectDao;
import com.ddpms.dao.ProjectWorkingDao;
import com.ddpms.model.Pagination;
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

public class ProjectWorkingSearchServlet extends HttpServlet {
 
final static Logger logger = Logger.getLogger(ProjectWorkingSearchServlet.class);
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...doGet ProjectWorkingSearchServlet");
        try {
            String menu = CharacterUtil.removeNull(request.getParameter("menu"));
            ProjectWorking pw = new ProjectWorking();
            ProjectWorkingDao dao = new ProjectWorkingDao();
            int limit = CharacterUtil.removeNullTo(request.getParameter("limit"), 5);
            int offset = CharacterUtil.removeNullTo(request.getParameter("offset"), 0);
            String proj_id = CharacterUtil.removeNull(request.getParameter("proj_id"));
            request.setAttribute("proj_id", proj_id);
            String budget_year = CharacterUtil.removeNull(request.getParameter("budget_year"));
            request.setAttribute("budget_year", budget_year);
            String budget_request = CharacterUtil.removeNull(request.getParameter("budget_request"));
            request.setAttribute("budget_request", budget_request);
            String budget_response = CharacterUtil.removeNull(request.getParameter("budget_response"));
            request.setAttribute("budget_response", budget_response);
            String budget_usage = CharacterUtil.removeNull(request.getParameter("budget_usage"));
            request.setAttribute("budget_usage", budget_usage);
            String pageUrl = request.getContextPath() + "/ProjectWorkingSearchServlet?" + request.getQueryString();
            String sqlConditionBuilder = dao.getConditionBuilder(pw);
            int countRecordAll = dao.getCountProjectWorking(sqlConditionBuilder);
            ProjectDao pjDao = new ProjectDao();
            request.setAttribute("projectList", pjDao.getProject(new Project(), 0, 0));
            if("searching".equals(menu)){
                pw.setProjId(proj_id);
                pw.setBudgetYear(budget_year);
                pw.setBudgetRequest(budget_request);
                pw.setBudgetResponse(budget_response);
                pw.setBudgetUsage(budget_usage);                  
                
                request.setAttribute("projectWorkingList", dao.getProjectWorking(pw, limit, offset));                
            }else{
                request.setAttribute("projectWorkingList", dao.getProjectWorking(new ProjectWorking(), limit, offset));
            }
            Pagination pagination = new Pagination(pageUrl, countRecordAll, limit, offset);
            request.setAttribute("pagination", pagination);
                
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/project-working/project-working-search.jsp");
            dispatcher.forward(request, response);
            
        } catch (Exception e) {
            logger.error("ProjectWorkingSearchServlet Error : "+e.getMessage());
        }
        
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...doPost ProjectWorkingSearchServlet");
        try {
            
        } catch (Exception e) {
            logger.error("ProjectWorkingSearchServlet Error : "+e.getMessage());
        }
    }

}
