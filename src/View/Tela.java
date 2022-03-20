package View;

import java.awt.ScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import Analisador.Main;
import Gramatica.Token;
import Reader.Reader;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.io.File;

import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Tela extends JFrame{
	
	String Texto="";
	
	private JLabel labelArq;
	private JLabel labelConsole;
	private JTextArea txtcomp;
	private JTextArea console;
	private JTable tbDicionario;
	private JButton btncompilar;
	private JButton btnsalvar;
	private JButton btnabrir;
	private JButton btncancelar;
	private JButton btndoc;
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
		
		String Text = "";
		JFileChooser dlg = new JFileChooser();
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
				dlg.showOpenDialog(null);
					Caminho=dlg.getSelectedFile();	
					try {
						final String texto;
						texto  = reader.Leitura(Caminho);
						SetTexto(texto);
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					//SETAR VALOR DO JTextArea
					
				   }
			
		});
		
		
		
		btnsalvar = new JButton();
		btnsalvar.setBounds(61, 60, 20, 20);
		btnsalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/salvar.png")));
		btnsalvar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e ) {
				dlg.showSaveDialog(null);
				File teste=dlg.getSelectedFile();
				System.out.println(teste);
			}
		});
		getContentPane().add(btnsalvar);
		
		
		btncompilar = new JButton();
		btncompilar.setBounds(82, 60, 20, 20);
		btncompilar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/compilar.png")));
		getContentPane().add(btncompilar);
		
		txtcomp = new JTextArea(GetTexto());
		txtComp = new JScrollPane(txtcomp);
		txtComp.setBounds(20,100,500,500);
		
		getContentPane().add(txtComp);
		
		
		model = new DefaultTableModel();
		model.addColumn("Código");
		model.addColumn("Palavra");
		//Stack <Token> pilha = main.getTokens();

		model=GerarTabela(model,main.getTokens());
		
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
