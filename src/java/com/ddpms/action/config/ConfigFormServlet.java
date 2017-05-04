package com.ddpms.action.config;

import com.ddpms.dao.ConfigDao;
import com.ddpms.model.Config;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ConfigFormServlet extends HttpServlet {
    final static Logger logger = Logger.getLogger(ConfigSaveServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String confId = CharacterUtil.removeNull(request.getParameter("confId"));
            Config config = null;
            if(confId.equals("")){ // new 
                config = new Config();
            }else{
                config = new ConfigDao().getConfig(confId);
            }
            request.setAttribute("config", config);
        } catch (Exception e) {
            logger.error("ConfigFormServlet error", e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/config/config-form.jsp");
        dispatcher.forward(request, response);
    }
    
}
