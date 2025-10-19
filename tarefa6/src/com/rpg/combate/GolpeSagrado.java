// src/com/rpg/combate/GolpeSagrado.java
package com.rpg.combate;

import com.rpg.exceptions.RecursoInsuficienteException;
import jakarta.xml.bind.annotation.XmlRootElement; // Import para a anotação JAXB

// O import de 'com.rpg.personagens.herois.Paladino' foi removido,
// pois a classe não dependerá mais diretamente do tipo concreto Paladino.

/**
 * Representa uma habilidade especial de combate, o Golpe Sagrado.
 * Causa dano aumentado com base na força do usuário e em seu atributo de carisma.
 * A sorte do usuário pode potencializar ainda mais o dano desta habilidade.
 * Esta habilidade consome mana do combatente que a executa.
 * A implementação utiliza apenas os métodos disponíveis na interface {@link Combatente},
 * promovendo a reutilização e reduzindo o acoplamento, conforme os princípios de agregação.
 */
@XmlRootElement(name = "golpeSagrado") // Define o elemento raiz para esta classe em XML
public class GolpeSagrado implements AcaoDeCombate {

    private static final int CUSTO_MANA = 15; // Custo fixo de mana da habilidade

    /**
     * Construtor sem argumentos exigido pelo JAXB para desserialização.
     */
    public GolpeSagrado() {} // Construtor padrão

    /**
     * Executa o Golpe Sagrado, causando dano ao alvo.
     * Calcula o dano com base na força do usuário, na arma equipada, no carisma e na sorte.
     * Consome mana do usuário e lança uma exceção se a mana for insuficiente.
     *
     * @param usuario O combatente que está executando a habilidade (espera-se que seja um usuário com mana, carisma e sorte, como o Paladino).
     * @param alvo O combatente que é o alvo da habilidade.
     * @throws RecursoInsuficienteException Se o combatente não tiver mana suficiente para usar a habilidade.
     */
    @Override
    public void executar(Combatente usuario, Combatente alvo) throws RecursoInsuficienteException {
        // A lógica agora opera diretamente com a interface Combatente.
        // Assume-se que a interface Combatente (e consequentemente a classe Personagem
        // e suas subclasses) expõe os getters e setters necessários como getMana(),
        // usarMana(), getArma(), getForca(), getCarisma(), getSorte(), getNome() e receberDano().

        // --- LÓGICA DE MANA ---
        // 1. Verifica se o usuário tem mana suficiente para usar a habilidade.
        if (usuario.getMana() < CUSTO_MANA) {
            throw new RecursoInsuficienteException("Recursos insuficientes para usar a habilidade.");
        }
        // 2. Se tiver, gasta a mana do usuário.
        usuario.usarMana(CUSTO_MANA);
        // --- FIM DA LÓGICA DE MANA ---

        // Lógica para cálculo do dano especial.
        // O dano inicial é a força do usuário, mais o dano da arma equipada, mais o carisma.
        int danoEspecial = (usuario.getArma() != null ? usuario.getArma().getDano() : 0) + usuario.getForca() + usuario.getCarisma();

        // A sorte alta pode aumentar o dano com base no carisma.
        // JAXB: Math.random() não é serializável, mas o resultado é temporário, então não há problema aqui.
        if (usuario.getSorte() > 0.4) {
            double boostDano = danoEspecial * 1.2; // Aplica um bônus de 20% no dano.
            danoEspecial = (int) Math.round(boostDano); // Arredonda o dano para um número inteiro.
            System.out.println("A fé de " + usuario.getNome() + " potencializa o Golpe Sagrado! [atributo de sorte ativado!]");
        } else {
            System.out.println(usuario.getNome() + " usou Golpe Sagrado!");
        }
        
        alvo.receberDano(danoEspecial); // O alvo recebe o dano especial.
        System.out.println(usuario.getNome() + " causou " + danoEspecial + " de dano especial!");
    }
}