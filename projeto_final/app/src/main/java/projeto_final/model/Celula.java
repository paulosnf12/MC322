// Em: src/main/java/projeto_final/model/Celula.java
package projeto_final.model;

public class Celula {
    private boolean ligada;

    public Celula() {
        // Toda célula começa desligada por padrão.
        this.ligada = false;
    }

    /**
     * Inverte o estado da célula (de ligada para desligada e vice-versa).
     */
    public void alternar() {
        this.ligada = !this.ligada;
    }

    public boolean isLigada() {
        return ligada;
    }
}