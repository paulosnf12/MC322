//ConstrutorDeCenario.java

import java.util.ArrayList;
import java.util.List;

// MUDANÇA 1: A classe agora implementa a interface GeradorDeFases
public class ConstrutorDeCenarioFixo implements GeradorDeFases {

    // Array de nomes de ambientes temáticos.
    private static final String[] AMBIENTES_TEMATICOS = {
        "Floresta Sussurrante",      // Para a Fase 1
        "Cripta Sombria",            // Para a Fase 2
        "Pico Nevado dos Ventos Uivantes" // Para a Fase 3 e subsequentes
    };


    // MUDANÇA 2: A assinatura do método foi alterada para corresponder à interface
    @Override                 //gerarFases virou gerar para corresponder à interface
    public List<InterfaceFase> gerar(int nFases) { //--> antigo public static ArrayList<Fase> gerarFases(int nFases);

        // MUDANÇA 3: O tipo da lista foi alterado para a interface
        List<InterfaceFase> fases = new ArrayList<>();
        System.out.println("\n-------------------------------------------------");
        System.out.println(" Preparando o reino para sua jornada, herói...");
        System.out.println("-------------------------------------------------");

        // Loop principal para criar as fases. O 'i' agora é o índice 0-based.
        for (int i = 0; i < nFases; i++) {
            int nivelFase = i + 1; // nivelFase será 1 para i=0, 2 para i=1, etc.

            // Seleciona um ambiente temático com base no índice 'i'.
            // Se o número de fases for maior que o array de temas, ele usará o último tema disponível.
            String ambiente;
            if (i < AMBIENTES_TEMATICOS.length) {
                ambiente = AMBIENTES_TEMATICOS[i];
            } else {
                ambiente = AMBIENTES_TEMATICOS[AMBIENTES_TEMATICOS.length - 1] + " (Nível " + nivelFase + ")";
            }

            ArrayList<Monstro> monstrosFase = new ArrayList<>();

            // Criar algumas armas para os monstros largarem, usando as novas classes concretas de Arma
          // Criar algumas armas para os monstros largarem, usando as novas classes concretas de Arma
            ArrayList<Arma> armasComuns = new ArrayList<>();
            // As armas agora são instâncias de classes concretas que herdam de Arma
            armasComuns.add(new EspadaMadeira(5 + nivelFase)); // Exemplo de Arma simples: Espada de Madeira
            armasComuns.add(new ArcoBeta(8 + nivelFase * 2));   // Exemplo de Arma média: Arco Beta

            // --- ADIÇÃO DE ARMAS MAIS AVANÇADAS PARA DROP ---
            // A partir da Fase 2 (nivelFase 2)
            if (nivelFase >= 2) {
                armasComuns.add(new EspadaFerro(12 + nivelFase * 3)); // Exemplo de Arma rara: Espada de Ferro (nível 2 mínimo na classe)
                armasComuns.add(new ArcoAlpha(15 + nivelFase * 3)); // Arco mais forte
            }
            // A partir da Fase 3 (nivelFase 3)
            if (nivelFase >= 3) {
                armasComuns.add(new EspadaDiamante(20 + nivelFase * 4)); // Espada Lendária
                armasComuns.add(new ArcoSigma(25 + nivelFase * 4)); // Arco Lendário
            }
            // --- FIM DA ADIÇÃO DE ARMAS MAIS AVANÇADAS PARA DROP ---

            // Aumenta a quantidade de monstros por fase
            for (int j = 0; j < nivelFase + 2; j++) { // Usa nivelFase para determinar a quantidade de monstros
                //aumento agressivo dos atributos dos monstros
                String nomeMonstro; // Variável temporária para definir o nome do monstro

                // Goblin
                if (j % 3 == 0) {
                    nomeMonstro = "Goblin " + (j + 1) + " da Fase " + nivelFase; // Nome padrão
                    // --- INÍCIO DA LÓGICA PARA MONSTROS COM FALAS ESPECIAIS ---
                    if (nivelFase == 1 && j == 0) { // O primeiro monstro da Fase 1 será o Goblin Guerreiro
                        nomeMonstro = "Goblin Guerreiro";
                    }
                    // --- FIM DA LÓGICA PARA MONSTROS COM FALAS ESPECIAIS ---
                    monstrosFase.add(new Goblin(
                            nomeMonstro, // Usa o nome definido (especial ou padrão)
                            nivelFase * 12 + 50, // Mais vida
                            nivelFase * 2 + 9,   // Mais força
                            nivelFase * 2 + 8,       // Mais agilidade (antes nivelFase + 6)
                            nivelFase * 25,      // Mais XP
                            "Adaga",
                            3 + nivelFase,
                            0.2 + (nivelFase * 0.05),
                            armasComuns // Lista de armas para largar (agora contém instâncias de Arma concreta)
                    ));

                // Vampiro
                } else if (j % 3 == 1) {
                    nomeMonstro = "Vampiro " + (j + 1) + " da Fase " + nivelFase; // Nome padrão
                    // --- INÍCIO DA LÓGICA PARA MONSTROS COM FALAS ESPECIAIS ---
                    if (nivelFase == 2 && j == 1) { // O segundo monstro da Fase 2 (Vampiro) será Edward Cullen
                        nomeMonstro = "Edward Cullen";
                    }
                    // --- FIM DA LÓGICA PARA MONSTROS COM FALAS ESPECIAIS ---
                    monstrosFase.add(new Vampiro(
                            nomeMonstro, // Usa o nome definido (especial ou padrão)
                            nivelFase * 15 + 60, // Mais vida
                            nivelFase * 3 + 10,  // Mais força
                            nivelFase * 2 + 8,   // Mais agilidade (antes nivelFase + 8)
                            nivelFase * 30,      // Mais XP
                            10 + nivelFase * 2,
                            armasComuns // Lista de armas para largar
                    ));

                // Espírito
                } else {
                    nomeMonstro = "Espirito " + (j + 1) + " da Fase " + nivelFase; // Nome padrão
                    // --- INÍCIO DA LÓGICA PARA MONSTROS COM FALAS ESPECIAIS ---
                    if (nivelFase == 3 && j == 2) { // O terceiro monstro da Fase 3 (Espirito) será Kaonashi
                        nomeMonstro = "Kaonashi";
                    }
                    // --- FIM DA LÓGICA PARA MONSTROS COM FALAS ESPECIAIS ---
                    monstrosFase.add(new Espirito(
                            nomeMonstro, // Usa o nome definido (especial ou padrão)
                            nivelFase * 10 + 40, // Mais vida
                            nivelFase * 2 + 8,   // Mais força (antes nivelFase + 4)
                            nivelFase * 2 + 8,   // Mais agilidade (antes nivelFase + 7)
                            nivelFase * 23,      // Mais XP
                            15 + nivelFase * 3,
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

            fases.add(new Fase(nivelFase, ambiente, monstrosFase)); // O 'ambiente' agora é o temático
            System.out.println("Fase " + nivelFase + ": '" + ambiente + "' criada com " + monstrosFase.size() + " monstros prontos para o desafio!");
        }
        System.out.println("Todas as fases foram geradas com sucesso! A aventura aguarda...\n");
        return fases;
    }
}