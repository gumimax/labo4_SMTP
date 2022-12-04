package MailSender.mail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Cette classe permet de générer une liste de groupe aléatoire de taille donnée nbGroup
 * à partir des e-mails contenus dans le fichier listOfAddress.utf8 dans le but d'effectuer
 * une campagne de farce par mail forgé.
 *
 * Les groupes sont constitués d'un expéditeur et d'au moins 2 destinataires.
 *
 * @author : T. Germano, G. Courbat
 */
public class Generator {

	private final Personne[][] tab;

	/**
	 * Constructeur d'un générateur de groupe
	 * @param nbGroup nombre de groupes de victimes voulus
	 * @throws FileNotFoundException si le chemin de la liste d'adresse est faux
	 * @throws RuntimeException si les groupes contiennent moins de 3 personnes
	 */
	public Generator(int nbGroup) throws FileNotFoundException {


		// arraylist pour stocker les strings
		List<String> listOfStrings = new ArrayList<>();

		String currentPath = System.getProperty("user.dir");

		// load content of file based on specific delimiter
		Scanner sc = new Scanner(new FileInputStream(currentPath + "/../src/main/java" +
			"/MailSender/config/listOfAddress.utf8"),
			StandardCharsets.UTF_8).useDelimiter("\n");
		String str;

		// check la fin de fichier
		while (sc.hasNext()) {
			str = sc.next();
			// ajout de chaque string dans l'arraylist
			listOfStrings.add(str);
		}

		sc.close();

		// mélange la liste, comme ça on change de sender à chaque fois
		Collections.shuffle(listOfStrings);
		// convert any arraylist to array
		String[] array = new String[listOfStrings.size()];
		array = listOfStrings.toArray(array);

		int tailleGrp = array.length / nbGroup;

		if (tailleGrp < 3) {
			throw new RuntimeException("Nombre de grp trop grand...");
		}

		tab = new Personne[nbGroup][tailleGrp];
		// Chaque groupe a le même nombre de personnes. Cependant, si mauvais
		// multiple, il y aura des personnes dans la liste qui ne seront pas prises en
		// compte pour faire les groupes. Par contre, le nombre de groupes est
		// respecté.
		for (int k = 0; k < nbGroup; ++k) {
			for (int j = 0; j < tailleGrp; ++j) {
				tab[k][j] = new Personne(array[(nbGroup * k) + j]);
			}
		}
	}

	/**
	 * méthode retournant un groupe de personne
	 * @param k index dans le premier tableau bi-dimensionnel
	 * @return un groupe de personne (tableau)
	 */
	public Personne[] getGrpTab(int k) {
		return tab[k];
	}
}
