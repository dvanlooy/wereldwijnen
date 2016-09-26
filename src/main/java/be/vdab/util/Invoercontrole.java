package be.vdab.util;

import be.vdab.enums.Bestelwijze;

public final class Invoercontrole {

	/**
	 * checks if given long is positive
	 * 
	 * @param param
	 * @param error
	 * @return Long
	 * @throws IllegalArgumentException
	 */
	public final static Long positiveLong(long param, String error) throws IllegalArgumentException {
		if (param < 0) {
			throw new IllegalArgumentException(error);
		}
		return param;
	}

	/**
	 * checks if given String is not empty or not null
	 * 
	 * @param param
	 * @param error
	 * @return String
	 * @throws IllegalArgumentException
	 */
	public final static String noEmptyOrNullString(String param, String error) throws IllegalArgumentException {
		if (param == null || param.equals("")) {
			throw new IllegalArgumentException(error);
		}
		return param;
	}

	/**
	 * checks if given String matches Belgian postal code
	 * 
	 * @param param
	 * @return String
	 * @throws IllegalArgumentException
	 */
	public final static String correctPostcodeBE(String param) throws IllegalArgumentException {
		try {
			Integer postcode = Integer.parseInt(param);
			if (postcode < 1000 || postcode > 9992) {
				// throw new om in Map fouten te zetten
				throw new IllegalArgumentException(
						"Belgische postcodes bestaan uitsluitend uit getallen van vier cijfers, van 1000 tot en met 9992.");
			}
		} catch (NumberFormatException ex) {
			// throw new om in Map fouten te zetten
			throw new IllegalArgumentException("Belgische postcodes bestaan uitsluitend uit getallen.");
		}
		return param;
	}

	/**
	 * checks if given String matches Bestelwijze enum
	 * 
	 * @param param
	 * @return
	 * @throws IllegalArgumentException
	 */
	public final static String correctBestelwijze(String param) throws IllegalArgumentException {
		try {
			Bestelwijze.valueOf(param);
			return param;
		} catch (IllegalArgumentException | NullPointerException ex) {
			// throw new om in Map fouten te zetten
			throw new IllegalArgumentException("Bestelwijze niet correct");
		}

	}

}
