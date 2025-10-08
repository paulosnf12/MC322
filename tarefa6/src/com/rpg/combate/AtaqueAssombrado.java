//AtaqueAssombrado.java
package com.rpg.combate;

import com.rpg.exceptions.RecursoInsuficienteException;

/**
 * Representa a ação de ataque assombrado.
 * Esta ação causa dano baseado na força do usuário e em seu atributo de tristeza.
 * O ataque tem uma chance de ser crítico, aplicando um multiplicador de dano.
 * A implementação utiliza apenas os métodos disponíveis na interface {@link Combatente},
 * promovendo a reutilização e reduzindo o acoplamento.
 */
public class AtaqueAssombrado implements AcaoDeCombate {

    /**
     * Executa a ação de ataque assombrado.
     * Calcula o dano com base na força do usuário e no seu atributo de tristeza.
     * Verifica se o ataque é crítico e aplica o multiplicador de dano se for o caso.
     * Aplica o dano calculado ao alvo.
     *
     * @param usuario O combatente que está executando a ação (espera-se que seja um usuário com atributo de tristeza, como o Espírito).
     * @param alvo O combatente que é o alvo da ação.
     * @throws RecursoInsuficienteException Não aplicável para este ataque, pois não consome recursos.
     */
    @Override
    public void executar(Combatente usuario, Combatente alvo) throws RecursoInsuficienteException {
        // A lógica agora opera diretamente com a interface Combatente, sem a necessidade de casting.
        // A interface Combatente já expõe os métodos getForca(), getTristeza(), getNome(),
        // isProximoAtaqueCritico(), setProximoAtaqueCritico() e receberDano().

        // Calcula o dano base: força do usuário + (tristeza do usuário / 10)
        int dano = usuario.getForca() + (usuario.getTristeza() / 10);

        // --- LÓGICA DO CRÍTICO ---
        // 1. Verifica se o ataque do combatente é crítico
        if (usuario.isProximoAtaqueCritico()) {
            System.out.println("DANO CRÍTICO!");
            dano *= 1.5; // Aumenta o dano em 50%
            // 2. Reseta o status de crítico para não afetar o próximo ataque
            usuario.setProximoAtaqueCritico(false);
        }
        // -------------------------
        
        // O alvo recebe o dano
        alvo.receberDano(dano);

        System.out.println(usuario.getNome() + " está triste, ele não gosta de ficar triste... " +
                           "Tristeza: " + usuario.getTristeza() + ". Dano causado: " + dano);
    }
}