package com.rpg.combate;
public interface Combatente {


    String getNome();
    boolean estaVivo();
    /**
     * Causa dano a este combatente, reduzindo seus pontos de vida.
     *
     * @param dano A quantidade de dano a ser recebida.
     */
    void receberDano(int dano);
    /**
     * Aumenta os pontos de vida deste combatente.
     *
     * @param cura A quantidade de cura a ser recebida.
     */
    void receberCura(int cura);
    /**
     * Determina e retorna a próxima ação que o combatente irá executar.
     * A lógica pode variar, desde uma escolha aleatória para monstros até uma
     * decisão estratégica para heróis.
     *
     * @param alvo O combatente que será o alvo da ação.
     * @return A ação de combate a ser executada.
     */
    AcaoDeCombate escolherAcao(Combatente alvo);
}
