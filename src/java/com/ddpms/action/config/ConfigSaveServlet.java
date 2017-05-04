package com.ddpms.action.config;

import com.ddpms.dao.ConfigDao;
import com.ddpms.model.Config;
import com.ddpms.model.Employee;
import com.ddpms.model.MessageUI;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ConfigSaveServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(ConfigSaveServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Employee employee = (Employee) request.getSession().getAttribute("EMPLOYEE");
            String confId = CharacterUtil.removeNull(request.getParameter("confId"));
            String confName = CharacterUtil.removeNull(request.getParameter("confName"));
            String confCode = CharacterUtil.removeNull(request.getParameter("confCode"));
            String confValue = CharacterUtil.removeNull(request.getParameter("confValue"));
            Config config = new Config();
            config.setConfCode(confCode);
            config.setConfValue(confValue);
            config.setConfName(confName);
            config.setModifiedBy(String.valueOf(employee.getEmpId()));
            ConfigDao dao = new ConfigDao();
            int exec = 0;
            if (confId.equals("")) {
                exec = dao.createConfig(config);
            } else {
                config.setConfId(confId);
                exec = dao.updateConfig(config);
            }
            MessageUI message = null;
            if (exec == 0) {
                message = new MessageUI(true, "สถานะการบันทึกข้อมูล", "เกิดข้อผิดพลาดในขั้นตอนการบันทึกข้อมูล", "danger");
            } else {
                message = new MessageUI(true, "สถานะการบันทึกข้อมูล", "บันทึกข้อมูลสำเร็จ", "info");
            }
            request.getSession().setAttribute("MessageUI", message);
        } catch (Exception e) {
            logger.error("ConfigSaveServlet", e);
        }
        response.sendRedirect(request.getContextPath() + "/ConfigListServlet?menu=config");
    }

}
