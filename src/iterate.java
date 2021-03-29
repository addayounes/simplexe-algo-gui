import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class iterate extends SGUI implements ActionListener {
	
	JFrame tableFrame = new JFrame();
	double[][] Matrix = new double[NombreDuLigne][NombreDuCologne];
	double[][] newMatrix = new double[NombreDuLigne][NombreDuCologne];
	JPanel TablePanel;
	JTable MatrixTable;
	JButton newIteration;
	JTextArea Resultat = new JTextArea();;
	JScrollPane scorllPane;
	int iterationCount=1;



	@Override
	public void actionPerformed(ActionEvent e) {
		
		// creation du TablePanel
		
		TablePanel = new JPanel();
		newIteration = new JButton("Nouvelle itération");
		
		TablePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		TablePanel.setLayout(new FlowLayout());
		try {
			
			// la lecture des entrés du matrice
			
			for(int i=0; i<NombreDuLigne; i++) {
				
				if(i==NombreDuLigne-1) {
					for(int j=0; j<NombreDuCologne; j++) {
						if(j<NombreDuVariables) {
							Matrix[i][j] = Double.parseDouble(Zinput[j].getText().toString());
						}
					}
				} else {
					for(int j=0; j<NombreDuCologne; j++) {
						
						if(j==NombreDuCologne-1) {
							Matrix[i][j] = Double.parseDouble(Biinput[i].getText().toString());


						}else if (j<NombreDuVariables){
							Matrix[i][j] = Double.parseDouble(Varinput[i][j].getText().toString());
						} else if (i==(j-NombreDuVariables)) 
							Matrix[i][j]=1; else Matrix[i][j]=0;
					}		
				}
			
			}
			
			inputFrame.dispose();
			tableFrame.add(TablePanel);
			TablePanel.setBackground(new Color(120,195,220));
			tableFrame.setVisible(true);
			
			
		}catch(Exception ex) {
			
			// l'injection d'erreur dans // container //

			JOptionPane.showMessageDialog(inputFrame, "Svp entrer les valeur de la matrice!");
		}
		
		
		// 1ere iteration
		
		Resultat.append("itération N : "+iterationCount+"\n");
		iteration(Matrix);
		
		// creation des objet pour l'injecter dans le tableau
		
		Object[][] newMatrixObject = new Object[NombreDuLigne][NombreDuCologne];
		Object[] rows = new Object[NombreDuCologne];		
		
		for(int i=0; i<NombreDuLigne; i++) {
			for(int j=0; j<NombreDuCologne; j++) {
				newMatrixObject[i][j]=newMatrix[i][j];
				if(j<NombreDuCologne-1) rows[j]="X"+j; else rows[j]="Z";
			}
		}
		DefaultTableModel dtm = new DefaultTableModel(newMatrixObject, rows);
		
		// creation de l'interface de tableau
		
		MatrixTable = new JTable(dtm);
		scorllPane = new JScrollPane(MatrixTable);
		scorllPane.setMaximumSize(new Dimension(450,100));
		TablePanel.add(scorllPane, FlowLayout.LEFT);
		TablePanel.add(Resultat);
		TablePanel.add(newIteration);
		
		newIteration.setBackground(Color.white);
		scorllPane.setBackground(Color.white);

		 
		Resultat.setEditable(false);
		Resultat.setSize(150,200);
		MatrixTable.setRowHeight(20);
		MatrixTable.setFillsViewportHeight(false);
		tableFrame.pack();
		
		// l'implementation des nouveaux itérations
		
		newIteration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				
				// le check de condition d'arret
				
				int count=0;
				boolean ConditionDarret=false;
					for(int j=0; j<NombreDuVariables; j++) {
						if(newMatrix[NombreDuLigne-1][j]<=0) count++;
					}
					if (NombreDuVariables==count) ConditionDarret=true; else ConditionDarret=false;
				
				// implementing the new iteration
					
				if(!ConditionDarret) {
					
					iterationCount++;
					
					// swaping
					
					swapMatrix();
					
					Resultat.append("itération N : "+iterationCount+"\n");
					
					//iterating
					
					iteration(Matrix);
										
					for(int i=0; i<NombreDuLigne; i++) {
						for(int j=0; j<NombreDuCologne; j++) {
							newMatrixObject[i][j]=newMatrix[i][j];
							if(j<NombreDuCologne-1) rows[j]="X"+j; else rows[j]="Z";
							
						}
					}
					tableFrame.pack();
					DefaultTableModel NEWdtm = new DefaultTableModel(newMatrixObject, rows);
					MatrixTable.setModel(NEWdtm);
				}else {
					
					// présentation de la solution optimale
					
					double Z = newMatrix[NombreDuLigne-1][NombreDuCologne-1];
					if(Z<0) Z=-1*Z;
					Resultat.append("La solution optimale :\n\nZ = " + Z);;
					tableFrame.pack();
				}

		    }
		});
	}
	
	// la methode de swapping des matrices
	
	public void swapMatrix() {
		for(int i=0; i<NombreDuLigne; i++) {
			for(int j=0; j<NombreDuCologne; j++) {
				Matrix[i][j]=newMatrix[i][j];
			}
		}
	}
	
	// la methode d'itération
	
	public void iteration(double[][] Matrix) { 
		
		// getCologneIndexPivot
		
		int indexLigne = 0;
		int indexCologne = 0;
		
		double Max = Matrix[NombreDuLigne-1][0];
		for(int i=0; i<NombreDuCologne; i++) {
			if(Matrix[NombreDuLigne-1][i]>Max) {
				Max = Matrix[NombreDuLigne-1][i];
				indexCologne = i;
			}
		}
		
		// getColognePivot
		
		double[] CologneDePivot = new double[NombreDuLigne-1];
		for(int i=0; i<NombreDuLigne-1; i++) {
			CologneDePivot[i]=Matrix[i][indexCologne];
		}
		
		// getQi
		
		double[] Qi = new double[NombreDuLigne-1];
		double[] Bi = new double[NombreDuLigne];
		for(int i=0; i<NombreDuLigne-1; i++) {
			Bi[i]=Matrix[i][NombreDuCologne-1];
			Qi[i]=Bi[i]/CologneDePivot[i];
		}
		
		// getLigneIndexPivot
		
		double Min = Qi[0];
		for(int i=0; i<NombreDuLigne-1; i++) {
			if(Qi[i]<Min) {
				Min = Qi[i];
				indexLigne = i;
			}
		}	
		
		// getPivot
		
		double Pivot = Matrix[indexLigne][indexCologne];
		
		// iterate
		
		DecimalFormat numberFormat = new DecimalFormat("#.000");
		newMatrix = new double[NombreDuLigne][NombreDuCologne];
		
		for(int i=0; i<NombreDuLigne; i++) {
			if(i==indexLigne) {
				for(int j=0; j<NombreDuCologne;j++) {
					newMatrix[i][j]=Double.parseDouble(numberFormat.format(Matrix[i][j]/Pivot));
					
										
				}
			}else {
				for(int k=0; k<NombreDuCologne; k++) {
					if(k==indexCologne && i!=NombreDuLigne) {
						newMatrix[i][k]=0;
					}else {
						newMatrix[i][k]=Double.parseDouble(numberFormat.format(Matrix[i][k]-(Matrix[indexLigne][k]*Matrix[i][indexCologne]/Pivot)));
					}
				}
			}
		}
		
		Resultat.append("Pivot = " + Pivot+"\n\n");

		
	}
	

}
