// src/com/rpg/game/Main.java
package com.rpg.game; // Declaração do pacote, conforme a nova estrutura

// --- IMPORTS ANTIGOS (REMOVIDOS/MOVIDOS PARA BATALHA) ---
// import com.rpg.cenario.ConstrutorDeCenarioFixo;
// import com.rpg.cenario.Evento;
// import com.rpg.cenario.Fase;
// import com.rpg.cenario.FaseDeCombate;
// import com.rpg.cenario.GeradorDeFases;
// import com.rpg.cenario.TipoCenario;
// import com.rpg.combate.AcaoDeCombate;
// import com.rpg.exceptions.NivelInsuficienteException;
// import com.rpg.exceptions.RecursoInsuficienteException;
// import com.rpg.itens.Arma;
// import com.rpg.itens.Item;
// import com.rpg.personagens.Monstro;
// import java.util.Random;

import com.rpg.personagens.Heroi;
import com.rpg.personagens.herois.Elfo;
import com.rpg.personagens.herois.Paladino; // Para o exemplo de herói
import com.rpg.util.InputManager;
import java.util.List;

/**
 * Classe principal que serve como ponto de entrada para a execução do jogo de RPG Narrativo.
 * Esta classe gerencia o menu principal, a inicialização de uma nova partida e
 * controla o fluxo geral do jogo, guiando o jogador através para os desafios e combates.
 * <p>
 * Refatorada para utilizar a classe {@link Batalha} para gerenciar a lógica de jogo
 * e {@link GerenciadorDePersistencia} para salvar e carregar o progresso.
 *
 * @author Bárbara Maria Barreto Fonseca de Cerqueira César
 * @author Paulo Santos do Nascimento Filho
 * @version 6.0 (Laboratório 6 - MC322)
 * @since 04-09-2025 (Originalmente) / Refatorado para 19-10-2025
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("          Bem-vindo ao RPG MC322!");
        System.out.println("      Uma Aventura Épica te Aguarda");
        System.out.println("==============================================");
        System.out.println("Prepare-se para uma jornada de coragem, estratégia e sorte!");
        System.out.println("----------------------------------------------");

        boolean rodandoMenuPrincipal = true;
        while (rodandoMenuPrincipal) {
            System.out.println("\n==============================================");
            System.out.println("       TERRAS SOMBRIAS - RPG");
            System.out.println("==============================================");
            System.out.println("[1] Iniciar Novo Jogo");

            // --- NOVIDADE: Opção "Carregar Jogo" só aparece se houver jogos salvos ---
            if (GerenciadorDePersistencia.existemJogosSalvos()) {
                System.out.println("[2] Carregar Jogo");
                System.out.println("[3] Ver Informações das Classes de Heróis");
                System.out.println("[4] Ver Informações das Classes de Monstros");
                System.out.println("[5] Sair do Jogo");
                int opcao = InputManager.lerInteiro("Digite sua opcao", 1, 5);

                switch (opcao) {
                    case 1:
                        iniciarNovoJogo(); // Chama o método para iniciar o jogo
                        rodandoMenuPrincipal = false; // Sai do menu principal após iniciar o jogo (ou terminar)
                        break;
                    case 2:
                        carregarJogoSalvo(); // NOVIDADE: Chama o método para carregar jogo
                        rodandoMenuPrincipal = false; // Sai do menu principal
                        break;
                    case 3:
                        exibirInfoHerois(); // Exibe informações sobre os heróis
                        InputManager.esperarEnter("Pressione ENTER para voltar ao menu...");
                        break;
                    case 4:
                        exibirInfoMonstros(); // Exibe informações sobre os monstros
                        InputManager.esperarEnter("Pressione ENTER para voltar ao menu...");
                        break;
                    case 5:
                        System.out.println("Saindo do jogo. Ate a proxima!");
                        rodandoMenuPrincipal = false; // Define para sair do loop do menu principal
                        break;
                }
            } else { // Se não houver jogos salvos, o menu é mais simples
                System.out.println("[2] Ver Informações das Classes de Herois");
                System.out.println("[3] Ver Informações das Classes de Monstros");
                System.out.println("[4] Sair do Jogo");
                int opcao = InputManager.lerInteiro("Digite sua opcao", 1, 4);

                switch (opcao) {
                    case 1:
                        iniciarNovoJogo(); // Chama o método para iniciar o jogo
                        rodandoMenuPrincipal = false; // Sai do menu principal após iniciar o jogo (ou terminar)
                        break;
                    case 2:
                        exibirInfoHerois(); // Exibe informações sobre os heróis
                        InputManager.esperarEnter("Pressione ENTER para voltar ao menu...");
                        break;
                    case 3:
                        exibirInfoMonstros(); // Exibe informações sobre os monstros
                        InputManager.esperarEnter("Pressione ENTER para voltar ao menu...");
                        break;
                    case 4:
                        System.out.println("Saindo do jogo. Ate a proxima!");
                        rodandoMenuPrincipal = false; // Define para sair do loop do menu principal
                        break;
                }
            }
        }

        // Garante que o Scanner seja fechado quando o programa termina
        InputManager.fecharScanner();
    }

    /**
     * Inicia um novo jogo, configurando o herói e a dificuldade, e então inicia a {@link Batalha}.
     */
    private static void iniciarNovoJogo() {
        // --- SELEÇÃO DE DIFICULDADE ---
        System.out.println("\n== Selecao de Dificuldade ==");
        System.out.println("[1] FACIL");
        System.out.println("[2] NORMAL");
        System.out.println("[3] DIFICIL");
        int escolhaDificuldade = InputManager.lerInteiro("Escolha o nivel de dificuldade", 1, 3);

        // Mapeia a escolha numérica para o enum Dificuldade
        Dificuldade dificuldadeSelecionada = Dificuldade.values()[escolhaDificuldade - 1];
        System.out.println("Voce escolheu a dificuldade: " + dificuldadeSelecionada.name() + "!");

        // --- SELEÇÃO DE HERÓI ---
        System.out.println("\n== Selecao de Heroi ==");
        System.out.println("[1] Elfo");
        System.out.println("[2] Paladino");
        int escolhaHeroi = InputManager.lerInteiro("Escolha seu heroi", 1, 2);
        Heroi heroi;
        if (escolhaHeroi == 1) {
            heroi = new Elfo("Legolas", 100, 15, 12, 1, 0, 8, 17, 32, 100);
        } else {
            heroi = new Paladino("Paladino Capitão Nascimento", 120, 17, 12, 1, 0, 6, 17, 37, 100);
        }

        System.out.println("\n== Despertar do Heroi ==");
        System.out.println("Seu campeao surge das lendas...");
        System.out.println("O heroi " + heroi.getNome() + " esta pronto para a gloria!");
        System.out.println(heroi.exibirStatus());
        System.out.println();

        // --- NOVIDADE: Cria uma instância de Batalha e delega o controle a ela ---
        Batalha batalha = new Batalha(heroi, dificuldadeSelecionada, 3); // 3 fases para o jogo
        // Loop principal do jogo: enquanto a batalha não terminar e o herói estiver vivo
        while(batalha.executarProximaFase());
    }

    /**
     * NOVIDADE: Carrega um jogo salvo, permitindo ao jogador escolher qual arquivo carregar.
     * Após carregar, a execução do jogo é retomada pela instância de {@link Batalha}.
     */
    private static void carregarJogoSalvo() {
        List<String> jogosSalvos = GerenciadorDePersistencia.listarJogosSalvos();
        if (jogosSalvos.isEmpty()) {
            System.out.println("Nenhum jogo salvo encontrado para carregar.");
            InputManager.esperarEnter("Pressione ENTER para voltar ao menu...");
            return;
        }

        System.out.println("\n== Carregar Jogo ==");
        for (int i = 0; i < jogosSalvos.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + jogosSalvos.get(i));
        }
        int escolha = InputManager.lerInteiro("Escolha o jogo para carregar", 1, jogosSalvos.size());
        String nomeSave = jogosSalvos.get(escolha - 1);

        Batalha batalhaCarregada = GerenciadorDePersistencia.carregarBatalha(nomeSave);
        if (batalhaCarregada != null) {
            System.out.println("Jogo '" + nomeSave + "' carregado com sucesso!");
            // Loop principal do jogo: retoma de onde parou
            while(batalhaCarregada.executarProximaFase());
        } else {
            System.out.println("Falha ao carregar o jogo '" + nomeSave + "'.");
        }
    }


    // --- MÉTODOS AUXILIARES PARA OS MENUS (PRESERVADOS) ---
    private static void exibirInfoHerois() {
        System.out.println("\n== Informacoes das Classes de Herois ==");
        System.out.println("---------------------------------------");
        System.out.println("Elfo: Mestre do arco, com ataques rapidos e habilidade de cura ao atacar.");
        System.out.println("      Possui agilidade elevada e sua arma principal e o arco.");
        System.out.println("Paladino: Guerreiro sagrado, forte na espada e com golpes abencoados.");
        System.out.println("          Possui alta forca e carisma, e sua arma principal e a espada.");
        // Adicione mais descrições conforme suas classes de heróis
        System.out.println("---------------------------------------");
    }

    private static void exibirInfoMonstros() {
        System.out.println("\n== Informacoes das Classes de Monstros ==");
        System.out.println("-----------------------------------------");
        System.out.println("Goblin: Pequenos, numerosos e oportunistas, capazes de roubar vida e equipados com adagas.");
        System.out.println("Vampiro: Elegantes e mortais, utilizam seu brilho em combate e possuem boa vitalidade.");
        System.out.println("Espirito: Etereos e melancolicos, seus ataques sao alimentados pela tristeza e sao dificeis de acertar.");
        // Adicione mais descrições conforme suas classes de monstros
        System.out.println("-----------------------------------------");
    }
}