package be.vdab.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.vdab.services.LandService;

@WebServlet("/success.htm")
public class SuccesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/success.jsp";
	private final transient LandService landService = new LandService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		

		// zet alle landen in attribute
		request.setAttribute("landen", landService.findAll());

		// -->
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

}
