package com.ddpms.action.department;

import com.ddpms.dao.DepartmentDao;
import com.ddpms.dao.ProjectTypeDao;
import com.ddpms.model.Department;
import com.ddpms.model.Employee;
import com.ddpms.model.MessageUI;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class DepartmentSaveServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(DepartmentSaveServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Employee employee = (Employee) request.getSession().getAttribute("EMPLOYEE");
            String depId=  CharacterUtil.removeNull(request.getParameter("depId"));
            String depName = CharacterUtil.removeNull(request.getParameter("depName"));
            String depAccount = CharacterUtil.removeNull(request.getParameter("depAccount"));            
            Department department = new Department();
            department.setDepAccount(depAccount);            
            department.setDepName(depName);
            department.setModifiedBy(String.valueOf(employee.getEmpId()));
            DepartmentDao dao = new DepartmentDao();
            int exec = 0;
            if (depId.equals("")) {
                exec = dao.createDepartment(department);
            } else {
                department.setDepId(depId);
                exec = dao.updateDepartment(department);
            }
            MessageUI message = null;
            if (exec == 0) {
                message = new MessageUI(true, "สถานะการบันทึกข้อมูล", "เกิดข้อผิดพลาดในขั้นตอนการบันทึกข้อมูล", "danger");
            } else {
                message = new MessageUI(true, "สถานะการบันทึกข้อมูล", "บันทึกข้อมูลสำเร็จ", "info");
            }
            request.getSession().setAttribute("MessageUI", message);
        } catch (Exception e) {
            logger.error("DepartmentSaveServlet", e);
        }
        response.sendRedirect(request.getContextPath() + "/DepartmentListServlet?menu=department");
    }

}
