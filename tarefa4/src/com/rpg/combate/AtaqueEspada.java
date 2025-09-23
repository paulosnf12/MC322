// AtaqueEspada.java (NOVO ARQUIVO)
package com.rpg.combate;
import com.rpg.personagens.herois.Paladino;


public class AtaqueEspada implements AcaoDeCombate {

    @Override
    public void executar(Combatente usuario, Combatente alvo) {
        if (usuario instanceof Paladino) {
            Paladino paladino = (Paladino) usuario;

            // Lógica do método atacar() original
            int danoTotal = (paladino.getArma() != null ? paladino.getArma().getDano() : 0) + paladino.getForca();

            System.out.println("Paladino " + paladino.getNome() + " atacou com " + (paladino.getArma() != null ? paladino.getArma().getNomeCompleto() : "punhos") + "!");
            alvo.receberDano(danoTotal);
            System.out.println("O ataque causou " + danoTotal + " de dano!");
        }
    }
}