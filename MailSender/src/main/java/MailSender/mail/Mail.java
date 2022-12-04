package MailSender.mail;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Cette classe permet de générer une liste de requêtes SMTP permettant l'envoi d'un mail
 * forgé de la part d'un expéditeur donné et de destinataires donnés. Ces derniers sont à
 * fournir sous forme de tableau de Personne. Le premier du tableau correspond à l'expéditeur
 * et les autres aux destinataires.
 * </br>
 * Le corps du mail est choisi aléatoirement dans le fichier prank.utf8.
 *
 * @author : T. Germano, G. Courbat
 */

public class Mail {

	private final String[] msg;

	public Mail(Personne[] tab) throws FileNotFoundException {

		List<String> listOfStrings = new ArrayList<>();


		// charge le body des mails dans un scanner
		Scanner sc = new Scanner(new FileInputStream("MailSender/src/main/java/MailSender/config" +
			"/prank.utf8"),
			StandardCharsets.UTF_8).useDelimiter("==");
		String str;

		// ajoute tous les mails dans un arraylist
		while (sc.hasNext()) {
			str = sc.next();
			// adding each string to arraylist
			listOfStrings.add(str);
		}

		sc.close();

		// mélange la liste, comme ça on change de prank à chaque envoi de mails
		Collections.shuffle(listOfStrings);

		// convertis arraylist en tableau
		String[] array = new String[listOfStrings.size()];

		array = listOfStrings.toArray(array);

		// nombre de champs
		msg = new String[7];

		{
			msg[0] = "EHLO me.com\r\n";
			msg[1] = "MAIL FROM:" + tab[0] + "\r\n";
			msg[2] = "RCPT TO:";
		}

		for (int k = 1; k < tab.length; ++k) {
			if (k != tab.length - 1) {
				msg[2] += tab[k] + ";";
			} else {
				msg[2] += tab[k] + "\r\n";
			}
		}

		{
			msg[3] = "DATA\r\n";
			msg[4] = "Content-Type: text/plain; charset=\"utf-8\"\r\n";
			msg[4] += "From: " + tab[0] + "\r\n";
			msg[4] += "To: ";
			for (int k = 1; k < tab.length; ++k) {
				if (k != tab.length - 1) {
					msg[4] += tab[k] + ", ";
				} else {
					msg[4] += tab[k] + "\r\n";
				}
			}
			msg[5] = "Subject: Info importantes\r\n\r\n";
			msg[5] += array[0] + "\r\n.\r\n";
			msg[6] = "QUIT\r\n";
		}
	}

	public String[] getMsgs() {
		return msg;
	}
}