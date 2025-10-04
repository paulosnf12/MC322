// GolpeSagrado.java (NOVO ARQUIVO)
package com.rpg.combate;
import com.rpg.exceptions.RecursoInsuficienteException;
import com.rpg.personagens.herois.Paladino; // Importe a exceção
/**
 * Representa a habilidade especial do Paladino, o Golpe Sagrado.
 * Causa dano aumentado com base na Força e no Carisma. A sorte do Paladino
 * pode potencializar ainda mais o dano desta habilidade.
 */
public class GolpeSagrado implements AcaoDeCombate {

    private static final int CUSTO_MANA = 15; // Custo fixo da habilidade

    /**
     * Executa o Golpe Sagrado, causando dano ao alvo.
     * @param usuario O Paladino que está executando a habilidade.
     * @param alvo O combatente que é o alvo da habilidade.
     * @throws RecursoInsuficienteException Se o Paladino não tiver mana suficiente.
     */

    @Override
    public void executar(Combatente usuario, Combatente alvo) throws RecursoInsuficienteException {
        if (usuario instanceof Paladino) {
            Paladino paladino = (Paladino) usuario;

            // --- LÓGICA NOVA ---
            // 1. Verifica se tem mana suficiente
            if (paladino.getMana() < CUSTO_MANA) {
                throw new RecursoInsuficienteException("Recursos insuficientes para usar a habilidade.");
            }
            // 2. Se tiver, gasta a mana
            paladino.usarMana(CUSTO_MANA);
            // --- FIM DA LÓGICA NOVA ---

            // Lógica do método usarHabilidadeEspecial() original
            int danoEspecial = (paladino.getArma() != null ? paladino.getArma().getDano() : 0) + paladino.getForca() + paladino.getCarisma();

            // A sorte alta aumenta o dano com base no carisma
            if (paladino.getSorte() > 0.4) {
                double boostDano = danoEspecial * 1.2; // Boost de 20% no dano
                danoEspecial = (int) Math.round(boostDano); // Arrendondar dano para inteiro
                System.out.println("A fé do Paladino potencializa o Golpe Sagrado! [atributo de sorte ativado!]");
            } else {
                System.out.println("Paladino usou Golpe Sagrado!");
            }
            
            alvo.receberDano(danoEspecial);
            System.out.println(paladino.getNome() + " causou " + danoEspecial + " de dano especial!");
        }
    }
}