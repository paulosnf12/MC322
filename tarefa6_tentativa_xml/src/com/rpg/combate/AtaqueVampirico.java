// src/com/rpg/combate/AtaqueVampirico.java
package com.rpg.combate;

import com.rpg.exceptions.RecursoInsuficienteException;
// O import de 'com.rpg.personagens.monstros.Vampiro' foi removido,
// pois a classe não dependerá mais diretamente do tipo concreto Vampiro.

/**
 * Representa o ataque vampírico, que utiliza um atributo de "brilho" para causar dano.
 * O dano é calculado com base na força do usuário e em seu atributo de brilho. Este
 * ataque pode ser um acerto crítico.
 * A implementação utiliza apenas os métodos disponíveis na interface {@link Combatente},
 * promovendo a reutilização e reduzindo o acoplamento, conforme os princípios de agregação.
 */
public class AtaqueVampirico implements AcaoDeCombate {

    /**
     * Executa a ação de ataque vampírico.
     * Calcula o dano com base na força do usuário e no seu atributo de brilho.
     * Verifica se o ataque é crítico e aplica um multiplicador de dano se for o caso.
     * Aplica o dano calculado ao combatente alvo.
     *
     * @param usuario O combatente que está executando a ação (espera-se que seja um combatente
     *                com um atributo de brilho, como o Vampiro).
     * @param alvo O combatente que é o alvo da ação.
     * @throws RecursoInsuficienteException Não aplicável para este ataque, pois não consome recursos
     *                                      como mana ou stamina.
     */
    @Override
    public void executar(Combatente usuario, Combatente alvo) throws RecursoInsuficienteException {
        // A lógica agora opera diretamente com a interface Combatente.
        // Assume-se que a interface Combatente (e consequentemente a classe Personagem
        // e suas subclasses) expõe os getters necessários como getForca(), getBrilho(),
        // isProximoAtaqueCritico(), setProximoAtaqueCritico(), getNome() e receberDano().

        // Calcula o dano base: força do usuário + (brilho do usuário / 10)
        int dano = usuario.getForca() + (usuario.getBrilho() / 10);

        // --- LÓGICA DO CRÍTICO ---
        // 1. Verifica se o ataque do combatente está marcado como crítico.
        if (usuario.isProximoAtaqueCritico()) {
            System.out.println("DANO CRÍTICO!");
            dano *= 1.5; // Aumenta o dano em 50%.
            // 2. Reseta o status de crítico para não afetar o próximo ataque.
            usuario.setProximoAtaqueCritico(false);
        }
        // -------------------------

        // O alvo recebe o dano calculado.
        alvo.receberDano(dano);

        System.out.println(usuario.getNome() + " ataca com seu brilho! Brilho: " + usuario.getBrilho() + ". Dano causado: " + dano);
    }
}