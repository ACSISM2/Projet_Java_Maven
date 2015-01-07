package Index;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

import Interface.Interface;
import Interface.Outils;
import RDF.Myrdf;
import SPARQL.MySPARQL;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDFS;

public class Recherche {

	static MySPARQL m = new MySPARQL();
	Outils tr=new Outils();
	javax.swing.table.DefaultTableModel mod_bis;
	public static Model model;
	Myrdf lec_rdf= new Myrdf(); 
	Interface interf ;
	public static ArrayList<ArrayList<String>> resultat3=new ArrayList<ArrayList<String>>();
	List<String> resultat=new ArrayList<String>();
	List<String> resultat2=new ArrayList<String>();
	List<String> resultat4=new ArrayList<String>();
	public static List<String> result;
	// List<String> resultat=new ArrayList<String>();

	@SuppressWarnings({ "rawtypes", "unused" })
	public Model  Recherche_index(StandardAnalyzer analyzer,Directory index) throws IOException{
		model = ModelFactory.createDefaultModel();
		// 2. query
		resultat.clear();
		resultat2.clear();
		resultat4.clear();
		resultat3.clear();

		String querystr=Interface.textField.getText().concat("*");

		try{
			tr.vider_Jtable(Interface.table);//on vide la table
			@SuppressWarnings("deprecation")
			Query query1 = new QueryParser(Version.LUCENE_CURRENT, "Objet", analyzer).parse(querystr);
			@SuppressWarnings("deprecation")
			Query query2 = new QueryParser(Version.LUCENE_CURRENT, "Sujet", analyzer).parse(querystr);

			// 3. search
			int hitsPerPage = 1000;
			IndexReader reader = DirectoryReader.open(index);
			IndexSearcher searcher = new IndexSearcher(reader);
			TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
			searcher.search(query1, collector);
			ScoreDoc[] hits = collector.topDocs().scoreDocs;
			//	String testeur="";
			int c=hits.length;
			// 4. display results

			for(int i=0;i<hits.length;++i) {
				int docId = hits[i].doc;
				Document d = searcher.doc(docId);
				resultat4.add(d.get("Objet"));
				//	System.out.println((i + 1) + ". " + d.get("Sujet") +"||"+ "\t" + d.get("Objet")+"||"+ "\t" + d.get("Num"));
				ArrayList<String> resultat=new ArrayList<String>();
				resultat.add(d.get("Sujet"));
				resultat.add(d.get("Objet"));
				resultat.add(d.get("Predicat"));
				resultat3.add(i,resultat);
				mod_bis = (javax.swing.table.DefaultTableModel) Interface.table.getModel();

				mod_bis.addRow(new Object[]{d.get("Sujet"),d.get("Predicat"),d.get("Objet")});

			}
			collector = TopScoreDocCollector.create(hitsPerPage, true);
			searcher.search(query2, collector);
			hits = collector.topDocs().scoreDocs;
			//	String testeur="";
			c+=hits.length;
			// 4. display results

			for(int i=0;i<hits.length;++i) {
				int docId = hits[i].doc;
				Document d = searcher.doc(docId);
				resultat2.add(d.get("Sujet"));
				//	System.out.println((i + 1) + ". " + d.get("Sujet") +"||"+ "\t" + d.get("Objet")+"||"+ "\t" + d.get("Num"));
				mod_bis = (javax.swing.table.DefaultTableModel) Interface.table.getModel();

				mod_bis.addRow(new Object[]{d.get("Sujet"),d.get("Predicat"),d.get("Objet")});

				ArrayList<String> resultat=new ArrayList<String>();
				resultat.add(d.get("Sujet"));
				resultat.add(d.get("Objet"));
				resultat.add(d.get("Predicat"));
				resultat3.add(i,resultat);

			}


			result=new ArrayList<String>(resultat4.size()+resultat2.size());
			result.addAll(resultat4);
			result.addAll(resultat2);
			for(ArrayList so: resultat3){

				Resource res = model.createResource(so.get(0).toString());

				//p="zz";
				String tex=so.get(2).toString();
				tex=tex.substring(tex.lastIndexOf("#")+1);
				int cont=0;
				
				if(cont==0)
					res.addProperty(RDFS.label,so.get(1).toString());

			}

			model.write(System.out);
			//-----------------------------------------------------------------------------------
			//m.sparqlTest(model);

			for(String ob : result){

			}

			reader.close();
			Interface.label.setText(c + "  Resultats trouvés.");
		}catch(org.apache.lucene.queryparser.classic.ParseException e){

			JOptionPane.showMessageDialog(null, "Ile ne faut pas commencer par "+Interface.textField.getText(), "Attention !", JOptionPane.WARNING_MESSAGE, null);

		}

		return model;
	}


}

