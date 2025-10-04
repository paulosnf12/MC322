// AtaqueVampirico.java (NOVO ARQUIVO)
package com.rpg.combate;
import com.rpg.personagens.monstros.Vampiro;

/**
 * Representa o ataque do Vampiro, que utiliza seu brilho sobrenatural para causar dano.
 * O dano é calculado com base na força e no atributo de Brilho do Vampiro. Este
 * ataque pode ser um acerto crítico.
 */
public class AtaqueVampirico implements AcaoDeCombate {

@Override
    public void executar(Combatente usuario, Combatente alvo) {
        if (usuario instanceof Vampiro) {
            Vampiro vampiro = (Vampiro) usuario;

            // Lógica do método atacar() original do Vampiro
            int dano = vampiro.getForca() + (vampiro.getBrilho() / 10);

            // --- LÓGICA DO CRÍTICO ---
            // 1. Verifica se o ataque do monstro é crítico
            if (vampiro.isProximoAtaqueCritico()) {
                System.out.println("DANO CRÍTICO!");
                dano *= 1.5; // Aumenta o dano em 50%

                // 2. Reseta o status de crítico para não afetar o próximo ataque
                vampiro.setProximoAtaqueCritico(false);
            }
            // -------------------------

            alvo.receberDano(dano);

            System.out.println(vampiro.getNome() + " ataca com seu brilho! Brilho: " + vampiro.getBrilho() + ". Dano causado: " + dano);
        }
    }
}