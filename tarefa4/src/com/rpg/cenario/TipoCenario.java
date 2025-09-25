// Enum: TipoCenario.java
/**
 * Enum que representa os diferentes ambientes do jogo.
 * Cada constante (FLORESTA, CRIPTA, PICO) é uma instância única
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
            System.out.println("O ar da Floresta Sussurrante é puro e úmido. Sons misteriosos ecoam entre as árvores antigas, e a luz do sol mal penetra a copa frondosa.");
            System.out.println("Cada passo levanta um tapete de folhas secas, revelando segredos ancestrais.");
            System.out.println("O ar puro da floresta revigora " + heroi.getNome() + ". Ele se sente mais ágil!");
            // Poderemos futuramente aplicar um bônus ao herói. Ex: heroi.aumentarAgilidade(5);
        }
    }, 

    CRIPTA("Uma cripta escura e úmida, com ecos misteriosos.") {
        @Override
        public void aplicarEfeitos(Heroi heroi) {
            System.out.println("Um calafrio percorre a espinha de " + heroi.getNome() + " ao adentrar a Cripta Sombria.");
            System.out.println("O cheiro de mofo e terra velha é quase insuportável, e as sombras dançam nas paredes de pedra, guardando horrores esquecidos.");
            System.out.println("Essa escuridão opressiva da cripta deixa nosso herói apreensivo. Sua visão é limitada.");
            // Efeito negativo: heroi.aplicarDebuff("Visão Reduzida"); --> ideia futura
        }
    },

    PICO("Pico Nevado dos Ventos Uivantes") {
        @Override
        public void aplicarEfeitos(Heroi heroi) {
            System.out.println("O vento gélido chicoteia o rosto de " + heroi.getNome() + " no Pico Nevado dos Ventos Uivantes. A paisagem é brutal, dominada por rochas afiadas e nevascas implacáveis.");
            System.out.println("A cada passo, a neve range sob os pés, e a visibilidade é quase nula. É o lar das criaturas mais resilientes e perigosas.");
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
     * Isso FORÇA cada constante (FLORESTA, CRIPTA, PICO, etc.) a fornecer
     * sua própria implementação, como fizemos acima.
     */
    public abstract void aplicarEfeitos(Heroi heroi);
}