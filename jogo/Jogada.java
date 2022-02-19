package jogo;

public class Jogada {
    private int origem;
    private int destino;
    public Jogada(int origem, int destino){
        this.origem = origem;
        this.destino = destino;
    }

    public int getOrigem() {
        return origem;
    }
    public int getDestino() {
        return destino;
    }
}
