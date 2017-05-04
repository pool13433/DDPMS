package com.ddpms.action.config;

import com.ddpms.dao.ConfigDao;
import com.ddpms.model.Pagination;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ConfigListServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(ConfigListServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int limit = CharacterUtil.removeNullTo(request.getParameter("limit"), 10);
            int offset = CharacterUtil.removeNullTo(request.getParameter("offset"), 0);
            String pageUrl = request.getContextPath() + "/ConfigListServlet?" + request.getQueryString();
            
            ConfigDao dao = new ConfigDao();
            int countRecordAll = dao.getCountConfig();            
            request.setAttribute("configList", new ConfigDao().getAllConfig(limit, offset));
            Pagination pagination = new Pagination(pageUrl, countRecordAll, limit, offset);
            request.setAttribute("pagination", pagination);
        } catch (Exception e) {
            logger.error("ConfigListServlet error", e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/config/config-search.jsp");
        dispatcher.forward(request, response);
    }

}
