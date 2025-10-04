// FlechaMagica.java (NOVO ARQUIVO)
package com.rpg.combate;
import com.rpg.exceptions.RecursoInsuficienteException;
import com.rpg.personagens.herois.Elfo;


/**
 * Representa a habilidade especial do Elfo, a Flecha Mágica. Causa um dano base
 * elevado que pode ser aumentado ou reduzido com base no atributo de sorte do Elfo.
 */
public class FlechaMagica implements AcaoDeCombate {

    private static final int CUSTO_MANA = 20; // Flecha Mágica custa 20 de mana

    /**
     * Executa a Flecha Mágica, causando dano ao alvo.
     * @param usuario O Elfo que está executando a habilidade.
     * @param alvo O combatente que é o alvo da habilidade.
     * @throws RecursoInsuficienteException Se o Elfo não tiver mana suficiente.
     */
    @Override
    public void executar(Combatente usuario, Combatente alvo) throws RecursoInsuficienteException {
        if (usuario instanceof Elfo) {
            Elfo elfo = (Elfo) usuario;

            // --- LÓGICA NOVA DE MANA ---
            if (elfo.getMana() < CUSTO_MANA) {
                throw new RecursoInsuficienteException("Recursos insuficientes para usar a habilidade.");
            }
            elfo.usarMana(CUSTO_MANA);
            // --- FIM DA LÓGICA NOVA ---
            
            // Mesma lógica antigo método usarHabilidadeEspecial()
            int danoEspecial = (elfo.getArma() != null ? elfo.getArma().getDano() : 0) + elfo.getForca() + 20;

            if (elfo.getSorte() > 0.4) {
                danoEspecial += 15;
                System.out.println("A sorte favorece! Flecha Mágica causa dano extra!");
            } 
            
            System.out.println(elfo.getNome() + " usou Flecha Mágica com " + (elfo.getArma() != null ? elfo.getArma().getNomeCompleto() : "punhos") + "!");
            alvo.receberDano(danoEspecial);
            System.out.println("A habilidade causou " + danoEspecial + " de dano especial!");
        }
    }
}