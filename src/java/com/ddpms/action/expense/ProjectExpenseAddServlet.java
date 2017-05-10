/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ddpms.action.expense;

import com.ddpms.dao.ProjectDao;
import com.ddpms.dao.ProjectExpenseDao;
import com.ddpms.model.Employee;
import com.ddpms.model.MessageUI;
import com.ddpms.model.Project;
import com.ddpms.model.ProjectExpense;
import com.ddpms.util.CharacterUtil;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;


public class ProjectExpenseAddServlet extends HttpServlet {

final static Logger logger = Logger.getLogger(ProjectExpenseAddServlet.class);
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...goGet ProjectExpenseAddServlet");
        try {
            ProjectDao pjDao = new ProjectDao();
            request.setAttribute("projectList", pjDao.getProject(new Project(), 0, 0));
            String proj_id = CharacterUtil.removeNull(request.getParameter("proj_id"));
            request.setAttribute("proj_id", proj_id);
           
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/expense/expense-form.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            logger.error("ProjectExpenseAddServlet Error : "+e.getMessage());
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("...goPost ProjectExpenseAddServlet");
        try {
            ProjectExpenseDao dao = new ProjectExpenseDao();
            ProjectExpense pe = new  ProjectExpense();
            String id = CharacterUtil.removeNull(request.getParameter("id"));
            String proj_id = CharacterUtil.removeNull(request.getParameter("proj_id"));
            request.setAttribute("proj_id", proj_id);
            String exp_date = CharacterUtil.removeNull(request.getParameter("exp_date"));
            request.setAttribute("exp_date", exp_date);
            String exp_desc = CharacterUtil.removeNull(request.getParameter("exp_desc"));
            request.setAttribute("exp_desc", exp_desc);
            String exp_amount = CharacterUtil.removeNull(request.getParameter("exp_amount"));
            request.setAttribute("exp_amount", exp_amount);
            String exp_pr = CharacterUtil.removeNull(request.getParameter("exp_pr"));
            request.setAttribute("exp_pr", exp_pr);
            String exp_voch = CharacterUtil.removeNull(request.getParameter("exp_voch"));
            request.setAttribute("exp_voch", exp_voch);
            String receipt = CharacterUtil.removeNull(request.getParameter("receipt"));
            request.setAttribute("receipt", receipt);
            String vender = CharacterUtil.removeNull(request.getParameter("vender"));
            request.setAttribute("vender", vender);
            Employee employee = (Employee) request.getSession().getAttribute("EMPLOYEE");  
            
            pe.setExpId(id);
            pe.setProjId(proj_id);
            pe.setExpDesc(exp_desc);
            pe.setExpAmount(exp_amount);
            pe.setExpDate(exp_date);
            pe.setExpVoch(exp_voch);
            pe.setExpPr(exp_pr);
            pe.setReceipt(receipt);
            pe.setVender(vender);
            pe.setModifiedBy(String.valueOf(employee.getEmpId()));
            
            int exe = 0;
            if(id.equals("")){
                exe = dao.createProjectExpense(pe);
            }else{
                exe = dao.updateProjectExpense(pe);
            }
            MessageUI message = null;
            if (exe == 0) {
                message = new MessageUI(true, "สถานะการบันทีกข้อมูล", "เกิดข้อผิดพลาดในขั้นตอนการบันทีกข้อมูล", "danger");
            } else {
                message = new MessageUI(true, "สถานะการบันทีกข้อมูล", "บันทีกข้อมูลสำเร็จ", "info");
            }        
            request.getSession().setAttribute("MessageUI", message);
            response.sendRedirect(request.getContextPath() + "/ProjectExpenseSearchServlet");
            
        } catch (Exception e) {
            logger.error("ProjectExpenseAddServlet Error : "+e.getMessage());
        }
    }

   

}
