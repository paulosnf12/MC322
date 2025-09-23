// AtaqueAssombrado.java (NOVO ARQUIVO)
package com.rpg.combate;
import com.rpg.combate.AcaoDeCombate;
import com.rpg.combate.Combatente;
import com.rpg.personagens.herois.Elfo;
import com.rpg.personagens.herois.Paladino;
import com.rpg.personagens.monstros.Goblin;


public class AtaqueAssombrado implements AcaoDeCombate {
    
    @Override

import Monstros.Espirito;
    public void executar(Combatente usuario, Combatente alvo) {
        if (usuario instanceof Espirito) {
            Espirito espirito = (Espirito) usuario;

            // Lógica do método atacar() original do Espirito
            int dano = espirito.getForca() + (espirito.getTristeza() / 10);
            alvo.receberDano(dano);

            System.out.println(espirito.getNome() + " está triste, ele não gosta de ficar triste... Tristeza: " + espirito.getTristeza() + ". Dano causado: " + dano);
        }
    }
}