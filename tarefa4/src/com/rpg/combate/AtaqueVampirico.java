// AtaqueVampirico.java (NOVO ARQUIVO)
package com.rpg.combate;
import com.rpg.combate.AcaoDeCombate;
import com.rpg.combate.Combatente;
import com.rpg.personagens.herois.Elfo;
import com.rpg.personagens.herois.Paladino;
import com.rpg.personagens.monstros.Goblin;


public class AtaqueVampirico implements AcaoDeCombate {

    @Override

import Monstros.Vampiro;
    public void executar(Combatente usuario, Combatente alvo) {
        if (usuario instanceof Vampiro) {
            Vampiro vampiro = (Vampiro) usuario;

            // Lógica do método atacar() original do Vampiro
            int dano = vampiro.getForca() + (vampiro.getBrilho() / 10);
            alvo.receberDano(dano);
            
            System.out.println(vampiro.getNome() + " ataca com seu brilho! Brilho: " + vampiro.getBrilho() + ". Dano causado: " + dano);
        }
    }
}