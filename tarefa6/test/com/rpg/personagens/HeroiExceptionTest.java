// test/com/rpg/personagens/HeroiTest.java
package com.rpg.personagens;

import com.rpg.combate.AcaoDeCombate;
import com.rpg.exceptions.NivelInsuficienteException;
import com.rpg.exceptions.RecursoInsuficienteException;
import com.rpg.itens.EspadaDiamante; // Uma arma de alto nível
import com.rpg.personagens.herois.Paladino; // Exemplo de herói concreto
import com.rpg.personagens.monstros.Goblin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.rpg.itens.ArmaDropSpec; // Importe esta classe
import java.util.ArrayList; // Importe esta classe

public class HeroiExceptionTest {

    private Paladino paladino;
    private Goblin monstroAlvo; // Alvo para a habilidade

    @BeforeEach
    void setUp() {
        // Paladino de nível 1, com dano de Madeira=5, Ferro=10, Diamante=15, e 100 de mana inicial
        paladino = new Paladino("TestPaladin", 100, 10, 5, 1, 0, 5, 10, 15, 100);

        // Criamos um monstro genérico como alvo.
        // AGORA PRECISAMOS PASSAR OS NOVOS PARÂMETROS PARA O CONSTRUTOR DE GOBLIN
        // String,int,int,int,int,String,int,double,ArrayList<ArmaDropSpec>,int
        monstroAlvo = new Goblin("Dummy", 10, 1, 1, 1, "Adaga", 1, 0.1, new ArrayList<ArmaDropSpec>(), 1); // Corrigido: lista vazia de specs e nivel 1
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
        String mensagemEsperada = "o nível 1 de TestPaladin é insuficiente para equipar a arma Espada Diamante (nível mínimo: 3).";
        assertEquals(mensagemEsperada, thrown.getMessage());
    }

    // --- NOVO TESTE ATIVO ---
    @Test
    void testHabilidadeThrowsRecursoInsuficienteException() {
            // 1. Simular um herói com mana insuficiente (menos que o custo de 15)
            paladino.setMana(10); 

            // 2. Tentar usar a habilidade (Golpe Sagrado é a segunda ação, índice 1)
            RecursoInsuficienteException thrown = assertThrows(
            RecursoInsuficienteException.class,
            () -> {
                // --- INÍCIO DA CORREÇÃO ---
            // 1. Sinaliza ao herói que o próximo ataque deve ser um especial (crítico)
            paladino.setProximoAtaqueCritico(true); 
            
            // 2. Pede para o herói escolher a ação (ele vai escolher a habilidade especial)
            AcaoDeCombate acaoEscolhida = paladino.escolherAcao(monstroAlvo);

            // 3. Tenta executar a ação. É aqui que a exceção deve ser lançada.
            acaoEscolhida.executar(paladino, monstroAlvo);
            },
            "Deveria lançar RecursoInsuficienteException quando a mana é baixa."
        );

        // 3. Verifica se a mensagem da exceção está correta
        assertEquals("Recursos insuficientes para usar a habilidade.", thrown.getMessage());
    }
}
