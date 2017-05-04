
package com.ddpms.action.department;

import com.ddpms.dao.DepartmentDao;
import com.ddpms.model.Department;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class DepartmentFormServlet extends HttpServlet {
final static Logger logger = Logger.getLogger(DepartmentFormServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String depId = CharacterUtil.removeNull(request.getParameter("depId"));
            Department department = null;
            if(depId.equals("")){ // new 
                department = new Department();
            }else{
                department = new DepartmentDao().getDepartment(depId);
            }
            request.setAttribute("department", department);
        } catch (Exception e) {
            logger.error("DepartmentFormServlet error", e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/department/department-form.jsp");
        dispatcher.forward(request, response);
    }
}
