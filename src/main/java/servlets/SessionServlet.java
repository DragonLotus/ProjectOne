package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import data.Employee;
import data.EmployeeDao;
import data.Reimbursement;
import data.ReimbursementDao;
import util.ConnectionUtil;

/**
 * Servlet implementation class SessionServlet
 */
public class SessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SessionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Enter doGet SessionServlet");
		HttpSession session = request.getSession(false);
		PrintWriter pw = response.getWriter();
		response.setContentType("application/json");
		ObjectMapper objectMapper = new ObjectMapper();
		EmployeeDao employeeDao = new EmployeeDao();
		ReimbursementDao reimbursementDao = new ReimbursementDao();
		if (session != null) {
			try {
				Employee employee = employeeDao.getEmployeeByName(ConnectionUtil.getConnection(), session.getAttribute("username").toString());
				String jsonInString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(employee);
				pw.write(jsonInString);
//				ArrayList<Reimbursement> reimbursementList = new ArrayList();
//				reimbursementList = reimbursementDao.getAllReimbursement(ConnectionUtil.getConnection());
//				jsonInString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(reimbursementList);
//				pw.write(jsonInString);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			pw.write("{\"username\": null}");
		}
		pw.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
