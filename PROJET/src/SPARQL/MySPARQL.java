package SPARQL;
import java.util.ArrayList;
import java.util.Iterator;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;

public class MySPARQL {
	
	public ArrayList<String> resultat_sparql;
	public ArrayList<String> resultat_sparql1;
	
	public ArrayList<String>sparqlTest(String film,String role,Model model){

		//Model model = FileManager.get().loadModel("F:/Utilisateurs/Oussama/Desktop/Soutenance JAVA/PROJET/RDF/ArnoldMovies (3).rdf");
		resultat_sparql=new ArrayList<String>();
		resultat_sparql1=new ArrayList<String>();
		System.out.println(film);
		System.out.println(role);
		



		// Requêtte pour Prefix de fichier RDF 

		String queryStringPrefix=
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
						"PREFIX movie: <http://movie#> "+
						"PREFIX foaf: <http://xmlns.com/foaf/0.1/> "+
						"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "+
						"PREFIX local: <http://Vocab#> "+
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
						"PREFIX arnold: <http://Arnold#> ";

		// ### nom du réalisateur du film Twins


		//String titre="Twins";
		//String queryString1= queryStringPrefix+
		//"SELECT DISTINCT ?realisateur WHERE {movie:Movies ?x ?y.?y movie:title \"Twins\".?y movie:directedBy ?realisateur}";

		/*String queryString1= queryStringPrefix+
			"SELECT DISTINCT ?realisateur WHERE {movie:filmId_2 ?x ?y.?y movie:title \"Twins\".?y movie:directedBy ?realisateur}";
		 */	
		//String film="The Expendables 3";
		if(role!=null){
		String queryString3 = null;
		String queryString4 = null;
if(role.contains("actors")){
	 queryString3=queryStringPrefix+
			"SELECT DISTINCT ?listActors WHERE {movie:Movies ?x ?y.?y movie:title \""+ film +"\".?y movie:movieActor ?actors.?actors rdfs:member ?listActors}";
	 Query query3= QueryFactory.create(queryString3);
	 QueryExecution qexec3 = QueryExecutionFactory.create(query3,model);
	 ResultSet results3= qexec3.execSelect();
		System.out.println("R3 = " +results3.getResultVars());
		while(results3.hasNext()){
			QuerySolution soln = results3.nextSolution();
			Literal Actor= soln.getLiteral("?listActors");
			System.out.println("Actor : "+Actor);
			 resultat_sparql.add(Actor.toString());
		}
		qexec3.close();
			
		}
else if(role.contains("writers")){
		
	 queryString4=queryStringPrefix+
			"SELECT DISTINCT ?listWriters WHERE {movie:Movies ?x ?y.?y movie:title \""+ film +"\".?y movie:movieWriter ?writers.?writers rdfs:member ?listWriters}";
	
	 Query query4= QueryFactory.create(queryString4);	
	 QueryExecution qexec4 = QueryExecutionFactory.create(query4,model);
	 ResultSet results4= qexec4.execSelect();
		System.out.println("R4 = " +results4.getResultVars());
		while(results4.hasNext()){
			QuerySolution soln = results4.nextSolution();
			Literal Writers= soln.getLiteral("?listWriters");
			System.out.println("Writers : "+Writers);
			 resultat_sparql.add(Writers.toString());
		}
		qexec4.close();
	 
		}
return resultat_sparql;
		
		
		

		// ### Liste des titres, réalisateurs et dates de sortie des films ordonnées par la date de sortie.

		/*String queryString2=queryStringPrefix+
	"SELECT ?movieTitle ?director ?dateRelease "+
	"WHERE {?movie movie:directedBy ?director ; movie:title ?movieTitle ; "+
	"movie:releaseDate ?dateRelease } ORDER BY ASC(?dateRelease)";
		 */


		// Requêtte 1:

		//Query query1= QueryFactory.create(queryString1);

		//Requêtte 2:

		//Query query2= QueryFactory.create(queryString2);
		//Requêtte 3:

		
		//Requêtte 4:

	   

		// Execution Requêtte 1 :

		//QueryExecution qexec1 = QueryExecutionFactory.create(query1,model);

		//Execution Requêtte 2 :

		//QueryExecution qexec2 = QueryExecutionFactory.create(query2,model);
		//Execution Requêtte 3 :

		

		//Execution Requêtte 4 :

		
	//	try{
			// Resultats du requêtte 1 :

			/*ResultSet results1= qexec1.execSelect();
	System.out.println("R = " +results1.getResultVars());
	while(results1.hasNext()){
		QuerySolution soln = results1.nextSolution();

		 Literal name = soln.getLiteral("?realisateur");

		System.out.println("Name: "+name);

	}
			 */
			// Resultats du requêtte 2 :

			/*ResultSet results2= qexec2.execSelect();
	System.out.println("R = " +results2.getResultVars());
	while(results2.hasNext()){
		QuerySolution soln = results2.nextSolution();
		Literal Titre= soln.getLiteral("?movieTitle");
		Literal Réalisateur= soln.getLiteral("?director");
		Literal Date= soln.getLiteral("?dateRelease");
		System.out.println("Titre : "+Titre+ "|| Réaliser par : "+ Réalisateur+ " || Date : "+Date);
	}
			 */

			// Resultats du requêtte 3 :

			


			// Resultats du requêtte 4 :

			


		//}
		//finally {
			//qexec1.close();
			//qexec2.close();
			
		//	qexec4.close();
//	}
		}
		else{
			//### Détails d'un films
			String queryString6= queryStringPrefix+
			"SELECT DISTINCT ?movieTitle ?realisateur ?dateRelease ?productionCompany WHERE {movie:Movies ?x ?y.?y movie:title \""+ film +"\".?y movie:directedBy ?realisateur"+
			";movie:title ?movieTitle ; movie:releaseDate ?dateRelease ; movie:productionCompany ?productionCompany }";
			//Requêtte 6:

			Query query6= QueryFactory.create(queryString6);


			//Execution Requêtte 6 :

			QueryExecution qexec6 = QueryExecutionFactory.create(query6,model);


			// Resultats du requêtte 6 :
				
				ResultSet results6= qexec6.execSelect();
				System.out.println("R6 = " +results6.getResultVars());
				
				while(results6.hasNext()){
					QuerySolution soln = results6.nextSolution();
					Literal Titre= soln.getLiteral("?movieTitle");
					Literal Realisateur= soln.getLiteral("?realisateur");
					Literal Date= soln.getLiteral("?dateRelease");
					Literal productionCompany= soln.getLiteral("?productionCompany");
					System.out.println("Détails : ");
					System.out.println("Titre : "+Titre+ "|| Réaliser par : "+ Realisateur+ " || Date : "+Date+ " || Compagny : "+productionCompany);
					resultat_sparql1.add(Titre.toString());
					resultat_sparql1.add(Realisateur.toString());
					resultat_sparql1.add(Date.toString());
					resultat_sparql1.add(productionCompany.toString());
					
				}
			
				return resultat_sparql1;
		}

		
	}
}
