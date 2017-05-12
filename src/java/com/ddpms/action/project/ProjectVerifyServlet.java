package com.ddpms.action.project;

import com.ddpms.dao.ProjectDao;
import com.ddpms.dao.ProjectWorkingDao;
import com.ddpms.model.Employee;
import com.ddpms.model.MessageUI;
import com.ddpms.model.Project;
import com.ddpms.model.ProjectWorking;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ProjectVerifyServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(ProjectVerifyServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int exec = 0;
        try {
            Employee emp = (Employee) request.getSession().getAttribute("EMPLOYEE");
            String projId = CharacterUtil.removeNull(request.getParameter("projId"));
            String reason = CharacterUtil.removeNull(request.getParameter("reason"));
            String verifyCase = CharacterUtil.removeNull(request.getParameter("verifyCase"));            
            String[] budgetYears = request.getParameterValues("budgetYear");
            int budgetYearTotal = 0;
            for (String year : budgetYears) {
                ProjectWorking workInYear = new ProjectWorking();
                workInYear.setBudgetYear(year);
                workInYear.setModifiedBy(String.valueOf(emp.getEmpId()));
                workInYear.setBudgetApproveM1(CharacterUtil.removeNull(request.getParameter(year + "_1")));
                workInYear.setBudgetApproveM2(CharacterUtil.removeNull(request.getParameter(year + "_2")));
                workInYear.setBudgetApproveM3(CharacterUtil.removeNull(request.getParameter(year + "_3")));
                workInYear.setBudgetApproveM4(CharacterUtil.removeNull(request.getParameter(year + "_4")));
                workInYear.setBudgetApproveM5(CharacterUtil.removeNull(request.getParameter(year + "_5")));
                workInYear.setBudgetApproveM6(CharacterUtil.removeNull(request.getParameter(year + "_6")));
                workInYear.setBudgetApproveM7(CharacterUtil.removeNull(request.getParameter(year + "_7")));
                workInYear.setBudgetApproveM8(CharacterUtil.removeNull(request.getParameter(year + "_8")));
                workInYear.setBudgetApproveM9(CharacterUtil.removeNull(request.getParameter(year + "_9")));
                workInYear.setBudgetApproveM10(CharacterUtil.removeNull(request.getParameter(year + "_10")));
                workInYear.setBudgetApproveM11(CharacterUtil.removeNull(request.getParameter(year + "_11")));
                workInYear.setBudgetApproveM12(CharacterUtil.removeNull(request.getParameter(year + "_12")));
                exec = new ProjectWorkingDao().updateProjectWorkingBudgetApprove(workInYear);         
                budgetYearTotal += CharacterUtil.removeNullTo(CharacterUtil.removeNull(request.getParameter(year+"_budgetTotal")), 0);
            }

            Project param = new Project();
            param.setProjVerifyBy(String.valueOf(emp.getEmpId()));
            param.setProjRemark(reason);
            param.setProjId(projId);
            param.setProjBudgApprove(budgetYearTotal);
            if (verifyCase.equals("APPROVE")) {// INPLAN
                param.setProjStatus("INPLAN");
            } else if (verifyCase.equals("REJECT")) {// REJECT
                param.setProjStatus("REJECT");
            } else if (verifyCase.equals("CANCEL")) {// CANCEL                
                param.setProjStatus("CANCEL");
            }
            exec = new ProjectDao().updateProjectVerifyStatus(param);

            MessageUI message = null;
            if (exec == 0) {
                message = new MessageUI(true, "สถานะการบันทึกข้อมูล", "เกิดข้อผิดพลาดในขั้นตอนการบันทึกข้อมูล", "danger");
            } else {
                message = new MessageUI(true, "สถานะการบันทึกข้อมูล", "บันทึกข้อมูลการปรับเปลี่ยนสถานะของโครงงาน สำเร็จ", "info");
            }
            request.getSession().setAttribute("MessageUI", message);
        } catch (Exception e) {
            logger.error("ProjectVerifyServlet error", e);
        }
        response.sendRedirect(request.getContextPath() + "/ProjectSearchServlet?menu=project");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String projId = CharacterUtil.removeNull(request.getParameter("id"));
            int projectId = Integer.parseInt(projId);
            Project project = new ProjectDao().getProject(projectId);
            List<ProjectWorking> projectWorkingList = new ProjectWorkingDao().getProjectWorkingByProject(projectId);
            request.setAttribute("projectWorkingList", projectWorkingList);
            request.setAttribute("project", project);
        } catch (Exception e) {
            logger.error("ProjectVerifyServlet Error", e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/project/project-verify.jsp");
        dispatcher.forward(request, response);
    }

}
