// Enum: TipoCenario.java

package com.rpg.cenario;
import com.rpg.personagens.Heroi;

/**
 * Enum que representa os diferentes ambientes do jogo.
 * Cada constante (FLORESTA, CRIPTA, PICO) é uma instância única
 * com sua própria descrição e implementação do método aplicarEfeitos.
 */
 public enum TipoCenario {

    // 1. DEFINIÇÃO DAS CONSTANTES
    // Cada constante chama o construtor do enum e, em seguida,
    // implementa o método abstrato 'aplicarEfeitos'.

    /**
     * Cenário Floresta Sussurrante. Poderá aplicar efeitos de cura ou agilidade.
     */
    
    FLORESTA("Floresta Sussurrante") {
        @Override
        public void aplicarEfeitos(Heroi heroi) {
            System.out.println("O ar da Floresta Sussurrante é puro e úmido. Sons misteriosos ecoam entre as árvores antigas, e a luz do sol mal penetra a copa frondosa.");
            System.out.println("Cada passo levanta um tapete de folhas secas, revelando segredos ancestrais.");
            System.out.println("O ar puro da floresta revigora " + heroi.getNome() + ". Ele se sente mais ágil!");
            // Poderemos futuramente aplicar um bônus ao herói. Ex: heroi.aumentarAgilidade(5);
        }
    }, 

    /**
     * Cenário Cripta Sombria. Aplicará efeitos negativos ou restrições no personagem.
     */

    CRIPTA("Cripta Sombria") {
        @Override
        public void aplicarEfeitos(Heroi heroi) {
            System.out.println("Um calafrio percorre a espinha de " + heroi.getNome() + " ao adentrar a Cripta Sombria.");
            System.out.println("O cheiro de mofo e terra velha é quase insuportável, e as sombras dançam nas paredes de pedra, guardando horrores esquecidos.");
            System.out.println("Essa escuridão opressiva da cripta deixa nosso herói apreensivo. Sua visão é limitada.");
            // Efeito negativo: heroi.aplicarDebuff("Visão Reduzida"); --> ideia futura
        }
    },

    /**
     * Cenário Pico Nevado dos Ventos Uivantes. Aplicará efeitos positivos ao personagem (em planejamento).
     */
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
     * Retorna uma descrição do cenário.
     * @return Uma {@code String} contendo a descrição do cenário.
     */

    public String getDescricao() {
        return this.descricao;
    }

    /**
     * Este é um método abstrato que cada constante do enum deve implementar.
     * Ele aplica efeitos específicos do cenário ao herói quando este entra na fase.
     *
     * @param heroi O objeto {@code Heroi} que entrará no cenário e receberá os efeitos.
     */
    public abstract void aplicarEfeitos(Heroi heroi);
}