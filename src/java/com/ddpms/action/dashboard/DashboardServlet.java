/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ddpms.action.dashboard;

import com.ddpms.dao.ProjectHistoryDao;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class DashboardServlet extends HttpServlet {
    final static Logger logger = Logger.getLogger(DashboardServlet.class);
        
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...goGet DashboardServlet");
        try {
            ProjectHistoryDao hisDao = new ProjectHistoryDao();
            request.setAttribute("sumStatusProjectList", hisDao.getSumProjectStatus()); 
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/dashboard.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            logger.error("DashboardServlet Error : "+e.getMessage());
        }
    }
}
