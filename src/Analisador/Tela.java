package Analisador;

import java.awt.ScrollPane;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import Analisador.Main;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Tela extends JFrame{
	
	private JLabel label;
	private JTextField txtcomp;
	private JTable tbDicionario;
	private JButton btncompilar;
	private JButton btnsalvar;
	private JButton btnabrir;
	private JButton btncancelar;
	private JButton btndoc;
	private JScrollPane spnDic;
	private JScrollBar scroll;
	private DefaultTableModel model;
	
	public Tela() {
		setSize(1050,700);
		setTitle("Compilador");
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		componentesCriar();
	}
	
	
	    
	    
	   
	
	private void componentesCriar() {
		
		label = new JLabel("Arquivo");
		label.setBounds(20,20,200,50);
		getContentPane().add(label);
		
		btndoc = new JButton();
		btndoc.setBounds(40, 60, 20, 20);
		getContentPane().add(btndoc);
		
		btnabrir = new JButton();
		btnabrir.setBounds(61, 60, 20, 20);
		getContentPane().add(btnabrir);
		
		btnsalvar = new JButton();
		btnsalvar.setBounds(82, 60, 20, 20);
		getContentPane().add(btnsalvar);
		
		btncancelar = new JButton();
		btncancelar.setBounds(103, 60, 20, 20);
		getContentPane().add(btncancelar);
		
		btncompilar = new JButton();
		btncompilar.setBounds(124, 60, 20, 20);
		getContentPane().add(btncompilar);
		
		txtcomp = new JTextField();
		txtcomp.setBounds(20,100,500,500);
		getContentPane().add(txtcomp);
		
		
		model = new DefaultTableModel();
		model.addColumn("Código");
		model.addColumn("Palavra");
		//model.addRow(new String[] {"25","teste"});*/
		
		tbDicionario = new JTable(model);
		spnDic = new JScrollPane(tbDicionario);
		spnDic.setBounds(520, 100, 500, 250);
		//insert_info(model);
		
		getContentPane().add(spnDic);
		
		
		
	}
	
	public DefaultTableModel insert_info (DefaultTableModel model,Stack<Token> pilhaTokens) {
		
		while() {
			model.addRown("Código",Token.)
			
			
		}
		
		
		
		return model;
		
	}
	
	
	
	
	
	public static void main(String[] args) {
		new Tela().setVisible(true);
		
	}
	

}
