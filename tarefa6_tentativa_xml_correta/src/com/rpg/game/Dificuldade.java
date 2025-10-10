package com.rpg.game; // ou com.rpg.cenario


/**
 * Enum que representa os níveis de dificuldade do jogo.
 * Cada nível de dificuldade define multiplicadores para os atributos dos monstros,
 * como vida, dano e experiência, além de influenciar a chance de obter melhores recompensas.
 */

public enum Dificuldade {
    /**
     * Dificuldade fácil, onde os monstros são mais fracos e concedem mais XP.
     */
    FACIL(0.3, 0.3, 0.7, 0.7),  // Vida x%, Dano x%, XP x% dos monstros, Chance de loot de armas comuns x%
    /**
     * Dificuldade normal, com atributos balanceados para monstros.
     */
    NORMAL(0.5, 0.5, 0.5, 0.5), // Padrão
    /**
     * Dificuldade difícil, onde os monstros são mais fortes e concedem menos XP.
     */
    DIFICIL(1.0, 1.0, 0.3, 0.3); // Vida x%, Dano x%, XP x% dos monstros, Chance de loot de armas melhores x%

    private final double multiplicadorVidaMonstro;
    private final double multiplicadorDanoMonstro;
    private final double multiplicadorXPMonstro;
    private final double chanceLootArmasBoas; // Chance de monstros droparem armas mais raras/avançadas

    Dificuldade(double multiplicadorVidaMonstro, double multiplicadorDanoMonstro,
                double multiplicadorXPMonstro, double chanceLootArmasBoas) {
        this.multiplicadorVidaMonstro = multiplicadorVidaMonstro;
        this.multiplicadorDanoMonstro = multiplicadorDanoMonstro;
        this.multiplicadorXPMonstro = multiplicadorXPMonstro;
        this.chanceLootArmasBoas = chanceLootArmasBoas;
    }

    /**
     * Retorna o multiplicador de vida para esta dificuldade.
     * @return O valor double do multiplicador de vida dos monstros.
     */

    public double getMultiplicadorVidaMonstro() {
        return multiplicadorVidaMonstro;
    }


    /**
     * Retorna o multiplicador de dano para esta dificuldade.
     * @return O valor double do multiplicador de dano dos monstros.
     */
    
    public double getMultiplicadorDanoMonstro() {
        return multiplicadorDanoMonstro;
    }

    public double getMultiplicadorXPMonstro() {
        return multiplicadorXPMonstro;
    }

    public double getChanceLootArmasBoas() {
        return chanceLootArmasBoas;
    }
}
