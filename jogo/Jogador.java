package jogo;

import java.awt.Color;

public class Jogador {
    private Color cor;
    private String nome;

    public Jogador(Color cor, String nome) {
		this.cor = cor;
        this.nome = nome;
	}
    public Color getCor() {
        return cor;
    }
    public String getNome() {
        return nome;
    }
}
