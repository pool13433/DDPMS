
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
            String projs_plan_date = CharacterUtil.removeNull(request.getParameter("projs_plan_date"));
            request.setAttribute("projs_plan_date", projs_plan_date);
            String pageUrl = request.getContextPath() + "/ProjectShiftSearchServlet?" + request.getQueryString();
            String sqlConditionBuilder = dao.getConditionBuilder(ps);
            int countRecordAll = dao.getCountProjectShift(sqlConditionBuilder);
            ProjectDao pjDao = new ProjectDao();
            request.setAttribute("projectList", pjDao.getProject(new Project(), 0, 0));
            if("searching".equals(menu)){
                ps.setProjId(proj_id);
                ps.setProjsReason(projs_reason);
                ps.setProjsPlanDate(projs_plan_date);
                ps.setModifiedBy("1");
                request.setAttribute("projectShiftList", dao.getProjectShift(ps, limit, offset));
                Project p = new Project();
                if(!"".equals(CharacterUtil.removeNull(proj_id))){
                    p.setProjId(proj_id);
                    request.setAttribute("projectSearchList", pjDao.getProject(p, 0, 0));
                }else{
                    request.setAttribute("projectSearchList", null);
                }
                
               
            }else{
                request.setAttribute("projectShiftList", dao.getProjectShift(new ProjectShift(), limit, offset));   
                request.setAttribute("projectSearchList", null);
            }
            Pagination pagination = new Pagination(pageUrl, countRecordAll, limit, offset);
            request.setAttribute("pagination", pagination);
                
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/project/projectshift-search.jsp");
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
