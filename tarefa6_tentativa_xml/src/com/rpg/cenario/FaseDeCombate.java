//src/com/rpg/cenario/FaseDeCombate.java
package com.rpg.cenario;

import com.rpg.personagens.Heroi;
import com.rpg.personagens.Monstro;
import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlElement; // ADICIONADO PARA JAXB
import jakarta.xml.bind.annotation.XmlRootElement; // ADICIONADO PARA JAXB
import jakarta.xml.bind.annotation.XmlAccessorType; // ADICIONADO PARA JAXB
import jakarta.xml.bind.annotation.XmlAccessType; // ADICIONADO PARA JAXB


/**
 * Representa uma fase de combate no jogo, onde o herói enfrenta monstros em um determinado cenário.
 * Cada fase possui um nível, um tipo de cenário (como floresta, caverna, etc.), uma lista de monstros
 * e uma lista de eventos que podem ocorrer durante a fase.
 */
@XmlRootElement // ADICIONADO PARA JAXB: Indica que esta classe pode ser a raiz de um documento XML.
@XmlAccessorType(XmlAccessType.FIELD) // ADICIONADO PARA JAXB: Permite que o JAXB acesse diretamente os campos para serialização/desserialização.
public class FaseDeCombate implements Fase {
    private int nivel;
    private TipoCenario tipoDeCenario;
    private ArrayList<Monstro> monstros;
    private List<Evento> eventos;

    // ADICIONADO PARA JAXB: Construtor vazio necessário para a desserialização do JAXB.
    public FaseDeCombate() {
        this.monstros = new ArrayList<>(); // Inicializa a lista para evitar NullPointerException
        this.eventos = new ArrayList<>();   // Inicializa a lista para evitar NullPointerException
    }

    // Construtor original
    public FaseDeCombate(int nivel, TipoCenario tipoDeCenario, ArrayList<Monstro> monstros, List<Evento> eventos) {
        this.nivel = nivel;
        this.tipoDeCenario = tipoDeCenario; // <-- MUDANÇA 2
        this.monstros = monstros;
        this.eventos = eventos;
    }

    @Override
    public void iniciar(Heroi heroi) {
        int numMonstros = this.monstros.size();
        System.out.println("\n========================================");
        // <-- MUDANÇA 3: Usamos o método getDescricao() do enum para obter o nome do ambiente
        System.out.println("INICIANDO FASE " + this.nivel + ": " + this.tipoDeCenario.getDescricao().toUpperCase());
        System.out.println("========================================");
        System.out.println();
        System.out.println(heroi.getNome() + " é transportado para " + this.tipoDeCenario.getDescricao() + " com o intuito de enfrentar " + numMonstros + " criaturas temíveis!");

        System.out.println();

        this.tipoDeCenario.aplicarEfeitos(heroi); // agora aplica efeitos do enum direto no iniciar
    }

    // Método da interface implementado corretamente
    @Override
    public TipoCenario getTipoDeCenario() {
        return this.tipoDeCenario;
    }

    // ADICIONADO PARA JAXB: Setter para o campo tipoDeCenario, necessário para a desserialização.
    public void setTipoDeCenario(TipoCenario tipoDeCenario) {
        this.tipoDeCenario = tipoDeCenario;
    }

    // ADICIONADO PARA JAXB: Getter para o campo nivel, necessário para serialização.
    public int getNivel() {
        return nivel;
    }

    // ADICIONADO PARA JAXB: Setter para o campo nivel, necessário para desserialização.
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    // Getters e outros métodos...
    public List<Evento> getEventos() {
        return this.eventos;
    }

    // ADICIONADO PARA JAXB: Setter para o campo eventos, necessário para a desserialização.
    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
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

    // ADICIONADO PARA JAXB: Setter para o campo monstros, necessário para a desserialização.
    public void setMonstros(ArrayList<Monstro> monstros) {
        this.monstros = monstros;
    }
}