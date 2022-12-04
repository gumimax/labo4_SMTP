package MailSender.mail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Cette classe représente une personne et son adresse mail, elle vérifie que l'e-mail
 * est bien conforme
 *
 * @author : T. Germano, G. Courbat
 */
public class Personne {
	private final String address;

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
			Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	/**
	 * Constructeur de la classe Personne
	 * @param address l'adresse mail d'une personne
	 * @throws IllegalArgumentException un mail est mal constitué
	 */
	public Personne(String address){
		if (validateEmail(address)) {
			this.address = address;
		} else {
			throw new IllegalArgumentException("L'adresse mail n'est pas valide");
		}
	}

	/**
	 * méthode qui permet d'afficher l'adresse mail d'une personne
	 * @return Un string contenant l'adresse mail de la personne
	 */
	public String toString() {
		return address;
	}

	/**
	 * Méthode qui permet de verifier si un mail match le pattern
	 * @param email l'adresse email de la personne
	 * @return true si correcte, false si faux
	 */
	private static boolean validateEmail(String email) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.find();
	}
}
