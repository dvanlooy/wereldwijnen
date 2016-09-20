package be.vdab.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.vdab.entities.Land;
import be.vdab.services.LandService;
import be.vdab.services.SoortService;


@WebServlet("/land.htm")
public class LandServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/land.jsp";
	
	private final transient LandService landService = new LandService();
	private final transient SoortService soortService = new SoortService();
       
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//zoek landid in parameter
		String parameter = request.getParameter("id");
		if (parameter != null) {
			try {
				long landId = Long.parseLong(parameter);
				//lees Land uit DB
				Land land = landService.read(landId);
				if (land != null){
				request.setAttribute("land", land);
				//lees Soorten per Land uit DB
				request.setAttribute("soorten", soortService.findPerLand(land));
				}else{
					request.setAttribute("fout", "Geen land gevonden voor id " + landId);
				}
			}
			catch (NumberFormatException ex) {
				request.setAttribute("fout", "Land id bestaat uit cijfers");
			}
		}else {
			request.setAttribute("fout", "Geen land geselecteerd");
		}
		
		//zet alle landen in attribute
				request.setAttribute("landen", landService.findAll());
		
		//-->
		request.getRequestDispatcher(VIEW).forward(request, response);
	}
}
