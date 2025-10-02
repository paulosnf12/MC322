// FaseDeCombate.java

package com.rpg.cenario;

import com.rpg.personagens.Heroi;
import com.rpg.personagens.Monstro;
import java.util.ArrayList;
import java.util.List;

public class FaseDeCombate implements Fase {
    private int nivel;
    private TipoCenario tipoDeCenario; // <-- MUDANÇA 1: Atributo agora é do tipo enum
    private ArrayList<Monstro> monstros;
    private List<Evento> eventos;

    // Construtor atualizado para receber o enum
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

    // Getters e outros métodos...
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
}