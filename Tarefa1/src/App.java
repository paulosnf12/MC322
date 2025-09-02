import java.util.Random;

public class App {
    public static void main(String[] args) {
        
                        // Criação dos Personagens (e Monstros)


        // Criação do herói (Elfo ou Paladino)

        System.out.println();

        Elfo elfo = new Elfo("Legolas", 100, 15, 12, 1, 0, new Arcos(10, 20, 30));
        System.out.println("Status inicial do herói:");
        System.out.println(elfo.exibirStatus());
        System.out.println();


        /*              

        Paladino aragorn = new Paladino("Aragorn", 120, 18, 1, 0, new Espada(10, 20, 30));
        System.out.println("Status inicial do herói:");
        System.out.println(aragorn.exibirStatus());
        System.out.println();
        
        */


        
        // Criação dos monstros (Goblin, Vampiro ou Kaonashi (Sem Rosto))

        Goblin goblin1 = new Goblin("Goblin Guerreiro", 80, 10, 8, 50, "Clava", 5, 0.3);
        System.out.println("Status inicial do monstro 1:");
        System.out.println(goblin1.exibirStatus());
        System.out.println();

        Goblin goblin2 = new Goblin("Goblin Ladrão", 70, 8, 10, 40, "Adaga", 4, 0.5);
        System.out.println("Status inicial do monstro 2:");
        System.out.println(goblin2.exibirStatus());
        System.out.println();

        Goblin goblin3 = new Goblin("Goblin Chefe", 100, 12, 6, 70, "Espada Curta", 6, 0.2);
        System.out.println("Status inicial do monstro 3:");
        System.out.println(goblin3.exibirStatus());
        System.out.println();

        // Criação array para monstros

        Goblin[] monstros = {goblin1, goblin2, goblin3};


        // Mensagem Inicial

        System.out.println("O herói entra na masmorra para enfrentar três desafios!");

        System.out.println();



        System.out.println("**mensagem de contextualização do cenário**");

        System.out.println();



        Random rand = new Random();

        // Loop de batalhas (cada monstro)
        for (int i = 0; i < monstros.length; i++) {
            Goblin monstro = monstros[i];
            System.out.println("Batalha " + (i + 1) + ": Surge o " + monstro.getNome() + "!");
            System.out.println(monstro.exibirStatus());
            System.out.println();

            // Cada batalha tem 3 turnos
            for (int turno = 1; turno <= 3; turno++) {
                System.out.println("Turno " + turno + ":");

                // Herói ataca monstro
                int rolagemHeroi = rand.nextInt(20) + 1; // 1d20
                System.out.println("Herói rola 1d20: " + rolagemHeroi);
                if (rolagemHeroi >= monstro.getAgilidade()) {
                    int dano = elfo.getDanoArco() + elfo.getForca();
                    if (rolagemHeroi == 20) {
                        dano *= 2;
                        System.out.println("Ataque crítico do herói!");
                    }
                    monstro.receberDano(dano);
                    System.out.println("Elfo atacou com arco " + elfo.getTipoDeArco() + " causando " + dano + " de dano!");
                } else {
                    System.out.println("O ataque do herói errou!");
                }

                // Monstro ataca herói (se estiver vivo)
                if (monstro.getpontosdevida() > 0) {
                    int rolagemMonstro = rand.nextInt(20) + 1; // 1d20
                    System.out.println("Monstro rola 1d20: " + rolagemMonstro);
                    if (rolagemMonstro >= elfo.getAgilidade()) {
                        int dano = monstro.getForca() + monstro.danoArma;
                        if (rolagemMonstro == 20) {
                            dano *= 2;
                            System.out.println("Ataque crítico do monstro!");
                        }
                        elfo.receberDano(dano);
                        System.out.println(monstro.getNome() + " atacou com " + monstro.tipoDeArma + " causando " + dano + " de dano!");
                    } else {
                        System.out.println("O ataque do monstro errou!");
                    }
                } else {
                    System.out.println(monstro.getNome() + " foi derrotado antes de atacar!");
                }

                // Verifica se o herói sobreviveu
                if (elfo.getpontosdevida() <= 0) {
                    System.out.println("\nGAME OVER! O herói foi derrotado.");
                    return;
                }

                // Exibe status ao final do turno
                System.out.println("\nStatus do herói:");
                System.out.println(elfo.exibirStatus());
                System.out.println();
                System.out.println("Status do monstro:");
                System.out.println(monstro.exibirStatus());
                System.out.println("-----------------------------------\n");

                // Verifica se o monstro foi derrotado
                if (monstro.getpontosdevida() <= 0) {
                    System.out.println(monstro.getNome() + " foi derrotado!");
                    break;
                }
            }
        }

        // Mensagem de vitória
        if (elfo.getpontosdevida() > 0) {
            System.out.println("O herói sobreviveu aos três desafios! Vitória!");
        }
    }
}