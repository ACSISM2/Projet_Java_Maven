package Interface;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;

import org.apache.lucene.queryparser.classic.ParseException;
import org.graphstream.graph.Graph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;

import Graph.GraphStream;
import Index.Myindex;
import Index.Recherche;
import RDF.Myrdf;
import SPARQL.MySPARQL;
import Graph.Algo_Dijkstra;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
@SuppressWarnings("serial")
public class Interface extends JFrame {
	public static JTable table = new JTable();
	public static  Model model_rdf = ModelFactory.createDefaultModel();
	Outils traite= new Outils(); 
	Myrdf lec_rdf= new Myrdf(); 
	Myindex index=new Myindex();
	Recherche rech = new Recherche();
	Algo_Dijkstra algo = new Algo_Dijkstra();
	GraphStream stream;
	GraphStream stream2;
	MySPARQL sparql = new MySPARQL();
	public static JTextField textField;
	public static JLabel label = new JLabel("");
	public static JLabel label_1 = new JLabel("");
	public static String filename="";
	public Viewer viewer;
	public View view=null;
	JPanel panel_1 = new JPanel();
	JPanel  panel1 = new JPanel();
	@SuppressWarnings("deprecation")
	public Interface () throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(0, 0, 1365, 730);
		//Genre application windows sur l'interface
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		JMenuBar menuBar = new JMenuBar();
		panel_1.removeAll();
		setJMenuBar(menuBar);
		

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		JMenuItem mntmRDF = new JMenuItem("RDF");
		mntmRDF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				try{ 

					model_rdf=lec_rdf.lire_fichier_rdf(filename=traite.ouvrir_fichier());

					lec_rdf.affichage_rdf_Jtable(model_rdf,table);
				}catch(Exception E){
					// java.lang.NullPointerException
				}
			}
		});
		mnFile.add(mntmRDF);

		

		JMenuItem mntmQuitter = new JMenuItem("Quitter");
		mntmQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnFile.add(mntmQuitter);
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		 
		panel1.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panel1);
		panel1.setLayout(new MigLayout("", "[grow]", "[][grow][]"));

		JPanel panel = new JPanel();
		panel1.add(panel, "cell 0 1,grow");
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(209, 15, 2, 2);
		panel.add(scrollPane);

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if(e.getKeyCode()==10){
					try {
						if(!textField.getText().equals(""))
							index.ajout_adddoc(table,model_rdf);
						else traite.actualiser_table(table);
					} catch (IOException e1) {

						e1.printStackTrace();
					} catch (ParseException e1) {

						e1.printStackTrace();
					}

				}

			}
		});
		textField.setBounds(278, 86, 482, 28);
		panel.add(textField);
		textField.setColumns(10);
		textField.disable();
		JButton btnRecherche = new JButton("Recherche");


		btnRecherche.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
				try {
					try {
						index.ajout_adddoc(table,model_rdf);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (IOException  e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				stream2 =new GraphStream(rech.model);
				stream2.setModel(rech.model);
				Graph graph = null;
				try {
					try {
						graph = stream2.construir_Graph();
						
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (IOException  e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				/*if(viewer!=null){
				viewer.close();
				}*/
			    //panel1.add(graph);
				Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
				 //viewer= graph.display();
				 viewer.enableAutoLayout();
				viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
				View view = viewer.addDefaultView(false);
				view.setPreferredSize(new Dimension(panel_1.getWidth(),panel_1.getHeight()));
				panel_1.removeAll();
				panel_1.add(view);
				
				panel_1.validate();
				stream2.afficher_Resulta_Noeud (rech.result);
				algo.plusCourtChemin(rech.result);
				if (textField.getText().contains(" ")){
				ArrayList<String> al=sparql.sparqlTest(algo.titrefilm, algo.rolepers,model_rdf);
				stream2.afficher_Resulta_Noeud(al,"");
				}


			}
		});

		btnRecherche.setBounds(770, 86, 89, 28);
		panel.add(btnRecherche);


		JButton btnaffich = new JButton("Afficher le chemin sur le graphe");
		panel1.add(btnaffich, "cell 0 2");


		btnaffich.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
				try {
					if(!textField.getText().equals(""))
					{
						index.ajout_adddoc(table,model_rdf);
						stream =new GraphStream(model_rdf);

						Graph graph = null;
						try {

							graph = stream.construir_Graph();
						} catch (IOException  e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if(viewer!=null){
							viewer.close();
							}
						viewer= graph.display();
						viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
						System.out.println("la taille du resultat dans List "+rech.result.size());
						stream.afficher_Resulta_Noeud (rech.result);
						algo.plusCourtChemin(rech.result,"");
						ArrayList<String> al=sparql.sparqlTest(algo.titrefilm, algo.rolepers,model_rdf);
						stream.afficher_Resulta_Noeud(al,"");



						while(rech.result.size()>0){rech.result.remove(0);}
					}else traite.actualiser_table(table);
				} catch (IOException e1) {

					e1.printStackTrace();
				} catch (ParseException e1) {

					e1.printStackTrace();
				}
			}
		});
		panel1.add(btnaffich, "flowx,cell 0 3");



		label.setBounds(869, 96, 222, 14);
		panel.add(label);
		label_1.setFont(new Font("Arial", Font.ITALIC, 10));


		label_1.setBounds(280, 61, 190, 14);

		panel.add(label_1);
		
		
		panel_1.setBounds(10, 123, 1305, 429);
		panel.add(panel_1);

		JButton btnNewButton = new JButton("Afficher Graph");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				//graph.JGraph(index.model);
				stream =new GraphStream(model_rdf);
				Graph graph = null;
				try {
					try {
						graph = stream.construir_Graph();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (IOException  e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*if(viewer!=null){
					viewer.close();
					}*/
				viewer= graph.display();
				viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);

			}
		});
		panel1.add(btnNewButton, "cell 0 2");
	}
}

