package cliente;

import java.util.Random;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Canvas extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;

	private BufferedImage cenario;
	private int[][] linhas = new int[5][4];	
	private int[][] pontos = new int[7][4];

	private boolean jogando;
	private Janela janela;
	private Thread gameloop = new Thread(this);
	private String scenePath = "Data/backaground.jpg";
	
	private int h = 0;
	private int w = 0;
	
	private int ponto_borda_offset = 20;

	public Canvas(Janela janela) {
		jogando = false;
		this.janela = janela;
		this.h = janela.getAlturaAtual();
		this.w = janela.getLarguraAtual();
		criarTabuleiro();
		
	
		//Load background
		try {
			cenario = ImageIO.read(new File(scenePath));
			cenario = resize(cenario, h, w);
		}
		catch(IOException e) {
			Logger.getLogger(Canvas.class.getName()).log(Level.SEVERE, null, e);
		}
		
		gameloop.start();
	}
		
	public void atualizaDimensoes(){
		this.h = this.janela.getAlturaAtual();
		this.w = this.janela.getLarguraAtual();
	}
	public void criarTabuleiro() {
		/**
		 * Cria linhas e pontos do tabuleiro
		 */

		atualizaDimensoes();
		// valores para gerar posicao de linha e pontos
		int centro_w = this.h / 2;  // centro
		int desloca_w = (this.w/100 * 20); // meio deslocado de 20% da tela
		int centro_h = this.h / 2;
		int desloca_h = (this.h/100 * 20); // meio deslocado de 20% da tela

		int tam_ponto = (this.w/100 * 5);
		int eixo_ponto = tam_ponto/2;

		// /\
		linhas[0][0] = centro_w; // x0
		linhas[0][1] = centro_h - desloca_h * 2; // y0
		linhas[0][2] = centro_w + desloca_w; // x1
		linhas[0][3] = centro_h; // y1
		linhas[1][0] = centro_w; // x0
		linhas[1][1] = centro_h - desloca_h * 2; // y0
		linhas[1][2] = centro_w - desloca_w; // x1
		linhas[1][3] = centro_h; // y1
		
		// - --
		linhas[2][0] = centro_w + desloca_w/2; // x0
		linhas[2][1] = centro_h - desloca_h; // y0
		linhas[2][2] = centro_w - desloca_w/2; // x1
		linhas[2][3] = centro_h - desloca_h; // y1
		linhas[3][0] = centro_w + desloca_w; // x0
		linhas[3][1] = centro_h; // y0
		linhas[3][2] = centro_w - desloca_w; // x1
		linhas[3][3] = centro_h; // y1

		// |
		linhas[4][0] = centro_w; // x0
		linhas[4][1] = centro_h; // y0
		linhas[4][2] = centro_w; // x1
		linhas[4][3] = centro_h - desloca_h * 2; // y1

		// .
		pontos[0][0] = centro_w - eixo_ponto;
		pontos[0][1] = centro_h - (desloca_h*2) - eixo_ponto;
		pontos[0][2] = tam_ponto;
		pontos[0][3] = tam_ponto;	
		
		// . . .
		pontos[1][0] = centro_w - (desloca_w/2) - eixo_ponto;
		pontos[1][1] = centro_h - desloca_h - eixo_ponto;
		pontos[1][2] = tam_ponto;
		pontos[1][3] = tam_ponto;	
		pontos[2][0] = centro_w - eixo_ponto;
		pontos[2][1] = centro_h - desloca_h - eixo_ponto;
		pontos[2][2] = tam_ponto;
		pontos[2][3] = tam_ponto;	
		pontos[3][0] = centro_w + (desloca_w/2) - eixo_ponto;
		pontos[3][1] = centro_h - desloca_h - eixo_ponto;
		pontos[3][2] = tam_ponto;
		pontos[3][3] = tam_ponto;	

		// . . .
		pontos[4][0] = centro_w - desloca_w - eixo_ponto;
		pontos[4][1] = centro_h - eixo_ponto;
		pontos[4][2] = tam_ponto;
		pontos[4][3] = tam_ponto;	
		pontos[5][0] = centro_w - eixo_ponto;
		pontos[5][1] = centro_h - eixo_ponto;
		pontos[5][2] = tam_ponto;
		pontos[5][3] = tam_ponto;
		pontos[6][0] = centro_w + desloca_w - eixo_ponto;
		pontos[6][1] = centro_h - eixo_ponto;
		pontos[6][2] = tam_ponto;
		pontos[6][3] = tam_ponto;

	}
	
	// para manter o refreshRate a 60fps => 1000ms/16 = 62
	public void sleep() {
		try {
			Thread.sleep(16);
		} 
		catch(InterruptedException e) {
			Logger.getLogger(Canvas.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	// ciclo de atualizacoes
	public void run() {
		long timer = System.currentTimeMillis();
		
		while(true) {
			atualiza();	
			repaint();
			// mantem o refresh rate a 60 e conta os frames
			sleep();
			if(System.currentTimeMillis() - timer > 1000) 
			{
				escreveNoChat();
				timer+= 1000;
			}			
		}
	}
	
	public void escreveNoChat() {
		String saida1 = "Algum texto aleatorio";
		this.janela.setOutpuStatus(saida1);
	}
	
	
	public void atualiza() {
		/**
		 * Vai buscar no socket o estado do jogo e manter atualizado, assim como pegar mensagens
		 */
		criarTabuleiro();

		// fila.atualizar();
		
		// for(int i = 0; i < quantidadeDeAtores; i++) {
		// 	if(controladores[i] != null) {
		// 		controladores[i].atualizar();
		// 		casas[i].atualizar();
		// 	}
		// }
	}
	
	// desenha os componentes na tela
	public void paintComponent(Graphics g) {
		/**
		 * Deve desenhar as linhas do tabuleiros e pontos a cada frame
		 */
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		// paint background
		g2d.drawImage(cenario,  null,  0,  0);
		if(jogando) {

			// pinta linhas
			for (int i = 0; i < 5; i++){			
				g2d.drawLine(linhas[i][0], linhas[i][1], linhas[i][2], linhas[i][3]);
			}

			// pinta pontos
			int offset_borda = (pontos[0][3]/100 * this.ponto_borda_offset); // TODO: fix borda
			for (int i = 0; i < 7; i++){			
				Random gerador = new Random();
				g2d.setColor(Color.black);
				g2d.drawOval(pontos[i][0], pontos[i][1], pontos[i][2] + offset_borda, pontos[i][3] + offset_borda);
				
				if (gerador.nextInt(2) % 2 == 0){
					g2d.setColor(Color.white);				
				}else {
					g2d.setColor(Color.red);				

				}
				
				
				g2d.fillOval(pontos[i][0], pontos[i][1], pontos[i][2], pontos[i][3]);
				g2d.setColor(Color.black);
			}	
		}	
	}
	
	// redimensiona imagem
	public static BufferedImage resize(BufferedImage img, int W, int H) { 
	    Image temp = img.getScaledInstance(W, H, Image.SCALE_SMOOTH);
	    BufferedImage novaImagem = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2d = novaImagem.createGraphics();
	    g2d.drawImage(temp, 0, 0, null);
	    g2d.dispose();
	    return novaImagem;
	}  

	public void setJogando(boolean jogando) {
		this.jogando = jogando;
	}
	
	public boolean isJogando() {
		return this.jogando;
	}	
}