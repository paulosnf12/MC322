// Enum: TipoCenario.java
/**
 * Enum que representa os diferentes ambientes do jogo.
 * Cada constante (FLORESTA, CAVERNA, CASTELO) é uma instância única
 * com sua própria descrição e implementação do método aplicarEfeitos.
 */
package com.rpg.cenario;
import com.rpg.personagens.Heroi;


 public enum TipoCenario {

    // 1. DEFINIÇÃO DAS CONSTANTES
    // Cada constante chama o construtor do enum e, em seguida,
    // implementa o método abstrato 'aplicarEfeitos'.
    
    FLORESTA("Uma floresta densa e cheia de vida selvagem.") {
        @Override
        public void aplicarEfeitos(Heroi heroi) {
            System.out.println("O ar puro da floresta revigora " + heroi.getNome() + ". Ele se sente mais ágil!");
            // Poderemos futuramente aplicar um bônus ao herói. Ex: heroi.aumentarAgilidade(5);
        }
    }, 

    CAVERNA("Uma caverna escura e úmida, com ecos misteriosos.") {
        @Override
        public void aplicarEfeitos(Heroi heroi) {
            System.out.println("A escuridão opressiva da caverna deixa " + heroi.getNome() + " apreensivo. Sua visão é limitada.");
            // Efeito negativo: heroi.aplicarDebuff("Visão Reduzida"); --> ideia futura
        }
    },

    CASTELO("Um castelo majestoso, mas com uma aura de perigo antigo.") {
        @Override
        public void aplicarEfeitos(Heroi heroi) {
            System.out.println("A grandiosidade do castelo inspira " + heroi.getNome() + ". Ele se sente mais confiante e determinado!");
            // Bônus de moral: heroi.aumentarConfianca(10); --> ideia futura
        }
    }; 


    // 2. CAMPO (ATRIBUTO)
    // Cada constante terá seu próprio valor para este campo.
    private final String descricao;


    // 3. CONSTRUTOR
    // O construtor de um enum é sempre privado.
    // Ele é chamado automaticamente para cada constante definida acima.
    TipoCenario(String descricao) {
        this.descricao = descricao;
    }


    // 4. MÉTODOS
    
    /**
     * Retorna a descrição do cenário.
     */
    public String getDescricao() {
        return this.descricao;
    }

    /**
     * Este é um método abstrato. Ele não tem implementação aqui.
     * Isso FORÇA cada constante (FLORESTA, CAVERNA, etc.) a fornecer
     * sua própria implementação, como fizemos acima.
     */
    public abstract void aplicarEfeitos(Heroi heroi);
}