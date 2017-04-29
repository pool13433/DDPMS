/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ddpms.action.strategic;

import com.ddpms.action.expense.ProjectExpenseDeleteServlet;
import com.ddpms.dao.ProjectExpenseDao;
import com.ddpms.dao.StrategicDao;
import com.ddpms.model.MessageUI;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class StrategicDeleteServlet extends HttpServlet {
final static Logger logger = Logger.getLogger(StrategicDeleteServlet.class);
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...goGet StrategicDeleteServlet");
        try {
            String id = CharacterUtil.removeNull(request.getParameter("id"));
            StrategicDao dao = new StrategicDao();
            int exe = 0;
            exe = dao.deleteStrategic(id);
            MessageUI message = null;
            if (exe == 0) {
                message = new MessageUI(true, "สถานะการลบข้อมูล", "เกิดข้อผิดพลาดในขั้นตอนการลบข้อมูล", "danger");
            } else {
                message = new MessageUI(true, "สถานะการลบข้อมูล", "ลบข้อมูลสำเร็จ", "info");
            }       
            request.getSession().setAttribute("MessageUI", message);      
            response.sendRedirect(request.getContextPath() + "/StrategicSearchServlet");
        } catch (Exception e) {
            logger.error("StrategicDeleteServlet Error : "+e.getMessage());
        }
    }
}
