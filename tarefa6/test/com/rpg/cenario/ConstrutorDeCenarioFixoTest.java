package com.rpg.cenario;

import com.rpg.game.Dificuldade;
import com.rpg.personagens.Monstro;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Classe de teste para {@link ConstrutorDeCenarioFixo}.
 * Contém testes para verificar se os monstros são gerados corretamente
 * com base nos níveis de dificuldade e se seus atributos refletem
 * os multiplicadores definidos para cada dificuldade.
 */
public class ConstrutorDeCenarioFixoTest {

    // Instância do gerador de fases para ser usada nos testes.
    private GeradorDeFases gerador = new ConstrutorDeCenarioFixo();

    /**
     * Testa se os monstros gerados em dificuldades mais difíceis
     * possuem atributos (vida, força, XP) superiores ou inferiores
     * aos gerados em dificuldades mais fáceis, conforme os multiplicadores.
     */
    @Test
    void testMonstersAreHarderOnHardDifficulty() {
        System.out.println("Iniciando teste: testMonstersAreHarderOnHardDifficulty");

        // Geração de fases com dificuldade FÁCIL para comparação base.
        // É gerada apenas 1 fase para isolar o teste em um monstro específico.
        List<Fase> fasesFacil = gerador.gerar(1, Dificuldade.FACIL);
        // Obtém o primeiro monstro da primeira fase gerada com dificuldade FÁCIL.
        Monstro monstroFacil = ((FaseDeCombate) fasesFacil.get(0)).getMonstros().get(0);

        // Geração de fases com dificuldade DIFÍCIL para comparação.
        // Também é gerada apenas 1 fase.
        List<Fase> fasesDificil = gerador.gerar(1, Dificuldade.DIFICIL);
        // Obtém o primeiro monstro da primeira fase gerada com dificuldade DIFÍCIL.
        Monstro monstroDificil = ((FaseDeCombate) fasesDificil.get(0)).getMonstros().get(0);

        // --- Verificações de Atributos com base nos multiplicadores de dificuldade ---

        // 1. Multiplicador de Vida:
        // Na definição de Dificuldade, FACIL tem multiplicador de vida menor (0.3) que DIFICIL (1.0).
        // Portanto, o monstro DIFÍCIL deve ter mais vida que o monstro FÁCIL.
        assertTrue(monstroDificil.getpontosdevida() > monstroFacil.getpontosdevida(),
                "Monstro na dificuldade DIFÍCIL deve ter mais vida do que na FÁCIL");
        System.out.println("Vida: Monstro DIFÍCIL (" + monstroDificil.getpontosdevida() + ") > Monstro FÁCIL (" + monstroFacil.getpontosdevida() + ")");

        // 2. Multiplicador de Dano (representado pela Força):
        // Na definição de Dificuldade, FACIL tem multiplicador de dano menor (0.3) que DIFICIL (1.0).
        // A força do monstro é um indicador direto do seu potencial de dano.
        // Portanto, o monstro DIFÍCIL deve ter mais força que o monstro FÁCIL.
        assertTrue(monstroDificil.getForca() > monstroFacil.getForca(),
                "Monstro na dificuldade DIFÍCIL deve ter mais força do que na FÁCIL");
        System.out.println("Força: Monstro DIFÍCIL (" + monstroDificil.getForca() + ") > Monstro FÁCIL (" + monstroFacil.getForca() + ")");

        // 3. Multiplicador de XP Concedido:
        // Na definição de Dificuldade, FACIL tem multiplicador de XP maior (0.7) que DIFICIL (0.3).
        // Monstros em dificuldades mais fáceis devem conceder mais XP para acelerar o progresso do jogador.
        // Portanto, o monstro FÁCIL deve conceder mais XP que o monstro DIFÍCIL.
        assertTrue(monstroFacil.getXpConcedido() > monstroDificil.getXpConcedido(),
                "Monstro na dificuldade FÁCIL deve conceder mais XP do que na DIFÍCIL");
        System.out.println("XP Concedido: Monstro FÁCIL (" + monstroFacil.getXpConcedido() + ") > Monstro DIFÍCIL (" + monstroDificil.getXpConcedido() + ")");

        System.out.println("Teste 'testMonstersAreHarderOnHardDifficulty' concluído com sucesso.");
        // Observação: O teste de chance de loot não é realizado aqui, pois afeta o método droparLoot()
        // do monstro e não seus atributos iniciais, que é o foco deste teste unitário.
    }

    /**
     * Testa se os monstros gerados na dificuldade NORMAL possuem atributos válidos (positivos).
     * Este é um teste de sanidade para garantir que os valores não sejam zero ou negativos,
     * mesmo que não compare explicitamente com multiplicadores base.
     */
    @Test
    void testMonstersAreNormalOnNormalDifficulty() {
        System.out.println("Iniciando teste: testMonstersAreNormalOnNormalDifficulty");

        // Geração de fases com dificuldade NORMAL.
        List<Fase> fasesNormal = gerador.gerar(1, Dificuldade.NORMAL);
        // Obtém o primeiro monstro da primeira fase gerada com dificuldade NORMAL.
        Monstro monstroNormal = ((FaseDeCombate) fasesNormal.get(0)).getMonstros().get(0);

        // --- Verificações de Atributos para a dificuldade NORMAL ---

        // A dificuldade NORMAL tem multiplicadores de 0.5 para vida e dano.
        // Embora não esteja comparando diretamente com um monstro "base" não-multiplicado,
        // este teste garante que os atributos fundamentais sejam positivos e lógicos.
        assertTrue(monstroNormal.getpontosdevida() > 0, "Monstro deve ter vida positiva na dificuldade NORMAL");
        System.out.println("Vida do monstro NORMAL: " + monstroNormal.getpontosdevida() + " (Esperado > 0)");
        assertTrue(monstroNormal.getForca() > 0, "Monstro deve ter força positiva na dificuldade NORMAL");
        System.out.println("Força do monstro NORMAL: " + monstroNormal.getForca() + " (Esperado > 0)");
        assertTrue(monstroNormal.getXpConcedido() > 0, "Monstro deve conceder XP positivo na dificuldade NORMAL");
        System.out.println("XP Concedido pelo monstro NORMAL: " + monstroNormal.getXpConcedido() + " (Esperado > 0)");

        System.out.println("Teste 'testMonstersAreNormalOnNormalDifficulty' concluído com sucesso.");
    }
}