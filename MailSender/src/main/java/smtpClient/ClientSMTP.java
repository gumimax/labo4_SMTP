package smtpClient;

import mail.Mail;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientSMTP {

	static final Logger LOG = Logger.getLogger(ClientSMTP.class.getName());
	final int port;
	final String serverName;
	final Mail mail;
	public ClientSMTP(Mail mailToSend, String serverName, int port){
		this.port = port;
		this.serverName = serverName;
		this.mail = mailToSend;
	}
	public void SMTPRequests() {
		Socket clientSocket = null;


		try {
			clientSocket = new Socket(serverName, port);
		} catch (IOException ex) {
			LOG.log(Level.SEVERE,null, ex);
			return;
		}
		handler(clientSocket, mail.getMsg());
	}
	/**
	 * envoye la requête smtp et recupère le réponse server
	 * @param clientSocket
	 * @param request
	 */
	private void handler(Socket clientSocket, String request){
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

			out.write(request);
			out.flush();

			LOG.log(Level.INFO, "*** Response sent by the server: ***");
			String line;
			while ((line = in.readLine()) != null) {
				LOG.log(Level.INFO, line);
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
				if (clientSocket != null && ! clientSocket.isClosed()) clientSocket.close();
			} catch (IOException ex) {
				LOG.log(Level.SEVERE, ex.toString(), ex);
			}
		}

	}
}
