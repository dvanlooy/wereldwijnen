package be.vdab.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.vdab.entities.Land;
import be.vdab.entities.Soort;
import be.vdab.services.LandService;
import be.vdab.services.SoortService;
import be.vdab.services.WijnService;

@WebServlet("/soort.htm")
public class SoortServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/soort.jsp";
	private final transient LandService landService = new LandService();
	private final transient SoortService soortService = new SoortService();
	private final transient WijnService wijnService = new WijnService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// zet alle landen in attribute
		request.setAttribute("landen", landService.findAll());

		// zoek soortid in parameter
		if (request.getParameter("id") != null) {
			try {
				
				// lees Soort en Land uit DB
				long soortId = Long.parseLong(request.getParameter("id"));
				Soort soort = soortService.read(soortId);
				if (soort != null) {
					Land land = soort.getLand();
					request.setAttribute("soort", soort);
					request.setAttribute("land", land);

					// lees alle Soorten huidig Land uit DB
					request.setAttribute("soorten", soortService.findPerLand(land));

					// lees Wijnen per Soort uit DB
					request.setAttribute("wijnen", wijnService.findPerSoort(soort));
				}else{
					request.setAttribute("fout", "Geen soort gevonden voor id " + soortId);
				}

			} catch (NumberFormatException ex) {
				request.setAttribute("fout", "Soort id bestaat uit cijfers");
			}
		} else {
			request.setAttribute("fout", "Geen soort geselecteerd");
		}

		// -->
		request.getRequestDispatcher(VIEW).forward(request, response);
	}
}
