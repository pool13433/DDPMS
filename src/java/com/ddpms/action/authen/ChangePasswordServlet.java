/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ddpms.action.authen;

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

public class ChangePasswordServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(ChangePasswordServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MessageUI message = null;
        try {
            Employee user = (Employee) request.getSession().getAttribute("EMPLOYEE");
            String passwordOld = CharacterUtil.removeNull(request.getParameter("passwordOld"));
            String passwordNew = CharacterUtil.removeNull(request.getParameter("passwordNew"));
            String passwordNewConfirm = CharacterUtil.removeNull(request.getParameter("passwordNewConfirm"));
            String username = user.getUsername();
            EmployeeDao dao = new EmployeeDao();
            Employee employee = dao.getEmployee(username, passwordOld);
            if (employee == null) {
                message = new MessageUI(true, "สถานะการแก้ไขรหัสผ่านใหม่", "คุณกรอกหรัสผ่านเดิมไม่ถูกต้อง", "danger");
            } else {
              if (!passwordNew.equals(passwordNewConfirm)) {
                    message = new MessageUI(true, "สถานะการแก้ไขรหัสผ่านใหม่", "คุณกรอกหรัสผ่านใหม่ไม่ตรงกัน", "danger");
                } else {
                   int empId = Integer.parseInt(user.getEmpId());
                    int exec = dao.updatePassword(passwordNew, empId, empId);
                    if (exec == 0) {
                        message = new MessageUI(true, "สถานะการแก้ไขรหัสผ่านใหม่", "เกิดข้อผิดพลาดในขั้นตอนการบันทีกข้อมูล", "danger");
                    } else {
                        message = new MessageUI(true, "สถานะการแก้ไขรหัสผ่านใหม่", "บันทีกข้อมูลสำเร็จ", "info");
                        Employee employeeAfterChangePassword = dao.getEmployee(username, passwordNew);
                        request.getSession().setAttribute("EMPLOYEE", employeeAfterChangePassword);
                    }
                }
            }
            request.getSession().setAttribute("MessageUI", message);
        } catch (Exception e) {
            logger.error("ChangePasswordServlet error", e);
        }
        response.sendRedirect(request.getContextPath() + "/jsp/authen/password.jsp");
    }
}
