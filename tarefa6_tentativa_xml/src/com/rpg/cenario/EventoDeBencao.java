// src/com/rpg/cenario/EventoDeBencao.java
package com.rpg.cenario;

import com.rpg.personagens.Heroi;
import jakarta.xml.bind.annotation.XmlElement; // ADICIONADO PARA JAXB
import jakarta.xml.bind.annotation.XmlRootElement; // ADICIONADO PARA JAXB
import jakarta.xml.bind.annotation.XmlAccessorType; // ADICIONADO PARA JAXB
import jakarta.xml.bind.annotation.XmlAccessType; // ADICIONADO PARA JAXB

/**
 * Evento especial que ocorre na floresta, onde o herói recebe uma bênção que restaura parte de sua vida.
 * O evento é acionado quando o herói entra na floresta pela primeira vez.
 */
@XmlRootElement // ADICIONADO PARA JAXB: Indica que esta classe pode ser a raiz de um documento XML.
@XmlAccessorType(XmlAccessType.FIELD) // ADICIONADO PARA JAXB: Permite que o JAXB acesse diretamente os campos para serialização/desserialização.
public class EventoDeBencao implements Evento {

    private boolean jaExecutado = false; // Garante que o evento ocorra apenas uma vez

    // ADICIONADO PARA JAXB: Construtor vazio necessário para a desserialização do JAXB.
    public EventoDeBencao() {
        // Construtor padrão sem argumentos
    }

    @Override
    public boolean vericarGatilho(Heroi heroi, TipoCenario cenario) {
        // O gatilho é: estar na floresta e o evento ainda não ter ocorrido.
        if (cenario == TipoCenario.FLORESTA && !jaExecutado) {
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

    // ADICIONADO PARA JAXB: Getter para o campo jaExecutado, necessário para a serialização.
    // Embora XmlAccessType.FIELD permita acesso direto, ter um getter é boa prática para serialização de booleans.
    public boolean isJaExecutado() {
        return jaExecutado;
    }

    // ADICIONADO PARA JAXB: Setter para o campo jaExecutado, necessário para a desserialização.
    public void setJaExecutado(boolean jaExecutado) {
        this.jaExecutado = jaExecutado;
    }
}