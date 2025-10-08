// test/com/rpg/personagens/monstros/GoblinTest.java
package com.rpg.personagens.monstros;

import com.rpg.combate.AcaoDeCombate;
import com.rpg.combate.Combatente;
import com.rpg.exceptions.RecursoInsuficienteException;
import com.rpg.itens.Arma;
import com.rpg.itens.ArmaDropSpec; // NOVO IMPORT: Para usar a especificação de drop de armas
import com.rpg.itens.EspadaMadeira; // Import mantido para referenciar a classe dentro de ArmaDropSpec
import com.rpg.itens.Item;
import com.rpg.itens.Lootavel;
import com.rpg.personagens.Heroi;
import com.rpg.personagens.herois.Paladino;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List; // Import para List, se usado em algum lugar ou para clareza

/**
 * Classe de testes para a entidade {@link Goblin}.
 * Verifica o comportamento do Goblin em combate, na recepção de dano,
 * e seu mecanismo de drop de loot após as refatorações.
 */
public class GoblinTest {
    private Goblin goblin;
    private Heroi heroi;
    private final int NIVEL_TESTE = 1; // Definindo um nível de fase para o teste

    @BeforeEach
    void setUp() {
        // --- Refatoração: Usando ArmaDropSpec para o loot do monstro ---
        // Agora, 'armas' será uma lista de ArmaDropSpec, não de Arma.
        // Criamos uma especificação para uma Espada de Madeira que pode ser dropada.
        ArrayList<ArmaDropSpec> specsDeArmasParaLargar = new ArrayList<>(Arrays.asList(
            new ArmaDropSpec(EspadaMadeira.class.getName(), 5, 1) // Nome da classe, offset de dano (5), nível mínimo (1)
        ));

        // Instanciando o Goblin com o novo construtor que aceita ArmaDropSpec e nivelFase.
        goblin = new Goblin("Goblin Teste", 50, 5, 3, 10, "Clava", 5, 0.5,
                            specsDeArmasParaLargar, NIVEL_TESTE); // Últimos parâmetros: lista de specs e nível da fase.

        // Instanciando um Herói para os testes de combate.
        heroi = new Paladino("Teste Heroi", 100, 10, 5, 1, 0, 5, 10, 15, 100);
    }

    /**
     * Verifica se a classe Goblin implementa corretamente a interface Lootavel.
     */
    @Test
    void testGoblinImplementsLootavel() {
        assertTrue(goblin instanceof Lootavel, "Goblin deve implementar Lootavel");
    }

    /**
     * Testa o método droparLoot() do Goblin para garantir que ele retorna um item
     * e que este item é uma Arma instanciada a partir da ArmaDropSpec.
     */
    @Test
    void testDroparLoot() {
        Item loot = goblin.droparLoot(); // O monstro agora instanciará a arma a partir da spec

        assertNotNull(loot, "Goblin deve dropar um item");
        assertTrue(loot instanceof Arma, "O item dropado deve ser uma Arma");
        assertEquals("Espada Madeira", loot.getNomeCompleto(), "O nome da arma dropada deve ser 'Espada Madeira'");

        // Verifica se o dano da arma dropada é o esperado (offset + nivelFase)
        Arma armaDropada = (Arma) loot;
        // O dano esperado é 5 (offset da ArmaDropSpec) + NIVEL_TESTE (1) = 6
        assertEquals(5 + NIVEL_TESTE, armaDropada.getDano(), "O dano da arma dropada deve ser (offset + nivelFase)");
    }

    /**
     * Testa o método receberDano() do Goblin.
     */
    @Test
    void testReceberDano() {
        int vidaInicial = goblin.getpontosdevida();
        goblin.receberDano(20);
        assertEquals(vidaInicial - 20, goblin.getpontosdevida(), "Goblin deve ter vida reduzida");
    }

    /**
     * Testa o método atacarHeroi() do Goblin, verificando se o herói recebe dano.
     * Considera a lógica de dano base e roubo de vida (sem prever o crítico devido ao Math.random).
     */
    @Test
    void testAtacarHeroi() {
        int vidaInicialHeroi = heroi.getpontosdevida();
        // O Goblin tem AtaqueGoblin como sua ação padrão (configurada em inicializarAcoes()).
        // O método escolherAcao() do Monstro retorna uma das ações disponíveis.
        AcaoDeCombate acao = goblin.escolherAcao(heroi);

        assertNotNull(acao, "Goblin deve ter uma ação de combate para executar");

        try {
            acao.executar(goblin, heroi); // Tenta executar o ataque do Goblin.
        } catch (RecursoInsuficienteException e) {
            fail("O ataque do Goblin não deveria lançar uma RecursoInsuficienteException. Erro: " + e.getMessage());
        }

        // --- Verificação do dano causado ---
        // O dano base do Goblin é seu dano de arma (5) + sua força (5) = 10.
        // O AtaqueGoblin tem a chance de roubar vida, que também causa dano adicional ao alvo.
        // Para simplificar o teste unitário, verificamos que o herói recebeu *pelo menos* o dano base.
        // O roubo de vida (valorRoubo = 3) é aleatório, então o dano total pode ser maior.
                int danoBaseEsperado = goblin.getDanoArmaBase() + goblin.getForca();
        
        assertTrue(heroi.getpontosdevida() <= vidaInicialHeroi - danoBaseEsperado,
                "Herói deve ter recebido pelo menos o dano base do Goblin. Vida inicial: " + vidaInicialHeroi + ", Vida atual: " + heroi.getpontosdevida() + ", Dano base: " + danoBaseEsperado);

        // O goblin pode ter recuperado vida devido ao roubo. Isso pode ser testado separadamente
        // ou em testes de integração onde o Math.random possa ser mockado.
    }
}