/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ddpms.action.authen;

import com.ddpms.dao.EmployeeDao;
import com.ddpms.model.Employee;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author POOL_LAPTOP
 */
public class LoginServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(LoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("LoginServlet");
        try {
            String username = CharacterUtil.removeNull(request.getParameter("username"));
            String password = CharacterUtil.removeNull(request.getParameter("password"));
            logger.debug("username ::=="+username);
            logger.debug("password ::=="+password);
            Employee employee = new EmployeeDao().getEmployee(username, password);
            if (employee == null) {
                request.setAttribute("status", "cannot find user in system");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/login.jsp");
                dispatcher.forward(request, response);
            } else {
                request.getSession().setAttribute("EMPLOYEE", employee);
                request.setAttribute("status", "login success");
                response.sendRedirect(request.getContextPath() + "/jsp/dashboard.jsp?menu=dashboard");
            }
        } catch (Exception e) {
            logger.error("login error", e);
        }
    }

}
