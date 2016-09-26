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

		if (mandje != null) {
			// maak set bestelbonlijnen aan
			Set<Bestelbonlijn> bestelbonlijnen = createBestelbonlijnen(mandje);
			if (bestelbonlijnen != null) {

				// bereken totaal
				BigDecimal totaal = BigDecimal.ZERO;
				for (Bestelbonlijn bestelbonlijn : bestelbonlijnen) {
					totaal = totaal.add(bestelbonlijn.getTotaleWaarde());
				}

				// zet alles in request attribute
				request.setAttribute("bestelbonlijnen", bestelbonlijnen);
				request.setAttribute("bestelwijzen", Bestelwijze.values());
				request.setAttribute("totaal", totaal);
			}
		}
		// zet alle landen in request attribute
		request.setAttribute("landen", landService.findAll());

		// -->
		request.getRequestDispatcher(VIEW).forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// lees mandje uit session
		Map<Long, Long> mandje = getMandjeFromSession(request);

		if (mandje != null) {

			// op vuilbakje geklikt
			String action = request.getParameter("action");
			if (action.equals("remove")) {

				// verwijder regel uit mandje
				String param = request.getParameter("remove");
				if (param != null) {
					removeItemFromMandje(request, param, mandje);
					response.sendRedirect(String.format(REDIRECT_URL_RETURN, request.getContextPath()));
				}
			}

			// bestelbon ingevuld
			if (action.equals("maakBestelbon")) {

				// invoervelden controleren
				Map<String, String> fouten = inputFouten(request);

				// geen fouten gevonden: Go!
				if (fouten.isEmpty()) {

					// nieuwe bestelbon
					Bestelbon bestelbon = new Bestelbon(request.getParameter("naam"),
							new Adres(request.getParameter("straat"), request.getParameter("huisnummer"),
									request.getParameter("postcode"), request.getParameter("gemeente")),
							Bestelwijze.valueOf(request.getParameter("bestelwijze")), createBestelbonlijnen(mandje));

					// persist bestelbon & update in bestelling

					bestelbonService.create(bestelbon);

					// session (mandje) verwijderen
					request.getSession().invalidate();

					// id in parameter is eenvoudige oplossing,
					// maar succespagina kan gemanipuleerd worden,
					// eventueel in session steken
					response.sendRedirect(
							String.format(REDIRECT_URL_SUCCESS, request.getContextPath(), bestelbon.getId()));
				} else {
					request.setAttribute("fouten", fouten);
					doGet(request, response);

				}
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
			request.getSession().invalidate();
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

	/**
	 * controleert de invoervelden van het betselformulier en geeft een map met
	 * fouten terug en vult de parameter map correcteVelden aan
	 * 
	 * @param request
	 * @param correcteVelden
	 * @return map met fouten
	 */
	private Map<String, String> inputFouten(HttpServletRequest request) {
		Map<String, String> fouteVelden = new HashMap<>();

		try {
			String naam = request.getParameter("naam");
			Invoercontrole.noEmptyOrNullString(naam, "Naam mag niet leeg zijn");
		} catch (IllegalArgumentException ex) {
			fouteVelden.put("naam", ex.getMessage());
		}

		try {
			String straat = request.getParameter("straat");
			Invoercontrole.noEmptyOrNullString(straat, "Straat mag niet leeg zijn");
		} catch (IllegalArgumentException ex) {
			fouteVelden.put("straat", ex.getMessage());
		}
		try {
			String huisnummer = request.getParameter("huisnummer");
			Invoercontrole.noEmptyOrNullString(huisnummer, "Huisnummer mag niet leeg zijn");
		} catch (IllegalArgumentException ex) {
			fouteVelden.put("huisnummer", ex.getMessage());
		}
		try {
			String postcode = request.getParameter("postcode");
			Invoercontrole.correctPostcodeBE(postcode);
		} catch (IllegalArgumentException ex) {
			fouteVelden.put("postcode", ex.getMessage());
		}
		try {
			String gemeente = request.getParameter("gemeente");
			Invoercontrole.noEmptyOrNullString(gemeente, "Gemeente mag niet leeg zijn");
		} catch (IllegalArgumentException ex) {
			fouteVelden.put("gemeente", ex.getMessage());
		}
		try {
			Invoercontrole.correctBestelwijze(request.getParameter("bestelwijze"));
		} catch (IllegalArgumentException ex) {
			fouteVelden.put("bestelwijze", ex.getMessage());
		}
		return fouteVelden;
	}
}
