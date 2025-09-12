// Main.java

// Bárbara Maria Barreto Fonseca de Cerqueira César
// Paulo Santos do Nascimento Filho

import java.util.ArrayList;
import java.util.Random;
//import java.util.Scanner; // Para a entrada de usuário (decisão de equipar arma) --> implementação futura

// Removido: import Arcos; // A classe Arcos não existe mais, foi substituída por classes de arma concretas.

public class Main {
    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("           Bem-vindo ao RPG MC322!       ");
        System.out.println("          Uma Aventura Épica te Aguarda  ");
        System.out.println("=========================================");
        System.out.println("Prepare-se para uma jornada de coragem, estratégia e sorte!");
        System.out.println("-----------------------------------------");

        // Criação do herói
        System.out.println("\n== Despertar do Herói ==");
        System.out.println("Seu campeão surge das lendas...");
        // O Elfo agora recebe os valores de dano para cada tipo de arco diretamente (Beta, Alpha, Sigma)
        // Os valores 10, 20 e 30 são exemplos de dano para ArcoBeta, ArcoAlpha e ArcoSigma, respectivamente.
        Elfo elfo = new Elfo("Legolas", 100, 15, 12, 1, 0, 10, 20, 30);
        System.out.println("O herói " + elfo.getNome() + " está pronto para a glória!");
        System.out.println(elfo.exibirStatus());
        System.out.println();


        // Gerar Fases
        ArrayList<Fase> fasesDoJogo = ConstrutorDeCenario.gerarFases(3); // Gerar 3 fases

        Random rand = new Random();
        // Scanner scanner = new Scanner(System.in); // Para capturar a decisão do jogador

        ArrayList<Arma> armasDropadas = new ArrayList<>(); // NOVO: lista para guardar armas dropadas (inventário)

        System.out.println("\n== Início da Grande Jornada ==");
        System.out.println("O destino do reino repousa sobre os ombros de " + elfo.getNome() + ".");
        System.out.println("Que a sorte esteja ao seu lado!\n");

        // Loop de Fases
        for (int i = 0; i < fasesDoJogo.size(); i++) {
            Fase faseAtual = fasesDoJogo.get(i);
            String ambiente = faseAtual.getAmbiente();
            int numMonstros = faseAtual.getMonstros().size();

            System.out.println("\n=================================================");
            System.out.println("          INICIANDO FASE " + (i + 1) + ": " + ambiente.toUpperCase() + "         ");
            System.out.println("=================================================");

            // Mensagem de apresentação do desafio, conforme o enunciado
            System.out.println(elfo.getNome() + " entra na " + ambiente + " para enfrentar " + numMonstros + " criaturas temíveis!");

            // Narração adicional para o ambiente
            switch (ambiente) {
                case "Floresta Sussurrante":
                    System.out.println("O ar da Floresta Sussurrante é úmido e denso. Sons misteriosos ecoam entre as árvores antigas, e a luz do sol mal penetra a copa frondosa. Cada passo levanta um tapete de folhas secas, revelando segredos ancestrais.");
                    break;
                case "Cripta Sombria":
                    System.out.println("Um calafrio percorre a espinha de " + elfo.getNome() + " ao adentrar a Cripta Sombria. O cheiro de mofo e terra velha é quase insuportável, e as sombras dançam nas paredes de pedra, guardando horrores esquecidos.");
                    break;
                case "Pico Nevado dos Ventos Uivantes":
                    System.out.println("O vento gélido chicoteia o rosto de " + elfo.getNome() + " no Pico Nevado dos Ventos Uivantes. A paisagem é brutal, dominada por rochas afiadas e nevascas implacáveis. A cada passo, a neve range sob as botas, e a visibilidade é quase nula. É o lar das criaturas mais resilientes e perigosas.");
                    break;
                default:
                    System.out.println("Um novo e misterioso ambiente se revela! Que desafios aguardam " + elfo.getNome() + "?");
                    break;
            }
            System.out.println("\nStatus atual do herói antes da batalha: " + elfo.exibirStatus());
            System.out.println("\n" + elfo.getNome() + " aperta o punho em sua arma, pronto para o combate!");


            // Loop de Combate contra Monstros na Fase
            for (Monstro monstro : faseAtual.getMonstros()) {
                if (elfo.getpontosdevida() <= 0) {
                    System.out.println("\n=========================================");
                    System.out.println("           GAME OVER!                      ");
                    System.out.println("O herói foi derrotado nas profundezas da Fase " + (i + 1) + ": " + ambiente + ".");
                    System.out.println("Sua jornada termina aqui, mas a lenda de " + elfo.getNome() + " será lembrada.");
                    System.out.println("=========================================");
                    // scanner.close(); // Fechar scanner antes de sair
                    return;
                }

                System.out.println("\n--- ENCONTRO COM NOVO INIMIGO ---");
                System.out.println("Das sombras, surge o temível " + monstro.getNome() + "!");
                System.out.println(monstro.exibirStatus());
                System.out.println();
                System.out.println(elfo.getNome() + " encara a ameaça e se prepara para a batalha contra " + monstro.getNome() + "!");
                System.out.println();

                // Chama o diálogo especial do monstro, se houver
                monstro.apresentarDialogoEspecial();

                // Respostas do herói (mantidas aqui pois dependem do elfo)
                if (monstro instanceof Vampiro && monstro.getNome().equals("Edward Cullen")) {
                    System.out.println(elfo.getNome() + ": \"Brilho não vai te salvar do meu arco, Edward! Prepare-se para a derrota! Sua beleza é inútil contra minha flecha!\"");
                    System.out.println();
                } else if (monstro instanceof Espirito && monstro.getNome().equals("Kaonashi")) {
                    System.out.println(elfo.getNome() + ": \"Não quero ouro, Kaonashi. Só quero passar por você e vencer! Seu vazio não me afetará!\"");
                    System.out.println();
                } else if (monstro instanceof Goblin && monstro.getNome().equals("Goblin Guerreiro")) {
                    System.out.println(elfo.getNome() + ": \"Veremos se sua clava é páreo para minha mira, Goblin! Seu grito de guerra é apenas um sussurro para o vento!\"");
                    System.out.println();
                } 

                // Loop de combate contínuo até que um seja derrotado
                int turno = 1;
                while (elfo.getpontosdevida() > 0 && monstro.getpontosdevida() > 0) {
                    System.out.println("\n--- Turno " + turno + " ---");
                    System.out.println("Vida do Herói: " + elfo.getpontosdevida() + " | Vida do Monstro: " + monstro.getpontosdevida());

                    // Herói ataca monstro
                    System.out.println(elfo.getNome() + " se move agilmente para atacar!");
                    int rolagemHeroi = rand.nextInt(20) + 1; // 1d20
                    System.out.println("Herói rola 1d20: " + rolagemHeroi);
                    if (rolagemHeroi >= monstro.getAgilidade()) { // Adiciona agilidade do herói no acerto
                    if (rolagemHeroi == 20) {
                        System.out.println("UM ATAQUE CRÍTICO! A flecha de " + elfo.getNome() + " encontra um ponto vital!");
                        elfo.usarHabilidadeEspecial(monstro); // Habilidade especial em crítico
                    } else {
                        elfo.atacar(monstro);
                        System.out.println("O golpe de " + elfo.getNome() + " atinge o alvo com precisão.");
                    }
                    } else {
                        System.out.println("O ataque de " + elfo.getNome() + " falha! O monstro desvia por pouco.");
                    }

                    // Verifica se o monstro foi derrotado após o ataque do herói
                    if (monstro.getpontosdevida() <= 0) {
                        System.out.println("\n" + monstro.getNome() + " tomba, derrotado! Sua forma se desfaz no ar.");
                        elfo.ganharExperiencia(monstro.getXpConcedido()); // XP por derrotar o monstro
                        // Teste de sorte para largar arma
                    if (rand.nextDouble() < elfo.getSorte()) { // Quanto maior a sorte, maior a chance
                        System.out.println("A sorte de " + elfo.getNome() + " brilha! Há um brilho no chão onde o monstro caiu!");
                        Arma armaLargada = monstro.largaArma();
                    if (armaLargada != null) {
                        System.out.println("Uma arma foi largada! Uma " + armaLargada.toString() + " aguarda!");
                        armasDropadas.add(armaLargada); // Guarda a arma no inventário 
                        // System.out.println("A arma foi guardada no inventário [para uso futuro]."); // Removido, pois a próxima lógica já infoma o que acontece.

                        // --- LÓGICA PARA EQUIPAR A ARMA ---
                        if (elfo.getArma() == null) {
                            System.out.println(elfo.getNome() + " não tinha nenhuma arma equipada. Ele rapidamente empunha a " + armaLargada.getNomeCompleto() + "!");
                            elfo.equiparArma(armaLargada);
                        } else if (armaLargada.getDano() > elfo.getArma().getDano() && elfo.getNivel() >= armaLargada.getMinNivel()) {
                            System.out.println("Uma arma superior! " + elfo.getNome() + " troca sua " + elfo.getArma().getNomeCompleto() + " pela poderosa " + armaLargada.getNomeCompleto() + "!");
                            elfo.equiparArma(armaLargada);
                        } else {
                            System.out.println("A " + armaLargada.getNomeCompleto() + " não é tão boa quanto a arma atual de " + elfo.getNome() + " (" + elfo.getArma().getNomeCompleto() + "). Ele decide não equipá-la.");
                                }
                        // --- FIM DA LÓGICA PARA EQUIPAR A ARMA ---

                        } else {
                            System.out.println("O monstro não largou nenhuma arma digna. A sorte nem sempre sorri.");
                        }
                        } else {
                            System.out.println("A sorte não favoreceu desta vez. O monstro não largou nada valioso.");
                        }
                        break; // Monstro derrotado, sai do loop de combate do turno
                    }

                    // Monstro ataca herói (se estiver vivo)
                    System.out.println(); // Quebra de linha para clareza
                    if (elfo.getpontosdevida() > 0) { // Verifica se herói ainda está vivo antes do contra-ataque
                        System.out.println(monstro.getNome() + " contra-ataca com ferocidade!");
                        int rolagemMonstro = rand.nextInt(20) + 1; // 1d20
                        System.out.println(monstro.getNome() + " rola 1d20: " + rolagemMonstro);
                        if (rolagemMonstro >= elfo.getAgilidade()) { // Adiciona agilidade do monstro no acerto
                        if (rolagemMonstro == 20) {
                            System.out.println("UM ATAQUE CRÍTICO de " + monstro.getNome() + "! O golpe ressoa com força total!");
                        }
                            monstro.atacar(elfo); // Chama o método correto de cada monstro
                            System.out.println("O ataque de " + monstro.getNome() + " atinge " + elfo.getNome() + "!");
                        } else {
                            System.out.println("O ataque de " + monstro.getNome() + " erra! " + elfo.getNome() + " se esquiva com maestria.");
                        }
                    }

                    // Verifica se o herói sobreviveu ao ataque do monstro
                    if (elfo.getpontosdevida() <= 0) {
                        System.out.println("\n=========================================");
                        System.out.println("           GAME OVER!                      ");
                        System.out.println("O herói foi esmagado pela força de " + monstro.getNome() + ".");
                        System.out.println("Sua lenda termina aqui...");
                        System.out.println("=========================================");
                        // scanner.close(); // Fechar scanner antes de sair
                        return;
                    }

                    // Ganho de XP por turno (mantido, pois o enunciado pedia)
                    // System.out.println(elfo.getNome() + " sente um leve aumento de experiência mesmo sem derrotar o monstro."); // Exemplo de mensagem, descomente se quiser
                    elfo.ganharExperiencia(monstro.getXpConcedido() / 2); // Menos XP por turno, mais por derrota
                    turno++;
                }
            }
        }

        // Mensagem de vitória se todas as fases forem completadas
        if (elfo.getpontosdevida() > 0) {
            System.out.println("\n=================================================");
            System.out.println("           VITÓRIA GRANDIOSA!                      ");
            System.out.println("           O HERÓI CONQUISTOU TODOS OS DESAFIOS!   ");
            System.out.println("=================================================");
            System.out.println("A jornada de " + elfo.getNome() + " chegou ao fim, e a vitória é sua!");
            System.out.println("O reino está seguro, e seu nome será cantado por eras.");
            System.out.println("\nStatus final do herói, um verdadeiro campeão:");
            System.out.println(elfo.exibirStatus());
        }

        // scanner.close(); // Fechar scanner
    }
}