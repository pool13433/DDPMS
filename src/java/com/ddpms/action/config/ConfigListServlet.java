package com.ddpms.action.config;

import com.ddpms.dao.ConfigDao;
import com.ddpms.model.Config;
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
            
            String confCode = CharacterUtil.removeNull(request.getParameter("confCode"));
            String confName = CharacterUtil.removeNull(request.getParameter("confName"));
            String confValue = CharacterUtil.removeNull(request.getParameter("confValue"));
            Config config = new Config();
            config.setConfCode(confCode);
            config.setConfName(confName);
            config.setConfValue(confValue);
            
            ConfigDao dao = new ConfigDao();
            String sqlCondition = dao.getConditionBuilder(config);
            int countRecordAll = dao.getCountConfig(sqlCondition);            
            request.setAttribute("configList", dao.getAllConfig(limit, offset,sqlCondition));
            Pagination pagination = new Pagination(pageUrl, countRecordAll, limit, offset);
            request.setAttribute("pagination", pagination);
            request.setAttribute("criteria", config);
        } catch (Exception e) {
            logger.error("ConfigListServlet error", e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/config/config-search.jsp");
        dispatcher.forward(request, response);
    }

}
