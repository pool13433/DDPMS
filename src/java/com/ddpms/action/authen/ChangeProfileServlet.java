/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ddpms.action.authen;

import com.ddpms.dao.DepartmentDao;
import com.ddpms.model.Department;
import com.ddpms.model.Employee;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ChangeProfileServlet extends HttpServlet {
    final static Logger logger = Logger.getLogger(ChangeProfileServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Department> departmentList = new DepartmentDao().getDepartmentAll();
            Employee profile = (Employee) request.getSession().getAttribute("EMPLOYEE");
            request.setAttribute("departmentList", departmentList);
            request.setAttribute("profile", profile);
        } catch (Exception e) {
            logger.error("change profile error", e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/authen/profile.jsp");
        dispatcher.forward(request, response);
    }
        
}
