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
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Tela extends JFrame{
	
	private JLabel label;
	private JTextArea txtcomp;
	private JTable tbDicionario;
	private JButton btncompilar;
	private JButton btnsalvar;
	private JButton btnabrir;
	private JButton btncancelar;
	private JButton btndoc;
	private JScrollPane spnDic;
	private ScrollPane txtComp;
	private DefaultTableModel model;
	
	public Tela() {

		setSize(1050,700);
		setTitle("Compilador");
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		componentesCriar();
		
		//main.executar();
	}
	
	
	    
	    
	   
	
	private void componentesCriar() {
		Main main = new Main();
		main.executar();
		
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
		
		txtcomp = new JTextArea();
		txtcomp.setBounds(20,100,500,500);
		
		getContentPane().add(txtcomp);
		
		
		model = new DefaultTableModel();
		model.addColumn("Código");
		model.addColumn("Palavra");
		//Stack <Token> pilha = main.getTokens();

		//model=GerarTabela(model,main.getTokens());
		
		tbDicionario = new JTable(model);
		spnDic = new JScrollPane(tbDicionario);
		spnDic.setBounds(520, 100, 500, 250);
		
		getContentPane().add(spnDic);
		
		
		
	}
	
	public DefaultTableModel GerarTabela (DefaultTableModel model,Stack<Token> pilhaTokens) {
		
		while(!pilhaTokens.isEmpty()) {
			Token topo = pilhaTokens.peek();
			model.addRow(new String[] {topo.getCodigo().toString(),topo.getPalavra()});
			pilhaTokens.pop();
		}
		return model;
		
	}
	
	public static void main(String[] args) {
		new Tela().setVisible(true);
		
	}
	

}
