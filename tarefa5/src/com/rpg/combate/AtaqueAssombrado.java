package com.rpg.combate;

import com.rpg.personagens.monstros.Espirito;

public class AtaqueAssombrado implements AcaoDeCombate {
    @Override
    public void executar(Combatente usuario, Combatente alvo) {
        if (usuario instanceof Espirito) {
            Espirito espirito = (Espirito) usuario;

            int dano = espirito.getForca() + (espirito.getTristeza() / 10);
            alvo.receberDano(dano);

            System.out.println(espirito.getNome() + " está triste, ele não gosta de ficar triste... Tristeza: " + espirito.getTristeza() + ". Dano causado: " + dano);
        }
    }
}