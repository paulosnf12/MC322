//FaseDeCombate.java
package com.rpg.cenario;
import com.rpg.personagens.Heroi;
import com.rpg.personagens.Monstro;
import java.util.ArrayList;
import java.util.List;

public class FaseDeCombate implements Fase { // Agora implementa InterfaceFase

    // atributos = nível, ambiente, ArrayList de monstros

    private int nivel;
    private String ambiente;
    private ArrayList<Monstro> monstros;

    private List<Evento> eventos; // NOVO ATRIBUTO


    // NOVO CONSTRUTOR:
    public FaseDeCombate(int nivel, String ambiente, ArrayList<Monstro> monstros, List<Evento> eventos) {
        this.nivel = nivel;
        this.ambiente = ambiente;
        this.monstros = monstros;
        this.eventos = eventos; // Inicializa a lista de eventos
    }

    // métodos: construtor para inicializar os atributos

    // Getter para os eventos
    public List<Evento> getEventos() {
        return this.eventos;
    }

    @Override
    public void iniciar(Heroi heroi) {

    // ---- NOVO CÓDIGO ----
    int numMonstros = this.monstros.size();
    System.out.println("\n========================================================");
    System.out.println("INICIANDO FASE " + this.nivel + ": " + this.ambiente.toUpperCase());
    System.out.println("========================================================");
    System.out.println(heroi.getNome() + " entra na " + this.ambiente + " para enfrentar " + numMonstros + " criaturas temíveis!");
}

    @Override
    public boolean isConcluida() {
        // A lógica é verificar se todos os monstros da lista estão mortos.
        for (Monstro monstro : monstros) {
            if (monstro.estaVivo()) {
                return false; // Se acharmos um monstro vivo, a fase não terminou.
            }
        }
        return true; // Se o loop acabar, significa que todos foram derrotados.
    }

    @Override
    public String getTipoDeCenario() {
        return this.ambiente;
    }

    // getters para retornar os atributos da fase
    public int getNivel() {
        return nivel;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public ArrayList<Monstro> getMonstros() {
        return monstros;
    }
}
