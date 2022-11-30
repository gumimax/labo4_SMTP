package mail;

public class Mail {

	private String debut;
	private String envoyeur;
	private String joke;
	private String receveurs;
	private String data;
	private String corps;

	private String fin;

	public Mail(Personne[] tab){

		debut = "EHLO me.com\r\n";

		envoyeur = "MAIL FROM:<" + tab[0] + ">\r\n";

		for(int k = 1; k < tab.length; ++k){
			receveurs = "RCPT TO:<" + tab[k] + "\r\n";
		}
		data = "DATA\r\n";
		//put joke...
		corps = "dd";
		fin = "QUIT\r\n";
	}

	// ici on créer un string utf8 avec tous les champs
	// un mail avec comme attributs les champs ?

	// FROM += = generator.envoyeur
	// RCPT to += generator.receveurs

	// DATA += subject: Salut les amis \r\n + prank.

	// QUIT = "QUIT\r\n"
	// etc etc


	// pour ajouter plusieurs reveceur au champs RCPT to: juste coller les adresses
	// avec un ; entre-elles
}
