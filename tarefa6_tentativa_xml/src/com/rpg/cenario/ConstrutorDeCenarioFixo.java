//src/com/rpg/cenario/ConstrutorDeCenarioFixo.java
package com.rpg.cenario;

import com.rpg.game.Dificuldade;
import com.rpg.itens.Arma;
import com.rpg.itens.ArmaDropSpec; // NOVO IMPORT: Para lidar com as especificações de drop de armas.
import com.rpg.itens.ArcoAlpha;
import com.rpg.itens.ArcoBeta;
import com.rpg.itens.ArcoSigma;
import com.rpg.itens.EspadaDiamante;
import com.rpg.itens.EspadaFerro;
import com.rpg.itens.EspadaMadeira;
import com.rpg.personagens.Monstro;
import com.rpg.personagens.monstros.Espirito;
import com.rpg.personagens.monstros.Goblin;
import com.rpg.personagens.monstros.Vampiro;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementação de {@link GeradorDeFases} que cria um cenário com uma sequência predefinida de fases.
 * Esta classe é responsável por instanciar monstros, suas especificações de drop de armas e eventos
 * para cada fase, de acordo com o nível e a dificuldade selecionada.
 *
 * A refatoração do sistema de recompensa foi aplicada, onde os monstros agora recebem
 * {@link ArmaDropSpec} em vez de instâncias diretas de {@link Arma}, seguindo o conceito de agregação.
 * Isso garante que as instâncias de armas sejam criadas apenas no momento do "drop",
 * reduzindo o número de instâncias desnecessárias e otimizando a memória.
 */
public class ConstrutorDeCenarioFixo implements GeradorDeFases {

    // Array de ambientes temáticos disponíveis no jogo.
    private static final TipoCenario[] CENARIOS_TEMATICOS = {
        TipoCenario.FLORESTA,
        TipoCenario.CRIPTA,
        TipoCenario.PICO
    };

    /**
     * Gera uma lista de fases para o jogo com base no número de fases desejado
     * e na dificuldade selecionada.
     * Cada fase é configurada com monstros e cenários temáticos. Os monstros
     * são criados com especificações de armas para posterior 'drop',
     * implementando o conceito de agregação.
     *
     * @param nFases O número total de fases a serem geradas.
     * @param dificuldade A dificuldade do jogo, que influenciará os atributos dos monstros
     *                    e as especificações de loot.
     * @return Uma {@code List} de objetos {@code Fase}, representando a sequência de desafios do jogo.
     * @see Fase
     * @see Dificuldade
     * @see ArmaDropSpec
     */
    @Override
    public List<Fase> gerar(int nFases, Dificuldade dificuldade) {
        List<Fase> fases = new ArrayList<>();
        System.out.println("\n-------------------------------------------------");
        System.out.println(" Preparando o reino para sua jornada, herói...");
        System.out.println("-------------------------------------------------");

        // Loop principal para criar cada fase do jogo.
        for (int i = 0; i < nFases; i++) {
            int nivelFase = i + 1; // O nível da fase é 1-based.

            // Seleciona um ambiente temático para a fase atual.
            // Se houver mais fases do que cenários temáticos definidos, o último cenário é repetido.
            TipoCenario cenario;
            if (i < CENARIOS_TEMATICOS.length) {
                cenario = CENARIOS_TEMATICOS[i];
            } else {
                cenario = CENARIOS_TEMATICOS[CENARIOS_TEMATICOS.length - 1]; // Repete o último cenário.
            }

            ArrayList<Monstro> monstrosFase = new ArrayList<>();

            // --- REFACTORING: Criando especificações de drop de armas (ArmaDropSpec) ---
            // Em vez de instâncias diretas de Arma, armazenamos ArmaDropSpec.
            // Isso permite que a Arma seja instanciada apenas no momento do drop pelo método droparLoot() do Monstro.
            // Conforme a sua implementação de ArmaDropSpec, agora passamos o nome da classe, um offset de dano base
            // e o nível mínimo para a própria especificação.
            ArrayList<ArmaDropSpec> specsDeArmasParaLargar = new ArrayList<>();

            // Adicionando especificações para armas comuns (disponíveis a partir da fase 1).
            specsDeArmasParaLargar.add(new ArmaDropSpec(EspadaMadeira.class.getName(), 9, 1)); // Espada de Madeira
            specsDeArmasParaLargar.add(new ArmaDropSpec(ArcoBeta.class.getName(), 12, 1));     // Arco Beta

            // --- ADIÇÃO DE ESPECIFICAÇÕES DE ARMAS MAIS AVANÇADAS PARA DROP --
            // Estas especificações são adicionadas à lista *se* o nível da fase atender ao requisito.
            // O 'minLevel' na ArmaDropSpec é um indicativo, mas a lógica de adição aqui já atua como filtro.
            if (nivelFase >= 2) {
                specsDeArmasParaLargar.add(new ArmaDropSpec(EspadaFerro.class.getName(), 21, 2)); // Espada de Ferro
                specsDeArmasParaLargar.add(new ArmaDropSpec(ArcoAlpha.class.getName(), 19, 2));   // Arco Alpha
            }
            if (nivelFase >= 3) {
                specsDeArmasParaLargar.add(new ArmaDropSpec(EspadaDiamante.class.getName(), 42, 3)); // Espada de Diamante
                specsDeArmasParaLargar.add(new ArmaDropSpec(ArcoSigma.class.getName(), 38, 3));     // Arco Sigma
            }
            // --- FIM DA ADIÇÃO DE ESPECIFICAÇÕES DE ARMAS MAIS AVANÇADAS PARA DROP --

            // Loop para criar a quantidade de monstros para a fase atual.
            for (int j = 0; j < nivelFase + 2; j++) { // A quantidade de monstros aumenta com o nível da fase.
                String nomeMonstro; // Variável temporária para definir o nome do monstro.

                // Variáveis para calcular atributos base e aplicar multiplicadores de dificuldade.
                int vidaBaseMonstro;
                int forcaBaseMonstro;
                int xpBaseMonstro;

                // Criação de Goblins.
                if (j % 3 == 0) {
                    nomeMonstro = "Goblin " + (j + 1) + " da Fase " + nivelFase;
                    // Lógica para nome especial do primeiro Goblin da Fase 1.
                    if (nivelFase == 1 && j == 0) {
                        nomeMonstro = "Goblin Guerreiro";
                    }

                    // Aplica multiplicadores de dificuldade aos atributos do monstro.
                    vidaBaseMonstro = (int) ((nivelFase * 12 + 50) * dificuldade.getMultiplicadorVidaMonstro());
                    forcaBaseMonstro = (int) ((nivelFase * 2 + 9) * dificuldade.getMultiplicadorDanoMonstro());
                    xpBaseMonstro = (int) (nivelFase * 25 * dificuldade.getMultiplicadorXPMonstro());

                    monstrosFase.add(new Goblin(
                        nomeMonstro,
                        vidaBaseMonstro,
                        forcaBaseMonstro,
                        nivelFase * 2 + 8, // Agilidade escalonada.
                        xpBaseMonstro,
                        "Adaga",
                        3 + nivelFase, // Dano da adaga escalonado.
                        0.2 + (nivelFase * 0.05), // Chance de roubo escalonada.
                        specsDeArmasParaLargar, // Passa a lista de ArmaDropSpec.
                        nivelFase // Passa o nível da fase para o construtor do monstro.
                    ));
                // Criação de Vampiros.
                } else if (j % 3 == 1) {
                    nomeMonstro = "Vampiro " + (j + 1) + " da Fase " + nivelFase;
                    // Lógica para nome especial do Vampiro da Fase 2.
                    if (nivelFase == 2 && j == 1) {
                        nomeMonstro = "Edward Cullen";
                    }

                    // Aplica multiplicadores de dificuldade aos atributos do monstro.
                    vidaBaseMonstro = (int) ((nivelFase * 15 + 60) * dificuldade.getMultiplicadorVidaMonstro());
                    forcaBaseMonstro = (int) ((nivelFase * 3 + 10) * dificuldade.getMultiplicadorDanoMonstro());
                    xpBaseMonstro = (int) (nivelFase * 30 * dificuldade.getMultiplicadorXPMonstro());

                    monstrosFase.add(new Vampiro(
                        nomeMonstro,
                        vidaBaseMonstro,
                        forcaBaseMonstro,
                        nivelFase * 2 + 8, // Agilidade escalonada.
                        xpBaseMonstro,
                        10 + nivelFase * 2, // Brilho escalonado.
                        specsDeArmasParaLargar, // Passa a lista de ArmaDropSpec.
                        nivelFase // Passa o nível da fase para o construtor do monstro.
                    ));
                // Criação de Espíritos.
                } else {
                    nomeMonstro = "Espirito " + (j + 1) + " da Fase " + nivelFase;
                    // Lógica para nome especial do Espírito da Fase 3.
                    if (nivelFase == 3 && j == 2) {
                        nomeMonstro = "Kaonashi";
                    }

                    // Aplica multiplicadores de dificuldade aos atributos do monstro.
                    vidaBaseMonstro = (int) ((nivelFase * 10 + 40) * dificuldade.getMultiplicadorVidaMonstro());
                    forcaBaseMonstro = (int) ((nivelFase * 2 + 8) * dificuldade.getMultiplicadorDanoMonstro());
                    xpBaseMonstro = (int) (nivelFase * 23 * dificuldade.getMultiplicadorXPMonstro());

                    monstrosFase.add(new Espirito(
                        nomeMonstro,
                        vidaBaseMonstro,
                        forcaBaseMonstro,
                        nivelFase * 2 + 8, // Agilidade escalonada.
                        xpBaseMonstro,
                        15 + nivelFase * 3, // Tristeza escalonada.
                        specsDeArmasParaLargar, // Passa a lista de ArmaDropSpec.
                        nivelFase // Passa o nível da fase para o construtor do monstro.
                    ));
                }
            }

            // --- LÓGICA DE EVENTOS DA FASE ---
            List<Evento> eventosDaFase = new ArrayList<>();
            // Adiciona um EventoDeBencao se o cenário for Floresta.
            if (cenario == TipoCenario.FLORESTA) {
                eventosDaFase.add(new EventoDeBencao());
            }

            // Adiciona a fase de combate recém-criada à lista de fases do jogo.
            fases.add(new FaseDeCombate(nivelFase, cenario, monstrosFase, eventosDaFase));
            System.out.println("Fase " + nivelFase + ": '" + cenario.getDescricao() + "' criada com " + monstrosFase.size() + " monstros prontos para o desafio!");
        }
        System.out.println("Todas as fases foram geradas com sucesso! A aventura aguarda...\n");
        return fases;
    }
}