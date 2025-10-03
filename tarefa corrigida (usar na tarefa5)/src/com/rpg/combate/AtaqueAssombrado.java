package com.rpg.combate;
import com.rpg.personagens.monstros.Espirito;

public class AtaqueAssombrado implements AcaoDeCombate {
    @Override
    public void executar(Combatente usuario, Combatente alvo) {
        if (usuario instanceof Espirito) {
            Espirito espirito = (Espirito) usuario;

            int dano = espirito.getForca() + (espirito.getTristeza() / 10);

            // --- LÓGICA DO CRÍTICO ---
            // 1. Verifica se o ataque do monstro é crítico
            if (espirito.isProximoAtaqueCritico()) {
                System.out.println("DANO CRÍTICO!");
                dano *= 1.5; // Aumenta o dano em 50%

                // 2. Reseta o status de crítico para não afetar o próximo ataque
                espirito.setProximoAtaqueCritico(false);
            }
            // -------------------------

            alvo.receberDano(dano);

            System.out.println(espirito.getNome() + " está triste, ele não gosta de ficar triste... Tristeza: " + espirito.getTristeza() + ". Dano causado: " + dano);
        }
    }
}