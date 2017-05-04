
package com.ddpms.action.department;

import com.ddpms.dao.DepartmentDao;
import com.ddpms.model.Pagination;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class DepartmentListServlet extends HttpServlet {

     final static Logger logger = Logger.getLogger(DepartmentListServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int limit = CharacterUtil.removeNullTo(request.getParameter("limit"), 10);
            int offset = CharacterUtil.removeNullTo(request.getParameter("offset"), 0);
            String pageUrl = request.getContextPath() + "/DepartmentListServlet?" + request.getQueryString();
            
            DepartmentDao dao = new DepartmentDao();
            int countRecordAll = dao.getCountDepartment();
            request.setAttribute("departmentList", dao.getDepartmentList(limit, offset));
            Pagination pagination = new Pagination(pageUrl, countRecordAll, limit, offset);
            request.setAttribute("pagination", pagination);
        } catch (Exception e) {
            logger.error("DepartmentListServlet error", e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/department/department-search.jsp");
        dispatcher.forward(request, response);
    }
}
