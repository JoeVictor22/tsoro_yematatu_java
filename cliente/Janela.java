package cliente;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Janela implements Runnable, ActionListener {
	List<String> threadInfo_bebendo = new ArrayList<>();
    List<String> threadInfo_dormindo = new ArrayList<>();
    
	// jframe e jpanel do jogo
	private JFrame janela;
	private Canvas jogo;
	
	// componentes IO do user
	JPanel inputUser = new JPanel();
	JPanel output = new JPanel();

	static JLabel errorMessage = new JLabel("");
	JLabel tempoBebendoLabel = new JLabel("Informe o tempo no bar!");	
	JLabel tempoDormindoLabel = new JLabel("Informe o tempo de soneca!");
	JLabel nomeDaThreadLabel = new JLabel("Nomeie o seu Papudim!");
	static JTextField tempoBebendo = new JTextField("", 5);
	static JTextField tempoDormindo = new JTextField("", 5);
	static JTextField nomeDaThread = new JTextField("", 5);
	JButton beboButton = new JButton("Adicionar Papudim");
	JLabel consoleLabel = new JLabel("Console\nConsole\nConsole\nConsole\nConsole\n");
	private JTextArea textAreaClientes = new JTextArea(5,33);
	private JTextArea textAreaStatus = new JTextArea(5,33);
	private JTextArea textAreaLog = new JTextArea(5,33);

	private TextAreaOutputStream consoleStream = new TextAreaOutputStream(textAreaLog, "/");
		
	//dimensoes
	private int h;
	private int w;
	
	private String soundtrack = "Data/BGM/fortaleza8bit.wav";
	public Janela(int altura, int largura) {
		this.h = altura;
		this.w = largura;
	}

	public void create() {
		janela = new JFrame("Cliente");
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setSize(w,h+160);
		janela.setResizable(false);
		janela.setLayout(new BorderLayout());
		
		// acoes para os butoes
		// beboButton.addActionListener(this);
 
		// add componentes de tela ao jpanel correspondente
		// inputUser.add(nomeDaThreadLabel);
		// inputUser.add(nomeDaThread);
		// inputUser.add(tempoBebendoLabel);
		// inputUser.add(tempoBebendo);
		// inputUser.add(tempoDormindoLabel);
		// inputUser.add(tempoDormindo);
		// inputUser.add(beboButton);

		// console redirect implementation
		output.setLayout(new BorderLayout());
		textAreaStatus.setCaretColor(Color.WHITE);
		textAreaLog.setCaretColor(Color.WHITE);

		output.add(new JScrollPane(textAreaStatus, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
		output.add(new JScrollPane(textAreaLog, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.LINE_START);

		System.setOut(new PrintStream(consoleStream));
		
		// criacao de instancia principal do jogo
		jogo = new Canvas(w, h, this);
		
		janela.add(jogo, BorderLayout.CENTER);
		janela.add(inputUser, BorderLayout.PAGE_START);
		janela.add(output, BorderLayout.PAGE_END);
		
		janela.setVisible(true);
		jogo.setJogando(true);
		// BGM.play(this.soundtrack);

	}
	
	public void setOutpuClientes(String texto) {
		this.textAreaClientes.setText("<>CLIENTES</>\n" + texto);
	}
	public void setOutpuStatus(String texto) {
		this.textAreaStatus.setText("<>STATUS</>\n" + texto);
	}
	
	public void setOutpuLog(String texto) {
		this.textAreaLog.setText("<>LOG</>\n" + texto);
	}
	
	public void actionPerformed(ActionEvent ae) {
        // String action = ae.getActionCommand();
        // if(action.equals("Adicionar Papudim")) {
        // }
	}
	

	public void run() {
	}
	
	public void restart() {
	}
}