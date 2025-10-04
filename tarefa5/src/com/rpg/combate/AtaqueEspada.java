// AtaqueEspada.java (NOVO ARQUIVO)
package com.rpg.combate;
import com.rpg.personagens.herois.Paladino;

/**
 * Representa a ação de ataque básico com uma espada, específica para a classe Paladino.
 * O dano é calculado com base na força do Paladino e no dano da arma equipada.
 */

public class AtaqueEspada implements AcaoDeCombate {

    @Override
    public void executar(Combatente usuario, Combatente alvo) {
        if (usuario instanceof Paladino) {
            Paladino paladino = (Paladino) usuario;

            // Lógica do método atacar() original
            int danoTotal = (paladino.getArma() != null ? paladino.getArma().getDano() : 0) + paladino.getForca();

            System.out.println(paladino.getNome() + " atacou com " + (paladino.getArma() != null ? paladino.getArma().getNomeCompleto() : "punhos") + "!");
            alvo.receberDano(danoTotal);
            System.out.println("O ataque causou " + danoTotal + " de dano!");
        }
    }
}