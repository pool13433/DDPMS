
package com.ddpms.action.project.type;

import com.ddpms.dao.ConfigDao;
import com.ddpms.dao.ProjectTypeDao;
import com.ddpms.model.Pagination;
import com.ddpms.model.ProjectType;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ProjectTypeListServlet extends HttpServlet {

     final static Logger logger = Logger.getLogger(ProjectTypeListServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int limit = CharacterUtil.removeNullTo(request.getParameter("limit"), 10);
            int offset = CharacterUtil.removeNullTo(request.getParameter("offset"), 0);
            String pageUrl = request.getContextPath() + "/ProjectTypeListServlet?" + request.getQueryString();
            
            String protCode = CharacterUtil.removeNull(request.getParameter("protCode"));
            String protName = CharacterUtil.removeNull(request.getParameter("protName"));
            String protType = CharacterUtil.removeNull(request.getParameter("protType"));
            ProjectType criteria = new ProjectType();
            criteria.setProtCode(protCode);
            criteria.setProtName(protName);
            criteria.setProtType(protType);
            
            ProjectTypeDao dao = new ProjectTypeDao();
            String sqlCondition = dao.getConditionBuilder(criteria);
            int countRecordAll = dao.getCountProjectType(sqlCondition);
            request.setAttribute("projectTypeList", dao.getProjectTypeAll(limit, offset,sqlCondition));
            Pagination pagination = new Pagination(pageUrl, countRecordAll, limit, offset);
            request.setAttribute("pagination", pagination);
            request.setAttribute("criteria", criteria);
            request.setAttribute("projectGroupList", new ConfigDao().getConfigMap("PROJECT_GROUP"));
        } catch (Exception e) {
            logger.error("ProjectTypeListServlet error", e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/project-type/projectType-search.jsp");
        dispatcher.forward(request, response);
    }
}
