// test/com/rpg/personagens/monstros/GoblinTest.java
package com.rpg.personagens.monstros;

import com.rpg.combate.AcaoDeCombate;
import com.rpg.combate.Combatente;
import com.rpg.exceptions.RecursoInsuficienteException;
import com.rpg.itens.Arma;
import com.rpg.itens.EspadaMadeira;
import com.rpg.itens.Item;
import com.rpg.itens.Lootavel;
import com.rpg.personagens.Heroi;
import com.rpg.personagens.herois.Paladino;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

public class GoblinTest {

    private Goblin goblin;
    private Heroi heroi;

    @BeforeEach
    void setUp() {
        ArrayList<Arma> armas = new ArrayList<>(Arrays.asList(new EspadaMadeira(5)));
        goblin = new Goblin("Goblin Teste", 50, 5, 3, 10, "Clava", 5, 0.5, armas);
        heroi = new Paladino("Teste Heroi", 100, 10, 5, 1, 0, 5, 10, 15, 100);
    }

    @Test
    void testGoblinImplementsLootavel() {
        assertTrue(goblin instanceof Lootavel, "Goblin deve implementar Lootavel");
    }

    @Test
    void testDroparLoot() {
        Item loot = goblin.droparLoot();
        assertNotNull(loot, "Goblin deve dropar um item");
        assertTrue(loot instanceof Arma, "O item dropado deve ser uma Arma");
        assertEquals("Espada Madeira", loot.getNomeCompleto(), "O nome da arma dropada deve ser 'Espada Madeira'");
    }

    @Test
    void testReceberDano() {
        int vidaInicial = goblin.getpontosdevida();
        goblin.receberDano(20);
        assertEquals(vidaInicial - 20, goblin.getpontosdevida(), "Goblin deve ter vida reduzida");
    }

    @Test
    void testAtacarHeroi() {
        int vidaInicialHeroi = heroi.getpontosdevida();
        AcaoDeCombate acao = goblin.escolherAcao(heroi); // O Goblin tem AtaqueGoblin como ação
        assertNotNull(acao, "Goblin deve ter uma ação de combate");

        try {
            // Tenta executar o ataque do Goblin
            acao.executar(goblin, heroi);
        } catch (RecursoInsuficienteException e) {
            // Esta exceção nunca deve acontecer com um Goblin, então se acontecer, o teste falha.
            fail("O ataque do Goblin não deveria lançar uma RecursoInsuficienteException. Erro: " + e.getMessage());
        }

        // O dano do Goblin é danoArma (5) + forca (5) = 10.
        // Além disso, há uma chance de roubo (roubo 3 de vida)
        // Ação AtaqueGoblin tem lógica de roubo e crítico. Para simplificar o teste, vamos focar no dano base.
        // Para testar o roubo, precisaríamos de mocks ou garantir que a chance de roubo seja 100%
        // ou testar em um loop e verificar o dano mínimo/máximo.

        // Por enquanto, testamos apenas o dano base.
        // O dano base é danoArma + forca. O AtaqueGoblin pode ter crítico (1.5x) e roubo (+3 dano, +3 cura para o goblin)
        // Para este teste, vamos assumir o cenário mais simples sem crítico ou roubo para a asserção exata.
        // Testes mais complexos precisariam de setup para controlar Math.random.

        int danoBaseEsperado = goblin.getDanoArma() + goblin.getForca(); // 5 + 5 = 10
        // Como o Math.random não pode ser controlado diretamente, é difícil prever o roubo no teste unitário padrão.
        // Se o teste falhar por causa do roubo, você pode comentar a linha de roubo no método AtaqueGoblin para testar o dano base,
        // ou aceitar que o dano ao herói pode variar um pouco.

        assertTrue(heroi.getpontosdevida() <= vidaInicialHeroi - danoBaseEsperado,
                "Herói deve ter recebido pelo menos o dano base do Goblin");

        // O goblin pode ter recuperado vida devido ao roubo. Isso pode ser testado separadamente.
    }
}