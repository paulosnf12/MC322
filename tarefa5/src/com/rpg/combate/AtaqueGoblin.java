// AtaqueGoblin.java (NOVO ARQUIVO)
package com.rpg.combate;
import com.rpg.personagens.monstros.Goblin;

/**
 * Representa a ação de ataque do Goblin. Causa dano com base na sua arma e força,
 * e possui uma chance de roubar vida do alvo. Este ataque também pode ser um
 * acerto crítico.
 */

public class AtaqueGoblin implements AcaoDeCombate {

    @Override
    public void executar(Combatente usuario, Combatente alvo) {
        if (usuario instanceof Goblin) {
            Goblin goblin = (Goblin) usuario;
        
        int danoTotal = goblin.getDanoArma() + goblin.getForca();

            // --- LÓGICA DO CRÍTICO ---
            // 1. Verifica se o ataque do monstro é crítico
            if (goblin.isProximoAtaqueCritico()) {
                System.out.println("DANO CRÍTICO!");
                danoTotal *= 1.5; // Aumenta o dano em 50% (ou 2 para dobrar)

                // 2. Reseta o status de crítico para não afetar o próximo ataque
                goblin.setProximoAtaqueCritico(false);
            }

            alvo.receberDano(danoTotal);
            System.out.println("Goblin atacou com " + goblin.getTipoDeArma() + " causando " + danoTotal + " de dano!");

            // Implementação do roubo
            if (Math.random() < goblin.getChanceDeRoubo()) {
                int valorRoubo = 3;
                alvo.receberDano(valorRoubo);
                // Precisamos de um método para alterar a vida diretamente ou usar receberCura
                goblin.receberCura(valorRoubo);
                System.out.println("Goblin roubou " + valorRoubo + " de vida do alvo!");
            }
        }
    }
}