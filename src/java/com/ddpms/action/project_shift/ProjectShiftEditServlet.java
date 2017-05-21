
package com.ddpms.action.project_shift;

import com.ddpms.dao.ProjectDao;
import com.ddpms.dao.ProjectShiftDao;
import com.ddpms.model.Project;
import com.ddpms.model.ProjectShift;
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

public class ProjectShiftEditServlet extends HttpServlet {
final static Logger logger = Logger.getLogger(ProjectShiftEditServlet.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...doGet ProjectShiftEditServlet");
        try {
            ProjectDao pjDao = new ProjectDao();
            request.setAttribute("projectList", pjDao.getProject(new Project(), 0, 0));
            ProjectShift ps = new ProjectShift();
            ProjectShiftDao dao = new  ProjectShiftDao();
            String id = CharacterUtil.removeNull(request.getParameter("id"));
            ps.setProjsId(id);
            List<ProjectShift> list = new ArrayList<ProjectShift>();
            list = dao.getProjectShift(ps, 0, 0);
            if(!list.isEmpty()){
                request.setAttribute("projs_id", list.get(0).getProjsId());
                request.setAttribute("proj_id",list.get(0).getProjId());
                request.setAttribute("reason",list.get(0).getProjsReason());
                request.setAttribute("projsPlanDate",list.get(0).getProjsPlanDate());
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/project/projectshift-form.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            logger.error("ProjectShiftEditServlet Error : "+e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...doPost ProjectShiftEditServlet");
        try {
            
        } catch (Exception e) {
            logger.error("ProjectShiftEditServlet Error : "+e.getMessage());
        }
    }
}
