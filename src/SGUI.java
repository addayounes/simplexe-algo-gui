import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SGUI implements ActionListener  {
	
	JFrame frame = new JFrame();
	static JFrame inputFrame = new JFrame();
	static JPanel panel = new JPanel();
	JLabel Lvar = new JLabel("Variables");
	JLabel Lcont = new JLabel("Contraintes");
	JLabel show = new JLabel("Contraintes");
	JTextField txf1 = new JTextField();
	JTextField txf2 = new JTextField();
	JButton btn = new JButton("Suivant");
	static JTextField[] Biinput = new JTextField[10];
	static JTextField[] Zinput = new JTextField[10];
	static JTextField[][] Varinput = new JTextField[10][10];
	static JLabel Z,Bi,Var;
	static JPanel ZHolder,BiHolder,VarHolder;
	static int NombreDuLigne;
	static int NombreDuCologne;
	static int NombreDuVariables;
	static JPanel container;

	public SGUI() {
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		panel.setLayout(new GridLayout(0, 1));
		panel.add(Lvar);
		panel.add(txf1);
		panel.add(Lcont);
		panel.add(txf2);
		panel.add(btn);
		btn.addActionListener(this);
		frame.add(panel, BorderLayout.CENTER);
		frame.setSize(300,300); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Simplexe");
		frame.setVisible(true);
		panel.setBackground(new Color(120,195,220));
		txf1.setBackground(Color.white);
		txf2.setBackground(Color.white);
		btn.setBackground(Color.white);
		

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			
			if(e.getSource()==btn) {
				String CologneTXT = txf2.getText();
				String LigneTXT = txf1.getText();
				int NombreDuContraintes = Integer.parseInt(CologneTXT);
				NombreDuVariables = Integer.parseInt(LigneTXT);
				NombreDuLigne = NombreDuContraintes+1;
				NombreDuCologne = NombreDuContraintes+NombreDuVariables+1;
				container = new JPanel();
				
				
				for(int i=0; i<NombreDuLigne; i++) {
					
					
					if(i==NombreDuLigne-1) {
						for(int j=0; j<NombreDuCologne; j++) {
							if(j<NombreDuVariables) {
								Zinput[j] = new JTextField(3);
								Z = new JLabel("Z "+j);
								JPanel ZHolder = new JPanel();
								ZHolder.add(Z);
								ZHolder.add(Zinput[j]);
								container.add(ZHolder);
								ZHolder.setBackground(new Color(120,195,220));
							}
						}
					} else {
						for(int j=0; j<NombreDuCologne; j++) {
							
							if(j==NombreDuCologne-1) {
								
								Biinput[i] = new JTextField(5);
								Bi = new JLabel("B"+i);
								BiHolder = new JPanel();
								BiHolder.add(Bi);
								BiHolder.add(Biinput[i]);
								BiHolder.setBackground(new Color(120,195,220));
								container.add(BiHolder);

							}else if (j<NombreDuVariables){
								Varinput[i][j] = new JTextField(3);
								Var = new JLabel("X"+j);
								VarHolder = new JPanel();
								VarHolder.add(Var);
								VarHolder.add(Varinput[i][j]);
								container.add(VarHolder);
								VarHolder.setBackground(new Color(120,195,220));


								}
						}		
					}
				
				}
				
				JButton secondBTN = new JButton("iterate");
				JPanel btnPanel = new JPanel();
				btnPanel.add(secondBTN);
				container.add(btnPanel);
				int border = 70;
				container.setBorder(BorderFactory.createEmptyBorder(border, border, border, border));
				container.setLayout(new GridLayout(NombreDuLigne,NombreDuCologne));
				container.setBackground(new Color(120,195,220));
				secondBTN.setBackground(Color.white);
				btnPanel.setBackground(new Color(120,195,220));
				

				
				frame.dispose();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				inputFrame.add(container);
				inputFrame.setPreferredSize(new Dimension(NombreDuLigne*100,NombreDuLigne*100));
				inputFrame.pack();
				inputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				inputFrame.setVisible(true);

				secondBTN.addActionListener(new iterate());
							
			}
			
		}catch(Exception ex) {
			System.out.println(ex);
			JOptionPane.showMessageDialog(frame, "mauvaise entrée");
		}
	
		
	}
	

}
