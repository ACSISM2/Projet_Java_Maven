package RDF;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import Interface.Interface;
import Interface.Outils;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;

public class Myrdf {
	public static List<Resource> l = new ArrayList<Resource>();
	//////////recupèrè un fichier rdf a partir de son path/////////// 
	/////////////////////////////////////////////////////
	public Model lire_fichier_rdf (String inputFileName){
		Model model = ModelFactory.createDefaultModel();
		Interface.label.setText("");
		Interface.textField.setText("");
		
		InputStream in = FileManager.get().open( inputFileName );
		if (in == null) {
			throw new IllegalArgumentException(
					"Fichier: " + inputFileName + " non trouvé");    
		}                                       
		model.read(in, null);

		return model;
	}	
	///////////// affichage du rdf dans la Jtable

	public void affichage_rdf_Jtable (Model model,JTable Table)
	{
		Outils tr=new Outils();


		//on vide la table au moment de l'affiche
		tr.vider_Jtable(Table);

		//--------------------------------------------------------------
		StmtIterator iter = model.listStatements();
		while (iter.hasNext()) {
			Statement stmt      = iter.nextStatement();  // get next statement
			Resource subject   = stmt.getSubject();        // get the subject
			Property predicate = stmt.getPredicate();     // get the predicate
			RDFNode object    = stmt.getObject(); // get the object
			l.add(subject);
			//remplissage de la table 
			javax.swing.table.DefaultTableModel mod = (javax.swing.table.DefaultTableModel) Table.getModel();
			mod.addRow(new Object[]{subject,predicate,object});
		}
	


	}

}