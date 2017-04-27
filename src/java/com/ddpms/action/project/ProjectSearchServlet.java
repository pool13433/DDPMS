
package com.ddpms.action.project;

import com.ddpms.dao.ProjectDao;
import com.ddpms.model.Pagination;
import com.ddpms.model.Project;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;


public class ProjectSearchServlet extends HttpServlet {
    
    final static Logger logger = Logger.getLogger(ProjectSearchServlet.class);
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...doGet ProjectSearchServlet");
        try {
            String menu = CharacterUtil.removeNull(request.getParameter("menu"));
            Project p = new Project();
            ProjectDao projectDao = new ProjectDao();
            int limit = CharacterUtil.removeNullTo(request.getParameter("limit"), 5);
            int offset = CharacterUtil.removeNullTo(request.getParameter("offset"), 0);
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
            String pageUrl = request.getContextPath() + "/ProjectSearchServlet?" + request.getQueryString();
            String sqlConditionBuilder = projectDao.getConditionBuilder(p);
            int countRecordAll = projectDao.getCountProject(sqlConditionBuilder);
            
            if("searching".equals(menu)){
                p.setProjName(proj_name);
                p.setProjDetails(proj_details);
                p.setProjStatus(proj_status);
                p.setPlanId(plan_id);
                p.setBudpId(budp_id);                        
                
                request.setAttribute("projectList", projectDao.getProject(p, limit, offset));                
            }else{
                request.setAttribute("projectList", projectDao.getProject(new Project(), limit, offset));
            }
            Pagination pagination = new Pagination(pageUrl, countRecordAll, limit, offset);
            request.setAttribute("pagination", pagination);
                
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/project/project-search.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            logger.error("ProjectSearchServlet Error : "+e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       logger.debug("...doPost ProjectSearchServlet");
        try {
            
        } catch (Exception e) {
             logger.error("ProjectSearchServlet Error : "+e.getMessage());
        }
    }

}
