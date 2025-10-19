// src/com/rpg/cenario/Fase.java
package com.rpg.cenario;

import com.rpg.personagens.Heroi;
import jakarta.xml.bind.annotation.XmlSeeAlso; // JAXB: Para reconhecer subclasses

/**
 * Representa uma etapa ou nível dentro do jogo.
 * Define os comportamentos essenciais para iniciar uma fase, verificar sua conclusão
 * e obter informações sobre seu cenário.
 */
// JAXB: Indica que Fase pode ter a subclasse FaseDeCombate
@XmlSeeAlso({FaseDeCombate.class})
public interface Fase {
    /**
     * Inicia a fase, exibindo mensagens introdutórias e aplicando efeitos
     * de cenário ao herói.
     * @param heroi O herói que está entrando na fase.
     */
    void iniciar(Heroi heroi);

    /**
     * Verifica se todos os objetivos da fase foram concluídos (ex: todos os monstros derrotados).
     * @return {@code true} se a fase foi concluída, {@code false} caso contrário.
     */
    boolean isConcluida();

    /**
     * Retorna o tipo de cenário associado a esta fase.
     * @return O enum {@link TipoCenario} da fase.
     */
    TipoCenario getTipoDeCenario();
}