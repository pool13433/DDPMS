package com.ddpms.action.taskassign;

import com.ddpms.dao.TaskAssignDao;
import com.ddpms.model.Employee;
import com.ddpms.model.MessageUI;
import com.ddpms.model.TaskAssign;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class TaskAssignSaveServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(TaskAssignSaveServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String taskId = CharacterUtil.removeNull(request.getParameter("taskId"));
            String taskaId = CharacterUtil.removeNull(request.getParameter("taskaId"));
            String projId = CharacterUtil.removeNull(request.getParameter("projId"));
            String empId = CharacterUtil.removeNull(request.getParameter("empId"));
            String taskaAssignDate = CharacterUtil.removeNull(request.getParameter("taskaAssignDate"));
            String taskaTargetDate = CharacterUtil.removeNull(request.getParameter("taskaTargetDate"));

            Employee employee = (Employee) request.getSession().getAttribute("EMPLOYEE");

            TaskAssign taskAssign = new TaskAssign();
            taskAssign.setModifiedBy(employee.getEmpId());
            taskAssign.setProjId(Integer.parseInt(projId));
            taskAssign.setTaskId(Integer.parseInt(taskId));
            taskAssign.setTaskUserId(Integer.parseInt(empId));
            taskAssign.setTaskaAssignDate(taskaAssignDate);
            taskAssign.setTaskaTargetDate(taskaTargetDate);
            int exec = 0;
            if (taskaId.equals("")) {//
                exec = new TaskAssignDao().createTaskAssign(taskAssign);
            } else {                
                taskAssign.setTaskaId(taskaId);
                exec = new TaskAssignDao().updateTaskAssign(taskAssign);
            }
            MessageUI message = null;
            if (exec == 0) {
                message = new MessageUI(true, "สถานะการบันทึกข้อมูล", "เกิดข้อผิดพลาดในขั้นตอนการบันทึกข้อมูล", "danger");
            } else {
                message = new MessageUI(true, "สถานะการบันทึกข้อมูล", "บันทึกข้อมูลสำเร็จ", "info");
            }
            request.getSession().setAttribute("MessageUI", message);
        } catch (Exception e) {
            logger.error("TaskAssignSaveServlet error", e);
        }
        response.sendRedirect(request.getContextPath() + "/TaskAssignListServlet?menu=task_assign");
    }

}
