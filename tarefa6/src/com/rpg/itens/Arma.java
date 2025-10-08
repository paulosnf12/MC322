// Arma.java
package com.rpg.itens;

/**
 * Classe abstrata que representa uma arma equipável no jogo.
 * Define atributos essenciais como dano e nível mínimo para uso.
 * Implementa a interface {@link Item}.
 */
public abstract class Arma implements Item{
    protected int minNivel; // Nível mínimo para equipar a arma
    protected String nomeTipoArma; // Ex: "Arco", "Espada", "Machado"
    protected String nomeSubtipoArma; // Ex: "Beta", "Madeira", "Bronze"

    // Construtor para inicializar os atributos básicos de qualquer arma
    
    public Arma(String nomeTipoArma, String nomeSubtipoArma, int minNivel) {
        this.nomeTipoArma = nomeTipoArma;
        this.nomeSubtipoArma = nomeSubtipoArma;
        this.minNivel = minNivel;
    }

    // Método abstrato que cada arma concreta deverá implementar para retornar seu dano
    /**
     * Retorna o valor de dano base da arma.
     * @return O dano da arma como um inteiro.
     */
    
    public abstract int getDano();

    // Getter para o nível mínimo
    public int getMinNivel() {
        return minNivel;
    }

    // Método para obter o nome completo da arma (ex: "Arco Beta", "Espada de Madeira")
    // Agora implementado da interface Item
    @Override
    public String getNomeCompleto() {
        return nomeTipoArma + " " + nomeSubtipoArma;
    }

    // Sobrescrita do método toString para uma representação amigável da arma
    @Override
    public String toString() {
        return getNomeCompleto() + " - Dano: " + getDano() + ", Nível Mínimo: " + minNivel + "";
    }

    public String getTipoArma() {
        return this.nomeTipoArma;
    }
}