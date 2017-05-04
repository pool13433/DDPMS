
package com.ddpms.action.projecttype;

import com.ddpms.dao.ProjectTypeDao;
import com.ddpms.model.Pagination;
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
            
            ProjectTypeDao dao = new ProjectTypeDao();
            int countRecordAll = dao.getCountProjectType();
            request.setAttribute("projectTypeList", dao.getProjectTypeAll(limit, offset));
            Pagination pagination = new Pagination(pageUrl, countRecordAll, limit, offset);
            request.setAttribute("pagination", pagination);
        } catch (Exception e) {
            logger.error("ProjectTypeListServlet error", e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/project-type/projectType-search.jsp");
        dispatcher.forward(request, response);
    }
}
