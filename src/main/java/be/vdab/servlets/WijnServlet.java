package be.vdab.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.vdab.services.WijnService;

@WebServlet("/wijn.htm")
public class WijnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/wijn.jsp";
	private final transient WijnService wijnService = new WijnService();


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// zoek wijnid in parameter
		if (request.getParameter("id") != null) {
			try {
				long wijnId = Long.parseLong(request.getParameter("id"));
				// lees Wijn uit DB
				request.setAttribute("wijn", wijnService.read(wijnId));
			} catch (NumberFormatException ex) {
				request.setAttribute("fout", "wijn id is niet juist");
			}
		} else {
			request.setAttribute("fout", "geen wijn geselecteerd");
		}

		// -->
		request.getRequestDispatcher(VIEW).forward(request, response);
	}
}
