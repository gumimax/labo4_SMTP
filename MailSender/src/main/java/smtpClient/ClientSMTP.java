package smtpClient;

import mail.Mail;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cette classe représente
 *
 * @author : T. Germano, G. Courbat
 */

public class ClientSMTP {

	static final Logger LOG = Logger.getLogger(ClientSMTP.class.getName());
	private final int port;
	private final String nomServeur;
	private final Mail mail;

	/**
	 * Constructeur
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
	 * envoye les requêtes smtp et recupère les réponses server
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
	 * envoye les requêtes smtp et recupère les réponses server
	 *
	 * @param clientSocket le socket client
	 * @param requete      le tableau de string contenant les requêtes
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
