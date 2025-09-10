//ConstrutorDeCenario.java

import java.util.ArrayList;

public class ConstrutorDeCenario {
    public static ArrayList<Fase> gerarFases(int nFases) {
        ArrayList<Fase> fases = new ArrayList<>();
        System.out.println("\nGerando " + nFases + " fases para o cenário...");

        for (int i = 1; i <= nFases; i++) {
            int nivelFase = i;
            String ambiente = "Ambiente da Fase " + i;
            ArrayList<Monstro> monstrosFase = new ArrayList<>();

            // Criar algumas armas para os monstros largarem
            ArrayList<Arma> armasComuns = new ArrayList<>();
            armasComuns.add(new Arma(5 + i, 1)); // Arma simples, aumenta com o nível da fase
            armasComuns.add(new Arma(8 + i * 2, 2)); // Arma média
            armasComuns.add(new Arma(12 + i * 3, 3)); // Arma rara

            // Aumenta a quantidade de monstros por fase
            for (int j = 0; j < i + 2; j++) {
                //aumento agressivo dos atributos dos monstros
                
                // Goblin
                if (j % 3 == 0) { 
                    monstrosFase.add(new Goblin(
                            "Goblin " + (j + 1) + " da Fase " + i,
                            i * 12 + 50, // Mais vida
                            i * 2 + 9,   // Mais força
                            i * 2 + 8,       // Mais agilidade (antes i + 6)
                            i * 25,      // Mais XP
                            "Adaga",
                            3 + i,
                            0.2 + (i * 0.05),
                            armasComuns // Lista de armas para largar
                    ));
                
                // Vampiro
                } else if (j % 3 == 1) {
                    monstrosFase.add(new Vampiro(
                            "Vampiro " + (j + 1) + " da Fase " + i,
                            i * 15 + 60, // Mais vida
                            i * 3 + 10,  // Mais força
                            i * 2 + 8,   // Mais agilidade (antes i + 8)
                            i * 30,      // Mais XP
                            10 + i * 2,
                            armasComuns // Lista de armas para largar
                    ));

                // Espírito
                } else {
                    monstrosFase.add(new Espirito(
                            "Espirito " + (j + 1) + " da Fase " + i,
                            i * 10 + 40, // Mais vida
                            i * 2 + 8,   // Mais força (antes i + 4)
                            i * 2 + 8,   // Mais agilidade (antes i + 7)
                            i * 23,      // Mais XP
                            15 + i * 3,
                            armasComuns // Lista de armas para largar
                    ));
                }
            }
            
            /*
            for (int j = 0; j < i + 1; j++) { // Aumenta a quantidade de monstros por fase
                // Monstro Goblin
                if (j % 3 == 0) {
                    monstrosFase.add(new Goblin(
                            "Goblin " + (j + 1) + " da Fase " + i,
                            i * 10 + 50, // Mais vida
                            i * 2 + 5,   // Mais força
                            i + 5,       // Mais agilidade
                            i * 20,      // Mais XP
                            "Adaga",
                            3 + i,
                            0.2 + (i * 0.05),
                            armasComuns // Lista de armas para largar
                    ));
                // Monstro Vampiro
                } else if (j % 3 == 1) {
                    monstrosFase.add(new Vampiro(
                            "Vampiro " + (j + 1) + " da Fase " + i,
                            i * 12 + 60,
                            i * 3 + 8,
                            i + 7,
                            i * 25,
                            10 + i * 2,
                            armasComuns
                    ));
                // Monstro Espirito
                } else {
                    monstrosFase.add(new Espirito(
                            "Espirito " + (j + 1) + " da Fase " + i,
                            i * 8 + 40,
                            i * 1 + 3,
                            i + 6,
                            i * 18,
                            15 + i * 3,
                            armasComuns
                    ));
                }
            }*/
            
            fases.add(new Fase(nivelFase, ambiente, monstrosFase));
            System.out.println("Fase " + nivelFase + " ('" + ambiente + "') criada com " + monstrosFase.size() + " monstros.");
        }
        System.out.println("Todas as fases foram geradas com sucesso!\n");
        return fases;
    }
}

