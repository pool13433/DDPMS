
package com.ddpms.action.project_shift;

import com.ddpms.dao.ProjectDao;
import com.ddpms.dao.ProjectShiftDao;
import com.ddpms.model.Pagination;
import com.ddpms.model.Project;
import com.ddpms.model.ProjectShift;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ProjectShiftSearchServlet extends HttpServlet {
    final static Logger logger = Logger.getLogger(ProjectShiftSearchServlet.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...doGet ProjectShiftSearchServlet");
        try {
            ProjectShift ps = new ProjectShift();
            ProjectShiftDao dao = new  ProjectShiftDao();
            String menu = CharacterUtil.removeNull(request.getParameter("menu"));
            int limit = CharacterUtil.removeNullTo(request.getParameter("limit"), 5);
            int offset = CharacterUtil.removeNullTo(request.getParameter("offset"), 0);
            String proj_id = CharacterUtil.removeNull(request.getParameter("proj_id"));
            request.setAttribute("proj_id", proj_id);
            String projs_reason = CharacterUtil.removeNull(request.getParameter("projs_reason"));
            request.setAttribute("projs_reason", projs_reason);
            String projs_plan = CharacterUtil.removeNull(request.getParameter("projs_plan"));
            request.setAttribute("projs_plan", projs_plan);
            String pageUrl = request.getContextPath() + "/ProjectWorkingSearchServlet?" + request.getQueryString();
            String sqlConditionBuilder = dao.getConditionBuilder(ps);
            int countRecordAll = dao.getCountProjectShift(sqlConditionBuilder);
            ProjectDao pjDao = new ProjectDao();
            request.setAttribute("projectList", pjDao.getProject(new Project(), 0, 0));
            if("searching".equals(menu)){
                ps.setProjId(proj_id);
                ps.setProjsReason(projs_reason);
                ps.setProjsPlan(projs_plan);
                ps.setModifiedBy("1");
                
                request.setAttribute("projectWorkingList", dao.getProjectShift(ps, limit, offset));      
            }else{
                request.setAttribute("projectWorkingList", dao.getProjectShift(new ProjectShift(), limit, offset));   
            }
            Pagination pagination = new Pagination(pageUrl, countRecordAll, limit, offset);
            request.setAttribute("pagination", pagination);
                
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/project-shift/project-shift-search.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            logger.error("ProjectShiftSearchServlet Error : "+e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...doPost ProjectShiftSearchServlet");
        try {
            
        } catch (Exception e) {
            logger.error("ProjectShiftSearchServlet Error : "+e.getMessage());
        }
    }

}
