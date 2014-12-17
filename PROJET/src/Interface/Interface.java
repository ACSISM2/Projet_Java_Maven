package Interface;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;
import org.graphstream.ui.swingViewer.util.DefaultCamera;

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

	public static  Model model_rdf = ModelFactory.createDefaultModel();
	public static JTable table = new JTable();
	Outils traite= new Outils(); 
	Myrdf lec_rdf= new Myrdf(); 
	Myindex index=new Myindex();
	static MySPARQL m = new MySPARQL();
	Recherche rech = new Recherche();
	Algo_Dijkstra algo = new Algo_Dijkstra();
	GraphStream stream;
	GraphStream stream2;
	public static JTextField textField;
	public static JLabel label = new JLabel("");
	public static JLabel label_1 = new JLabel("");
    public static String filename="";
    public Viewer viewer;
    public View view=null;
	@SuppressWarnings("deprecation")
	public Interface () throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 916, 512);
		//Genre application windows sur l'interface
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		JMenuItem mntmRDF = new JMenuItem("RDF");
		mntmRDF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				try{ 
					
					model_rdf=lec_rdf.lire_fichier_rdf(filename=traite.ouvrir_fichier());
				
					lec_rdf.affichage_rdf_Jtable(model_rdf,table);
					m.sparqlTest();
				}catch(Exception E){
					// java.lang.NullPointerException
				}
			}
		});
		mnFile.add(mntmRDF);

		JMenuItem mntmGraph = new JMenuItem("Graph");
		mntmGraph.addActionListener(new ActionListener() {
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
				
				 Viewer viewer= graph.display();
				//	viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
			}
		});
		mnFile.add(mntmGraph);

		JMenuItem mntmQuitter = new JMenuItem("Quitter");
		mntmQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnFile.add(mntmQuitter);
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		JPanel  panel1 = new JPanel();
		panel1.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panel1);
		panel1.setLayout(new MigLayout("", "[grow]", "[][grow][]"));

		JPanel panel = new JPanel();
		panel1.add(panel, "cell 0 1,grow");
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(209, 15, 2, 2);
		panel.add(scrollPane);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(0, 148, 866, 241);
		panel.add(scrollPane_2);


		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Sujet", "Predicat", "Objet"
				}
				) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, Object.class, String.class, String.class
			};
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
					false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane_2.setViewportView(table);	

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
		textField.setBounds(39, 86, 482, 28);
		panel.add(textField);
		textField.setColumns(10);
		textField.disable();
		JButton btnRecherche = new JButton("Recherche");


		btnRecherche.addActionListener(new ActionListener() {
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
				
				 Viewer viewer= graph.display();
				
					viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
					// DefaultCamera def=new DefaultCamera((GraphicGraph) graph);
				    //  def.setViewPercent(1000);
					
				 stream2.afficher_Resulta_Noeud (rech.result);
				  algo.plusCourtChemin(rech.result);
				/*try {
					if(!textField.getText().equals(""))
					{
						index.ajout_adddoc(table,model_rdf);
						stream =new GraphStreamJena(model_rdf);
						
						 Graph graph = null;
						try {
							
							graph = stream.buildGraph();
						} catch (IOException | InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						 graph.display();
						 System.out.println("la taille du resultat dans List "+rech.result.size());
					//	stream.viewResultNode(rech.result);
					//  stream.printShortPath(rech.result);
					  while(rech.result.size()>0){rech.result.remove(0);}
					}else traite.actualiser_table(table);
				} catch (IOException e1) {

					e1.printStackTrace();
				} catch (ParseException e1) {

					e1.printStackTrace();
				}*/
			}
		});

		btnRecherche.setBounds(552, 86, 89, 28);
		panel.add(btnRecherche);

		
		JButton btnaffich = new JButton("Afficher le chemin sur le graphe");
		panel1.add(btnaffich, "cell 0 2");
		
		
				btnaffich.addActionListener(new ActionListener() {
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
								
								 Viewer viewer= graph.display();
									viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
								 System.out.println("la taille du resultat dans List "+rech.result.size());
								stream.afficher_Resulta_Noeud (rech.result);
							 algo.plusCourtChemin(rech.result);
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
	
		
		/*JButton btntest = new JButton("test");


		btntest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stream2 =new GraphStreamJena(rech.model);
				stream2.setModel(rech.model);
				 Graph graph = null;
				try {
					graph = stream2.buildGraph();
				} catch (IOException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 graph.display();
				 
				 stream2.viewResultNode(rech.result);
				  stream2.printShortPath(rech.result);
			}
			
		});

		btntest.setBounds(20, 20, 20, 40);
		panel.add(btntest);*/

		label.setBounds(562, 123, 222, 14);
		panel.add(label);
		label_1.setFont(new Font("Arial", Font.ITALIC, 10));


		label_1.setBounds(60, 61, 190, 14);

		panel.add(label_1);
		
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
				
				    Viewer viewer= graph.display();
					viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
				// System.out.println("la taille du resultat dans List "+rech.result.size());
			//	stream.viewResultNode(rech.result);
			//  stream.printShortPath(rech.result);
			 
				/*if(textField.getText()!="")
				{
				graph.JGraph(index.model);
				}
				//else{ graph.JGraph(model_rdf);}*/
			}
		});
		panel1.add(btnNewButton, "cell 0 2");
	}
}
