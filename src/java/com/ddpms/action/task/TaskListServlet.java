package com.ddpms.action.task;

import com.ddpms.dao.TaskDao;
import com.ddpms.model.Pagination;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class TaskListServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(TaskListServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {                        
            int limit = CharacterUtil.removeNullTo(request.getParameter("limit"), 10);
            int offset = CharacterUtil.removeNullTo(request.getParameter("offset"), 0);
            String pageUrl = request.getContextPath() + "/TaskListServlet?" + request.getQueryString();
            
            String taskName = CharacterUtil.removeNull(request.getParameter("taskName"));            
            logger.info(" taskName ::=="+taskName);

            TaskDao dao = new TaskDao();
            String sqlCondition = dao.getConditionBuilder(taskName);
            int countRecordAll = dao.getCountTask(sqlCondition);
            request.setAttribute("taskList", dao.getTaskList(limit, offset,sqlCondition));
            request.setAttribute("taskName", taskName);
            
            Pagination pagination = new Pagination(pageUrl, countRecordAll, limit, offset);
            request.setAttribute("pagination", pagination);            
        } catch (Exception e) {
            logger.error("TaskListServlet error", e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/task/task-search.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        String taskName = CharacterUtil.removeNull(request.getParameter("taskName"));            
        logger.info(" taskName ::=="+taskName);
    }
    
    
}
