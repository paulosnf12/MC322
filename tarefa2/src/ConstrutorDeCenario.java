/*Classe ConstrutorDeCenário
Esta classe será utilizada para guardar os métodos estáticos necessários para a execução de partes do
nosso RPG relacionadas à definição dos cenários onde a história se passa.
Métodos:
• gerarFases(int nFases) (Método Estático)- Cria ”n”fases de acordo com o parâmetro do
método. Retorna uma lista de Fases com dificuldade crescente (nível da fase). */

import java.util.ArrayList;

public class ConstrutorDeCenario {

    public static ArrayList<Fase> gerarFases(int nFases) {
        ArrayList<Fase> fases = new ArrayList<>();

        for (int i = 1; i <= nFases; i++) {
            int nivel = i;
            String ambiente = "Ambiente da Fase " + i;
            ArrayList<Monstro> monstros = new ArrayList<>();

            for (int j = 0; j < i; j++) {
                if (j % 3 == 0) {
                    monstros.add(new Goblin(
                        "Goblin " + (j + 1) + " da Fase " + i,
                        i * 10,
                        i * 2,
                        i,
                        i * 5,
                        "Adaga",
                        3 + i,
                        0.2 + (i * 0.05)
                    ));
                } else if (j % 3 == 1) {
                    monstros.add(new Vampiro(
                        "Vampiro " + (j + 1) + " da Fase " + i,
                        i * 12,
                        i * 3,
                        i + 1,
                        i * 6,
                        10 + i * 2
                    ));
                } else {
                    monstros.add(new Espirito(
                        "Espirito " + (j + 1) + " da Fase " + i,
                        i * 8,
                        i * 1,
                        i + 2,
                        i * 4,
                        15 + i * 3
                    ));
                }
            }

            fases.add(new Fase(nivel, ambiente, monstros));
        }

        return fases;
    }
}