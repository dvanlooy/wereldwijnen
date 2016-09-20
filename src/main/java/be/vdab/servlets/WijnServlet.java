package be.vdab.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.vdab.entities.Wijn;
import be.vdab.services.LandService;
import be.vdab.services.WijnService;

@WebServlet("/wijn.htm")
public class WijnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/wijn.jsp";
	private static final String REDIRECT_URL = "%s/index.htm";
	private final transient WijnService wijnService = new WijnService();
	private final transient LandService landService = new LandService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		// zet alle landen in attribute
		request.setAttribute("landen", landService.findAll());

		// zoek wijnid in parameter
		if (request.getParameter("id") != null) {
			try {

				// lees wijn uit DB
				long wijnId = Long.parseLong(request.getParameter("id"));
				Wijn wijn = wijnService.read(wijnId);
				if (wijn != null) {
					request.setAttribute("wijn", wijn);
					
					// zoek naar bestaand mandje-item voor wijn, 
					// aantal meegeven in request attribute
					@SuppressWarnings("unchecked")
					Map<Long, Long> mandje = (Map<Long, Long>) session.getAttribute("mandje");
					if (mandje != null) {
						request.setAttribute("reedsToegevoegdAantal", mandje.get(wijnId));

					}
				}else{
					request.setAttribute("fout", "Geen wijn gevonden voor id " + wijnId);
				}
			} catch (NumberFormatException ex) {
				request.setAttribute("fout", "Wijn id bestaat uit cijfers");
			}
		} else {
			request.setAttribute("fout", "Geen wijn geselecteerd");
		}

		// -->
		request.getRequestDispatcher(VIEW).forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Map<String, String> fouten = new HashMap<>();
		long wijnId = 0L;

		// lees mandje uit session
		@SuppressWarnings("unchecked")
		Map<Long, Long> mandje = (Map<Long, Long>) session.getAttribute("mandje");

		// begin controle
		if (request.getParameter("id") != null && request.getParameter("aantal") != null) {
			try {
				wijnId = Long.parseLong(request.getParameter("id"));
				long aantal = Long.parseLong(request.getParameter("aantal"));

				if (aantal > 0) {
					// aantal correct ingegeven
					if (mandje == null) {
						// nieuw mandje indien niet aanwezig
						mandje = new HashMap<>();
					}
					// put wijn in mandje
					mandje.put(wijnId, aantal);
					session.setAttribute("mandje", mandje);
					response.sendRedirect(String.format(REDIRECT_URL, request.getContextPath()));

				} else {
					fouten.put("aantal", "aantal moet minstens 1 zijn");
				}

			} catch (NumberFormatException ex) {
				fouten.put("parameters", "ongeldige parameters opgegeven");
			}

		} else {
			if (request.getParameter("id") == null) {
				fouten.put("id", "ongeldig id opgegeven");
			}
			if (request.getParameter("aantal") == null) {
				fouten.put("aantal", "ongeldig aantal opgegeven");
			}
		}

		// als er foute gegevens gepost zijn: zet alle attributen terug in
		// request samen met foutboodschappen
		if (!fouten.isEmpty()) {
			request.setAttribute("fouten", fouten);
			request.setAttribute("landen", landService.findAll());
			request.setAttribute("wijn", wijnService.read(wijnId));
			if (mandje != null) {
				request.setAttribute("reedsToegevoegdAantal", mandje.get(wijnId));
				session.setAttribute("mandje", mandje);
			}
			request.getRequestDispatcher(VIEW).forward(request, response);
		}
	}

}
