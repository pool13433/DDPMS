/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ddpms.action.project_shift;

import com.ddpms.dao.ProjectShiftDao;
import com.ddpms.model.MessageUI;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class ProjectShiftDeleteServlet extends HttpServlet {
final static Logger logger = Logger.getLogger(ProjectShiftDeleteServlet.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...doGet ProjectShiftDeleteServlet");
        try {
            String id = CharacterUtil.removeNull(request.getParameter("id"));
            ProjectShiftDao dao = new ProjectShiftDao();
             int exe = 0;
            exe = dao.deleteProjectShift(id);
            MessageUI message = null;
            if (exe == 0) {
                message = new MessageUI(true, "สถานะการลบข้อมูล", "เกิดข้อผิดพลาดในขั้นตอนการลบข้อมูล", "danger");
            } else {
                message = new MessageUI(true, "สถานะการลบข้อมูล", "ลบข้อมูลสำเร็จ", "info");
            }       
            request.getSession().setAttribute("MessageUI", message);
            response.sendRedirect(request.getContextPath() + "/ProjectShiftSearchServlet");
        } catch (Exception e) {
            logger.error("ProjectShiftDeleteServlet Error : "+e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...doPost ProjectShiftDeleteServlet");
        try {
            
        } catch (Exception e) {
            logger.error("ProjectShiftDeleteServlet Error : "+e.getMessage());
        }
    }
}
