package MailSender;

import MailSender.smtpClient.ClientSMTP;
import MailSender.mail.Mail;
import MailSender.mail.Generator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Classe entrée de progromme, Sert à tout initialiser et envoyer les mails au
 * serveur
 *
 * @author : T. Germano, G. Courbat
 */
public class MailSender {

	public static void main(String[] args) {

		try {
			String currentPath = System.getProperty("user.dir");

			InputStream input = new FileInputStream(currentPath + "/../src/main/java/MailSender/config" +
				"/config.properties");

			Properties prop = new Properties();

			// charges les propritiétés
			prop.load(input);

			input.close();

			// parsing en un tab de string
			String[] tokens = new String[prop.size()];

			tokens[0] = prop.getProperty("smtpServerAddress");
			tokens[1] = prop.getProperty("smtpServerPort");
			tokens[2] = prop.getProperty("grpNb");


			int grpNB = Integer.parseInt(tokens[2]);

			// génère les groupes
			Generator g1 = new Generator(grpNB);

			// génère et envoie les mails
			for (int k = 0; k < grpNB; ++k) {
				Mail mail = new Mail(g1.getGrpTab(k));
				new ClientSMTP(mail, tokens[0], Integer.parseInt(tokens[1])).SMTPRequests();
			}


		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
