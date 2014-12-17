package Interface;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import RDF.Myrdf;

public class Outils {
	Myrdf rdf=new Myrdf();
	
	

	public void vider_Jtable (JTable Table)  // vider la table
	{   
		int i=Table.getRowCount();
		//System.out.println(i);
		for(int p=1;p<=i;p++){
			((DefaultTableModel)Table.getModel()).removeRow(0);
		}
	}
	//////actualiser la table //////////
	public void actualiser_table(JTable Table){
		vider_Jtable(Table);
		rdf.affichage_rdf_Jtable (Interface.model_rdf,Table);
		Interface.label.setText("");
		Interface.textField.setText("");
	}
	
	
	public class extention extends FileFilter{
		   private String description;
		   private String extension;
		   
		   // Constructeur à partir de la description et de l'extension acceptée
		   public extention(String description, String extension){
		      if(description == null || extension == null){
		         throw new NullPointerException("La description (ou extension) ne peut être null.");
		      }
		      
		      this.description = description;
		      this.extension = extension;
		   }
		   
		   // Implémentation de FileFilter
		   public boolean accept(File file){
		      if (file.isDirectory()) { 
		         return true; 
		      } 
		      
		      String nomFichier = file.getName().toLowerCase(); 
		      	return nomFichier.endsWith(extension);
		   }
		      public String getDescription(){
		      return description;
		   }
		}


	@SuppressWarnings("deprecation")
	public String ouvrir_fichier()  {  
		// choix d'un fichier rdf 

		String filename ;

		JFileChooser chooser=new JFileChooser("./RDF");
		FileFilter rdf 		= new extention("RDF", ".rdf");
		FileFilter xml		= new extention("XML", ".xml");
		chooser.addChoosableFileFilter(rdf);
		chooser.addChoosableFileFilter(xml);
		chooser.setAcceptAllFileFilterUsed(false);
		
		
		chooser.showOpenDialog(null);

		File f=chooser.getSelectedFile();
		filename=f.getAbsolutePath();
		Interface.label_1.setText(f.getName());
		Interface.textField.enable();
		return filename;
	}


}