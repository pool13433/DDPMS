package com.ddpms.action.employee;

import com.ddpms.dao.ConfigDao;
import com.ddpms.dao.DepartmentDao;
import com.ddpms.dao.EmployeeDao;
import com.ddpms.model.Employee;
import com.ddpms.model.Pagination;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class EmployeeListServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(EmployeeListServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int limit = CharacterUtil.removeNullTo(request.getParameter("limit"), 10);
            int offset = CharacterUtil.removeNullTo(request.getParameter("offset"), 0);
            String pageUrl = request.getContextPath() + "/EmployeeListServlet?" + request.getQueryString();

            String empId = CharacterUtil.removeNull(request.getParameter("empId"));
            String empCode = CharacterUtil.removeNull(request.getParameter("empCode"));
            String username = CharacterUtil.removeNull(request.getParameter("username"));
            String password = CharacterUtil.removeNull(request.getParameter("password"));
            String fname = CharacterUtil.removeNull(request.getParameter("fname"));
            String lname = CharacterUtil.removeNull(request.getParameter("lname"));
            String mobile = CharacterUtil.removeNull(request.getParameter("mobile"));
            String email = CharacterUtil.removeNull(request.getParameter("email"));
            String gender = CharacterUtil.removeNull(request.getParameter("gender"));
            String department = CharacterUtil.removeNull(request.getParameter("department"));
            String status = CharacterUtil.removeNull(request.getParameter("status"));
            Employee criteria = new Employee();
            criteria.setDepId(CharacterUtil.removeNullTo(department, -99));
            criteria.setDepName(lname);
            criteria.setEmpCode(empCode);
            criteria.setEmpEmail(email);
            criteria.setEmpFname(fname);
            criteria.setEmpId(empId);
            criteria.setEmpLname(lname);
            criteria.setEmpMobile(mobile);
            criteria.setGender(gender);
            criteria.setPassword(password);
            criteria.setStatus(status);
            criteria.setTitle(status);
            criteria.setUsername(username);

            EmployeeDao dao = new EmployeeDao();
            String sqlCondition = dao.getConditionBuilder(criteria);
            int countRecordAll = dao.getCountEmployee(sqlCondition);
            request.setAttribute("employeeList", dao.getEmployeeList(limit, offset, sqlCondition));
            Pagination pagination = new Pagination(pageUrl, countRecordAll, limit, offset);
            request.setAttribute("pagination", pagination);
            request.setAttribute("criteria", criteria);
            request.setAttribute("departmentList", new DepartmentDao().getDepartmentAll());
            request.setAttribute("employeeStatusList", new ConfigDao().getConfigList("EMPLOYEE_STATUS"));
            request.setAttribute("genderList", new ConfigDao().getConfigList("GENDER"));
        } catch (Exception e) {
            logger.error("EmployeeListServlet error", e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/employee/employee-search.jsp");
        dispatcher.forward(request, response);
    }
}
