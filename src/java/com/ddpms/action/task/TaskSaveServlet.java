package com.ddpms.action.task;

import com.ddpms.action.department.DepartmentSaveServlet;
import com.ddpms.dao.TaskDao;
import com.ddpms.model.Employee;
import com.ddpms.model.MessageUI;
import com.ddpms.model.Task;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class TaskSaveServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(DepartmentSaveServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Employee employee = (Employee) request.getSession().getAttribute("EMPLOYEE");
            String taskId = CharacterUtil.removeNull(request.getParameter("taskId"));
            String taskName = CharacterUtil.removeNull(request.getParameter("taskName"));            
            Task task = new Task();
            task.setTaskName(taskName);
            task.setModifiedBy(String.valueOf(employee.getEmpId()));
            TaskDao dao = new TaskDao();
            int exec = 0;
            if (taskId.equals("")) {
                exec = dao.createTask(task);
            } else {
                task.setTaskId(taskId);
                exec = dao.updateTask(task);
            }
            MessageUI message = null;
            if (exec == 0) {
                message = new MessageUI(true, "สถานะการบันทึกข้อมูล", "เกิดข้อผิดพลาดในขั้นตอนการบันทึกข้อมูล", "danger");
            } else {
                message = new MessageUI(true, "สถานะการบันทึกข้อมูล", "บันทึกข้อมูลสำเร็จ", "info");
            }
            request.getSession().setAttribute("MessageUI", message);
        } catch (Exception e) {
            logger.error("TaskSaveServlet error", e);
        }
        response.sendRedirect(request.getContextPath() + "/TaskListServlet?menu=task");
    }
}
