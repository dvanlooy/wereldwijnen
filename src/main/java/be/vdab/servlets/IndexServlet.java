package be.vdab.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.vdab.services.LandService;


@WebServlet("/index.htm")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/index.jsp";
	private final transient LandService landService = new LandService();
       
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// lees mandje uit session
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		Map<Long, Long> mandje = (Map<Long, Long>) session.getAttribute("mandje");
		request.setAttribute("mandje", mandje);
		//zet alle landen in attribute
		request.setAttribute("landen", landService.findAll());
		//-->
		request.getRequestDispatcher(VIEW).forward(request, response);
	}
}
