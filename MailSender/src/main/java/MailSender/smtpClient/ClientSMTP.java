package MailSender.smtpClient;

import MailSender.mail.Mail;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cette classe permet l'envoi de requêtes SMTP à un serveur SMTP.
 * </br>
 * Pour cela, il est nécessaire de fournir, l'adresse du serveur mail,
 * le port du serveur mail, et la liste de requêtes SMTP à envoyer.
 *
 * @author : T. Germano, G. Courbat
 */

public class ClientSMTP {

	static final Logger LOG = Logger.getLogger(ClientSMTP.class.getName());
	private final int port;
	private final String nomServeur;
	private final Mail mail;

	/**
	 * Constructeur de la classe ClientSMTP
	 * @param mailAEnv
	 * @param nomServeur
	 * @param port
	 */
	public ClientSMTP(Mail mailAEnv, String nomServeur, int port) {
		this.port = port;
		this.nomServeur = nomServeur;
		this.mail = mailAEnv;
	}

	/**
	 * Se connecte au serveur SMTP et envoie le mail
	 */
	public void SMTPRequests() {
		Socket clientSocket;

		try {
			clientSocket = new Socket(nomServeur, port);
		} catch (IOException ex) {
			LOG.log(Level.SEVERE, null, ex);
			return;
		}
		handler(clientSocket, mail.getMsgs());
	}

	/**
	 * Envoie les requêtes smtp permettant l'envoie du mail forgé
	 * et récupère les réponses server. Gère les erreurs.
	 *
	 * @param clientSocket le socket client
	 * @param requete      le tableau de string contenant les requêtes à envoyer
	 */
	private void handler(Socket clientSocket, String[] requete) {
		//buffer in et out
		BufferedWriter out = null;
		BufferedReader in = null;

		try {
			out =
				new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(),
					StandardCharsets.UTF_8));
			in =
				new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),
					StandardCharsets.UTF_8));

			String reponse = in.readLine();
			LOG.info(reponse);
			out.write(requete[0]);
			out.flush();
			reponse = in.readLine();

			if (!reponse.startsWith("250")) {
				throw new IOException("SMTP ERROR: " + reponse);
			}
			while (reponse.startsWith("250-")) {
				reponse = in.readLine();
				LOG.info(reponse);
			}


			for (int k = 1; k < requete.length; ++k) {

				out.write(requete[k]);
				out.flush();

				if (!(k == 4)) {
					reponse = in.readLine();
					LOG.info(reponse);
				}

			}

		} catch (IOException ex) {
			LOG.log(Level.SEVERE, ex.toString(), ex);
		} finally {
			try {
				if (out != null) out.close();
			} catch (IOException ex) {
				LOG.log(Level.SEVERE, ex.toString(), ex);
			}
			try {
				if (in != null) in.close();
			} catch (IOException ex) {
				LOG.log(Level.SEVERE, ex.toString(), ex);
			}
			try {
				if (clientSocket != null && !clientSocket.isClosed())
					clientSocket.close();
			} catch (IOException ex) {
				LOG.log(Level.SEVERE, ex.toString(), ex);
			}
		}

	}
}
