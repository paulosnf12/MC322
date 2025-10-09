// src/com/rpg/combate/AtaqueArco.java
package com.rpg.combate;

import com.rpg.exceptions.RecursoInsuficienteException;

/**
 * Representa a ação de ataque básico com um arco.
 * Esta ação causa dano ao alvo e, como efeito passivo, cura o combatente que a executa
 * (tipicamente o {@link com.rpg.personagens.herois.Elfo}) em uma pequena porcentagem do dano causado.
 * A implementação utiliza apenas os métodos disponíveis na interface {@link Combatente},
 * promovendo a reutilização e reduzindo o acoplamento.
 */
public class AtaqueArco implements AcaoDeCombate {

    /**
     * Executa a ação de ataque com um arco.
     * Calcula o dano com base na força do usuário e na arma equipada.
     * Aplica o dano ao alvo e realiza uma cura no próprio usuário (combatente).
     *
     * @param usuario O combatente que está executando a ação (espera-se que seja um usuário de arco como o Elfo).
     * @param alvo O combatente que é o alvo da ação.
     * @throws RecursoInsuficienteException Se o combatente não tiver recursos suficientes (não aplicável para este ataque básico).
     */
    @Override
    public void executar(Combatente usuario, Combatente alvo) throws RecursoInsuficienteException {
        // A lógica agora opera diretamente com a interface Combatente, sem a necessidade de casting.
        // A interface Combatente já expõe os métodos getArma(), getForca(), getNome() e receberCura().

        // Calcula o dano total: dano da arma equipada + força do usuário
        int danoTotal = (usuario.getArma() != null ? usuario.getArma().getDano() : 0) + usuario.getForca();
        
        System.out.println(usuario.getNome() + " ataca com " + (usuario.getArma() != null ? usuario.getArma().getNomeCompleto() : "punhos") + "!");
        
        // O alvo recebe o dano
        alvo.receberDano(danoTotal);
        
        // Lógica de cura do Elfo (aplica-se ao usuário, que pode ser um Elfo)
        // O método receberCura está disponível na interface Combatente
        int cura = (int) (0.02 * danoTotal); // Exemplo: 2% do dano como cura
        usuario.receberCura(cura); // O usuário se cura

        System.out.println("O ataque causou " + danoTotal + " de dano e curou " + usuario.getNome() + " em " + cura + " pontos de vida!");
    }
}