package com.rpg.game; // ou com.rpg.cenario

public enum Dificuldade {
    FACIL(0.8, 0.7, 0.7, 0.3),  // Vida 80%, Dano 70%, XP 70% dos monstros, Chance de loot de armas comuns 30%
    NORMAL(1.0, 1.0, 1.0, 0.5), // Padrão
    DIFICIL(1.3, 1.2, 1.3, 0.7); // Vida 130%, Dano 120%, XP 130% dos monstros, Chance de loot de armas melhores 70%

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
