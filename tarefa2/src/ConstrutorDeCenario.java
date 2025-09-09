/*Classe ConstrutorDeCenário
Esta classe será utilizada para guardar os métodos estáticos necessários para a execução de partes do
nosso RPG relacionadas à definição dos cenários onde a história se passa.
Métodos:
• gerarFases(int nFases) (Método Estático)- Cria ”n”fases de acordo com o parâmetro do
método. Retorna uma lista de Fases com dificuldade crescente (nível da fase). */

import java.util.ArrayList;

public static class ConstrutorDeCenario {

    public static ArrayList<Fase> gerarFases(int nFases) {
        ArrayList<Fase> fases = new ArrayList<>();

        for (int i = 1; i <= nFases; i++) {
            int nivel = i;
            String ambiente = "Ambiente da Fase " + i;
            ArrayList<Monstro> monstros = new ArrayList<>();

            // Adiciona monstros à fase com dificuldade crescente
            for (int j = 0; j < i; j++) {
                monstros.add(new Monstro("Monstro " + (j + 1) + " da Fase " + i, i * 10, i * 2));
            }

            fases.add(new Fase(nivel, ambiente, monstros));
        }

        return fases;
    }
}
