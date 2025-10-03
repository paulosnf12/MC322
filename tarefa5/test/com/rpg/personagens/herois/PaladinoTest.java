// test/com/rpg/personagens/herois/PaladinoTest.java
package com.rpg.personagens.herois;

import com.rpg.combate.AcaoDeCombate;
import com.rpg.combate.Combatente;
import com.rpg.itens.EspadaMadeira;
import com.rpg.personagens.Monstro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Você pode estender de uma classe base para heróis se houver muito código repetitivo
public class PaladinoTest {

    private Paladino paladino;
    private Monstro goblin; // Um monstro para o paladino atacar

    @BeforeEach
    void setUp() {
        // Inicializa um Paladino e um Monstro antes de cada teste
        paladino = new Paladino("Arthur", 100, 10, 5, 1, 0, 5, 10, 15);
        // Cria um monstro simples para ser o alvo, com valores base
        goblin = new Monstro("Goblin de Teste", 50, 5, 3, 10, null) {
            @Override
            protected void inicializarAcoes() { /* Nenhuma ação específica para este teste */ }
            @Override
            public AcaoDeCombate escolherAcao(Combatente alvo) { return null; }
        };
    }

    @Test
    void testPaladinoImplementsCombatente() {
        // Isso é implicitamente testado pela compilação.
        // Se Paladino não implementasse Combatente, o código não compilaria.
        // Mas podemos verificar se uma instância de Paladino é uma instância de Combatente.
        assertTrue(paladino instanceof Combatente, "Paladino deve implementar Combatente");
    }

    @Test
    void testReceberDano() {
        int danoInicial = 20;
        paladino.receberDano(danoInicial);
        assertEquals(80, paladino.getpontosdevida(), "Paladino deve ter 80 de vida após receber 20 de dano");
        paladino.receberDano(100); // Dano excessivo
        assertEquals(0, paladino.getpontosdevida(), "Vida não deve ser negativa");
        assertFalse(paladino.estaVivo(), "Paladino não deve estar vivo com 0 de vida");
    }

    @Test
    void testAtacarMonstro() {
        int vidaInicialMonstro = goblin.getpontosdevida();
        // Paladino precisa de uma arma para atacar efetivamente
        try {
            paladino.equiparArma(new EspadaMadeira(10)); // Paladino começa com Espada de Madeira (dano 10)
        } catch (Exception e) {
            fail("Não deveria lançar exceção ao equipar arma de nível adequado: " + e.getMessage());
        }

        AcaoDeCombate acao = paladino.escolherAcao(goblin);
        assertNotNull(acao, "Paladino deve ter uma ação de combate");

        acao.executar(paladino, goblin); // Paladino ataca o goblin

        // O dano total do Paladino é Força (10) + Dano da Arma (10) = 20
        int danoEsperado = paladino.getForca() + paladino.getArma().getDano(); // Força 10 + Dano da arma 10 = 20
        assertEquals(vidaInicialMonstro - danoEsperado, goblin.getpontosdevida(), "Monstro deve ter vida reduzida");
    }

    @Test
    void testHeroiGanharExperienciaESubirDeNivel() {
        paladino.ganharExperiencia(100); // 100 XP
        assertEquals(2, paladino.getNivel(), "Paladino deve subir para nível 2");
        // Verifica se a espada foi atualizada (se as configurações permitirem, depende do configDanoFerro)
        // No setup o Paladino é nível 1 e tem EspadaMadeira(5). Ao subir para nível 2, ele atualiza para EspadaFerro(10).
        assertEquals("Espada Ferro", paladino.getArma().getNomeCompleto(), "Arma deve ser atualizada para Espada Ferro");
    }
}
