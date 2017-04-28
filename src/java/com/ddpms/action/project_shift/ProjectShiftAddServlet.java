
package com.ddpms.action.project_shift;

import com.ddpms.dao.ProjectDao;
import com.ddpms.dao.ProjectShiftDao;
import com.ddpms.model.MessageUI;
import com.ddpms.model.Project;
import com.ddpms.model.ProjectShift;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ProjectShiftAddServlet extends HttpServlet {
final static Logger logger = Logger.getLogger(ProjectShiftAddServlet.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...doGet ProjectShiftAddServlet");
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/project-shift/project-shift-form.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            logger.error("ProjectShiftAddServlet Error : "+e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...doPost ProjectShiftAddServlet");
        try {
            ProjectShift ps = new ProjectShift();
            ProjectShiftDao dao = new  ProjectShiftDao();
            String id = CharacterUtil.removeNull(request.getParameter("id"));
            String proj_id = CharacterUtil.removeNull(request.getParameter("proj_id"));
            request.setAttribute("proj_id", proj_id);
            String projs_reason = CharacterUtil.removeNull(request.getParameter("projs_reason"));
            request.setAttribute("projs_reason", projs_reason);
            String projs_plan = CharacterUtil.removeNull(request.getParameter("projs_plan"));
            request.setAttribute("projs_plan", projs_plan);
            ProjectDao pjDao = new ProjectDao();
            request.setAttribute("projectList", pjDao.getProject(new Project(), 0, 0));
            
            ps.setProjsId(id);
            ps.setProjId(proj_id);
            ps.setProjsReason(projs_reason);
            ps.setProjsPlan(projs_plan);
            ps.setModifiedBy("1");
            
            int exe = 0;
            if(id.equals("")){
                exe = dao.createProjectShift(ps);
            }else{
                exe = dao.updateProjectShift(ps);
            }
            MessageUI message = null;
            if (exe == 0) {
                message = new MessageUI(true, "สถานะการบันทีกข้อมูล", "เกิดข้อผิดพลาดในขั้นตอนการบันทีกข้อมูล", "danger");
            } else {
                message = new MessageUI(true, "สถานะการบันทีกข้อมูล", "บันทีกข้อมูลสำเร็จ", "info");
            }        
            request.getSession().setAttribute("MessageUI", message);
            response.sendRedirect(request.getContextPath() + "/ProjectShiftSearchServlet");
        } catch (Exception e) {
            logger.error("ProjectShiftAddServlet Error : "+e.getMessage());
        }
    }
}
