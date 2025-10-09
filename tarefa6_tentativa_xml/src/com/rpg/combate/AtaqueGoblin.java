// src/com/rpg/combate/AtaqueGoblin.java
package com.rpg.combate;

import com.rpg.exceptions.RecursoInsuficienteException;
// O import de 'com.rpg.personagens.monstros.Goblin' foi removido,
// pois a classe não dependerá mais diretamente do tipo concreto Goblin.

/**
 * Representa a ação de ataque do Goblin. Esta ação causa dano com base na
 * força do usuário e no dano da sua arma inerente, e possui uma chance de
 * roubar vida do alvo. Este ataque também pode ser um acerto crítico.
 * A implementação utiliza apenas os métodos disponíveis na interface {@link Combatente},
 * promovendo a reutilização e reduzindo o acoplamento, conforme os princípios de agregação.
 */
public class AtaqueGoblin implements AcaoDeCombate {

    /**
     * Executa a ação de ataque do Goblin.
     * Calcula o dano com base na força do usuário e no dano da sua arma inerente.
     * Verifica se o ataque é crítico e aplica um multiplicador de dano se for o caso.
     * Há uma chance de roubar vida do alvo, curando o usuário em troca.
     *
     * @param usuario O combatente que está executando a ação (espera-se que seja um combatente
     *                com atributos específicos de Goblin, como dano de arma base e chance de roubo).
     * @param alvo O combatente que é o alvo da ação.
     * @throws RecursoInsuficienteException Não aplicável para este ataque, pois não consome recursos
     *                                      como mana ou stamina.
     */
    @Override
    public void executar(Combatente usuario, Combatente alvo) throws RecursoInsuficienteException {
        // A lógica agora opera diretamente com a interface Combatente.
        // Assume-se que a interface Combatente (e consequentemente a classe Personagem
        // e suas subclasses) expõe os getters necessários como getDanoArmaBase(),
        // getForca(), isProximoAtaqueCritico(), setProximoAtaqueCritico(),
        // getTipoDeArmaBase(), getChanceDeRoubo(), receberDano() e receberCura().

        // O dano total é a força do usuário somada ao dano base da sua arma inerente.
        int danoTotal = usuario.getDanoArmaBase() + usuario.getForca();

        // --- LÓGICA DO CRÍTICO ---
        // 1. Verifica se o ataque do combatente está marcado como crítico.
        if (usuario.isProximoAtaqueCritico()) {
            System.out.println("DANO CRÍTICO!");
            danoTotal *= 1.5; // Aumenta o dano em 50%.
            // 2. Reseta o status de crítico para não afetar o próximo ataque.
            usuario.setProximoAtaqueCritico(false);
        }

        // O alvo recebe o dano calculado.
        alvo.receberDano(danoTotal);
        System.out.println(usuario.getNome() + " atacou com " + usuario.getTipoDeArmaBase() + " causando " + danoTotal + " de dano!");

        // Implementação do roubo de vida.
        // Utiliza a chance de roubo do combatente, assumindo que Combatente.getChanceDeRoubo() está disponível.
        if (Math.random() < usuario.getChanceDeRoubo()) {
            int valorRoubo = 3; // Valor fixo de vida roubada.
            alvo.receberDano(valorRoubo); // O alvo perde vida adicional.
            usuario.receberCura(valorRoubo); // O usuário se cura na mesma quantidade.
            System.out.println(usuario.getNome() + " roubou " + valorRoubo + " de vida do alvo!");
        }
    }
}