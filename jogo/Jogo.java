package jogo;

import java.awt.Color;
import java.util.Scanner;

public class Jogo {

    /**
     * Tabuleiro é representado assim
     * 0
     * 1 2 3
     * 4 5 6
     */
    private Jogador[] tabuleiro = new Jogador[7];
    private Jogador vencedor;

    public Jogo(){
        for(int i = 0; i < 7; i++){
            tabuleiro[i] = new Jogador(Color.WHITE, "_");
        }
    }

	public static void main(String[] args) {
        Jogo jogo = new Jogo();
        Jogador jogador1 = new Jogador(Color.BLACK, "x");
        Jogador jogador2 = new Jogador(Color.RED, "y");

        Jogador jogada_eh_do = jogador1;
        int jogadas_feitas = 0;        
        while(!jogo.alguem_venceu()){
            jogo.printa_jogo();

            if (jogadas_feitas > 6){

                System.out.printf("do %s, lanca jogada origem e destino, assim: 0 1\n", jogada_eh_do.getNome());
                Scanner sc = new Scanner(System.in);
                int num1 = sc.nextInt();
                int num2 = sc.nextInt();

                System.out.printf("jogada invalida, jogue novamente\n");

            }else{

                System.out.printf("do %s, lanca jogada destino pra buraco vazio, assim: 0\n", jogada_eh_do.getNome());
                Scanner sc = new Scanner(System.in);
                int num1 = sc.nextInt();

                if(jogo.realizar_jogada_em_vazio(new Jogada(num1, num1), jogada_eh_do)){
                    jogada_eh_do = (jogada_eh_do == jogador1 ? jogador2 : jogador1);
                }else{
                    System.out.printf("jogada invalida, jogue novamente\n");
                }
            }

        }

        System.out.printf("venceu o %s", jogo.getVencedor().getNome());
    }

    public boolean realizar_jogada_em_vazio(Jogada jogada, Jogador jogador){
        /**
         * Checar se é valido e realiza jogada
         */
        if (this.tabuleiro[jogada.getDestino()] == null || this.tabuleiro[jogada.getDestino()].getCor() == Color.WHITE){
            this.tabuleiro[jogada.getDestino()] = jogador;
            return true;
        }else{
            return false;
        }   
    }

    public void printa_jogo(){
        System.out.printf("  %s  \n", print_jog(0));
        System.out.printf("%s %s %s\n", print_jog(1), print_jog(2), print_jog(3));
        System.out.printf("%s %s %s\n", print_jog(4), print_jog(5), print_jog(6));
    }

    private String print_jog(int pos){
        if (tabuleiro[pos] == null || tabuleiro[pos].getCor() == Color.WHITE){
            return "_";
        }else{
            return tabuleiro[pos].getNome();
        }
    }
 
    public boolean alguem_venceu(){

        int[][] vitorias = { 
            { 0, 1, 4}, 
            { 0, 2, 5},
            { 0, 3, 6},
            { 1, 2, 3},
            { 4, 5, 6}
         };

         for(int i = 0 ; i < vitorias.length; i++){
             if (
                tabuleiro[vitorias[i][0]].getCor() == tabuleiro[vitorias[i][1]].getCor() &&
                tabuleiro[vitorias[i][1]].getCor() == tabuleiro[vitorias[i][2]].getCor()
                ){
                    if (tabuleiro[vitorias[i][0]].getCor() == Color.WHITE){
                        return false;
                    }
                    this.vencedor = tabuleiro[vitorias[i][0]];
                    System.out.printf("jogada vencedora %d %d %d", vitorias[i][0], vitorias[i][1], vitorias[i][2]);
                    return true;
             }
         }
         return false;

    }

    public Jogador getVencedor() {
        return vencedor;
    }
}