// Main.java
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
// import java.util.Scanner; // Mantido comentado como no original

public class Main {
    public static void main(String[] args) {
        // --- SETUP INICIAL (Idêntico ao original, com a criação de objetos atualizada) ---
        System.out.println("==============================================");
        System.out.println("         Bem-vindo ao RPG MC322!");
        System.out.println("     Uma Aventura Épica te Aguarda");
        System.out.println("==============================================");
        System.out.println("Prepare-se para uma jornada de coragem, estratégia e sorte!");
        System.out.println("----------------------------------------------");

        // ---> MUDANÇA MECÂNICA: Usando a interface para criar o gerador.
        GeradorDeFases gerador = new ConstrutorDeCenarioFixo();

        // DISCLAIMER:

         // Os últimos 3 argumentos são os danos base das armas! (Recomenda-se manter os valores sugeridos para um melhor balanceamento.)

        // Comentar/Descomentar caso queira testar/jogar com o Elfo, Heroi padrão sendo testado agora é o Paladino.

        // Jogar com o Elfo:
        //Heroi heroi = new Elfo("Legolas", 100, 15, 12, 1, 0, 8, 17, 32);

        // Jogar com o Paladino:
        Heroi heroi = new Paladino("Paladino", 120, 17, 12, 1, 0, 6, 17, 37);
        
        System.out.println("\n== Despertar do Herói ==");
        System.out.println("Seu campeão surge das lendas...");
        System.out.println("O herói " + heroi.getNome() + " está pronto para a glória!");
        System.out.println(heroi.exibirStatus());
        System.out.println();

        // ---> MUDANÇA MECÂNICA: Gerando as fases através da interface.
        List<InterfaceFase> fasesDoJogo = gerador.gerar(3);
        Random rand = new Random();
        // Scanner scanner = new Scanner(System.in);
        ArrayList<Item> armasDropadas = new ArrayList<>();

        // --- LOOP DE FASES (Lógica e narração 100% preservadas) ---
        System.out.println("\n== Início da Grande Jornada ==");
        System.out.println("O destino do reino repousa sobre os ombros de " + heroi.getNome() + ".");
        System.out.println("Que a sorte esteja ao seu lado!\n");

        for (int i = 0; i < fasesDoJogo.size(); i++) {
            InterfaceFase faseAtualInterface = fasesDoJogo.get(i);
            
            if (!heroi.estaVivo()) break;

            // ---> MUDANÇA MECÂNICA: Usando o método da interface para obter dados.
            String ambiente = faseAtualInterface.getTipoDeCenario();
            
            // ---> MUDANÇA MECÂNICA: Cast para acessar a lista de monstros.
            if (!(faseAtualInterface instanceof Fase)) continue; // Segurança
            Fase faseAtual = (Fase) faseAtualInterface;
            
            // 1. Usa o método iniciar() da fase para exibir o cabeçalho.
            faseAtual.iniciar(heroi);

            // 2. Determina o TipoCenario e chama aplicarEfeitos() para a mensagem temática.

            //    (Foi utilizado o .contains() para maior flexibilidade com os nomes dos ambientes)
            if (ambiente.contains("Floresta")) {
                TipoCenario.FLORESTA.aplicarEfeitos(heroi);
            } else if (ambiente.contains("Cripta")) {
                TipoCenario.CAVERNA.aplicarEfeitos(heroi);
            } else if (ambiente.contains("Pico")) {
                TipoCenario.CASTELO.aplicarEfeitos(heroi); // Corresponde à constante que ajustamos
            }

            // O status do herói e o loop de combate permanecem exatamente os mesmos
            System.out.println("\nStatus atual do herói antes da batalha: " + heroi.exibirStatus());
            System.out.println("\n" + heroi.getNome() + " aperta o punho em sua arma, pronto para o combate!");

            /* 

            int numMonstros = faseAtual.getMonstros().size();

            System.out.println("==============================================");
            System.out.println("          INICIANDO FASE " + (i + 1) + ": " + ambiente.toUpperCase());
            System.out.println("==============================================");
            System.out.println(heroi.getNome() + " entra na " + ambiente + " para enfrentar " + numMonstros + " criaturas temíveis!");

            // Narração do ambiente preservada
            switch (ambiente.split(" ")[0]) {
                case "Floresta":
                    System.out.println("O ar da Floresta Sussurrante é úmido e denso. Sons misteriosos ecoam entre as árvores antigas...");
                    break;
                case "Cripta":
                    System.out.println("Um calafrio percorre a espinha de " + heroi.getNome() + " ao adentrar a Cripta Sombria...");
                    break;
                case "Pico":
                    System.out.println("O vento gélido chicoteia o rosto de " + heroi.getNome() + " no Pico Nevado dos Ventos Uivantes...");
                    break;
                default:
                    System.out.println("Um novo e misterioso ambiente se revela! Que desafios aguardam " + heroi.getNome() + "?");
                    break;
            }

            System.out.println("\nStatus atual do herói antes da batalha: " + heroi.exibirStatus());
            System.out.println("\n" + heroi.getNome() + " aperta o punho em sua arma, pronto para o combate!");

            */

            // --- LOOP DE COMBATE POR MONSTRO (Lógica e diálogos 100% preservados) ---
            for (Monstro monstro : faseAtual.getMonstros()) {

                // ---> LÓGICA PARA VERIFICAR EVENTOS <---
                if (faseAtual.getEventos() != null) {
                    for (Evento evento : faseAtual.getEventos()) {
                        if (evento.vericarGatilho(heroi, ambiente)) {
                            evento.executar(heroi);
                        }
                    }
                }

                if (!heroi.estaVivo()) {
                    System.out.println("\n==============================================");
                    System.out.println("                 GAME OVER!");
                    System.out.println("O herói foi derrotado nas profundezas da Fase " + (i + 1) + ": " + ambiente + ".");
                    return;
                }

                System.out.println("\n--- ENCONTRO COM NOVO INIMIGO ---");
                System.out.println("Das sombras, surge o temível " + monstro.getNome() + "!");
                System.out.println(monstro.exibirStatus());
                System.out.println();
                System.out.println(heroi.getNome() + " encara a ameaça e se prepara para a batalha contra " + monstro.getNome() + "!");
                System.out.println();

                monstro.apresentarDialogoEspecial();
                // Respostas do herói preservadas
                if (monstro.getNome().equals("Edward Cullen")) { System.out.println(heroi.getNome() + ": \"O brilho da vida que eu defendo é muito mais forte que o seu falso esplendor, criatura!\""); } 
                else if (monstro.getNome().equals("Kaonashi")) { System.out.println(heroi.getNome() + ": \"Não quero ouro, Kaonashi. Só quero vencer!\""); } 
                else if (monstro.getNome().equals("Goblin Guerreiro")) { System.out.println(heroi.getNome() + ": \"Sua sede de batalha será o seu fim, Goblin!\""); }

                // --- LOOP DE TURNO (Lógica de ataque e d20 preservada, mas usando os novos métodos) ---
                int turno = 1;
                while (heroi.estaVivo() && monstro.estaVivo()) {
                    System.out.println("\n--- Turno " + turno + " ---");
                    System.out.println("Vida do Herói: " + heroi.getpontosdevida() + " | Vida do Monstro: " + monstro.getpontosdevida());

                    // --- TURNO DO HERÓI ---
                    System.out.println(heroi.getNome() + " se move agilmente para atacar!");
                    int rolagemHeroi = rand.nextInt(20) + 1;
                    System.out.println("Herói rola 1d20: " + rolagemHeroi);

                    if (rolagemHeroi >= monstro.getAgilidade()) {
                    // É um acerto! Agora vamos verificar se é um crítico.
                    
                    if (rolagemHeroi == 20) {
                        // Se for 20, sinalizar ao herói que a ação será um crítico.
                        System.out.println("UM ATAQUE CRÍTICO! " + heroi.getNome() + " prepara um golpe devastador!");
                        heroi.setProximoAtaqueCritico(true);
                    }
                    
                    // Chama o método escolherAcao, como requisitado pelo enunciado.

                    // O herói agora sabe qual ação tomar com base no sinal que definimos.
                    heroi.escolherAcao(monstro);

                } else {
                    System.out.println("O ataque de " + heroi.getNome() + " falha! O monstro desvia por pouco.");
                }

                    if (!monstro.estaVivo()) break;

                    // --- TURNO DO MONSTRO ---
                    System.out.println(); // Quebra de linha
                    int rolagemMonstro = rand.nextInt(20) + 1;
                    System.out.println(monstro.getNome() + " rola 1d20: " + rolagemMonstro);
                    
                    if (rolagemMonstro >= heroi.getAgilidade()) {
                        if (rolagemMonstro == 20) System.out.println("UM ATAQUE CRÍTICO de " + monstro.getNome() + "!");
                        monstro.escolherAcao(heroi);
                    } else {
                        System.out.println("O ataque de " + monstro.getNome() + " erra! " + heroi.getNome() + " se esquiva com maestria.");
                    }
                
                    turno++;
                }

                // --- FIM DO COMBATE (Lógica de loot e equipamento 100% preservada) ---
                if (heroi.estaVivo()) {
                    System.out.println("\n" + monstro.getNome() + " tomba, derrotado! Sua forma se desfaz no ar.");
                    heroi.ganharExperiencia(monstro.getXpConcedido());

                    if (rand.nextDouble() < heroi.getSorte()) {
                        System.out.println("A sorte de " + heroi.getNome() + " brilha! Há um brilho no chão onde o monstro caiu!");
                        
                        // ---> MUDANÇA MECÂNICA: Usando droparLoot() que retorna um Item.
                        Item loot = monstro.droparLoot();
                        if (loot != null && loot instanceof Arma) {
                        Arma armaLargada = (Arma) loot;
                        System.out.println(" - " + armaLargada.toString());

                        boolean podeEquipar = false;

                        // 1. Verifica se o herói é do tipo certo para a arma dropada
                        if (heroi instanceof Paladino && armaLargada.getTipoArma().equals("Espada")) {
                            podeEquipar = true;
                        } else if (heroi instanceof Elfo && armaLargada.getTipoArma().equals("Arco")) {
                            podeEquipar = true;
                        }

                        // 2. Se o herói PODE equipar este TIPO de arma, então verifica se VALE A PENA
                        if (podeEquipar) {
                            boolean equipou = false; // Flag para saber se a arma foi equipada

                            // Lógica para equipar se o slot estiver vazio
                            if (heroi.getArma() == null) {
                                System.out.println(heroi.getNome() + " não tinha nenhuma arma equipada. Ele rapidamente empunha a " + armaLargada.getNomeCompleto() + "!");
                                heroi.equiparArma(armaLargada);
                                equipou = true;
                            
                                    // Lógica para comparar com a arma atual
                                    } else if (armaLargada.getDano() > heroi.getArma().getDano() && heroi.getNivel() >= armaLargada.getMinNivel()) {
                                        System.out.println("Uma arma superior! " + heroi.getNome() + " troca sua " + heroi.getArma().getNomeCompleto() + " pela poderosa " + armaLargada.getNomeCompleto() + "!");
                                        heroi.equiparArma(armaLargada);
                                        equipou = true;
                                    }

                                    // Se a arma era do tipo certo, mas não era melhor, informa o jogador
                                    if (!equipou) {
                                        System.out.println("A " + armaLargada.getNomeCompleto() + " não é tão boa quanto a arma atual de " + heroi.getNome() + ". Ele decide não equipá-la.");
                                    }

                                    } else {
                                        // 3. Se a arma é do tipo errado, informa o jogador e a ignora
                                        System.out.println(heroi.getNome() + " encontra a arma, mas ela não é do seu estilo. (" + armaLargada.getNomeCompleto() + " ignorada).");
                                    }

                                } else if (loot != null) {
                                    // Caso o loot seja um Item mas não uma Arma (preparado para o futuro)
                                    System.out.println(heroi.getNome() + " encontrou um item: " + loot.getNomeCompleto());

                                } else {
                                    // Se não houve loot de arma
                                    System.out.println("O monstro não largou nenhuma arma digna. A sorte nem sempre sorri.");
                                }             
                            } 
                        }
                    }

            // 3. (NOVO) VERIFICA SE A FASE FOI CONCLUÍDA USANDO A INTERFACE
            if (heroi.estaVivo() && faseAtual.isConcluida()) {
                System.out.println("\n--------------------------------------------------------");
                System.out.println("VITÓRIA NA FASE! " + heroi.getNome() + " superou todos os desafios de " + ambiente + "!");
                System.out.println("--------------------------------------------------------");
            }

        }

        // --- MENSAGEM FINAL (100% preservada) ---
        if (heroi.estaVivo()) {
            System.out.println("\n==============================================");
            System.out.println("               VITÓRIA GRANDIOSA!");
            System.out.println("        O HERÓI CONQUISTOU TODOS OS DESAFIOS!");
            System.out.println("==============================================");
            System.out.println("A jornada de " + heroi.getNome() + " chegou ao fim, e a vitória é sua!");
            System.out.println("O reino está seguro, e seu nome será cantado por eras.");
            System.out.println("\nStatus final do herói, um verdadeiro campeão:");
            System.out.println(heroi.exibirStatus());
        } else if (!heroi.estaVivo()) { // Verifica de novo caso morra no último golpe
             System.out.println("\n==============================================");
             System.out.println("                 GAME OVER!");
             System.out.println("O herói foi esmagado pela força de seus inimigos.");
             System.out.println("Sua lenda termina aqui...");
        }
    }
}