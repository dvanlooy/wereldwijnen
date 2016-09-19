package be.vdab.servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.vdab.entities.Bestelbon;
import be.vdab.enums.Bestelwijze;
import be.vdab.services.BestelbonService;
import be.vdab.services.LandService;
import be.vdab.services.WijnService;
import be.vdab.util.Invoercontrole;
import be.vdab.valueobjects.Adres;
import be.vdab.valueobjects.Bestelbonlijn;

@WebServlet("/mandje.htm")
public class MandjeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/mandje.jsp";
	private static final String REDIRECT_URL_RETURN = "%s/mandje.htm";
	private static final String REDIRECT_URL_SUCCESS = "%s/success.htm?id=%d";
	private final transient LandService landService = new LandService();
	private final transient WijnService wijnService = new WijnService();
	private final transient BestelbonService bestelbonService = new BestelbonService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// lees mandje uit session
		Map<Long, Long> mandje = getMandjeFromSession(request);

		// maak set bestelbonlijnen aan
		Set<Bestelbonlijn> bestelbonlijnen = createBestelbonlijnen(mandje);
		if (bestelbonlijnen != null) {
			// bereken totaal
			BigDecimal totaal = BigDecimal.ZERO;
			for (Bestelbonlijn bestelbonlijn : bestelbonlijnen) {
				totaal = totaal.add(bestelbonlijn.getTotaleWaarde());
			}

			request.setAttribute("bestelbonlijnen", bestelbonlijnen);
			request.setAttribute("bestelwijzen", Bestelwijze.values());
			request.setAttribute("totaal", totaal);
		}

		// zet alle landen in attribute
		request.setAttribute("landen", landService.findAll());

		// -->
		request.getRequestDispatcher(VIEW).forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map<Long, Long> mandje = getMandjeFromSession(request);

		String action = request.getParameter("action");
		if (action.equals("remove")) {
			// verwijder regel uit mandje
			String param = request.getParameter("remove");
			if (param != null) {
				removeItemFromMandje(request, param, mandje);
				response.sendRedirect(String.format(REDIRECT_URL_RETURN, request.getContextPath()));
			}
		}
		if (action.equals("maakBestelbon")) {
			Map<String, String> fouten = inputFouten(request);
			if (fouten.isEmpty()) {
				Bestelbon bestelbon = new Bestelbon(request.getParameter("naam"),
						new Adres(request.getParameter("straat"), request.getParameter("huisnummer"),
								request.getParameter("postcode"), request.getParameter("gemeente")),
						Bestelwijze.valueOf(request.getParameter("bestelwijze")), createBestelbonlijnen(mandje));
				System.out.println(bestelbon);
				bestelbonService.create(bestelbon);
				request.getSession().invalidate();
				// id in parameter is eenvoudige oplossing, maar succespagina
				// kan gemanipuleerd worden, eventueel in session steken
				response.sendRedirect(String.format(REDIRECT_URL_SUCCESS, request.getContextPath(), bestelbon.getId()));
			} else {
				request.getSession().setAttribute("fouten", fouten);
				response.sendRedirect(String.format(REDIRECT_URL_RETURN, request.getContextPath()));
			}
		}

	}

	private Set<Bestelbonlijn> createBestelbonlijnen(Map<Long, Long> mandje) {
		if (mandje != null) {
			Set<Bestelbonlijn> bestelbonlijnen = new HashSet<>();
			for (Map.Entry<Long, Long> entry : mandje.entrySet()) {
				Bestelbonlijn bestelbonlijn = new Bestelbonlijn(wijnService.read(entry.getKey()), entry.getValue());
				bestelbonlijnen.add(bestelbonlijn);
			}
			return bestelbonlijnen;
		}
		return null;
	}

	private void removeItemFromMandje(HttpServletRequest request, String param, Map<Long, Long> mandje) {
		// verwijder item uit mandje
		try {
			mandje.remove(Long.parseLong(param));
		} catch (NumberFormatException e) {
			// IF NO LONG DO NOTHING
		}
		// mandje leeg: weggooien
		if (mandje.isEmpty()) {
			request.getSession().removeAttribute("mandje");
		} else {
			request.getSession().setAttribute("mandje", mandje);
		}
	}

	private Map<Long, Long> getMandjeFromSession(HttpServletRequest request) {
		// lees mandje uit session
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		Map<Long, Long> mandje = (Map<Long, Long>) session.getAttribute("mandje");
		return mandje;
	}

	private Map<String, String> inputFouten(HttpServletRequest request) {
		Map<String, String> fouten = new HashMap<>();

		try {
			Invoercontrole.noEmptyOrNullString(request.getParameter("naam"), "Naam mag niet leeg zijn");
		} catch (IllegalArgumentException ex) {
			fouten.put("naam", ex.getMessage());
		}
		try {
			Invoercontrole.noEmptyOrNullString(request.getParameter("straat"), "Straat mag niet leeg zijn");
		} catch (IllegalArgumentException ex) {
			fouten.put("straat", ex.getMessage());
		}
		try {
			Invoercontrole.noEmptyOrNullString(request.getParameter("huisnummer"), "Huisnummer mag niet leeg zijn");
		} catch (IllegalArgumentException ex) {
			fouten.put("huisnummer", ex.getMessage());
		}
		try {
			Invoercontrole.correctPostcodeBE(request.getParameter("postcode"));
		} catch (IllegalArgumentException ex) {
			fouten.put("postcode", ex.getMessage());
		}
		try {
			Invoercontrole.noEmptyOrNullString(request.getParameter("gemeente"), "Gemeente mag niet leeg zijn");
		} catch (IllegalArgumentException ex) {
			fouten.put("gemeente", ex.getMessage());
		}
		try {
			Invoercontrole.correctBestelwijze(request.getParameter("bestelwijze"));
		} catch (IllegalArgumentException ex) {
			fouten.put("bestelwijze", ex.getMessage());
		}
		return fouten;
	}
}
