package mail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Mail {

	private String msg;

	public Mail(Personne[] tab) throws FileNotFoundException {

		// arraylist to store strings
		List<String> listOfStrings = new ArrayList<String>();

		// load content of file based on specific delimiter
		Scanner sc = new Scanner(new FileInputStream("MailSender/src/main/java" +
			"/config/prank.utf8"),
			StandardCharsets.UTF_8).useDelimiter("==");
		String str;

		// checking end of file
		while (sc.hasNext()) {
			str = sc.next();
			// adding each string to arraylist
			listOfStrings.add(str);
		}

		sc.close();
		// mélange la liste, comme ça on change de prank à chaque envoie de mails
		Collections.shuffle(listOfStrings);
		// convert any arraylist to array
		String[] array = new String[listOfStrings.size()];

		array = listOfStrings.toArray(array);

		msg = "EHLO me.com\r\n" + "MAIL FROM:<" + tab[0] + ">\r\n";
		msg += "RCPT TO:<";

		for(int k = 1; k < tab.length; ++k){
			if(k != tab.length - 1) {
				msg += tab[k] + ":";
			}else{
				msg += tab[k] + ">\r\n";
			}
		}
		msg += "DATA\r\n";
		msg += "Subject: Info importantes\r\n\r\n";
		msg += array[0] + "\n\r\n.\r\nQUIT\r\n";
		/*
		msg += "\r\n";
		msg += "QUIT\r\n";*/
	}

	public String getMsg() {
		return msg;
	}
}
