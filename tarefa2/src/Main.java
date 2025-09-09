// Bárbara Maria Barreto Fonseca de Cerqueira César 
// Paulo Santos do Nacimento Filho

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        
        // Criação do herói
        System.out.println();

        Elfo elfo = new Elfo("Legolas", 100, 15, 12, 1, 0, new Arcos(10, 20, 30));
        System.out.println("Status inicial do herói:");
        System.out.println(elfo.exibirStatus());
        System.out.println();

        /* Em nosso código foram criadas classes a mais, personagens a mais etc para uma possível implementação futura.
           Por isso, nem tudo que foi criado está sendo utilizado. 
           Como pede no enunciado, criamos o cenário para um dos heróis e para três monstros.
           Deixamos o que não está sendo usado comentado.
        */
        

        /*              

        Paladino aragorn = new Paladino("Aragorn", 120, 18, 1, 0, new Espada(10, 20, 30));
        System.out.println("Status inicial do herói:");
        System.out.println(aragorn.exibirStatus());
        System.out.println();
        
        */
        
        // Criação dos monstros

        Vampiro edward = new Vampiro("Edward Cullen", 120, 10, 9, 50, 80);
        System.out.println("Status inicial do vampiro:");
        System.out.println(edward.exibirStatus());
        System.out.println();  
        
        Espirito kaonashi = new Espirito("Kaonashi", 100, 15, 5, 50, 70);
        System.out.println("Status inicial do espírito:");
        System.out.println(kaonashi.exibirStatus());
        System.out.println();
    
        Goblin goblin1 = new Goblin("Goblin Guerreiro", 80, 10, 8, 50, "Clava", 5, 0.3);
        System.out.println("Status inicial do goblin:");
        System.out.println(goblin1.exibirStatus());
        System.out.println();

        /*Goblin goblin2 = new Goblin("Goblin Ladrão", 70, 8, 10, 40, "Adaga", 4, 0.5);
        System.out.println("Status inicial do goblin:");
        System.out.println(goblin2.exibirStatus());
        System.out.println();

        Goblin goblin3 = new Goblin("Goblin Chefe", 100, 12, 6, 70, "Espada Curta", 6, 0.2);
        System.out.println("Status inicial do goblin:");
        System.out.println(goblin3.exibirStatus());
        System.out.println();*/

        // Criação array para monstros

       Monstro[] monstros = {edward, kaonashi, goblin1};


        // Mensagem Inicial

            System.out.println("O herói entra na arena para o teste definitivo: sobreviver a três desafios consecutivos!");
            System.out.println();

            System.out.println("O rugido da multidão é uma muralha de som. Holofotes cegantes varrem a escuridão, revelando um chão de pedra marcado por incontáveis batalhas. Cada mancha escura é o eco de um guerreiro que tombou exatamente onde ele pisa agora.");
            System.out.println("O ar é denso, pesado com a promessa de violência. Nos ombros do herói, não pesa apenas a própria vida, mas o legado de todos que vieram antes. Três monstros. Três testes de vontade e aço. O caminho para a glória é pavimentado com perigo.");
            System.out.println();
            System.out.println("Que o primeiro oponente se revele!");
            System.out.println();


        Random rand = new Random();

        // Loop de batalhas (cada monstro)
        for (int i = 0; i < monstros.length; i++) {
            Monstro monstro = monstros[i];
            System.out.println("Batalha " + (i + 1) + ": Surge o " + monstro.getNome() + "!");
            System.out.println(monstro.exibirStatus());
            System.out.println();

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

           
            // Cada batalha tem 3 turnosa
            for (int turno = 1; turno <= 3; turno++) {
                System.out.println("Turno " + turno + ":");

                // Herói ataca monstro
                int rolagemHeroi = rand.nextInt(20) + 1; // 1d20
                System.out.println("Herói rola 1d20: " + rolagemHeroi);

            if (rolagemHeroi >= monstro.getAgilidade()) {
                if (rolagemHeroi == 20) {
                    System.out.println("Ataque crítico do herói!");
                    elfo.usarHabilidadeEspecial(monstro);
                } else {
                    elfo.atacar(monstro);
                }
            } else {
                System.out.println("O ataque do herói errou!");
            }

                // Monstro ataca herói (se estiver vivo)
                if (monstro.getpontosdevida() > 0) {
                    int rolagemMonstro = rand.nextInt(20) + 1; // 1d20
                    System.out.println("Monstro rola 1d20: " + rolagemMonstro);
                    if (rolagemMonstro >= elfo.getAgilidade()) {
                        if (rolagemMonstro == 20) {
                            System.out.println("Ataque crítico do monstro!");
                        
                        }
                        monstro.atacar(elfo); // Chama o método correto de cada monstro
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

                // Ganho de XP por turno:
                // Herói ganha experiência ao sobreviver ao turno.
                // Este é o segundo ponto de ganho de XP, intencionalmente duplicado
                // para garantir que o herói suba de nível mais rapidamente,
                // dado o pequeno número de turnos por batalha.
                elfo.ganharExperiencia(monstro.getXpConcedido());

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
                    
                    // Ganho de XP por derrota do monstro:
                    // Herói ganha experiência adicional ao derrotar o monstro.
                    // Combinado com o ganho por turno, isso acelera ainda mais o desenvolvimento do personagem.
                    elfo.ganharExperiencia(monstro.getXpConcedido());
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