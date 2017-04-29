
package com.ddpms.action.plan;

import com.ddpms.dao.PlanDao;
import com.ddpms.model.MessageUI;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class PlanDeleteServlet extends HttpServlet {
final static Logger logger = Logger.getLogger(PlanDeleteServlet.class);
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...goGet PlanDeleteServlet");
        try {
            String id = CharacterUtil.removeNull(request.getParameter("id"));
            PlanDao dao = new PlanDao();
            int exe = 0;
            exe = dao.deletePlan(id);
            MessageUI message = null;
            if (exe == 0) {
                message = new MessageUI(true, "สถานะการลบข้อมูล", "เกิดข้อผิดพลาดในขั้นตอนการลบข้อมูล", "danger");
            } else {
                message = new MessageUI(true, "สถานะการลบข้อมูล", "ลบข้อมูลสำเร็จ", "info");
            }       
            request.getSession().setAttribute("MessageUI", message);      
            response.sendRedirect(request.getContextPath() + "/PlanSearchServlet");
        } catch (Exception e) {
            logger.error("PlanDeleteServlet Error : "+e.getMessage());
        }
    }
}
