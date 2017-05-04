
package com.ddpms.action.department;

import com.ddpms.dao.DepartmentDao;
import com.ddpms.model.MessageUI;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class DepartmentDeleteServlet extends HttpServlet {
    final static Logger logger = Logger.getLogger(DepartmentDeleteServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int exec = new DepartmentDao().deleteDepartment(Integer.parseInt(CharacterUtil.removeNull(request.getParameter("depId"))));
            MessageUI message = null;
            if (exec == 0) {
                message = new MessageUI(true, "สถานะการลบข้อมูล", "เกิดข้อผิดพลาดในขั้นตอนการลบข้อมูล", "danger");
            } else {
                message = new MessageUI(true, "สถานะการลบข้อมูล", "ลบข้อมูลสำเร็จ", "info");
            }
            request.getSession().setAttribute("MessageUI", message);
        } catch (Exception e) {
            logger.error("ProjectTypeDeleteServlet error", e);
            request.setAttribute("message", "delete project type error");
        }
        response.sendRedirect(request.getContextPath() + "/DepartmentListServlet?menu=department");
    }
}
