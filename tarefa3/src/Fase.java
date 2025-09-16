//Fase.java

import java.util.ArrayList;

public class Fase implements InterfaceFase { // Agora implementa InterfaceFase

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

    @Override
    public void iniciar(Heroi heroi) {
        System.out.println("\n==============================================");
        System.out.println("INICIANDO FASE " + this.nivel + ": " + this.ambiente.toUpperCase());
        System.out.println("==============================================");
        
        // Aqui você pode adicionar a lógica de aplicar efeitos do cenário, se desejar
        // Ex: TipoCenario.valueOf(ambiente.toUpperCase()).aplicarEfeitos(heroi);
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
