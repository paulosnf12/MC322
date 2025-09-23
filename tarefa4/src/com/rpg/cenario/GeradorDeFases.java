// GeradorDeFases.java
package com.rpg.cenario; // Pacote correto para interfaces de cenário

import com.rpg.game.Dificuldade;
import java.util.List; // Importar o novo enum Dificuldade



// Define o contrato para um objeto que pode criar uma sequência de fases.
public interface GeradorDeFases {
    List<Fase> gerar(int quantidadeDeFases, Dificuldade dificuldade); // <-- MUDANÇA AQUI: Adicionado Dificuldade
}