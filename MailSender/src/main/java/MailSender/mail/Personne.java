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

	public Personne(String address){
		if (validateEmail(address)) {
			this.address = address;
		} else {
			throw new IllegalArgumentException("L'adresse mail n'est pas valide");
		}
	}
	public String toString() {
		return address;
	}

	private static boolean validateEmail(String email) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.find();
	}
}
