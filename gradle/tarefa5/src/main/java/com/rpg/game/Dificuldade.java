// Dificuldade.java

package com.rpg.game; // ou com.rpg.cenario

public enum Dificuldade {
    FACIL(0.3, 0.3, 0.7, 0.7),  // Vida x%, Dano x%, XP x% dos monstros, Chance de loot de armas comuns x%
    NORMAL(0.5, 0.5, 0.5, 0.5), // Padrão
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

    public double getMultiplicadorVidaMonstro() {
        return multiplicadorVidaMonstro;
    }

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
