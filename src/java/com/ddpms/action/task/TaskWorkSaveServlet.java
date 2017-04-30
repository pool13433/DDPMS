package com.ddpms.action.task;

import com.ddpms.dao.TaskWorkDao;
import com.ddpms.model.Employee;
import com.ddpms.model.MessageUI;
import com.ddpms.model.TaskWork;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class TaskWorkSaveServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(TaskWorkSaveServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Employee employee = (Employee) request.getSession().getAttribute("EMPLOYEE");
            String taskwId = CharacterUtil.removeNull(request.getParameter("taskwId"));
            String taskwDetail = CharacterUtil.removeNull(request.getParameter("taskwDetail"));
            String taskaId = CharacterUtil.removeNull(request.getParameter("taskaId"));
            String taskwManday = CharacterUtil.removeNull(request.getParameter("taskwManday"));
            String taskwDate = CharacterUtil.removeNull(request.getParameter("taskwDate"));
            TaskWork taskWork = new TaskWork();
            taskWork.setModifiedBy(employee.getEmpId());
            taskWork.setTaskaId(Integer.parseInt(taskaId));
            taskWork.setTaskwDate(taskwDate);
            taskWork.setTaskwDetail(taskwDetail);
            taskWork.setTaskwManday(Integer.parseInt(taskwManday));
            TaskWorkDao dao = new TaskWorkDao();
            int exec = 0;
            if (taskwId.equals("")) {
                exec = dao.createTaskWork(taskWork);
            } else {
                taskWork.setTaskwId(taskwId);
                exec = dao.updateTaskWork(taskWork);
            }
            MessageUI message = null;
            if (exec == 0) {
                message = new MessageUI(true, "สถานะการบันทึกข้อมูล", "เกิดข้อผิดพลาดในขั้นตอนการบันทึกข้อมูล", "danger");
            } else {
                message = new MessageUI(true, "สถานะการบันทึกข้อมูล", "บันทึกข้อมูลสำเร็จ", "info");
            }
            request.getSession().setAttribute("MessageUI", message);
        } catch (Exception e) {
            logger.error("TaskWorkSaveServlet Error", e);
        }
        response.sendRedirect(request.getContextPath() + "/TaskWorkListServlet?menu=task_work");
    }

}
