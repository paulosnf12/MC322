package com.rpg.combate;
public interface Combatente {
    String getNome();
    boolean estaVivo();
    void receberDano(int dano);
    void receberCura(int cura);
    void escolherAcao(Combatente alvo);
}
