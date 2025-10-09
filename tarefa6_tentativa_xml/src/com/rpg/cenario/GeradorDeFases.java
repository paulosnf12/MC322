// src/com/rpg/cenario/GeradorDeFases.java
package com.rpg.cenario; // Pacote correto para interfaces de cenário

import com.rpg.game.Dificuldade;
import java.util.List; // Importar o novo enum Dificuldade

/**
    Define o contrato para classes que criam uma sequência de fases para o jogo.
    Esta interface permite a geração de fases com base em uma quantidade e
    dificuldade específicas, abstraindo a lógica de criação do cenário.
    */

// Define o contrato para um objeto que pode criar uma sequência de fases.
public interface GeradorDeFases {

/**
    Gera uma lista de fases para o jogo.
    @param quantidadeDeFases O número total de fases a serem criadas.
    @param dificuldade O nível de dificuldade que influenciará a criação das fases (ex: poder dos monstros, qualidade do loot).
    @return Uma lista de objetos do tipo Fase, prontos para serem jogados.
    */
    List<Fase> gerar(int quantidadeDeFases, Dificuldade dificuldade); // <-- MUDANÇA AQUI: Adicionado Dificuldade
}