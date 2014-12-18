package Graph;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;




import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;


public class GraphStream {

	private Model model;
	public static MultiGraph graph;
	

	public GraphStream(Model model) {
		this.model = model;
		graph=new  MultiGraph("");
		
	}
	public GraphStream() {
	
	}
	
	public MultiGraph construir_Graph() throws IOException, InterruptedException{
		
		this.ajouter_noeud();

		        		
		return graph;
		
	}
	
	
	
	public void ajouter_noeud()throws IOException, InterruptedException {
		
		
		 String styleSheet= "node.resultat {"+"shape: box;"+
				 "fill-color: #EEEE00;"+
				   " size: 25px;"+
				   "}"+"node.resultat2 {"+"shape: box;"+
				 "fill-color: blue;"+
				   " size: 30px;"+
				   "}"+"node.resultat3 {"+"shape: box;"+
					 "fill-color: pink;"+
					   " size: 40px;"+
					   "}"+"graph{fill-color:#E0EEEE ;padding: 20px;}";
		 graph.addAttribute("ui.stylesheet", styleSheet);
		// list the statements in the graph
        StmtIterator iter = model.listStatements();

    
		
		    while (iter.hasNext()) {
		        Statement stmt      = iter.nextStatement();         // get next statement
		        Resource  subject   = stmt.getSubject();   // get the subject
		      
		        RDFNode   object    = stmt.getObject();    // get the object

		       if(!existNode(subject.toString()))// Vérifie si le sujet existe déja 
		        {
		        	graph.addNode(subject.toString());
		        	graph.getNode(subject.toString()).addAttribute("ui.label", subject.toString());
		        	graph.getNode(subject.toString()).addAttribute("ui.style", "fill-color: rgb(255,128,0);");
		        	//rgb(51,51,153);");
		        }
		        

		        if(!existNode(object.toString()))// Vérifie si l'objet existe déja
		        {
		        	graph.addNode(object.toString());
		        
		        }
		        int cout=1;
		        
	            if (object instanceof Resource) {
	            	
	            	// Resource
	            	graph.getNode(object.toString()).addAttribute("ui.label",object.toString() );
		        	graph.getNode(object.toString()).addAttribute("ui.style", "fill-color: rgb(255,128,0);");
		        	graph.getNode(object.toString()).addAttribute("ui.size",40);
		        	
		        	cout=2;
	            	
	            }
	            else{
	            	
	            	// Litteral
	            	
	            	graph.getNode(object.toString()).addAttribute("ui.label",object.toString());
	            	graph.getNode(object.toString()).addAttribute("ui.style", "fill-color: rgb(40,90,49);");
	            	
	            }
	            
	            if(graph.getEdge(subject.toString()+object.toString())==null){
	            
	            
	            	
	            	graph.addEdge(subject.toString()+object.toString(), subject.toString(),object.toString()).addAttribute("cout", cout);
	            }
	            
		       
		        	
		    }

	}
	
	
	public void afficher_Resulta_Noeud(List<String> resultat){
		
		Node current;
		try{
		for(String r: resultat){
			
			current=graph.getNode(r.toString());
			current.addAttribute("ui.class", "resultat");
			
			//current.addAttribute("ui.style", "fill-color: rgb(0,0,0);");
		}
		}catch(java.lang.NullPointerException e){System.out.println(e.getMessage()+" erreur ");}
	}
public void afficher_Resulta_Noeud(List<String> resultat,String g){
		
		Node current;
		try{
		for(String r: resultat){
			System.out.println(r.toString());
			System.out.println("Je suis la");
			current=graph.getNode(r.toString());
			current.addAttribute("ui.class", "resultat2");
			
			//current.addAttribute("ui.style", "fill-color: rgb(0,0,0);");
		}
		}catch(java.lang.NullPointerException e){System.out.println(e.getMessage()+" erreur ");}
	}
	
	
	
	
	public boolean existNode(String id_node){
		
		Node node =null;
		node=graph.getNode(id_node);
		
		if(node==null)
			return false;
		return true;
	}
	
	
	
	public  Graph getGraph() {
		return graph;
	}

	public  void setGraph(MultiGraph graph) {
		this.graph = graph;
	}

	public final Model getModel() {
		return model;
	}

	public final void setModel(Model model) {
		this.model = model;
	}
	
	
	
}
