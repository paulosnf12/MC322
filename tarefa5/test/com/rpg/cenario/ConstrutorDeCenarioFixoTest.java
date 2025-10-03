// test/com/rpg/cenario/ConstrutorDeCenarioFixoTest.java
package com.rpg.cenario;

import com.rpg.game.Dificuldade;
import com.rpg.personagens.Monstro;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class ConstrutorDeCenarioFixoTest {

    private GeradorDeFases gerador = new ConstrutorDeCenarioFixo();

   @Test
    void testMonstersAreHarderOnHardDifficulty() {
        // Gerar fases com dificuldade FÁCIL
        List<Fase> fasesFacil = gerador.gerar(1, Dificuldade.FACIL);
        Monstro monstroFacil = ((FaseDeCombate) fasesFacil.get(0)).getMonstros().get(0); // Pega o primeiro monstro

        // Gerar fases com dificuldade DIFÍCIL
        List<Fase> fasesDificil = gerador.gerar(1, Dificuldade.DIFICIL);
        // <<-- CORREÇÃO AQUI! >>
        Monstro monstroDificil = ((FaseDeCombate) fasesDificil.get(0)).getMonstros().get(0); // Pega o primeiro monstro DA LISTA DE FASES DIFÍCEIS

        // Verificações:
        // Multiplicador de vida: FACIL(0.3) vs DIFICIL(1.0)
        assertTrue(monstroDificil.getpontosdevida() > monstroFacil.getpontosdevida(),
                "Monstro na dificuldade DIFÍCIL deve ter mais vida do que na FÁCIL");

        // Multiplicador de dano: FACIL(0.3) vs DIFICIL(1.0)
        // Para isso, precisaríamos de uma forma de extrair a força base do monstro
        // A classe Monstro tem `getForca()`. Mas `AcaoDeCombate` que usa essa força.
        // O teste ideal seria verificar o dano causado por um monstro em cada dificuldade.
        // Por simplicidade, vamos comparar diretamente a força se ela for um indicador direto do dano.
        // No seu código, força base do monstro é multiplicada por dificuldade.getMultiplicadorDanoMonstro()
        // Então, getForca() DEVE refletir essa diferença.
        assertTrue(monstroDificil.getForca() > monstroFacil.getForca(),
                "Monstro na dificuldade DIFÍCIL deve ter mais força do que na FÁCIL");

        // Multiplicador de XP: FACIL(0.7) vs DIFICIL(0.3)
        // Note que XP é *menor* na dificuldade difícil para o monstro conceder ao herói
        assertTrue(monstroFacil.getXpConcedido() > monstroDificil.getXpConcedido(),
                "Monstro na dificuldade FÁCIL deve conceder mais XP do que na DIFÍCIL");

        // Testar chance de loot (se for relevante e tiver impacto direto nos atributos do monstro)
        // A chanceLootArmasBoas afeta o droparLoot() do monstro, não diretamente o monstro em si.
        // Este teste foca nos atributos do monstro.
    }

    @Test
    void testMonstersAreNormalOnNormalDifficulty() {
        List<Fase> fasesNormal = gerador.gerar(1, Dificuldade.NORMAL);
        Monstro monstroNormal = ((FaseDeCombate) fasesNormal.get(0)).getMonstros().get(0);

        // A dificuldade NORMAL tem multiplicadores de 0.5 para vida e dano.
        // Precisaríamos de um monstro "base" não-multiplicado para comparar diretamente,
        // ou garantir que 0.5 está sendo aplicado.
        // Como já testamos a relação entre FACIL e DIFICIL, este pode ser mais um teste de sanidade.
        // Poderíamos, por exemplo, criar um "ConstrutorDeCenarioBase" sem multiplicadores para comparar.
        // Por hora, apenas garantir que não é zero ou absurdo.
        assertTrue(monstroNormal.getpontosdevida() > 0, "Monstro deve ter vida positiva na dificuldade NORMAL");
        assertTrue(monstroNormal.getForca() > 0, "Monstro deve ter força positiva na dificuldade NORMAL");
    }
}