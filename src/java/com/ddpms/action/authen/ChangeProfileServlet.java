package com.ddpms.action.authen;

import com.ddpms.dao.ConfigDao;
import com.ddpms.dao.DepartmentDao;
import com.ddpms.dao.EmployeeDao;
import com.ddpms.model.Department;
import com.ddpms.model.Employee;
import com.ddpms.model.MessageUI;
import com.ddpms.util.CharacterUtil;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MessageUI message = null;
        try {
            Employee sessionEmp = (Employee) request.getSession().getAttribute("EMPLOYEE");
            String empCode = CharacterUtil.removeNull(request.getParameter("empCode"));
            String empFname = CharacterUtil.removeNull(request.getParameter("empFname"));
            String empLname = CharacterUtil.removeNull(request.getParameter("empLname"));
            String empEmail = CharacterUtil.removeNull(request.getParameter("empEmail"));
            String empMobile = CharacterUtil.removeNull(request.getParameter("empMobile"));
            String empGender = CharacterUtil.removeNull(request.getParameter("empGender"));
            String empTitle = CharacterUtil.removeNull(request.getParameter("empTitle"));
            String depId = CharacterUtil.removeNull(request.getParameter("depId"));
            Employee employee = new Employee();
            employee.setDepId(Integer.parseInt(depId));
            employee.setEmpCode(empCode);
            employee.setEmpEmail(empEmail);
            employee.setEmpFname(empFname);
            employee.setEmpId(sessionEmp.getEmpId());
            employee.setEmpLname(empLname);
            employee.setEmpMobile(empMobile);
            employee.setGender(empGender);
            employee.setModifiedBy(String.valueOf(sessionEmp.getEmpId()));
            employee.setTitle(empTitle);
            EmployeeDao dao = new EmployeeDao();
            int exec = dao.updateProfile(employee);
            if (exec == 0) {
                message = new MessageUI(true, "สถานะการแก้ไขรหัสผ่านใหม่", "เกิดข้อผิดพลาดในขั้นตอนการบันทีกข้อมูล", "danger");
            } else {
                Employee e = dao.getEmployee(Integer.parseInt(sessionEmp.getEmpId()));
                request.getSession().setAttribute("EMPLOYEE", e);
                message = new MessageUI(true, "สถานะการแก้ไขรหัสผ่านใหม่", "บันทีกข้อมูลสำเร็จ", "info");
            }
            request.getSession().setAttribute("MessageUI", message);
        } catch (Exception e) {
            logger.error("ChangeProfileServlet error", e);
        }
        response.sendRedirect(request.getContextPath() + "/jsp/authen/profile.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Department> departmentList = new DepartmentDao().getDepartmentAll();
            Employee profile = (Employee) request.getSession().getAttribute("EMPLOYEE");
            request.setAttribute("departmentList", departmentList);
            request.setAttribute("profile", profile);
            request.setAttribute("genderList", new ConfigDao().getConfigMap("GENDER"));
        } catch (Exception e) {
            logger.error("change profile error", e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/authen/profile.jsp");
        dispatcher.forward(request, response);
    }

}
