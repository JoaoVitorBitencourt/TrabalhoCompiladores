package View;

import java.awt.Color;
//import java.awt.TextComponent;
//import java.awt.ScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
import java.util.Stack;

//import java.awt.*;
//import javax.swing.*;
//import javax.swing.event.*;
import javax.swing.text.Element;

import Analisador.Main;
//import Enumerate_TextArea.LineNumberingTextArea;
import Gramatica.Token;
import Reader.Reader;
import Writer.Escritor;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
//import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.io.File;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableModel;

public class Tela extends JFrame{
	
	String Texto="";
	
	private JLabel labelArq;
	private JLabel labelConsole;
	private static JTextArea lines;
	private static JTextArea txtcomp;
	private JTextArea console;
	private JTable tbDicionario;
	private JButton btncompilar;
	private JButton btnsalvar;
	private JButton btnabrir;
	private JScrollPane spnDic;
	private JScrollPane txtComp;
	private JScrollPane SpnConsole;
	private DefaultTableModel model;
	
	
	
	
	
	public String GetTexto() {
		
		return Texto;
	}
	
	public void SetTexto(String texto) {
		this.Texto = texto;
	}
	
	
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
		
		
		txtcomp= new JTextArea();
		lines = new JTextArea("1");
		
		String Text = "";
		JFileChooser Arquivo = new JFileChooser();
		Main main = new Main();
		main.executar();
		
		labelArq = new JLabel("Arquivo");
		labelArq.setBounds(20,20,200,50);
		getContentPane().add(labelArq);
		
		btnabrir = new JButton();
		btnabrir.setBounds(40, 60, 20, 20);
		btnabrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/abrir.png")));
		getContentPane().add(btnabrir);
		
		btnabrir.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				Reader reader = new Reader();
				File Caminho = null;
				Arquivo.showOpenDialog(null);
					Caminho=Arquivo.getSelectedFile();	
					try {
						
						final String texto;
						texto  = reader.Leitura(Caminho);
						SetTexto(texto);
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					if(txtcomp.getText()!="" || txtcomp.getText().equals(null)) {
						txtcomp.setText("");
						txtcomp.append(GetTexto());
					}else {
						txtcomp.append(GetTexto());
					}
					
				   }
		});
		
		btnsalvar = new JButton();
		btnsalvar.setBounds(61, 60, 20, 20);
		btnsalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/salvar.png")));
		btnsalvar.addMouseListener(new MouseAdapter() {
			Escritor Writer = new Escritor();
			public void mouseClicked(MouseEvent e ) {
				
				Arquivo.showSaveDialog(null);
				File arq=Arquivo.getSelectedFile();
				System.out.println(arq);
				try {
					Writer.Escritor(arq, txtcomp.getText());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		getContentPane().add(btnsalvar);
		
		
		btncompilar = new JButton();
		btncompilar.setBounds(82, 60, 20, 20);
		btncompilar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/compilar.png")));
		getContentPane().add(btncompilar);
		
		
		
		lines.setBackground(Color.LIGHT_GRAY);
		lines.setEditable(false);
		txtcomp.getDocument().addDocumentListener(new DocumentListener() {
	         public String getText() {
	            int caretPosition = txtcomp.getDocument().getLength();
	            Element root = txtcomp.getDocument().getDefaultRootElement();
	            String text = "1" + System.getProperty("line.separator");
	               for(int i = 2; i < root.getElementIndex(caretPosition) + 2; i++) {
	                  text += i + System.getProperty("line.separator");
	               }
	            return text;
	         }
	         @Override
	         public void changedUpdate(DocumentEvent de) {
	            lines.setText(getText());
	         }
	         @Override
	         public void insertUpdate(DocumentEvent de) {
	            lines.setText(getText());
	         }
	         @Override
	         public void removeUpdate(DocumentEvent de) {
	            lines.setText(getText());
	         }
	         
	      });
		
		

		
		txtComp = new JScrollPane(txtcomp);
		txtComp.setRowHeaderView(lines);
		txtComp.setBounds(20,100,500,500);
		
		getContentPane().add(txtComp);
		
		
		
		
		model = new DefaultTableModel();
		model.addColumn("Código");
		model.addColumn("Palavra");
		//Stack <Token> pilha = main.getTokens();

		//model=GerarTabela(model,main.getTokens());
		
		tbDicionario = new JTable(model);
		spnDic = new JScrollPane(tbDicionario);
		spnDic.setBounds(520, 100, 500, 250);
		
		getContentPane().add(spnDic);
		
		
		labelConsole = new JLabel("Console");
		labelConsole.setBounds(550,350,200,50);
		getContentPane().add(labelConsole);
		console = new JTextArea();
		SpnConsole= new JScrollPane(console);
		SpnConsole.setBounds(520, 400, 500, 150);
		getContentPane().add(SpnConsole);
		
		
		
		
		
		
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
