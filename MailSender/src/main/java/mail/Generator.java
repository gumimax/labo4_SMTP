package mail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class Generator {

	private final Personne[][] tab;

	// va générer les grp de personne. il faut 1 envoyeur et 2 receveur mini
	// donc 3 personnes
	// si le nb de grp demandé est trop grand par rapport à la list de la list->
	// renvoyer une erreur taille grp trop grande
	public Generator(int nbGroup) throws FileNotFoundException {


		// arraylist to store strings
		List<String> listOfStrings = new ArrayList<String>();

		// load content of file based on specific delimiter
		Scanner sc = new Scanner(new FileInputStream("MailSender/src/main/java" +
			"/config/listOfAddress.utf8"),
			StandardCharsets.UTF_8).useDelimiter("\n");
		String str;

		// checking end of file
		while (sc.hasNext()) {
			str = sc.next();
			// adding each string to arraylist
			listOfStrings.add(str);
		}
		// mélange la liste, comme ça on change de sender à chaque fois
		Collections.shuffle(listOfStrings);
		// convert any arraylist to array
		String[] array = new String[listOfStrings.size()];
		array = listOfStrings.toArray(array);

		int tailleGrp = array.length / nbGroup;

		if(tailleGrp < 3){
			throw new RuntimeException("Nombre de grp trop grand...");
		}

		tab = new Personne[nbGroup][tailleGrp];

		for (int k = 0; k < nbGroup; ++k){
			for( int j = 0; j < tailleGrp; ++j){
				tab[k][j] = new Personne(array[(nbGroup * k) + j]);
			}
		}
	}

	public Personne[] getGrpTab(int k) {
		return tab[k];
	}
}
