package com.ddpms.action.employee;

import com.ddpms.dao.ConfigDao;
import com.ddpms.dao.DepartmentDao;
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

public class EmployeeFormServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(EmployeeFormServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String empId = CharacterUtil.removeNull(request.getParameter("empId"));
            Employee employee = null;
            if (empId.equals("")) { // new 
                employee = new Employee();
            } else {
                employee = new EmployeeDao().getEmployee(Integer.parseInt(empId));
            }
            request.setAttribute("employee", employee);
            request.setAttribute("departmentList", new DepartmentDao().getDepartmentAll());
            request.setAttribute("employeeStatusList", new ConfigDao().getConfigList("EMPLOYEE_STATUS"));
            request.setAttribute("genderList", new ConfigDao().getConfigList("GENDER"));
        } catch (Exception e) {
            logger.error("EmployeeFormServlet error", e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/employee/employee-form.jsp");
        dispatcher.forward(request, response);
    }
}
