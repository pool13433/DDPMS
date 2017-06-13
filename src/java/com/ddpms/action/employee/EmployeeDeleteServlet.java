package com.ddpms.action.employee;

import com.ddpms.dao.EmployeeDao;
import com.ddpms.model.MessageUI;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class EmployeeDeleteServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(EmployeeDeleteServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MessageUI message = null;
        try {
            int exec = new EmployeeDao().deleteEmployee(Integer.parseInt(CharacterUtil.removeNull(request.getParameter("empId"))));
            
            if (exec == 0) {
                message = new MessageUI(false, "สถานะการลบข้อมูล", "เกิดข้อผิดพลาดในขั้นตอนการลบข้อมูล", "danger");
            } else {
                message = new MessageUI(true, "สถานะการลบข้อมูล", "ลบข้อมูลสำเร็จ", "info");
            }            
        } catch (Exception e) {
            logger.error("EmployeeDeleteServlet error", e);      
             message = new MessageUI(false, "สถานะการลบข้อมูล", "เกิดข้อผิดพลาดในขั้นตอนการลบข้อมูล", "danger");
        }
        request.getSession().setAttribute("MessageUI", message);
        response.sendRedirect(request.getContextPath() + "/EmployeeListServlet?menu=employee");
    }
}
