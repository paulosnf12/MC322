// AtaqueVampirico.java (NOVO ARQUIVO)
package com.rpg.combate;
import com.rpg.personagens.monstros.Vampiro;

public class AtaqueVampirico implements AcaoDeCombate {

@Override
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