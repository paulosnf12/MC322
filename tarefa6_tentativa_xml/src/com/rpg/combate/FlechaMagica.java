// src/com/rpg/combate/FlechaMagica.java
package com.rpg.combate;

import com.rpg.exceptions.RecursoInsuficienteException;
// O import de 'com.rpg.personagens.herois.Elfo' foi removido,
// pois a classe não dependerá mais diretamente do tipo concreto Elfo.

/**
 * Representa uma habilidade mágica de ataque, a Flecha Mágica. Causa um dano base
 * elevado que pode ser aumentado ou reduzido com base no atributo de sorte do usuário.
 * Esta habilidade consome mana do combatente que a executa.
 * A implementação utiliza apenas os métodos disponíveis na interface {@link Combatente},
 * promovendo a reutilização e reduzindo o acoplamento, conforme os princípios de agregação.
 */
public class FlechaMagica implements AcaoDeCombate {

    private static final int CUSTO_MANA = 20; // Flecha Mágica custa 20 de mana

    /**
     * Executa a Flecha Mágica, causando dano ao alvo.
     * Calcula o dano com base na força do usuário, na arma equipada e no atributo de sorte.
     * Consome mana do usuário e lança uma exceção se a mana for insuficiente.
     *
     * @param usuario O combatente que está executando a habilidade (espera-se que seja um usuário com mana e sorte, como o Elfo).
     * @param alvo O combatente que é o alvo da habilidade.
     * @throws RecursoInsuficienteException Se o combatente não tiver mana suficiente para usar a habilidade.
     */
    @Override
    public void executar(Combatente usuario, Combatente alvo) throws RecursoInsuficienteException {
        // A lógica agora opera diretamente com a interface Combatente.
        // Assume-se que a interface Combatente (e consequentemente a classe Personagem
        // e suas subclasses) expõe os getters e setters necessários como getMana(),
        // usarMana(), getArma(), getForca(), getNome(), getSorte() e receberDano().

        // --- LÓGICA DE MANA ---
        // Verifica se o usuário tem mana suficiente para usar a habilidade.
        if (usuario.getMana() < CUSTO_MANA) {
            throw new RecursoInsuficienteException("Recursos insuficientes para usar a habilidade.");
        }
        usuario.usarMana(CUSTO_MANA); // Consome a mana do usuário.
        // --- FIM DA LÓGICA DE MANA ---
        
        // Calcula o dano especial: dano da arma equipada + força do usuário + um bônus base.
        int danoEspecial = (usuario.getArma() != null ? usuario.getArma().getDano() : 0) + usuario.getForca() + 20;

        // Adiciona bônus de dano se a sorte do usuário for alta.
        if (usuario.getSorte() > 0.4) {
            danoEspecial += 15;
            System.out.println("A sorte favorece! Flecha Mágica causa dano extra!");
        } 
        
        System.out.println(usuario.getNome() + " usou Flecha Mágica com " + (usuario.getArma() != null ? usuario.getArma().getNomeCompleto() : "punhos") + "!");
        
        alvo.receberDano(danoEspecial); // O alvo recebe o dano especial.
        System.out.println("A habilidade causou " + danoEspecial + " de dano especial!");
    }
}