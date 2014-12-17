package SPARQL;
import java.util.Iterator;

import RDF.Myrdf;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.util.FileManager;

public class MySPARQL {
	static Myrdf my = new Myrdf();
public static void sparqlTest(){
	
	//Model model = FileManager.get().loadModel(my.model);
	
	
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
	
	
	String queryString1= queryStringPrefix+
	"SELECT DISTINCT ?realisateur WHERE {movie:Movies ?x ?y.?y movie:title \"Twins\".?y movie:directedBy ?realisateur}";
	
	// ### Liste des titres, réalisateurs et dates de sortie des films ordonnées par la date de sortie.
	
	String queryString2=queryStringPrefix+
	"SELECT ?movieTitle ?director ?dateRelease "+
	"WHERE {?movie movie:directedBy ?director ; movie:title ?movieTitle ; "+
	"movie:releaseDate ?dateRelease } ORDER BY ASC(?dateRelease)";
// Requêtte 1:
	
Query query1= QueryFactory.create(queryString1);

//Requêtte 2:

Query query2= QueryFactory.create(queryString2);

// Execution Requêtte 1 :

QueryExecution qexec1 = QueryExecutionFactory.create(query1,my.model);

//Execution Requêtte 2 :

QueryExecution qexec2 = QueryExecutionFactory.create(query2,my.model);

try{
	// Resultats du requêtte 1 :
	
	ResultSet results1= qexec1.execSelect();
	System.out.println("R = " +results1.getResultVars());
	while(results1.hasNext()){
		QuerySolution soln = results1.nextSolution();
		Literal name= soln.getLiteral("?realisateur");
		System.out.println("Name: "+name);
		
	}
	
	// Resultats du requêtte 2 :
	
	ResultSet results2= qexec2.execSelect();
	System.out.println("R = " +results2.getResultVars());
	while(results2.hasNext()){
		QuerySolution soln = results2.nextSolution();
		Literal Titre= soln.getLiteral("?movieTitle");
		Literal Réalisateur= soln.getLiteral("?director");
		Literal Date= soln.getLiteral("?dateRelease");
		System.out.println("Titre : "+Titre+ "|| Réaliser par : "+ Réalisateur+ " || Date : "+Date);
	}
}
finally {
	qexec1.close();
	qexec2.close();
}

}
}
