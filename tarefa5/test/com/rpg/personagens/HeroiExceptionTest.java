// test/com/rpg/personagens/HeroiTest.java
package com.rpg.personagens;

import com.rpg.exceptions.NivelInsuficienteException;
import com.rpg.itens.EspadaDiamante; // Uma arma de alto nível
import com.rpg.personagens.herois.Paladino; // Exemplo de herói concreto
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeroiExceptionTest {

    private Paladino paladino;

    @BeforeEach
    void setUp() {
        // Paladino de nível 1, com as configurações de dano padrão
        paladino = new Paladino("LowLevelPaladin", 100, 10, 5, 1, 0, 5, 10, 15);
    }

    @Test
    void testEquiparArmaThrowsNivelInsuficienteException() {
        // Espada Diamante tem nível mínimo 3. O Paladino está no nível 1.
        EspadaDiamante espadaPoderosa = new EspadaDiamante(50);

        // Usa assertThrows para verificar se a exceção correta é lançada
        NivelInsuficienteException thrown = assertThrows(NivelInsuficienteException.class, () -> {
            paladino.equiparArma(espadaPoderosa);
        });

        // Opcional: verifica a mensagem da exceção
        String mensagemEsperada = "o nivel 1 de LowLevelPaladin e insuficiente para equipar a arma Espada Diamante (nivel minimo: 3).";
        assertEquals(mensagemEsperada, thrown.getMessage());
    }

    // Se você implementasse algo para RecursoInsuficienteException
    /*
    @Test
    void testHabilidadeThrowsRecursoInsuficienteException() {
        // Simular um herói com 0 de mana tentando usar uma habilidade que custa 10 de mana
        HeroiComMana heroi = new HeroiComMana("Mago", 100, 10, 5, 1, 0, 0); // mana inicial 0
        HabilidadeQueCustaMana habilidade = new HabilidadeQueCustaMana(10); // Custo 10

        RecursoInsuficienteException thrown = assertThrows(RecursoInsuficienteException.class, () -> {
            heroi.usarHabilidade(habilidade);
        });

        assertEquals("Recursos insuficientes para usar a habilidade.", thrown.getMessage());
    }
    */
}