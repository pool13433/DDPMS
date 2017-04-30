
package com.ddpms.action.strategic;

import com.ddpms.dao.StrategicDao;
import com.ddpms.model.MessageUI;
import com.ddpms.model.Strategic;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;


public class StrategicAddServlet extends HttpServlet {
final static Logger logger = Logger.getLogger(StrategicAddServlet.class);
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...goGet StrategicAddServlet");
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/strategic/strategic-form.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            logger.error("StrategicAddServlet Error : "+e.getMessage());
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...goPost StrategicAddServlet");
        try {
            StrategicDao dao = new StrategicDao();
            Strategic s = new  Strategic();
            String id = CharacterUtil.removeNull(request.getParameter("id"));
            String stra_name = CharacterUtil.removeNull(request.getParameter("stra_name"));
            request.setAttribute("stra_name", stra_name);
            
            s.setStraId(id);
            s.setStraName(stra_name);
            s.setModifiedBy("1");
            
            int exe = 0;
            if(id.equals("")){
                exe = dao.createStrategic(s);
            }else{
                exe = dao.updateStrategic(s);
            }
            MessageUI message = null;
            if (exe == 0) {
                message = new MessageUI(true, "สถานะการบันทีกข้อมูล", "เกิดข้อผิดพลาดในขั้นตอนการบันทีกข้อมูล", "danger");
            } else {
                message = new MessageUI(true, "สถานะการบันทีกข้อมูล", "บันทีกข้อมูลสำเร็จ", "info");
            }        
            request.getSession().setAttribute("MessageUI", message);
            response.sendRedirect(request.getContextPath() + "/StrategicSearchServlet");
            
        } catch (Exception e) {
            logger.error("StrategicAddServlet Error : "+e.getMessage());
        }
    }
}
