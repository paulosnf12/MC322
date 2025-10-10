// src/com/rpg/cenario/FaseDeCombate.java
package com.rpg.cenario;

import com.rpg.personagens.Heroi;
import com.rpg.personagens.Monstro;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement; // JAXB: É uma classe concreta que pode ser root
import jakarta.xml.bind.annotation.XmlSeeAlso; // JAXB: Para reconhecer subclasses de Evento

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma fase de combate no jogo, onde o herói enfrenta monstros em um determinado cenário.
 * Cada fase possui um nível, um tipo de cenário (como floresta, caverna, etc.), uma lista de monstros
 * e uma lista de eventos que podem ocorrer durante a fase.
 */
@XmlRootElement(name = "faseDeCombate") // JAXB: Define o elemento raiz para esta classe
@XmlSeeAlso({EventoDeBencao.class}) // JAXB: Para reconhecer subclasses concretas de Evento
public class FaseDeCombate implements Fase {
    @XmlElement
    private int nivel;
    @XmlElement
    private TipoCenario tipoDeCenario;
    @XmlElement(name = "monstro") // JAXB: Cada elemento da lista será um "monstro" no XML
    private ArrayList<Monstro> monstros;
    @XmlElement(name = "evento") // JAXB: Cada elemento da lista será um "evento" no XML
    private List<Evento> eventos;

    /**
     * JAXB: Construtor sem argumentos exigido pelo JAXB para deserialização.
     */
    public FaseDeCombate() {
        this.monstros = new ArrayList<>();
        this.eventos = new ArrayList<>();
    }

    // Construtor atualizado para receber o enum
    public FaseDeCombate(int nivel, TipoCenario tipoDeCenario, ArrayList<Monstro>
            monstros, List<Evento> eventos) {
        this.nivel = nivel;
        this.tipoDeCenario = tipoDeCenario;
        this.monstros = monstros;
        this.eventos = eventos;
    }

    @Override
    public void iniciar(Heroi heroi) {
        int numMonstros = this.monstros.size();
        System.out.println("\n========================================");
        System.out.println("INICIANDO FASE " + this.nivel + ": " +
                this.tipoDeCenario.getDescricao().toUpperCase());
        System.out.println("========================================");
        System.out.println();
        System.out.println(heroi.getNome() + " é transportado para " +
                this.tipoDeCenario.getDescricao() + " com o intuito de enfrentar " + numMonstros +
                " criaturas temíveis!");
        System.out.println();
        this.tipoDeCenario.aplicarEfeitos(heroi);
    }

    @Override
    public TipoCenario getTipoDeCenario() {
        return this.tipoDeCenario;
    }

    public List<Evento> getEventos() {
        return this.eventos;
    }

    @Override
    public boolean isConcluida() {
        for (Monstro monstro : monstros) {
            if (monstro.estaVivo()) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Monstro> getMonstros() {
        return monstros;
    }

    public void setMonstros(ArrayList<Monstro> monstros) {
        this.monstros = monstros;
    }
}