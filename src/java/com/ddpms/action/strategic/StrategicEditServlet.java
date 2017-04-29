
package com.ddpms.action.strategic;

import com.ddpms.dao.StrategicDao;
import com.ddpms.model.Strategic;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class StrategicEditServlet extends HttpServlet {
final static Logger logger = Logger.getLogger(StrategicEditServlet.class);
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...goGet StrategicEditServlet");
        try {
            StrategicDao dao = new StrategicDao();
            String id = CharacterUtil.removeNull(request.getParameter("id"));
            Strategic s = new  Strategic();
            s.setStraId(id);
            List<Strategic> list = new ArrayList<Strategic>();
            list = dao.getStrategic(s, 0, 0);
            if(!list.isEmpty()){
                request.setAttribute("stra_id", id);
                request.setAttribute("stra_name", list.get(0).getStraName());              
            }
                        
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/strategic/strategic-form.jsp");
            dispatcher.forward(request, response);
            
        } catch (Exception e) {
            logger.error("StrategicEditServlet Error : "+e.getMessage());
        }
    }
}
