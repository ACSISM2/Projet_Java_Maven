package Graph;

import java.util.Iterator;
import java.util.List;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.Edge;

import SPARQL.MySPARQL;

public class Algo_Dijkstra {
	
	public Algo_Dijkstra(){
		
	}
GraphStream Gr = new GraphStream();
static MySPARQL m = new MySPARQL();
public  static String titrefilm;
public  static String rolepers;
	public void plusCourtChemin(List<String> list){
		try {
		
			System.out.println("**************");
			System.out.println(list.get(0));
			System.out.println(list.get(1));
			System.out.println(list.get(2));
			System.out.println("**************");
			
	
		
		Dijkstra dijkstra= new Dijkstra(Dijkstra.Element.EDGE,null,"cout");
		dijkstra.init(Gr.graph);	
		dijkstra.setSource(Gr.graph.getNode(list.get(0)));
		//dijkstra.setSource(graph.getNode("http://Cinema"));
		dijkstra.compute();
		String Str="";
		for(int i=1;i<list.size();i++){
			Iterator<Edge> it = Gr.graph.getEdgeIterator();
		       it= (Iterator<Edge>) dijkstra.getPathEdgesIterator(Gr.graph.getNode(list.get(i)));
		       
		       while(it.hasNext())
		       {   
		    	   Edge edge=it.next();
		    	   
		    	   if(i==list.size()-1&& edge.toString().contains("http://movie#")&& !edge.toString().contains("http://movie#M")){
		    	   
		    	   Str=Str+edge.toString();
		    	   System.out.println(Str);
		    	   System.out.println("--------------------");   
		    	   edge.addAttribute("ui.style", "fill-color: pink;");
		    	   edge.addAttribute("ui.style", "size: 5;");
		    	  
		    	   }		    	
		    	   else{ edge.addAttribute("ui.style", "fill-color: green;");
		    	   edge.addAttribute("ui.style", "size: 5;");
		    	   }
		    	  
		       }
		       
		}
		int ind=Str.lastIndexOf("--");
		int ind2=Str.lastIndexOf("]");
		 titrefilm=Str.substring(ind+2,ind2);
		        rolepers=Str;
		
		//System.out.println(titre);
		//m.sparqlTest(titre,Str);
		}
		catch( java.lang.IndexOutOfBoundsException e){
			System.out.println(e.getMessage());
		}
		
             
		
		
		
	}
}
