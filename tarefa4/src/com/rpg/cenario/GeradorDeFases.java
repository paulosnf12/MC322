// GeradorDeFases.java
package com.rpg.cenario; // Pacote correto para interfaces de cenário

import com.rpg.game.Dificuldade; // Importar o novo enum Dificuldade
import java.util.List;

// Define o contrato para um objeto que pode criar uma sequência de fases.
public interface GeradorDeFases {
    List<Fase> gerar(int quantidadeDeFases, Dificuldade dificuldade); // <-- MUDANÇA AQUI: Adicionado Dificuldade
}