package Index;

import java.io.IOException;
import javax.swing.JTable;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

public class Myindex {
	public Model model;
	Recherche rech=new Recherche();


	//static int i ;
	public void ajout_adddoc(JTable table,Model model_rdf) throws IOException, ParseException{

		@SuppressWarnings("deprecation")
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);

		// 1. creation de l'index
		Directory index = new RAMDirectory();

		@SuppressWarnings("deprecation")
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_CURRENT, analyzer);

		IndexWriter w = new IndexWriter(index, config);

		//i=0;
		StmtIterator iter = model_rdf.listStatements();
		while (iter.hasNext()) {
			//i++;
			Statement stmt      = iter.nextStatement();    // get next statement
			Resource subject   = stmt.getSubject();        // get the subject
			Property predicate = stmt.getPredicate();     // get the predicate
			RDFNode object    = stmt.getObject();  
			addDoc(w, object.toString(),subject.toString(),predicate.toString());
			//addDoc(w, subject.toString(),object.toString(),predicate.toString(),i);
		}


		w.close();



		model=rech.Recherche_index(analyzer,index);

	}

	public void addDoc(IndexWriter w, String title1, String title2,String title3) throws IOException {

		Document doc = new Document();
		doc.add(new TextField("Objet", title1, Field.Store.YES));
		doc.add(new TextField("Sujet", title2, Field.Store.YES));
		doc.add(new TextField("Predicat", title3+"", Field.Store.YES));

		//doc.add(new StringField("index", nom, Field.Store.YES));
		//doc.add(new StringField("ligne", ligne_num+"", Field.Store.YES));

		w.addDocument(doc);

	}

}

