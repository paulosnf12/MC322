// Main.java

// Bárbara Maria Barreto Fonseca de Cerqueira César
// Paulo Santos do Nascimento Filho

import java.util.ArrayList;
import java.util.Random;
//import java.util.Scanner; // Para a entrada de usuário (decisão de equipar arma) --> implementação futura

public class Main {
    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("        Bem-vindo ao RPG MC322!        ");
        System.out.println("=========================================");
        System.out.println("Prepare-se para uma aventura épica!");
        System.out.println("-----------------------------------------");

        // Criação do herói
        System.out.println("\nCriando seu herói...");
        // O Elfo terá uma configuração de Arco para definir os danos de seus arcos por nível
        Elfo elfo = new Elfo("Legolas", 100, 15, 12, 1, 0, new Arcos(10, 20, 30));
        System.out.println("Herói criado:");
        System.out.println(elfo.exibirStatus());
        System.out.println();


        // Gerar Fases
        ArrayList<Fase> fasesDoJogo = ConstrutorDeCenario.gerarFases(3); // Gerar 3 fases

        Random rand = new Random();
        // Scanner scanner = new Scanner(System.in); // Para capturar a decisão do jogador

        ArrayList<Arma> armasDropadas = new ArrayList<>(); // NOVO: lista para guardar armas dropadas (inventário)

        // Loop de Fases
        for (int i = 0; i < fasesDoJogo.size(); i++) {
            Fase faseAtual = fasesDoJogo.get(i);
            System.out.println("\n=========================================");
            System.out.println("   INICIANDO FASE " + (i + 1) + ": " + faseAtual.getAmbiente());
            System.out.println("   Monstros nesta fase: " + faseAtual.getMonstros().size());
            System.out.println("=========================================");
            System.out.println(elfo.getNome() + " entra no " + faseAtual.getAmbiente() + " para enfrentar seus desafios!");
            System.out.println("Status atual do herói: " + elfo.exibirStatus());

            // Loop de Combate contra Monstros na Fase
            for (Monstro monstro : faseAtual.getMonstros()) {
                if (elfo.getpontosdevida() <= 0) {
                    System.out.println("\nGAME OVER! O herói foi derrotado na Fase " + (i + 1) + ".");
                    // scanner.close(); // Fechar scanner antes de sair
                    return;
                }

                System.out.println("\n--- ENCONTRO COM NOVO MONSTRO ---");
                System.out.println("Surge o " + monstro.getNome() + "!");
                System.out.println(monstro.exibirStatus());
                System.out.println();
                System.out.println(elfo.getNome() + " se prepara para a batalha contra " + monstro.getNome() + "!");

                // Fala especial do Edward Cullen
                if (monstro instanceof Vampiro && monstro.getNome().equals("Edward Cullen")) {
                    System.out.println("Edward Cullen: \"Eu sou um monstro, Bella! Minha pele brilha. Essa é a pele de um assassino!\"");
                    System.out.println();
                    // Resposta do herói
                    System.out.println(elfo.getNome() + ": \"Brilho não vai te salvar do meu arco, Edward! Prepare-se para a derrota!\"");
                    System.out.println();
                }

                // Fala especial do Kaonashi
                if (monstro instanceof Espirito && monstro.getNome().equals("Kaonashi")) {
                    System.out.println("Kaonashi: \"Você... quer?...\" (A voz que sai dele é um eco distorcido de suas vítimas. Ao mesmo tempo, ele estende uma mão trêmula, oferecendo pepitas de ouro que brilham com uma luz doentia. O gesto é uma armadilha, e sua fome insaciável parece entortar os traços de sua máscara.)");
                    System.out.println();
                    // Resposta do herói
                    System.out.println(elfo.getNome() + ": \"Não quero ouro, Kaonashi. Só quero passar por você e vencer!\"");
                    System.out.println();
                }
                // Fala especial do Goblin Guerreiro
                if (monstro instanceof Goblin && monstro.getNome().equals("Goblin Guerreiro")) {
                    System.out.println("Goblin Guerreiro: \"Hehehe! Você não vai passar por mim tão fácil, herói! Minha clava está sedenta por batalha!\"");
                    System.out.println();
                    // Resposta do herói
                    System.out.println(elfo.getNome() + ": \"Veremos se sua clava é páreo para minha mira, Goblin!\"");
                    System.out.println();
                }  

                // Loop de combate contínuo até que um seja derrotado
                int turno = 1;
                while (elfo.getpontosdevida() > 0 && monstro.getpontosdevida() > 0) {
                    System.out.println("\nTurno " + turno + ":");
                    System.out.println("  Vida do Herói: " + elfo.getpontosdevida() + " | Vida do Monstro: " + monstro.getpontosdevida());

                    // Herói ataca monstro
                    int rolagemHeroi = rand.nextInt(20) + 1; // 1d20
                    System.out.println("Herói rola 1d20: " + rolagemHeroi);
                    if (rolagemHeroi >= monstro.getAgilidade()) { // Adiciona agilidade do herói no acerto
                    if (rolagemHeroi == 20) {
                        System.out.println("ATAQUE CRÍTICO do herói!");
                        elfo.usarHabilidadeEspecial(monstro); // Habilidade especial em crítico
                    } else {
                        elfo.atacar(monstro);
                           }
                    } else {
                        System.out.println("O ataque do herói errou!");
                    }

                    // Verifica se o monstro foi derrotado após o ataque do herói
                    if (monstro.getpontosdevida() <= 0) {
                        System.out.println("\n" + monstro.getNome() + " foi derrotado!");
                        elfo.ganharExperiencia(monstro.getXpConcedido()); // XP por derrotar o monstro
                        // Teste de sorte para largar arma
                    if (rand.nextDouble() < elfo.getSorte()) { // Quanto maior a sorte, maior a chance
                        System.out.println("A sorte de " + elfo.getNome() + " brilhou! O monstro pode largar uma arma!");
                        Arma armaLargada = monstro.largaArma();
                    if (armaLargada != null) {
                        String tipo = "";
                        String nome = armaLargada.getClass().getSimpleName();
                        if (armaLargada instanceof Adaga) tipo = ((Adaga)armaLargada).getTipoArma();
                        else if (armaLargada instanceof Garras) tipo = ((Garras)armaLargada).getTipoArma();
                        else if (armaLargada instanceof Cajado) tipo = ((Cajado)armaLargada).getTipoArma();

                        System.out.println("Uma arma foi largada! [" + tipo + "] Nome: " + nome + ", Dano: " + armaLargada.getDano() + ", Nível Mínimo: " + armaLargada.getMinNivel());
                        armasDropadas.add(armaLargada);
                        System.out.println("A arma foi guardada no inventário.");
                    } else {
                        System.out.println("O monstro não largou nenhuma arma.");
                    }
                    } 
                    
                    /* possível implementação futura com scanner para decisão de equipar arma largada
                     
                    if (elfo.getArma() == null || armaLargada.getDano() > elfo.getArma().getDano()) {
                                            System.out.print("Deseja equipar esta arma? (S/N): ");
                                            String escolha = scanner.nextLine().trim().toUpperCase();
                                        if (escolha.equals("S")) {
                                            elfo.equiparArma(armaLargada);
                                        } else {
                                            System.out.println("O herói decidiu não equipar a arma largada.");
                                                }
                                        } else {
                                            System.out.println("O herói já tem uma arma melhor equipada ou não precisa desta.");
                                                }
                                                }
                    }

                     */
                    
                    else {
                        System.out.println("A sorte não favoreceu... " + monstro.getNome() + " não largou nenhuma arma extra.");
                           }
                        break; // Monstro derrotado, sai do loop de combate do turno
                    }

                    // Monstro ataca herói (se estiver vivo)
                    System.out.println(); // Quebra de linha para clareza
                    if (elfo.getpontosdevida() > 0) { // Verifica se herói ainda está vivo antes do contra-ataque
                        int rolagemMonstro = rand.nextInt(20) + 1; // 1d20
                        System.out.println("Monstro rola 1d20: " + rolagemMonstro);
                    if (rolagemMonstro >= elfo.getAgilidade()) { // Adiciona agilidade do monstro no acerto
                    if (rolagemMonstro == 20) {
                        System.out.println("ATAQUE CRÍTICO do monstro!");
                            }
                        monstro.atacar(elfo); // Chama o método correto de cada monstro
                    } else {
                        System.out.println("O ataque do monstro errou!");
                           }
                    }

                    // Verifica se o herói sobreviveu ao ataque do monstro
                    if (elfo.getpontosdevida() <= 0) {
                        System.out.println("\nGAME OVER! O herói foi derrotado.");
                        // scanner.close(); // Fechar scanner antes de sair
                        return;
                    }

                    // Ganho de XP por turno (mantido, pois o enunciado pedia)
                    elfo.ganharExperiencia(monstro.getXpConcedido() / 2); // Menos XP por turno, mais por derrota
                    turno++;
                }
            }
        }

        // Mensagem de vitória se todas as fases forem completadas
        if (elfo.getpontosdevida() > 0) {
            System.out.println("\n=========================================");
            System.out.println("    PARABÉNS! O HERÓI SOBREVIVEU AOS   ");
            System.out.println("         TRÊS DESAFIOS! VITÓRIA!         ");
            System.out.println("=========================================");
            System.out.println("Status final do herói:");
            System.out.println(elfo.exibirStatus());
        }

        // scanner.close(); // Fechar scanner
    }
}