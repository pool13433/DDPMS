package com.ddpms.action.config;

import com.ddpms.dao.ConfigDao;
import com.ddpms.model.MessageUI;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ConfigDeleteServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(ConfigDeleteServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int exec = new ConfigDao().deleteConfig(Integer.parseInt(CharacterUtil.removeNull(request.getParameter("confId"))));
            MessageUI message = null;
            if (exec == 0) {
                message = new MessageUI(false, "สถานะการลบข้อมูล", "เกิดข้อผิดพลาดในขั้นตอนการลบข้อมูล", "danger");
            } else {
                message = new MessageUI(true, "สถานะการลบข้อมูล", "ลบข้อมูลสำเร็จ", "info");
            }
            request.getSession().setAttribute("MessageUI", message);
        } catch (Exception e) {
            logger.error("delete sim error", e);
        }
        response.sendRedirect(request.getContextPath() + "/ConfigListServlet?menu=config");
    }
}
