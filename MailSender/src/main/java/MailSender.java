import mail.Generator;
import mail.Mail;
import smtpClient.ClientSMTP;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MailSender {


	public static void main(String[] args) {

		// charge les propriétés

		try {
			InputStream input = new FileInputStream("MailSender/src/main/java/config" +
				"/config.properties");

			Properties prop = new Properties();

			// charges les proptiétés
			prop.load(input);

			// parsing en un tab de string
			String[] tokens = new String[prop.size()];

			tokens[0] = prop.getProperty("smtpServerAddress");
			tokens[1] = prop.getProperty("smtpServerPort");
			tokens[2] = prop.getProperty("grpNb");

			int grpNB = Integer.parseInt(tokens[2]);

			// génère les grps
			Generator g1 = new Generator(grpNB);

			// génère et envoie les mails
			for (int  k = 0; k < grpNB; ++k){
				Mail mail = new Mail(g1.getGrp(k));
				new ClientSMTP(mail, tokens[0], grpNB);
			}


		} catch (IOException ex) {
			ex.printStackTrace();
		}



		// y'a des classes pour lire les properties...
		// Tout doit être en utf8.
		// charger les propriétés.
		// les pranks sont délimitées par un == comme ça on sait ou terminer.
		// creer le nb de grp de personnes
		// creer le mail stmp avec la prank
		// creer un client smtp et passer le mail, le port et l'adresse dedans
		// terminer
		// pas oublier d'ajouter la gestion d'erreur

		// Pour finir faudra faire le read.me + ajouter un pti uml de l'app...


	}
}
