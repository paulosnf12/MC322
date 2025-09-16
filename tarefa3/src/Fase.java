//Fase.java

import java.util.ArrayList;

public class Fase {

    // atributos = nível, ambiente, ArrayList de monstros

    private int nivel;
    private String ambiente;
    private ArrayList<Monstro> monstros;

    // construtor
    public Fase(int nivel, String ambiente, ArrayList<Monstro> monstros) {
        this.nivel = nivel;
        this.ambiente = ambiente;
        this.monstros = monstros;
    }

    // métodos: construtor para inicializar os atributos

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
