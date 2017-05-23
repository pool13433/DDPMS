package com.ddpms.action.project.expense;

import com.ddpms.dao.ProjectExpenseDao;
import com.ddpms.dao.ProjectTypeDao;
import com.ddpms.model.Employee;
import com.ddpms.model.MessageUI;
import com.ddpms.model.ProjectExpense;
import com.ddpms.model.ProjectType;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ProjectExpenseSaveServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(ProjectExpenseSaveServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Employee employee = (Employee) request.getSession().getAttribute("EMPLOYEE");
            String projId = CharacterUtil.removeNull(request.getParameter("projId"));
            String expId = CharacterUtil.removeNull(request.getParameter("expId"));
            String expDate = CharacterUtil.removeNull(request.getParameter("expDate"));
            String expPoPr = CharacterUtil.removeNull(request.getParameter("expPoPr"));
            String expVoch = CharacterUtil.removeNull(request.getParameter("expVoch"));
            String expReceipt = CharacterUtil.removeNull(request.getParameter("expReceipt"));
            String expDesc = CharacterUtil.removeNull(request.getParameter("expDesc"));
            String expAmount = CharacterUtil.removeNull(request.getParameter("expAmount"));
            String expVender = CharacterUtil.removeNull(request.getParameter("expVender"));
            
            ProjectExpense expense = new ProjectExpense();
            expense.setExpAmount(expAmount);
            expense.setExpDate(expDate);
            expense.setExpDesc(expDesc);
            expense.setExpId(expDesc);
            expense.setExpPr(expPoPr);
            expense.setExpVoch(expVoch);
            expense.setModifiedBy(projId);
            expense.setModifiedDate(expDate);
            expense.setProjId(projId);
            expense.setProjName(projId);
            expense.setReceipt(expReceipt);
            expense.setVender(expVender);
            
            ProjectExpenseDao dao = new ProjectExpenseDao();
            int exec = 0;
            if (expId.equals("")) {
                exec = dao.createProjectExpense(expense);
            } else {
                expense.setExpId(expId);
                exec = dao.updateProjectExpense(expense);
            }
            MessageUI message = null;
            if (exec == 0) {
                message = new MessageUI(true, "สถานะการบันทึกข้อมูล", "เกิดข้อผิดพลาดในขั้นตอนการบันทึกข้อมูล", "danger");
            } else {
                message = new MessageUI(true, "สถานะการบันทึกข้อมูล", "บันทึกข้อมูลสำเร็จ", "info");
            }
            request.getSession().setAttribute("MessageUI", message);
        } catch (Exception e) {
            logger.error("ProjectExpenseSaveServlet", e);
        }
        response.sendRedirect(request.getContextPath() + "/ProjectExpenseSearchServlet?menu=project_expense");
    }
}
