package Main;

import java.awt.EventQueue;

import Interface.Interface;
import SPARQL.MySPARQL;

public class Main {

	public static void main(String[] args) {

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
