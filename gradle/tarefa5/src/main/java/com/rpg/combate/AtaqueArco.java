// AtaqueArco.java (NOVO ARQUIVO)
package com.rpg.combate;
import com.rpg.personagens.herois.Elfo;


public class AtaqueArco implements AcaoDeCombate {

    @Override
    public void executar(Combatente usuario, Combatente alvo) {
        // Verifica se o usuário é um Elfo para acessar seus atributos específicos
        if (usuario instanceof Elfo) {
            Elfo elfo = (Elfo) usuario;

            // Mesma lógica antigo método atacar()
            int danoTotal = (elfo.getArma() != null ? elfo.getArma().getDano() : 0) + elfo.getForca();
            
            System.out.println("Elfo " + elfo.getNome() + " ataca com " + (elfo.getArma() != null ? elfo.getArma().getNomeCompleto() : "punhos") + "!");
            alvo.receberDano(danoTotal);
            
            // A lógica de cura do Elfo também vem para cá
            int cura = (int) (0.02 * danoTotal); // Exemplo: 2% do dano como cura
            elfo.receberCura(cura);

            System.out.println("O ataque causou " + danoTotal + " de dano e curou o elfo em " + cura + " pontos de vida!");
        }
    }
}