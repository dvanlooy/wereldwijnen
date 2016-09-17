package be.vdab.servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.vdab.enums.Bestelwijze;
import be.vdab.services.LandService;
import be.vdab.services.WijnService;
import be.vdab.valueobjects.Bestelbonlijn;

@WebServlet("/mandje.htm")
public class MandjeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/mandje.jsp";
	private static final String REDIRECT_URL_REMOVED = "%s/mandje.htm";
	private static final String REDIRECT_URL_ORDER = "%s/success.htm";
	private final transient LandService landService = new LandService();
	private final transient WijnService wijnService = new WijnService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// lees mandje uit session
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		Map<Long, Long> mandje = (Map<Long, Long>) session.getAttribute("mandje");

		// maak set bestelbonlijnen aan
		if (mandje != null) {
			Set<Bestelbonlijn> bestelbonlijnen = new HashSet<>();
			BigDecimal totaal = BigDecimal.ZERO;
			for (Map.Entry<Long, Long> entry : mandje.entrySet()) {
				Bestelbonlijn bestelbonlijn = new Bestelbonlijn(wijnService.read(entry.getKey()), entry.getValue());
				bestelbonlijnen.add(bestelbonlijn);
				System.out.println(bestelbonlijn.getTotaleWaarde());
				totaal = totaal.add(bestelbonlijn.getTotaleWaarde());
			}
			System.out.println(totaal);
			request.setAttribute("bestelbonlijnen", bestelbonlijnen);
			request.setAttribute("bestelwijzen", Bestelwijze.values());
			request.setAttribute("totaal", totaal);
		}

		// zet alle landen in attribute
		request.setAttribute("landen", landService.findAll());

		// -->
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//
		if (request.getParameter("remove") != null) {
			// lees mandje uit session
			HttpSession session = request.getSession();
			@SuppressWarnings("unchecked")
			Map<Long, Long> mandje = (Map<Long, Long>) session.getAttribute("mandje");
			try {
				mandje.remove(Long.parseLong(request.getParameter("remove")));
			} catch (NumberFormatException e) {
				// IF NO LONG DO NOTHING
			}

			if (mandje.isEmpty()) {
				request.getSession().removeAttribute("mandje");
			} else {
				request.getSession().setAttribute("mandje", mandje);
			}
			response.sendRedirect(String.format(REDIRECT_URL_REMOVED, request.getContextPath()));
		}
	}
}
