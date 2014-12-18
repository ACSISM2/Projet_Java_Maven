package Main;

import java.awt.EventQueue;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.util.FileManager;

import Interface.Interface;
import SPARQL.MySPARQL;

public class Main {
	
	public static void main(String[] args) {
		//Model model = FileManager.get().loadModel("F:/Utilisateurs/Oussama/Desktop/Soutenance JAVA/PROJET/RDF/ArnoldMovies (3).rdf");
		//m.sparqlTest(model);
		//m.sparqlTest();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {	
					Interface frame = new Interface();
					frame.setTitle("Recherche par mots-clés dans des données RDF");
					frame.setVisible(true);			
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}); 

	}

}
