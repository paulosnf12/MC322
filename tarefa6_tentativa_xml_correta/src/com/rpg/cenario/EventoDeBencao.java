// src/com/rpg/cenario/EventoDeBencao.java
package com.rpg.cenario;

import com.rpg.personagens.Heroi;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement; // JAXB: É uma classe concreta que pode ser root

/**
 * Evento especial que ocorre na floresta, onde o herói recebe uma bênção que restaura
 * parte de sua vida. O evento é acionado quando o herói entra na floresta pela primeira vez.
 */
@XmlRootElement(name = "eventoDeBencao") // JAXB: Define o elemento raiz para esta classe
@XmlAccessorType(XmlAccessType.FIELD)
public class EventoDeBencao implements Evento {
    @XmlElement
    private boolean jaExecutado = false; // Garante que o evento ocorra apenas uma vez

    /**
     * JAXB: Construtor sem argumentos exigido pelo JAXB para deserialização.
     */
    public EventoDeBencao() {} // Construtor padrão

    @Override
    public boolean vericarGatilho(Heroi heroi, TipoCenario cenario) {
        if (cenario == TipoCenario.FLORESTA && !jaExecutado) {
            return true;
        }
        return false;
    }

    @Override
    public void executar(Heroi heroi) {
        System.out.println("\n>>> EVENTO ESPECIAL: BÊNÇÃO DA FLORESTA <<<");
        System.out.println("Uma luz suave desce por entre as árvores, envolvendo " +
                heroi.getNome() + ".");
        int cura = 25;
        heroi.receberCura(cura);
        System.out.println(heroi.getNome() + " recuperou " + cura + " pontos de vida!");
        this.jaExecutado = true; // Marca o evento como executado
    }

    public boolean isJaExecutado() {
        return jaExecutado;
    }

    public void setJaExecutado(boolean jaExecutado) {
        this.jaExecutado = jaExecutado;
    }
}