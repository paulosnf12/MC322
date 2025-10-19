// src/com/rpg/game/Batalha.java
package com.rpg.game;

import com.rpg.cenario.*;
import com.rpg.combate.AcaoDeCombate;
import com.rpg.exceptions.NivelInsuficienteException;
import com.rpg.exceptions.RecursoInsuficienteException;
import com.rpg.itens.Arma;
import com.rpg.itens.Item;
import com.rpg.personagens.Heroi;
import com.rpg.personagens.Monstro;
import com.rpg.personagens.herois.Elfo;
import com.rpg.personagens.herois.Paladino;
import com.rpg.util.InputManager;
import com.rpg.util.GerenciadorDePersistencia;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlSeeAlso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Representa uma sessão de jogo ativa, gerenciando o herói, a sequência de fases
 * e o progresso da aventura. Esta classe também lida com a lógica principal
 * de interação e combate, e é projetada para ser serializada e deserializada
 * para permitir o salvamento e carregamento do jogo.
 *
 * A classe Batalha utiliza composição para gerenciar o Heroi e as Fases,
 * consolidando o estado da aventura em um único objeto.
 */
@XmlRootElement(name = "batalha")
// Adicione aqui todas as subclasses concretas de Heroi e Fase que podem ser serializadas
@XmlSeeAlso({FaseDeCombate.class, EventoDeBencao.class, Paladino.class, Elfo.class})
public class Batalha implements Serializable {

    private static final long serialVersionUID = 1L; // Para a interface Serializable

    private Heroi heroi;
    @XmlElements({
    @XmlElement(name = "faseDeCombate", type = FaseDeCombate.class)
        // Se houver outras implementações de Fase, adicionamos aqui
        // @XmlElement(name = "faseExploracao", type = FaseExploracao.class)
    })
    private List<Fase> fasesDoJogo;
    private int faseAtualIndex;
    private String nomeJogoSalvo;

    // Marcamos o Random como transient para que JAXB o ignore.
    // Ele será reinicializado após a deserialização.
    //@XmlTransient
    private transient Random rand;

    /**
     * Construtor padrão exigido pelo JAXB para deserialização. Sem argumentos
     */
    public Batalha() {
        this.fasesDoJogo = new ArrayList<>();
        this.faseAtualIndex = 0;
        this.rand = new Random(); // Garante que o objeto Random seja criado
    }

    /**
     * Construtor para iniciar uma nova batalha.
     *
     * @param heroi O herói que participará da batalha.
     * @param fasesDoJogo A lista de fases a serem jogadas.
     * @param nomeJogoSalvo O nome do jogo salvo, se aplicável (null para novo jogo).
     */
    public Batalha(Heroi heroi, List<Fase> fasesDoJogo, String nomeJogoSalvo) {
        this.heroi = heroi;
        this.fasesDoJogo = fasesDoJogo;
        this.faseAtualIndex = 0; // Começa na primeira fase
        this.nomeJogoSalvo = nomeJogoSalvo;
        this.rand = new Random();
    }

    // --- Getters e Setters para JAXB ---
    // o return heroi que será o elemento xml dentro do elemento raiz (batalha)
    // vai criar uma tag heroi, na qual o elemento que vai estar dentro será o heroi de fato que está sendo utilizado
    @XmlElement
    public Heroi getHeroi() {
        return heroi;
    }

    public void setHeroi(Heroi heroi) {
        this.heroi = heroi;
    }

    @XmlElement(name = "fase") // Nome do elemento XML para itens da lista
    public List<Fase> getFasesDoJogo() {
        return fasesDoJogo;
    }

    public void setFasesDoJogo(List<Fase> fasesDoJogo) {
        this.fasesDoJogo = fasesDoJogo;
    }

    @XmlElement
    public int getFaseAtualIndex() {
        return faseAtualIndex;
    }

    public void setFaseAtualIndex(int faseAtualIndex) {
        this.faseAtualIndex = faseAtualIndex;
    }

    @XmlElement
    public String getNomeJogoSalvo() {
        return nomeJogoSalvo;
    }

    public void setNomeJogoSalvo(String nomeJogoSalvo) {
        this.nomeJogoSalvo = nomeJogoSalvo;
    }
    // --- Fim Getters e Setters para JAXB ---

    /**
     * Método de callback para ser chamado após a deserialização do objeto JAXB.
     * Garante que campos transient sejam reinicializados.
     * @param unmarshaller O objeto Unmarshaller (pode ser null).
     * @param parent O objeto pai no XML (pode ser null).
     */
    public void afterUnmarshal(jakarta.xml.bind.Unmarshaller unmarshaller, Object parent) {
        this.rand = new Random(); // Reinicializa o gerador de números aleatórios
        if (this.heroi != null) {
            this.heroi.initializeTransientFields(); // Chama para o herói e suas ações
        }
        for (Fase fase : fasesDoJogo) {
            if (fase instanceof FaseDeCombate) {
                for (Monstro monstro : ((FaseDeCombate) fase).getMonstros()) {
                    monstro.initializeTransientFields(); // Chama para monstros e suas ações
                }
            }
        }
    }


    /**
     * Inicia uma nova batalha com a dificuldade e herói selecionados.
     *
     * @param dificuldade A dificuldade do jogo.
     * @param selectedHero O herói escolhido pelo jogador.
     * @param nFases O número total de fases a serem geradas.
     */
    public void iniciarNovaBatalha(Dificuldade dificuldade, Heroi selectedHero, int nFases) {
        System.out.println("\n== Despertar do Herói ==");
        System.out.println("Seu campeão surge das lendas...");
        System.out.println("O herói " + selectedHero.getNome() + " está pronto para a glória!");
        System.out.println(selectedHero.exibirStatus());
        System.out.println();

        this.heroi = selectedHero;
        this.nomeJogoSalvo = null; // Um novo jogo não tem nome de save ainda

        GeradorDeFases gerador = new ConstrutorDeCenarioFixo();
        this.fasesDoJogo = gerador.gerar(nFases, dificuldade);
        this.faseAtualIndex = 0; // Começa da primeira fase

        System.out.println("\n== Início da Grande Jornada ==");
        System.out.println();
        System.out.println("O herói, " + heroi.getNome() + ", entra na arena para o teste definitivo: sobreviver a " + nFases + " desafios consecutivos!");
        System.out.println();
        System.out.println("O rugido da multidão é uma muralha de som. Holofotes cegantes varrem a escuridão, na arena o herói aguarda ser transportado para seu primeiro desafio, um chão que antes da tecnologia do teleporte foi marcado por incontáveis batalhas. Cada mancha escura é o eco de um guerreiro que tombou exatamente onde ele pisa, agora é a vez dele.");
        System.out.println("O ar é denso, pesado com a promessa de violência. Nos ombros de " + heroi.getNome() + ", não pesa apenas a própria vida, mas o legado de todos que vieram antes. Muitos monstros. Muitos testes de vontade e aço. O caminho para a glória é pavimentado com perigo.");
        System.out.println();
        System.out.println("O teleporte está sendo ajustado.");
        System.out.println();
        System.out.println(heroi.getNome() + " será transportado para o cenário de batalha.");
        System.out.println();
        System.out.println("Que o primeiro oponente se revele!");
        System.out.println();
        System.out.println("Que a sorte esteja ao seu lado!\n");

        executarLoopFases();
    }

    /**
     * Continua uma batalha previamente carregada.
     */
    public void continuarBatalha() {
        if (this.heroi == null || this.fasesDoJogo == null || this.fasesDoJogo.isEmpty()) {
            System.out.println("Nenhuma batalha para continuar. Por favor, inicie um novo jogo.");
            return;
        }
        System.out.println("\n== Continuando a Jornada ==");
        System.out.println("Bem-vindo de volta, " + heroi.getNome() + "!");
        System.out.println("Você estava na fase " + (faseAtualIndex + 1) + " do jogo '" + nomeJogoSalvo + "'.");
        System.out.println(heroi.exibirStatus());
        executarLoopFases();
    }

    /**
     * Executa o loop principal de fases e combate do jogo.
     */
    private void executarLoopFases() {
        // O loop começa do `faseAtualIndex`, permitindo continuar de onde parou.
        for (int i = faseAtualIndex; i < fasesDoJogo.size(); i++) {
            this.faseAtualIndex = i; // Atualiza o índice da fase atual para persistência
            Fase faseAtualInterface = fasesDoJogo.get(i);

            if (!heroi.estaVivo()) {
                break; // Se o herói morreu na fase anterior ou evento, sai do loop de fases
            }

            TipoCenario cenarioDaFase = faseAtualInterface.getTipoDeCenario();

            // Garantia de que a fase é de combate
            if (!(faseAtualInterface instanceof FaseDeCombate)) {
                System.err.println("Erro: Fase atual não é uma FaseDeCombate. Pulando.");
                continue;
            }
            FaseDeCombate faseAtual = (FaseDeCombate) faseAtualInterface;

            // 1. Inicia a fase
            faseAtual.iniciar(heroi);

            System.out.println("\nStatus atual do herói antes da batalha: " + heroi.exibirStatus());
            System.out.println("\n" + heroi.getNome() + " aperta o punho em sua arma, " + "pronto para o combate!");

            // --- LOOP DE COMBATE POR MONSTRO ---
            for (Monstro monstro : faseAtual.getMonstros()) {
                // --- LÓGICA PARA VERIFICAR EVENTOS ---
                if (faseAtual.getEventos() != null) {
                    for (Evento evento : faseAtual.getEventos()) {
                        if (evento.vericarGatilho(heroi, cenarioDaFase)) {
                            evento.executar(heroi);
                        }
                    }
                }
                if (!heroi.estaVivo()) {
                    break; // Se o herói morrer em um evento, sai do loop de monstros
                }

                System.out.println("\n--- ENCONTRO COM NOVO INIMIGO ---");
                System.out.println("Das sombras, surge o temível " + monstro.getNome() + "!");
                System.out.println(monstro.exibirStatus());
                System.out.println();
                System.out.println(heroi.getNome() + " encara a ameaça e se prepara " + "para a batalha contra " + monstro.getNome() + "!");
                System.out.println();
                monstro.apresentarDialogoEspecial();

                // Respostas do herói (preservadas)
                if (monstro.getNome().equals("Edward Cullen")) {
                    System.out.println(heroi.getNome() + ": \"O brilho da vida que eu defendo e muito mais forte que o seu falso esplendor, criatura!\"");
                } else if (monstro.getNome().equals("Kaonashi")) {
                    System.out.println(heroi.getNome() + ": \"Não quero ouro, Kaonashi. Só quero vencer!\"");
                } else if (monstro.getNome().equals("Goblin Guerreiro")) {
                    System.out.println(heroi.getNome() + ": \"Sua sede de batalha será o seu fim, Goblin!\"");
                }

                // --- LOOP DE TURNO ---
                int turno = 1;
                while (heroi.estaVivo() && monstro.estaVivo()) {
                    System.out.println("\n--- Turno " + turno + " ---");
                    System.out.println("Vida do Herói: " + heroi.getpontosdevida() + " " + "| Vida do Monstro: " + monstro.getpontosdevida());

                    // --- TURNO DO HERÓI ---
                    System.out.println(heroi.getNome() + " se move agilmente para atacar!");
                    int rolagemHeroi = rand.nextInt(20) + 1;
                    System.out.println("Herói rola 1d20: " + rolagemHeroi);

                    if (rolagemHeroi >= monstro.getAgilidade()) {
                        if (rolagemHeroi == 20) {
                            System.out.println("UM ATAQUE CRÍTICO! " + heroi.getNome() + " " + "prepara um golpe devastador!");
                            heroi.setProximoAtaqueCritico(true);
                        }

                        AcaoDeCombate acaoHeroi = heroi.escolherAcao(monstro);
                        if (acaoHeroi != null) {
                            try {
                                acaoHeroi.executar(heroi, monstro);
                            } catch (RecursoInsuficienteException e) {
                                System.out.println(heroi.getNome() + " não tem mana suficiente para essa habilidade!");
                                System.out.println("O ataque falha...");
                                // O turno do herói é perdido, mas o jogo continua
                            }
                        }
                    } else {
                        System.out.println("O ataque de " + heroi.getNome() + " falha! " + "O monstro desvia por pouco.");
                    }

                    if (!monstro.estaVivo()) {
                        break; // Monstro derrotado, sai do loop de turno
                    }

                    // --- TURNO DO MONSTRO ---
                    System.out.println();
                    int rolagemMonstro = rand.nextInt(20) + 1;
                    System.out.println(monstro.getNome() + " rola 1d20: " + rolagemMonstro);

                    if (rolagemMonstro >= heroi.getAgilidade()) {
                        if (rolagemMonstro == 20) {
                            System.out.println("UM ATAQUE CRÍTICO de " + monstro.getNome() + "!");
                            monstro.setProximoAtaqueCritico(true);
                        }

                        AcaoDeCombate acaoMonstro = monstro.escolherAcao(heroi);
                        if (acaoMonstro != null) {
                            try {
                                acaoMonstro.executar(monstro, heroi);
                            } catch (RecursoInsuficienteException e) {
                                System.out.println("O ataque de " + monstro.getNome() + " falhou por falta de energia!");
                            }
                        }
                    } else {
                        System.out.println("O ataque de " + monstro.getNome() + " erra! " + heroi.getNome() + " se esquiva com maestria.");
                    }
                    turno++;
                }

                // --- FIM DO COMBATE (Lógica de loot e equipamento 100% preservada) ---
                if (heroi.estaVivo()) {
                    System.out.println("\n" + monstro.getNome() + " tomba, derrotado! " + "Sua forma se desfaz no ar.");
                    heroi.ganharExperiencia(monstro.getXpConcedido());

                    // --- MENU PÓS-COMBATE ---
                    boolean continuarPosCombate = true;
                    while (continuarPosCombate) {
                        System.out.println("\n--- Menu Pós-Combate ---");
                        System.out.println("[1] Interagir com o Loot (se houver)");
                        System.out.println("[2] Ver Informações do Personagem");
                        System.out.println("[3] Salvar Jogo"); // NOVA OPÇÃO
                        System.out.println("[4] Desistir do Jogo");
                        System.out.println("[5] Continuar Aventura (Próximo monstro/fase)"); // Índice alterado
                        int opcaoPosCombate = InputManager.lerInteiro("Escolha sua ação", 1, 5); // Range alterado

                        switch (opcaoPosCombate) {
                            case 1:
                                System.out.println("\nVocê se aproxima do local onde o monstro caiu...");
                                if (rand.nextDouble() < heroi.getSorte()) {
                                    System.out.println("A sorte de " + heroi.getNome() + " brilha! " + "Há um brilho no chão onde o monstro caiu!");
                                    Item loot = monstro.droparLoot();
                                    if (loot != null && loot instanceof Arma) {
                                        Arma armaLargada = (Arma) loot;
                                        System.out.println(" - " + armaLargada.toString());
                                        boolean podeEquiparTipo = false;
                                        if (heroi instanceof Paladino && armaLargada.getTipoArma().equals("Espada")) {
                                            podeEquiparTipo = true;
                                        } else if (heroi instanceof Elfo && armaLargada.getTipoArma().equals("Arco")) {
                                            podeEquiparTipo = true;
                                        }
                                        if (podeEquiparTipo) {
                                            if (heroi.getArma() == null || armaLargada.getDano() > heroi.getArma().getDano()) {
                                                try {
                                                    heroi.equiparArma(armaLargada);
                                                    System.out.println(heroi.getNome() + " empunhou a " + armaLargada.getNomeCompleto() + "!");
                                                } catch (NivelInsuficienteException e) {
                                                    System.out.println("Você encontrou uma arma poderosa, mas " + e.getMessage());
                                                }
                                            } else {
                                                System.out.println("A " + armaLargada.getNomeCompleto() + " não é tão boa quanto a arma atual de " + heroi.getNome() + ". Ele decide não equipá-la.");
                                            }
                                        } else {
                                            System.out.println(heroi.getNome() + " encontra a arma, mas ela não é do seu estilo. (" + armaLargada.getNomeCompleto() + " ignorada).");
                                        }
                                    } else {
                                        System.out.println("O monstro não largou nenhuma arma digna. A sorte nem sempre sorri.");
                                    }
                                } else {
                                    System.out.println("A sorte não sorriu. Nada de valioso encontrado.");
                                }
                                InputManager.esperarEnter("Pressione ENTER para continuar...");
                                break;
                            case 2:
                                System.out.println("\n== Status Atual do Herói ==");
                                System.out.println(heroi.exibirStatus());
                                InputManager.esperarEnter("Pressione ENTER para voltar ao menu...");
                                break;
                            case 3: // Salvar Jogo
                                System.out.println("\n== Salvar Jogo ==");
                                String saveName = InputManager.lerString("Digite um nome para o jogo salvo (ou deixe em branco para nomear automaticamente)");
                                if (saveName.trim().isEmpty()) {
                                    saveName = "Aventura_" + System.currentTimeMillis(); // Nome único
                                }
                                this.nomeJogoSalvo = saveName; // Atualiza o nome de save da batalha
                                GerenciadorDePersistencia.salvarBatalha(this, this.nomeJogoSalvo);
                                System.out.println("Jogo '" + this.nomeJogoSalvo + "' salvo com sucesso!");
                                InputManager.esperarEnter("Pressione ENTER para continuar...");
                                continue; // Continua no menu pós-combate
                            case 4: // Desistir do Jogo
                                System.out.println("\nVocê decide que esta jornada foi o suficiente.");
                                System.out.println("O reino terá que esperar por outro herói...");
                                InputManager.fecharScanner();
                                System.exit(0);
                                break;
                            case 5: // Continuar Aventura
                                continuarPosCombate = false; // Sai do loop do menu pós-combate
                                break;
                        }
                    }
                } else { // Se o herói foi derrotado pelo monstro
                    break; // Sai do loop de monstros para verificar o estado do herói
                }
            }

            // 3. Verifica se a fase foi concluída
            if (heroi.estaVivo() && faseAtual.isConcluida()) {
                System.out.println("\n--------------------------------------------------------");
                System.out.println("VITÓRIA NA FASE! " + heroi.getNome() + " superou todos os desafios de " + faseAtual.getTipoDeCenario().getDescricao() + "!");
                System.out.println("--------------------------------------------------------");
            } else if (!heroi.estaVivo()) { // Se o herói morreu na fase
                break; // Sai do loop de fases
            }
        }

        // --- MENSAGEM FINAL ---
        if (heroi.estaVivo()) {
            System.out.println("\n==============================================");
            System.out.println("             VITÓRIA GRANDIOSA!");
            System.out.println("     O HERÓI CONQUISTOU TODOS OS DESAFIOS!");
            System.out.println("==============================================");
            System.out.println("A jornada de " + heroi.getNome() + " chegou ao fim, e a vitória é sua!");
            System.out.println("O reino está seguro, e seu nome será cantado por eras.");
            System.out.println("\nStatus final do herói, um verdadeiro campeão:");
            System.out.println(heroi.exibirStatus());
        } else { // Verifica de novo caso morra no último golpe ou durante uma fase
            System.out.println("\n==============================================");
            System.out.println("                 GAME OVER!");
            System.out.println("O herói foi esmagado pela força de seus inimigos.");
            System.out.println("Sua lenda termina aqui...");
            System.out.println("==============================================");
        }
    }
}