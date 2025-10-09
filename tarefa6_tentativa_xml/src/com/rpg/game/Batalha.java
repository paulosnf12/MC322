//src/com/rpg/game/Batalha.java

package com.rpg.game;

import com.rpg.cenario.ConstrutorDeCenarioFixo;
import com.rpg.cenario.Fase;
import com.rpg.cenario.FaseDeCombate;
import com.rpg.cenario.GeradorDeFases;
import com.rpg.personagens.Heroi;
import com.rpg.personagens.Monstro;
import com.rpg.personagens.herois.Paladino; // Exemplo de Heroi
import com.rpg.util.InputManager;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient; // Importe esta anotação

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Representa o estado completo de uma batalha ou aventura no RPG.
 * Esta classe encapsula o herói, as fases do jogo, a dificuldade,
 * e gerencia o fluxo de combate e progressão de fases.
 * É serializável para permitir salvar e carregar o jogo.
 */
@XmlRootElement // Necessário para JAXB, indica que esta é a raiz do XML
public class Batalha implements Serializable {
    private static final long serialVersionUID = 1L; // Necessário para Serializable

    @XmlElement // Marca o campo para ser serializado pelo JAXB
    private Heroi heroi;
    @XmlElement
    private List<Fase> fasesDoJogo;
    @XmlElement
    private Dificuldade dificuldadeSelecionada;
    @XmlElement
    private int faseAtualIndex;

    @XmlTransient // ADICIONADO: Ignora este campo na serialização XML, pois Random não é trivialmente serializável.
    private transient Random rand; // ADICIONADO: Instância de Random para eventos aleatórios.

    // Construtor vazio é necessário para JAXB
    public Batalha() {
        this.fasesDoJogo = new ArrayList<>();
        this.faseAtualIndex = 0;
        this.rand = new Random(); // ADICIONADO: Inicializa Random no construtor vazio.
    }

    /**
     * Construtor para iniciar uma nova batalha.
     * @param heroi O herói que participará da batalha.
     * @param dificuldade A dificuldade selecionada para a batalha.
     * @param numFases O número total de fases a serem geradas.
     */
    public Batalha(Heroi heroi, Dificuldade dificuldade, int numFases) {
        this(); // ADICIONADO: Chama o construtor vazio para inicializar 'rand' e 'fasesDoJogo'
        this.heroi = heroi;
        this.dificuldadeSelecionada = dificuldade;
        // this.faseAtualIndex = 0; // Já inicializado pelo construtor vazio
        // this.fasesDoJogo = new ArrayList<>(); // Já inicializado pelo construtor vazio
        gerarFases(numFases, dificuldade);
    }

    // --- Métodos de Getter para JAXB ---
    public Heroi getHeroi() { return heroi; }
    public List<Fase> getFasesDoJogo() { return fasesDoJogo; }
    public Dificuldade getDificuldadeSelecionada() { return dificuldadeSelecionada; }
    public int getFaseAtualIndex() { return faseAtualIndex; }

    // --- Métodos de Setter para JAXB (necessários para desserialização) ---
    public void setHeroi(Heroi heroi) { this.heroi = heroi; }
    public void setFasesDoJogo(List<Fase> fasesDoJogo) { this.fasesDoJogo = fasesDoJogo; }
    public void setDificuldadeSelecionada(Dificuldade dificuldadeSelecionada) { this.dificuldadeSelecionada = dificuldadeSelecionada; }
    public void setFaseAtualIndex(int faseAtualIndex) { this.faseAtualIndex = faseAtualIndex; }


    /**
     * Gera as fases do jogo usando o GeradorDeFases.
     * @param numFases O número de fases a serem geradas.
     * @param dificuldade A dificuldade do jogo.
     */
    private void gerarFases(int numFases, Dificuldade dificuldade) {
        GeradorDeFases gerador = new ConstrutorDeCenarioFixo();
        this.fasesDoJogo = gerador.gerar(numFases, dificuldade);
        System.out.println("\n== Inicio da Grande Jornada ==");
        System.out.println("O herói, " + heroi.getNome() + ", entra na arena para o teste definitivo!");
        System.out.println("Que a sorte esteja ao seu lado!\n");
    }

    /**
     * Executa a próxima fase incompleta da batalha.
     * Retorna true se houver mais fases ou se o herói ainda estiver vivo.
     */
    public boolean executarProximaFase() {
        if (!heroi.estaVivo()) {
            System.out.println("\n==============================================");
            System.out.println("               GAME OVER!                   ");
            System.out.println("O heroi foi esmagado pela forca de seus inimigos.");
            System.out.println("Sua lenda termina aqui...");
            System.out.println("==============================================");
            return false; // Herói morto, jogo termina.
        }

        if (faseAtualIndex >= fasesDoJogo.size()) {
            System.out.println("\n==============================================");
            System.out.println("             VITORIA GRANDIOSA!             ");
            System.out.println("O HEROI CONQUISTOU TODOS OS DESAFIOS!");
            System.out.println("==============================================");
            System.out.println("A jornada de " + heroi.getNome() + " chegou ao fim, e a vitoria e sua!");
            System.out.println("O reino esta seguro, e seu nome sera cantado por eras.");
            System.out.println("\nStatus final do heroi, um verdadeiro campeao:");
            System.out.println(heroi.exibirStatus());
            return false; // Todas as fases concluídas, jogo termina.
        }

        Fase faseAtualInterface = fasesDoJogo.get(faseAtualIndex);

        if (!(faseAtualInterface instanceof FaseDeCombate)) {
            System.out.println("Erro: Fase atual não é uma FaseDeCombate.");
            return false;
        }
        FaseDeCombate faseAtual = (FaseDeCombate) faseAtualInterface;

        faseAtual.iniciar(heroi);
        System.out.println("\nStatus atual do heroi antes da batalha: " + heroi.exibirStatus());
        System.out.println("\n" + heroi.getNome() + " aperta o punho em sua arma, pronto para o combate!");

        // Random rand = new Random(); // REMOVIDO: Usaremos o campo 'this.rand'

        for (Monstro monstro : faseAtual.getMonstros()) {
            if (!heroi.estaVivo()) break;

            // LÓGICA PARA VERIFICAR EVENTOS
            if (faseAtual.getEventos() != null) {
                for (com.rpg.cenario.Evento evento : faseAtual.getEventos()) { // Usar o nome completo para evitar conflito com Event do Java.util
                    if (evento.vericarGatilho(heroi, faseAtual.getTipoDeCenario())) {
                        evento.executar(heroi);
                    }
                }
            }
            if (!heroi.estaVivo()) break;

            System.out.println("\n--- ENCONTRO COM NOVO INIMIGO ---");
            System.out.println("Das sombras, surge o temivel " + monstro.getNome() + "!");
            System.out.println(monstro.exibirStatus());
            System.out.println();
            System.out.println(heroi.getNome() + " encara a ameaca e se prepara para a batalha contra " + monstro.getNome() + "!");
            System.out.println();
            monstro.apresentarDialogoEspecial();

            // Diálogos especiais do herói
            if (monstro.getNome().equals("Edward Cullen")) {
                // CORRIGIDO: Aspas duplas escapadas
                System.out.println(heroi.getNome() + ": \"O brilho da vida que eu defendo e muito mais forte que o seu falso esplendor, criatura!\"");
            } else if (monstro.getNome().equals("Kaonashi")) {
                // CORRIGIDO: Aspas duplas escapadas
                System.out.println(heroi.getNome() + ": \"Nao quero ouro, Kaonashi. So quero vencer!\"");
            } else if (monstro.getNome().equals("Goblin Guerreiro")) {
                // CORRIGIDO: Aspas duplas escapadas
                System.out.println(heroi.getNome() + ": \"Sua sede de batalha sera o seu fim, Goblin!\"");
            }

            // Loop de turno
            int turno = 1;
            while (heroi.estaVivo() && monstro.estaVivo()) {
                System.out.println("\n--- Turno " + turno + " ---");
                System.out.println("Vida do Heroi: " + heroi.getpontosdevida() + " | Vida do Monstro: " + monstro.getpontosdevida());

                // TURNO DO HERÓI
                System.out.println(heroi.getNome() + " se move agilmente para atacar!");
                int rolagemHeroi = this.rand.nextInt(20) + 1; // MODIFICADO: Usa 'this.rand'
                System.out.println("Heroi rola 1d20: " + rolagemHeroi);
                if (rolagemHeroi >= monstro.getAgilidade()) {
                    if (rolagemHeroi == 20) {
                        System.out.println("UM ATAQUE CRITICO! " + heroi.getNome() + " prepara um golpe devastador!");
                        heroi.setProximoAtaqueCritico(true);
                    }
                    com.rpg.combate.AcaoDeCombate acaoHeroi = heroi.escolherAcao(monstro);
                    if (acaoHeroi != null) {
                        try {
                            acaoHeroi.executar(heroi, monstro);
                        } catch (com.rpg.exceptions.RecursoInsuficienteException e) {
                            System.out.println(heroi.getNome() + " não tem mana suficiente para essa habilidade!");
                            System.out.println("O ataque falha...");
                        }
                    }
                } else {
                    System.out.println("O ataque de " + heroi.getNome() + " falha! O monstro desvia por pouco.");
                }
                if (!monstro.estaVivo()) break;

                // TURNO DO MONSTRO
                System.out.println();
                int rolagemMonstro = this.rand.nextInt(20) + 1; // MODIFICADO: Usa 'this.rand'
                System.out.println(monstro.getNome() + " rola 1d20: " + rolagemMonstro);
                if (rolagemMonstro >= heroi.getAgilidade()) {
                    if (rolagemMonstro == 20) {
                        System.out.println("UM ATAQUE CRITICO de " + monstro.getNome() + "!");
                        monstro.setProximoAtaqueCritico(true);
                    }
                    com.rpg.combate.AcaoDeCombate acaoMonstro = monstro.escolherAcao(heroi);
                    if (acaoMonstro != null) {
                        try {
                            acaoMonstro.executar(monstro, heroi);
                        } catch (com.rpg.exceptions.RecursoInsuficienteException e) {
                            System.out.println("O ataque de " + monstro.getNome() + " falhou por falta de energia!");
                        }
                    }
                } else {
                    System.out.println("O ataque de " + monstro.getNome() + " erra! " + heroi.getNome() + " se esquiva com maestria.");
                }
                turno++;
            }

            // FIM DO COMBATE
            if (heroi.estaVivo()) {
                System.out.println("\n" + monstro.getNome() + " tomba, derrotado! Sua forma se desfaz no ar.");
                heroi.ganharExperiencia(monstro.getXpConcedido());
                exibirMenuPosCombate(monstro); // Adicionando o menu pós-combate aqui
            } else {
                break; // Herói morto
            }
        }

        if (heroi.estaVivo() && faseAtual.isConcluida()) {
            System.out.println("\n--------------------------------------------------------");
            System.out.println("VITORIA NA FASE! " + heroi.getNome() + " superou todos os desafios de " + faseAtual.getTipoDeCenario().getDescricao() + "!");
            System.out.println("--------------------------------------------------------");
            faseAtualIndex++; // Avança para a próxima fase
            return faseAtualIndex < fasesDoJogo.size(); // Retorna true se houver mais fases
        } else if (!heroi.estaVivo()) {
            return false; // Herói morto
        }
        return false; // Se a fase não foi concluída por algum motivo (ex: herói morto)
    }

    /**
     * Exibe o menu pós-combate e processa as ações do jogador.
     * @param monstro O monstro recém-derrotado (para loot).
     */
    private void exibirMenuPosCombate(Monstro monstro) {
        // Random rand = new Random(); // REMOVIDO: Usa o campo 'this.rand'
        boolean continuarPosCombate = true;
        while (continuarPosCombate) {
            System.out.println("\n--- Menu Pos-Combate ---");
            System.out.println("[1] Interagir com o Loot (se houver)");
            System.out.println("[2] Ver Informacoes do Personagem");
            System.out.println("[3] Salvar Jogo"); // Nova opção
            System.out.println("[4] Desistir do Jogo");
            System.out.println("[5] Continuar Aventura (Proximo monstro/fase)");

            int opcaoPosCombate = InputManager.lerInteiro("Escolha sua acao", 1, 5);
            switch (opcaoPosCombate) {
                case 1:
                    System.out.println("\nVoce se aproxima do local onde o monstro caiu...");
                    if (this.rand.nextDouble() < heroi.getSorte()) { // MODIFICADO: Usa 'this.rand'
                        System.out.println("A sorte de " + heroi.getNome() + " brilha! Ha um brilho no chao onde o monstro caiu!");
                        com.rpg.itens.Item loot = monstro.droparLoot(); // Loot ignorado ao salvar, mas pode ser pego agora
                        if (loot != null && loot instanceof com.rpg.itens.Arma) {
                            com.rpg.itens.Arma armaLargada = (com.rpg.itens.Arma) loot;
                            System.out.println(" - " + armaLargada.toString());
                            boolean podeEquiparTipo = false;
                            if (heroi instanceof Paladino && armaLargada.getTipoArma().equals("Espada")) {
                                podeEquiparTipo = true;
                            } else if (heroi instanceof com.rpg.personagens.herois.Elfo && armaLargada.getTipoArma().equals("Arco")) {
                                podeEquiparTipo = true;
                            }

                            if (podeEquiparTipo) {
                                if (heroi.getArma() == null || armaLargada.getDano() > heroi.getArma().getDano()) {
                                    try {
                                        heroi.equiparArma(armaLargada);
                                        System.out.println(heroi.getNome() + " empunhou a " + armaLargada.getNomeCompleto() + "!");
                                    } catch (com.rpg.exceptions.NivelInsuficienteException e) {
                                        System.out.println("Voce encontrou uma arma poderosa, mas " + e.getMessage());
                                    }
                                } else {
                                    System.out.println("A " + armaLargada.getNomeCompleto() + " nao e tao boa quanto a arma atual de " + heroi.getNome() + ". Ele decide nao equipa-la.");
                                }
                            } else {
                                System.out.println(heroi.getNome() + " encontra a arma, mas ela nao e do seu estilo. (" + armaLargada.getNomeCompleto() + " ignorada).");
                            }
                        } else {
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
                case 3: // Salvar Jogo
                    String nomeSave = InputManager.lerString("Digite um nome para o seu save (ex: 'MinhaAventura')");
                    GerenciadorDePersistencia.salvarBatalha(this, nomeSave);
                    System.out.println("Jogo salvo com sucesso como '" + nomeSave + "'!");
                    InputManager.esperarEnter("Pressione ENTER para continuar...");
                    // Você pode optar por continuar o loop pós-combate ou sair dele
                    // Neste exemplo, o jogador continua no menu pós-combate.
                    break;
                case 4: // Desistir do Jogo
                    System.out.println("\nVoce decide que esta jornada foi o suficiente.");
                    System.out.println("O reino tera que esperar por outro heroi...");
                    InputManager.fecharScanner();
                    System.exit(0);
                    break;
                case 5:
                    continuarPosCombate = false; // Sai do loop do menu pós-combate
                    break;
            }
        }
    }
}