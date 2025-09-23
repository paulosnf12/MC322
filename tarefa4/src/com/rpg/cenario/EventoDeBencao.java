// EventoDeBencao.java
package com.rpg.cenario;
import java.util.ArrayList;
import java.util.List;
import com.rpg.personagens.Heroi;


public class EventoDeBencao implements Evento {

    private boolean jaExecutado = false; // Garante que o evento ocorra apenas uma vez

    @Override
    public boolean vericarGatilho(Heroi heroi, String ambiente) {
        // O gatilho é: estar na floresta e o evento ainda não ter ocorrido.
        if (ambiente.contains("Floresta") && !jaExecutado) {
            return true;
        }
        return false;
    }

    @Override
    public void executar(Heroi heroi) {
        System.out.println("\n>>> EVENTO ESPECIAL: BÊNÇÃO DA FLORESTA <<<");
        System.out.println("Uma luz suave desce por entre as árvores, envolvendo " + heroi.getNome() + ".");
        int cura = 25; // Uma cura fixa de 25 pontos
        heroi.receberCura(cura);
        System.out.println(heroi.getNome() + " recuperou " + cura + " pontos de vida!");
        this.jaExecutado = true; // Marca o evento como executado
    }
}

