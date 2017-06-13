package com.ddpms.action.employee;

import com.ddpms.dao.EmployeeDao;
import com.ddpms.model.Employee;
import com.ddpms.model.MessageUI;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class EmployeeSaveServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(EmployeeSaveServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Employee sesEmp = (Employee) request.getSession().getAttribute("EMPLOYEE");
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
            Employee employee = new Employee();
            employee.setEmpCode(empCode);
            employee.setDepId(Integer.parseInt(department));
            employee.setEmpEmail(email);
            employee.setEmpFname(fname);            
            employee.setEmpLname(lname);
            employee.setEmpMobile(mobile);
            employee.setGender(gender);
            employee.setPassword(password);
            employee.setStatus(status);
            employee.setUsername(username);
            employee.setTitle("xxxxxx");
            employee.setModifiedBy(String.valueOf(sesEmp.getEmpId()));
            EmployeeDao dao = new EmployeeDao();
            int exec = 0;
            if (empId.equals("")) {
                exec = dao.creteEmployee(employee);
            } else {
                employee.setEmpId(empId);
                exec = dao.updateEmployee(employee);
            }
            MessageUI message = null;
            if (exec == 0) {
                message = new MessageUI(true, "สถานะการบันทึกข้อมูล", "เกิดข้อผิดพลาดในขั้นตอนการบันทึกข้อมูล", "danger");
            } else {
                message = new MessageUI(true, "สถานะการบันทึกข้อมูล", "บันทึกข้อมูลสำเร็จ", "info");
            }
            request.getSession().setAttribute("MessageUI", message);
        } catch (Exception e) {
            logger.error("EmployeeSaveServlet", e);
        }
        response.sendRedirect(request.getContextPath() + "/EmployeeListServlet?menu=employee");
    }
}
