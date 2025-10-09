// src/com/rpg/combate/AtaqueEspada.java
package com.rpg.combate;

import com.rpg.exceptions.RecursoInsuficienteException;

/**
 * Representa a ação de ataque básico com uma espada.
 * O dano é calculado com base na força do usuário e no dano da arma equipada.
 * Esta ação é tipicamente utilizada por heróis que empunham espadas, como o Paladino.
 * A implementação utiliza apenas os métodos disponíveis na interface {@link Combatente},
 * promovendo a reutilização e reduzindo o acoplamento.
 */
public class AtaqueEspada implements AcaoDeCombate {

    /**
     * Executa a ação de ataque com uma espada.
     * Calcula o dano com base na força do usuário e na sua arma equipada.
     * Aplica o dano calculado ao combatente alvo.
     *
     * @param usuario O combatente que está executando a ação (espera-se que seja um usuário de espada como o Paladino).
     * @param alvo O combatente que é o alvo da ação.
     * @throws RecursoInsuficienteException Não aplicável para este ataque básico, pois não consome recursos.
     */
    @Override
    public void executar(Combatente usuario, Combatente alvo) throws RecursoInsuficienteException {
        // A lógica agora opera diretamente com a interface Combatente, sem a necessidade de casting.
        // A interface Combatente já expõe os métodos getArma(), getForca(), getNome() e receberDano().

        // Calcula o dano total: dano da arma equipada + força do usuário
        int danoTotal = (usuario.getArma() != null ? usuario.getArma().getDano() : 0) + usuario.getForca();

        System.out.println(usuario.getNome() + " atacou com " + (usuario.getArma() != null ? usuario.getArma().getNomeCompleto() : "punhos") + "!");
        
        // O alvo recebe o dano
        alvo.receberDano(danoTotal);
        System.out.println("O ataque causou " + danoTotal + " de dano!");
    }
}