package Graph;

import java.util.Iterator;
import java.util.List;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.Edge;

public class Algo_Dijkstra {
	public Algo_Dijkstra(){
		
	}
GraphStream Gr = new GraphStream();
	public void plusCourtChemin(List<String> list){
		try {
			
		
		Dijkstra dijkstra= new Dijkstra(Dijkstra.Element.EDGE,null,"cout");
		dijkstra.init(Gr.graph);	
		dijkstra.setSource(Gr.graph.getNode(list.get(0)));
		//dijkstra.setSource(graph.getNode("http://Cinema"));
		dijkstra.compute();
		for(int i=1;i<list.size();i++){
			Iterator<Edge> it = Gr.graph.getEdgeIterator();
		       it= (Iterator<Edge>) dijkstra.getPathEdgesIterator(Gr.graph.getNode(list.get(i)));
		       while(it.hasNext())
		       {
		    	   Edge edge=it.next();
		    	   edge.addAttribute("ui.style", "fill-color: pink;");
		    	   edge.addAttribute("ui.style", "size: 5;");
		       }
		}
		}
		catch( java.lang.IndexOutOfBoundsException e){
			System.out.println(e.getMessage());
		}
		
             
		
		
		
	}
}
