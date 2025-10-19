// src/com/rpg/game/Main.java
package com.rpg.game;

import com.rpg.personagens.Heroi;
import com.rpg.personagens.herois.Elfo;
import com.rpg.personagens.herois.Paladino;
import com.rpg.util.GerenciadorDePersistencia;
import com.rpg.util.InputManager;
import java.util.List;

/**
 * Classe principal que serve como ponto de entrada para a execução do jogo de RPG Narrativo.
 * Esta classe gerencia o menu principal, a inicialização de uma nova partida e
 * controla o fluxo geral do jogo, guiando o jogador através para os desafios e combates.
 *
 * Agora, a classe Main delega a maior parte da lógica de jogo para a classe {@link Batalha},
 * e gerencia as opções de iniciar um novo jogo ou carregar um jogo salvo.
 *
 * @author Bárbara Maria Barreto Fonseca de Cerqueira César
 * @author Paulo Santos do Nascimento Filho
 * @version 6.0 (Laboratório 6 - MC322)
 * @since 04-09-2025
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("             Bem-vindo ao RPG MC322!");
        System.out.println("         Uma Aventura Épica te Aguarda");
        System.out.println("==============================================");
        System.out.println("Prepare-se para uma jornada de coragem, estratégia e sorte!");
        System.out.println("----------------------------------------------");

        boolean rodandoMenuPrincipal = true;
        while (rodandoMenuPrincipal) {
            System.out.println("\n==============================================");
            System.out.println("           TERRAS SOMBRIAS - RPG");
            System.out.println("==============================================");

            System.out.println("[1] Iniciar Novo Jogo");
            System.out.println("[2] Ver Informações das Classes de Heróis");
            System.out.println("[3] Ver Informações das Classes de Monstros");

            List<String> jogosSalvos = GerenciadorDePersistencia.listarJogosSalvos();
            if (!jogosSalvos.isEmpty()) {
                System.out.println("[4] Carregar Jogo Salvo"); // NOVA OPÇÃO
            }
            System.out.println("[0] Sair do Jogo"); // Opção de saída no final

            int maxOption = jogosSalvos.isEmpty() ? 3 : 4; // Ajusta o máximo conforme há saves ou não

            System.out.println("Escolha a sua opção: ");
            int opcao = InputManager.lerInteiro("", 0, maxOption);

            switch (opcao) {
                case 1:
                    iniciarNovoJogo();
                    rodandoMenuPrincipal = false; // Sai do menu principal após iniciar o jogo
                    break;
                case 2:
                    exibirInfoHerois();
                    System.out.println("Pressione ENTER para voltar ao menu...");
                    InputManager.esperarEnter("");
                    break;
                case 3:
                    exibirInfoMonstros();
                    System.out.println("Pressione ENTER para voltar ao menu...");
                    InputManager.esperarEnter("");
                    break;
                case 4: // Carregar Jogo Salvo (apenas se houver saves)
                    if (!jogosSalvos.isEmpty()) {
                        carregarJogo(jogosSalvos);
                        rodandoMenuPrincipal = false; // Sai do menu principal após carregar
                    } else {
                        System.out.println("Nenhum jogo salvo disponível. Por favor, escolha outra opção.");
                    }
                    break;
                case 0:
                    System.out.println("Saindo do jogo. Até a próxima!");
                    rodandoMenuPrincipal = false;
                    break;
            }
        }
        InputManager.fecharScanner();
    }

    /**
     * Inicia um novo jogo, solicitando a dificuldade e criando um novo herói e fases.
     */
    private static void iniciarNovoJogo() {
        // --- SELEÇÃO DE DIFICULDADE ---
        System.out.println("\n== Seleção de Dificuldade ==");
        System.out.println("[1] FÁCIL");
        System.out.println("[2] NORMAL");
        System.out.println("[3] DIFÍCIL");
        System.out.println("Escolha o nível de dificuldade: ");
        int escolhaDificuldade = InputManager.lerInteiro("Dificuldade escolhida: ", 1, 3);
        Dificuldade dificuldadeSelecionada = Dificuldade.values()[escolhaDificuldade - 1];
        System.out.println("Você escolheu a dificuldade: " + dificuldadeSelecionada.name() + "!");

        // --- SELEÇÃO DE HERÓI (EXEMPLO) ---
        // A lógica de criação do herói é mantida aqui, mas a instância é passada para a Batalha.
        Heroi heroi;
        System.out.println("\n== Escolha seu herói ==");
        System.out.println("[1] Paladino (Especialista em Espada e Fé)");
        System.out.println("[2] Elfo (Mestre do Arco e Agilidade)");
        System.out.println("Digite o número do herói de sua escolha: ");
        int escolhaHeroi = InputManager.lerInteiro("Herói escolhido: ", 1, 2);

        if (escolhaHeroi == 1) {
            heroi = new Paladino("Paladino Capitão Nascimento", 120, 17, 12, 1, 0, 6, 17, 37, 100);
        } else { // Escolha 2
            heroi = new Elfo("Elfo Legolas da Floresta", 100, 15, 12, 1, 0, 8, 17, 32, 100);
        }

        // Cria uma nova instância de Batalha e delega o início do jogo a ela
        Batalha batalha = new Batalha();
        batalha.iniciarNovaBatalha(dificuldadeSelecionada, heroi, 3); // 3 fases por padrão
    }

    /**
     * Permite ao jogador carregar um jogo salvo.
     * @param jogosSalvos A lista de nomes de jogos salvos disponíveis.
     */
    private static void carregarJogo(List<String> jogosSalvos) {
        System.out.println("\n== Carregar Jogo Salvo ==");
        for (int i = 0; i < jogosSalvos.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + jogosSalvos.get(i));
        }
        System.out.println("Escolha o jogo salvo para carregar: ");
        int escolha = InputManager.lerInteiro("Jogo escolhido: ", 1, jogosSalvos.size());
        String nomeJogoEscolhido = jogosSalvos.get(escolha - 1);

        Batalha batalhaCarregada = GerenciadorDePersistencia.carregarBatalha(nomeJogoEscolhido);
        if (batalhaCarregada != null) {
            batalhaCarregada.continuarBatalha();
        } else {
            System.out.println("Não foi possível carregar o jogo '" + nomeJogoEscolhido + "'. Retornando ao menu principal.");
            System.out.println("Pressione ENTER para continuar...");
            InputManager.esperarEnter("");
        }
    }


    // --- MÉTODOS AUXILIARES PARA OS MENUS (Sem alterações) ---
    private static void exibirInfoHerois() {
        System.out.println("\n== Informações das Classes de Heróis ==");
        System.out.println("---------------------------------------");
        System.out.println("Elfo: Mestre do arco, com ataques rápidos e habilidade de cura ao atacar.");
        System.out.println("      Possui agilidade elevada e sua arma principal é o arco.");
        System.out.println("Paladino: Guerreiro sagrado, forte na espada e com golpes abençoados.");
        System.out.println("      Possui alta força e carisma, e sua arma principal é a espada.");
        System.out.println("---------------------------------------");
    }

    private static void exibirInfoMonstros() {
        System.out.println("\n== Informações das Classes de Monstros ==");
        System.out.println("-----------------------------------------");
        System.out.println("Goblin: Pequenos, numerosos e oportunistas, capazes de roubar vida e equipados com adagas.");
        System.out.println("Vampiro: Elegantes e mortais, utilizam seu brilho em combate e possuem boa vitalidade.");
        System.out.println("Espírito: Etéreos e melancólicos, seus ataques são alimentados pela tristeza e são difíceis de acertar.");
        System.out.println("-----------------------------------------");
    }
}