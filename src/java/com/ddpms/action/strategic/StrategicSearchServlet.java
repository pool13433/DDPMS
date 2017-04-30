
package com.ddpms.action.strategic;

import com.ddpms.dao.ProjectDao;
import com.ddpms.dao.StrategicDao;
import com.ddpms.model.Pagination;
import com.ddpms.model.Project;
import com.ddpms.model.Strategic;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class StrategicSearchServlet extends HttpServlet {
final static Logger logger = Logger.getLogger(StrategicSearchServlet.class);

@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...goGet StrategicSearchServlet");
        try {
            String menu = CharacterUtil.removeNull(request.getParameter("menu"));
            StrategicDao dao = new StrategicDao();
            Strategic s = new  Strategic();
            ProjectDao pjDao = new ProjectDao();
            request.setAttribute("projectList", pjDao.getProject(new Project(), 0, 0));
            int limit = CharacterUtil.removeNullTo(request.getParameter("limit"), 5);
            int offset = CharacterUtil.removeNullTo(request.getParameter("offset"), 0);
            String stra_name = CharacterUtil.removeNull(request.getParameter("stra_name"));
            request.setAttribute("stra_name", stra_name);
            String pageUrl = request.getContextPath() + "/StrategicSearchServlet?" + request.getQueryString();
            String sqlConditionBuilder = dao.getConditionBuilder(s);
            int countRecordAll = dao.getCountStrategic(sqlConditionBuilder);
            
             if("searching".equals(menu)){
                s.setStraName(stra_name);
                
                request.setAttribute("strategicList", dao.getStrategic(s, limit, offset));                
            }else{
                request.setAttribute("strategicList", dao.getStrategic(new Strategic(), limit, offset));
            }
            Pagination pagination = new Pagination(pageUrl, countRecordAll, limit, offset);
            request.setAttribute("pagination", pagination);
                
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/strategic/strategic-search.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            logger.error("StrategicSearchServlet Error : "+e.getMessage());
        }
    }
}
