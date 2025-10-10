// src/com/rpg/game/Main.java
package com.rpg.game;

import com.rpg.cenario.ConstrutorDeCenarioFixo;
import com.rpg.cenario.Evento;
import com.rpg.cenario.Fase;
import com.rpg.cenario.FaseDeCombate;
import com.rpg.cenario.GeradorDeFases;
import com.rpg.cenario.TipoCenario;
import com.rpg.combate.AcaoDeCombate;
import com.rpg.exceptions.NivelInsuficienteException;
import com.rpg.exceptions.RecursoInsuficienteException;
import com.rpg.itens.Arma;
import com.rpg.itens.Item;
import com.rpg.personagens.Heroi;
import com.rpg.personagens.Monstro;
import com.rpg.personagens.herois.Elfo;
import com.rpg.personagens.herois.Paladino; // Para o exemplo de herói
import com.rpg.util.InputManager;
import com.rpg.util.GerenciadorDePersistencia; // NOVO: Import para o gerenciador de persistência

import java.util.List;
import java.util.Random;

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
            int opcao = InputManager.lerInteiro("Digite sua opção", 0, maxOption);

            switch (opcao) {
                case 1:
                    iniciarNovoJogo();
                    rodandoMenuPrincipal = false; // Sai do menu principal após iniciar o jogo
                    break;
                case 2:
                    exibirInfoHerois();
                    InputManager.esperarEnter("Pressione ENTER para voltar ao menu...");
                    break;
                case 3:
                    exibirInfoMonstros();
                    InputManager.esperarEnter("Pressione ENTER para voltar ao menu...");
                    break;
                case 4: // Carregar Jogo Salvo (apenas se houver saves)
                    if (!jogosSalvos.isEmpty()) {
                        carregarJogo(jogosSalvos);
                        rodandoMenuPrincipal = false; // Sai do menu principal após carregar
                    } else {
                        System.out.println("Nenhum jogo salvo disponível. Por favor, escolha outra opção.");
                        InputManager.esperarEnter("Pressione ENTER para continuar...");
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
        int escolhaDificuldade = InputManager.lerInteiro("Escolha o nível de dificuldade", 1, 3);
        Dificuldade dificuldadeSelecionada = Dificuldade.values()[escolhaDificuldade - 1];
        System.out.println("Você escolheu a dificuldade: " + dificuldadeSelecionada.name() + "!");

        // --- SELEÇÃO DE HERÓI (EXEMPLO) ---
        // A lógica de criação do herói é mantida aqui, mas a instância é passada para a Batalha.
        Heroi heroi;
        System.out.println("\n== Escolha seu herói ==");
        System.out.println("[1] Paladino (Especialista em Espada e Fé)");
        System.out.println("[2] Elfo (Mestre do Arco e Agilidade)");
        int escolhaHeroi = InputManager.lerInteiro("Digite sua escolha", 1, 2);

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
        int escolha = InputManager.lerInteiro("Escolha o jogo para carregar", 1, jogosSalvos.size());
        String nomeJogoEscolhido = jogosSalvos.get(escolha - 1);

        Batalha batalhaCarregada = GerenciadorDePersistencia.carregarBatalha(nomeJogoEscolhido);
        if (batalhaCarregada != null) {
            batalhaCarregada.continuarBatalha();
        } else {
            System.out.println("Não foi possível carregar o jogo '" + nomeJogoEscolhido + "'. Retornando ao menu principal.");
            InputManager.esperarEnter("Pressione ENTER para continuar...");
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