package com.rpg.game; // Declaração do pacote, conforme a nova estrutura

import com.rpg.cenario.ConstrutorDeCenarioFixo;
import com.rpg.cenario.Evento;
import com.rpg.cenario.Fase;
import com.rpg.cenario.FaseDeCombate;
import com.rpg.cenario.GeradorDeFases;
import com.rpg.cenario.TipoCenario;
import com.rpg.combate.AcaoDeCombate;
import com.rpg.exceptions.NivelInsuficienteException;
import com.rpg.itens.Arma;
import com.rpg.itens.Item;
import com.rpg.personagens.Heroi;
import com.rpg.personagens.Monstro;
import com.rpg.personagens.herois.Elfo;
import com.rpg.personagens.herois.Paladino; // Para o exemplo de herói
import com.rpg.util.InputManager;
import java.util.List; // Importa o InputManager
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // --- SETUP INICIAL (Idêntico ao original, com a criação de objetos atualizada) --
        System.out.println("==============================================");
        System.out.println("          Bem-vindo ao RPG MC322!");
        System.out.println("      Uma Aventura Épica te Aguarda");
        System.out.println("==============================================");
        System.out.println("Prepare-se para uma jornada de coragem, estratégia e sorte!");
        System.out.println("----------------------------------------------");

        // --- LOOP DO MENU PRINCIPAL ---
        boolean rodandoMenuPrincipal = true;
        while (rodandoMenuPrincipal) {
            System.out.println("\n==============================================");
            System.out.println("       TERRAS SOMBRIAS - RPG");
            System.out.println("==============================================");
            System.out.println("[1] Iniciar Novo Jogo");
            System.out.println("[2] Ver Informacoes das Classes de Herois");
            System.out.println("[3] Ver Informacoes das Classes de Monstros");
            System.out.println("[4] Sair do Jogo");
            System.out.println("==============================================");

            // Usa o InputManager para ler a opção do menu principal
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

        // Garante que o Scanner seja fechado quando o programa termina
        InputManager.fecharScanner();
    }

    // --- NOVO MÉTODO PARA INICIAR UM NOVO JOGO ---
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

        // --- SELEÇÃO DE HERÓI (OPCIONAL, AQUI APENAS EXEMPLO) ---
        // Você poderia adicionar um menu de seleção de herói aqui, usando InputManager.
        // Por exemplo:
        // System.out.println("\nEscolha seu heroi:");
        // System.out.println("[1] Elfo");
        // System.out.println("[2] Paladino");
        // int escolhaHeroi = InputManager.lerInteiro("Digite sua escolha", 1, 2);
        Heroi heroi;
        // if (escolhaHeroi == 1) {
        //     heroi = new Elfo("Legolas", 100, 15, 12, 1, 0, 8, 17, 32);
        // } else {
        //     heroi = new Paladino("Paladino", 120, 17, 12, 1, 0, 6, 17, 37);
        // }
        // Para a tarefa, vamos manter um herói fixo como estava no seu código original:
        heroi = new Paladino("Paladino Capitão Nascimento", 120, 17, 12, 1, 0, 6, 17, 37);


        System.out.println("\n== Despertar do Herói ==");
        System.out.println("Seu campeão surge das lendas...");
        System.out.println("O herói " + heroi.getNome() + " esta pronto para a gloria!");
        System.out.println(heroi.exibirStatus());
        System.out.println();

        // ---> MUDANÇA MECÂNICA: Usando a interface para criar o gerador.
        GeradorDeFases gerador = new ConstrutorDeCenarioFixo();

        // ---> MUDANÇA MECÂNICA: Gerando as fases através da interface, passando a dificuldade.
        List<Fase> fasesDoJogo = gerador.gerar(3, dificuldadeSelecionada); // <-- MUDANÇA AQUI: Dificuldade passada

        Random rand = new Random();
        // Scanner scanner = new Scanner(System.in); // Removido, substituído por InputManager

        // --- LOOP DE FASES (Lógica e narração 100% preservadas) --
        System.out.println("\n== Inicio da Grande Jornada ==");
        System.out.println();
        System.out.println("O herói, " + heroi.getNome() + ", entra na arena para o teste definitivo: sobreviver a três desafios consecutivos!");
        System.out.println();
        System.out.println("O rugido da multidão é uma muralha de som. Holofotes cegantes varrem a escuridão, na arena o héroi aguarda a ser transportado para seu primeiro desafio, um chão que antes da tecnologia do teleporte foi marcado por incontáveis batalhas. Cada mancha escura é o eco de um guerreiro que tombou exatamente onde ele pisa, agora é a vez dele.");
        System.out.println("O ar é denso, pesado com a promessa de violência. Nos ombros de " + heroi.getNome() + ", não pesa apenas a própria vida, mas o legado de todos que vieram antes. Muitos monstros. Muitos testes de vontade e aço. O caminho para a glória é pavimentado com perigo.");
        System.out.println();
        System.out.println("O teleporte está sendo ajustado.");
        System.out.println();
        System.out.println(heroi.getNome() + " será transportado para o cenário de batalha.");
        System.out.println();
        System.out.println("Que o primeiro oponente se revele!");
        System.out.println();
        System.out.println("Que a sorte esteja ao seu lado!\n");

        for (int i = 0; i < fasesDoJogo.size(); i++) {
            Fase faseAtualInterface = fasesDoJogo.get(i);
            if (!heroi.estaVivo()) break;

            TipoCenario cenarioDaFase = faseAtualInterface.getTipoDeCenario();

            // ---> MUDANÇA MECÂNICA: Cast para acessar a lista de monstros.
            if (!(faseAtualInterface instanceof FaseDeCombate)) continue; // Segurança
            FaseDeCombate faseAtual = (FaseDeCombate) faseAtualInterface;

            // 1. Usa o método iniciar() da fase para exibir o cabeçalho.
            faseAtual.iniciar(heroi);

            // 2. Determina o TipoCenario e chama aplicarEfeitos() para a mensagem temática.
            // (Foi utilizado o .contains() para maior flexibilidade com os nomes
            // dos ambientes)

            faseAtualInterface.getTipoDeCenario().aplicarEfeitos(heroi);

            // O status do herói e o loop de combate permanecem exatamente os mesmos
            System.out.println("\nStatus atual do heroi antes da batalha: " +
                               heroi.exibirStatus());
            System.out.println("\n" + heroi.getNome() + " aperta o punho em sua arma, " +
                               "pronto para o combate!");

            // --- LOOP DE COMBATE POR MONSTRO (Lógica e diálogos 100% preservados) --
            for (Monstro monstro : faseAtual.getMonstros()) {
                // ---> LÓGICA PARA VERIFICAR EVENTOS <--
                if (faseAtual.getEventos() != null) {
                    for (Evento evento : faseAtual.getEventos()) {
                        if (evento.vericarGatilho(heroi, cenarioDaFase)) {
                            evento.executar(heroi);
                        }
                    }
                }
                if (!heroi.estaVivo()) break; // Se o herói morrer em um evento, para o loop

                System.out.println("\n--- ENCONTRO COM NOVO INIMIGO ---");
                System.out.println("Das sombras, surge o temivel " + monstro.getNome() + "!");
                System.out.println(monstro.exibirStatus());
                System.out.println();
                System.out.println(heroi.getNome() + " encara a ameaca e se prepara " +
                                   "para a batalha contra " + monstro.getNome() + "!");
                System.out.println();
                monstro.apresentarDialogoEspecial();

                // Respostas do herói preservadas
                if (monstro.getNome().equals("Edward Cullen")) {
                    System.out.println(heroi.getNome() + ": \"O brilho da vida que eu defendo e muito " +
                                    "mais forte que o seu falso esplendor, criatura!");
                } else if (monstro.getNome().equals("Kaonashi")) {
                    System.out.println(heroi.getNome() + ": \"Nao quero ouro, Kaonashi. So quero vencer!");
                } else if (monstro.getNome().equals("Goblin Guerreiro")) {
                    System.out.println(heroi.getNome() + ": \"Sua sede de batalha sera o seu fim, Goblin!");
                }

                // --- LOOP DE TURNO (Lógica de ataque e d20 preservada, mas usando os novos métodos) --
                int turno = 1;
                while (heroi.estaVivo() && monstro.estaVivo()) {
                    System.out.println("\n--- Turno " + turno + " ---");
                    System.out.println("Vida do Heroi: " + heroi.getpontosdevida() + " " +
                                       "| Vida do Monstro: " + monstro.getpontosdevida());

                    // --- TURNO DO HERÓI --
                    System.out.println(heroi.getNome() + " se move agilmente para atacar!");
                    int rolagemHeroi = rand.nextInt(20) + 1;
                    System.out.println("Heroi rola 1d20: " + rolagemHeroi);

                    if (rolagemHeroi >= monstro.getAgilidade()) {
                        // É um acerto! Agora vamos verificar se é um crítico.
                        if (rolagemHeroi == 20) {
                            // Se for 20, sinalizar ao herói que a ação será um crítico.
                            System.out.println("UM ATAQUE CRITICO! " + heroi.getNome() + " " +
                                               "prepara um golpe devastador!");
                            heroi.setProximoAtaqueCritico(true);
                        }
                        // Chama o método escolherAcao, como requisitado pelo enunciado.
                        // O herói agora sabe qual ação tomar com base no sinal que definimos.
                        AcaoDeCombate acaoHeroi = heroi.escolherAcao(monstro); 
                        if (acaoHeroi != null) {
                            acaoHeroi.executar(heroi, monstro);
                        }
                    } else {
                        System.out.println("O ataque de " + heroi.getNome() + " falha! " +
                                           "O monstro desvia por pouco.");
                    }

                    if (!monstro.estaVivo()) break; // Monstro derrotado, sai do loop de turno

                    // --- TURNO DO MONSTRO --
                    System.out.println(); // Quebra de linha
                    int rolagemMonstro = rand.nextInt(20) + 1;
                    System.out.println(monstro.getNome() + " rola 1d20: " + rolagemMonstro);

                    if (rolagemMonstro >= heroi.getAgilidade()) {
                        if (rolagemMonstro == 20) {
                            System.out.println("UM ATAQUE CRITICO de " + monstro.getNome() + "!");
                                // Converte o tipo para Monstro para acessar o novo método
                                if (monstro instanceof Monstro) {
                                    ((Monstro) monstro).setProximoAtaqueCritico(true); // <-- MUDANÇA AQUI
                                }
                        }
                        // 1. Pega a ação que o monstro escolheu
                        AcaoDeCombate acaoMonstro = monstro.escolherAcao(heroi);

                        // 2. Executa a ação
                        if (acaoMonstro != null) {
                            acaoMonstro.executar(monstro, heroi);
                        }
                    } 
                    
                    else {
                        System.out.println("O ataque de " + monstro.getNome() + " erra! " +
                                           heroi.getNome() + " se esquiva com maestria.");
                    }
                    turno++;
                }

                // --- FIM DO COMBATE (Lógica de loot e equipamento 100% preservada) --
                if (heroi.estaVivo()) {
                    System.out.println("\n" + monstro.getNome() + " tomba, derrotado! " +
                                       "Sua forma se desfaz no ar.");
                    heroi.ganharExperiencia(monstro.getXpConcedido());

                    // --- MENU PÓS-COMBATE ---
                    boolean continuarPosCombate = true;
                    while (continuarPosCombate) {
                        System.out.println("\n--- Menu Pos-Combate ---");
                        System.out.println("[1] Interagir com o Loot (se houver)");
                        System.out.println("[2] Ver Informacoes do Personagem");
                        System.out.println("[3] Desistir do Jogo");
                        System.out.println("[4] Continuar Aventura (Proximo monstro/fase)");

                        int opcaoPosCombate = InputManager.lerInteiro("Escolha sua acao", 1, 4);

                        switch (opcaoPosCombate) {
                            case 1:
                                System.out.println("\nVoce se aproxima do local onde o monstro caiu...");
                                if (rand.nextDouble() < heroi.getSorte()) { // Verifica se a sorte do herói favorece o loot
                                    System.out.println("A sorte de " + heroi.getNome() + " brilha! " +
                                                       "Ha um brilho no chao onde o monstro caiu!");
                                    Item loot = monstro.droparLoot(); // ---> MUDANÇA MECÂNICA: Usando droparLoot()
                                    if (loot != null && loot instanceof Arma) {
                                        Arma armaLargada = (Arma) loot;
                                        System.out.println(" - " + armaLargada.toString());

                                        boolean podeEquiparTipo = false; // Flag para verificar se o tipo de arma é compatível
                                        // 1. Verifica se o herói é do tipo certo para a arma dropada
                                        if (heroi instanceof Paladino && armaLargada.getTipoArma().equals("Espada")) {
                                            podeEquiparTipo = true;
                                        } else if (heroi instanceof Elfo && armaLargada.getTipoArma().equals("Arco")) {
                                            podeEquiparTipo = true;
                                        }

                                        // 2. Se o herói PODE equipar este TIPO de arma, então verifica se VALE A PENA
                                        if (podeEquiparTipo) {
                                            // Lógica para equipar se o slot estiver vazio ou a arma for melhor
                                            if (heroi.getArma() == null || armaLargada.getDano() > heroi.getArma().getDano()) {
                                                try {
                                                    // Tenta equipar, capturando a exceção de nível insuficiente
                                                    heroi.equiparArma(armaLargada);
                                                    System.out.println(heroi.getNome() + " empunhou a " + armaLargada.getNomeCompleto() + "!");
                                                } catch (NivelInsuficienteException e) {
                                                    System.out.println("Voce encontrou uma arma poderosa, mas " + e.getMessage());
                                                }
                                            } else {
                                                System.out.println("A " + armaLargada.getNomeCompleto() + " nao e tao boa quanto a arma atual de " + heroi.getNome() + ". Ele decide nao equipa-la.");
                                            }
                                        } else {
                                            // 3. Se a arma é do tipo errado, informa o jogador e a ignora
                                            System.out.println(heroi.getNome() + " encontra a arma, mas ela nao e do seu estilo. (" + armaLargada.getNomeCompleto() + " ignorada).");
                                        }
                                    } else {
                                        // Se não houve loot de arma
                                        System.out.println("O monstro nao largou nenhuma arma digna. A sorte nem sempre sorri.");
                                    }
                                } else {
                                    System.out.println("A sorte nao sorriu. Nada de valioso encontrado.");
                                }
                                InputManager.esperarEnter("Pressione ENTER para continuar...");
                                break;
                            case 2:
                                System.out.println("\n== Status Atual do Heroi ==");
                                System.out.println(heroi.exibirStatus());
                                InputManager.esperarEnter("Pressione ENTER para voltar ao menu...");
                                break;
                            case 3:
                                System.out.println("\nVoce decide que esta jornada foi o suficiente.");
                                System.out.println("O reino tera que esperar por outro heroi...");
                                // Sai do jogo completamente
                                InputManager.fecharScanner();
                                System.exit(0);
                                break;
                            case 4:
                                continuarPosCombate = false; // Sai do loop do menu pós-combate
                                break;
                        }
                    }
                } else { // Se o herói foi derrotado pelo monstro
                    break; // Sai do loop de monstros para verificar o estado do herói
                }
            }

            // 3. (NOVO) VERIFICA SE A FASE FOI CONCLUÍDA USANDO A INTERFACE
            if (heroi.estaVivo() && faseAtual.isConcluida()) {
                System.out.println("\n--------------------------------------------------------");
                System.out.println("VITORIA NA FASE! " + heroi.getNome() + " superou todos os desafios de " + faseAtual.getTipoDeCenario().getDescricao() + "!");
                System.out.println("--------------------------------------------------------");
            } else if (!heroi.estaVivo()) { // Se o herói morreu na fase
                break; // Sai do loop de fases
            }
        }

        // --- MENSAGEM FINAL (100% preservada) --
        if (heroi.estaVivo()) {
            System.out.println("\n==============================================");
            System.out.println("            VITORIA GRANDIOSA!");
            System.out.println("   O HEROI CONQUISTOU TODOS OS DESAFIOS!");
            System.out.println("==============================================");
            System.out.println("A jornada de " + heroi.getNome() + " chegou ao fim, e a vitoria e sua!");
            System.out.println("O reino esta seguro, e seu nome sera cantado por eras.");
            System.out.println("\nStatus final do heroi, um verdadeiro campeao:");
            System.out.println(heroi.exibirStatus());
        } else { // Verifica de novo caso morra no último golpe ou durante uma fase
            System.out.println("\n==============================================");
            System.out.println("             GAME OVER!");
            System.out.println("O heroi foi esmagado pela forca de seus inimigos.");
            System.out.println("Sua lenda termina aqui...");
            System.out.println("==============================================");
        }
    }

    // --- MÉTODOS AUXILIARES PARA OS MENUS ---
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