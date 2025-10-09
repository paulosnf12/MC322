// src/com/rpg/personagens/Monstro.java
package com.rpg.personagens; // Declaração do pacote para monstros

import com.rpg.combate.AcaoDeCombate;
import com.rpg.combate.Combatente;
import com.rpg.itens.Arma;
import com.rpg.itens.ArmaDropSpec; // Importa a nova classe ArmaDropSpec
import com.rpg.itens.Item;
import com.rpg.itens.Lootavel;
import jakarta.xml.bind.annotation.XmlElement; // ADICIONADO PARA JAXB
import jakarta.xml.bind.annotation.XmlSeeAlso; // ADICIONADO PARA JAXB
import jakarta.xml.bind.annotation.XmlTransient; // ADICIONADO PARA JAXB
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors; // Para usar streams no droparLoot

/**
 * Classe abstrata para todos os inimigos do jogo.
 * Estende {@link Personagem} e implementa {@link Lootavel}, adicionando a
 * capacidade de conceder experiência e dropar itens ao ser derrotado.
 * Esta classe gerencia a lista de possíveis itens a serem dropados,
 * utilizando {@link ArmaDropSpec} para o conceito de agregação de recompensas.
 */
@XmlSeeAlso({com.rpg.personagens.monstros.Espirito.class, com.rpg.personagens.monstros.Goblin.class, com.rpg.personagens.monstros.Vampiro.class}) // ADICIONADO PARA JAXB: Indica as subclasses para JAXB
public abstract class Monstro extends Personagem implements Lootavel {
    /**
     * A quantidade de pontos de experiência que o monstro concede ao ser derrotado.
     */
    protected int xpConcedido;

    /**
     * Lista de especificações de armas ({@link ArmaDropSpec}) que o monstro
     * pode largar ao ser derrotado. Utiliza agregação, pois não armazena
     * instâncias de {@link Arma} diretamente, mas sim "receitas" para criá-las.
     */
    protected ArrayList<ArmaDropSpec> listaDeArmasParaLargar;

    /**
     * O nível da fase em que o monstro se encontra. Usado para escalonar
     * o dano das armas dropadas e filtrar drops por nível mínimo.
     */
    protected int nivelFase;

    /**
     * Lista de {@link AcaoDeCombate} disponíveis para o monstro.
     */
    protected List<AcaoDeCombate> acoes;

    // O atributo 'proximoAtaqueEhCritico' foi movido para a classe Personagem,
    // que é a superclasse, pois é um conceito aplicável a qualquer combatente.

    // ADICIONADO PARA JAXB: Construtor vazio necessário para a desserialização do JAXB.
    public Monstro() {
        super(); // Chama o construtor vazio da superclasse Personagem
        this.listaDeArmasParaLargar = new ArrayList<>(); // Inicializa para evitar NullPointerException
        this.acoes = new ArrayList<>(); // Inicializa para evitar NullPointerException
    }

    /**
     * Construtor da classe abstrata Monstro.
     *
     * @param nome O nome do monstro.
     * @param pontosDeVida Os pontos de vida iniciais do monstro.
     * @param forca A força inicial do monstro.
     * @param agilidade A agilidade inicial do monstro.
     * @param xpConcedido A quantidade de experiência que o monstro concede.
     * @param listaDeArmasParaLargar Uma {@link ArrayList} de {@link ArmaDropSpec}
     *                               que o monstro pode dropar.
     * @param nivelFase O nível da fase em que o monstro está, usado para escalonamento de loot.
     */
    public Monstro(String nome, int pontosDeVida, int forca, int agilidade, int
                   xpConcedido, ArrayList<ArmaDropSpec> listaDeArmasParaLargar, int nivelFase) {
        super(nome, pontosDeVida, forca, agilidade);
        this.xpConcedido = xpConcedido;
        // NOTA: A lista de ArmaDropSpec passada aqui já deve ter sido populada no GeradorDeFases
        // levando em conta a dificuldade do jogo.
        this.listaDeArmasParaLargar = listaDeArmasParaLargar;
        this.nivelFase = nivelFase; // Inicializa o nível da fase
        // Inicializa a lista e chama o método que as subclasses irão implementar
        this.acoes = new ArrayList<>();
        inicializarAcoes();
    }

    // Os métodos 'setProximoAtaqueCritico' e 'isProximoAtaqueCritico' foram
    // removidos daqui, pois a lógica foi movida para a classe Personagem,
    // que já implementa os métodos da interface Combatente.

    /**
     * Método abstrato que força as subclasses (Goblin, Vampiro e Espírito)
     * a definirem e inicializarem suas ações de combate.
     */
    protected abstract void inicializarAcoes();
    
    /**
     * Seleciona uma ação de combate de forma aleatória da sua lista de ações.
     * Simula a "IA" do monstro.
     * @param alvo O combatente alvo da ação (normalmente o herói).
     * @return A {@link AcaoDeCombate} escolhida para o turno. Retorna {@code null}
     *         se não houver ações disponíveis.
     */
    @Override
    public AcaoDeCombate escolherAcao(Combatente alvo) {
        if (acoes != null && !acoes.isEmpty()) {
            Random rand = new Random();
            int indiceAcao = rand.nextInt(acoes.size());
            return acoes.get(indiceAcao); // Retorna a ação escolhida
        } else {
            System.out.println(this.getNome() + " observa, sem saber o que fazer!");
            return null; // Retorna null se não houver ações disponíveis
        }
    }

    /**
     * Gera e retorna um item que o monstro deixa ao ser derrotado.
     * A escolha do item é aleatória entre as {@link ArmaDropSpec} compatíveis
     * com o {@code nivelFase} do monstro. Pode não retornar nenhum item.
     *
     * @return Um {@link Item} (especificamente uma {@link Arma})
     *         ou {@code null} se nenhum item for dropado ou se não houver
     *         especificações de drop válidas para o nível.
     */
    @Override
    // NOTA: O método droparLoot() não precisa de @XmlElement porque ele é uma ação, não um campo.
    // As listas que contêm as ArmaDropSpec é que serão serializadas.
    public Item droparLoot() {
        if (listaDeArmasParaLargar == null || listaDeArmasParaLargar.isEmpty()) {
            System.out.println(nome + " não largou nenhuma arma.");
            return null;
        }
        Random rand = new Random();

        // Filtra as especificações de armas que podem ser dropadas com base no nível da fase do monstro
        List<ArmaDropSpec> availableSpecs = listaDeArmasParaLargar.stream()
                                                .filter(spec -> this.nivelFase >= spec.getMinLevel())
                                                .collect(Collectors.toList());

        if (availableSpecs.isEmpty()) {
             System.out.println(nome + " não largou nenhuma arma adequada ao seu nível nesta fase.");
             return null;
        }

        int idx = rand.nextInt(availableSpecs.size());
        ArmaDropSpec selectedSpec = availableSpecs.get(idx);
        Arma armaLargada = selectedSpec.instantiate(this.nivelFase); // Instancia a arma usando o nível da fase

        if (armaLargada != null) {
            System.out.println(nome + " largou uma arma! (" + armaLargada.getNomeCompleto() + ")");
        } else {
            System.out.println(nome + " tentou largar uma arma, mas falhou ao instanciar.");
        }
        return armaLargada;
    }

    /**
     * Concede a experiência que este monstro vale ao ser derrotado.
     *
     * @return A quantidade de pontos de experiência.
     */
    @XmlElement // ADICIONADO PARA JAXB
    public int getXpConcedido() {
        return xpConcedido;
    }
    // ADICIONADO PARA JAXB: Setter necessário para desserialização
    public void setXpConcedido(int xpConcedido) {
        this.xpConcedido = xpConcedido;
    }

    /**
     * Retorna a lista de especificações de armas ({@link ArmaDropSpec})
     * que este monstro pode largar.
     * @return Uma {@link ArrayList} de {@link ArmaDropSpec}.
     */
    @XmlElement // ADICIONADO PARA JAXB
    public ArrayList<ArmaDropSpec> getListaDeArmasParaLargar() {
        return listaDeArmasParaLargar;
    }
    // ADICIONADO PARA JAXB: Setter necessário para desserialização
    public void setListaDeArmasParaLargar(ArrayList<ArmaDropSpec> listaDeArmasParaLargar) {
        this.listaDeArmasParaLargar = listaDeArmasParaLargar;
    }

    /**
     * Retorna o nível da fase em que o monstro se encontra.
     * @return O nível da fase.
     */
    @XmlElement // ADICIONADO PARA JAXB
    public int getNivelFase() {
        return nivelFase;
    }
    // ADICIONADO PARA JAXB: Setter necessário para desserialização
    public void setNivelFase(int nivelFase) {
        this.nivelFase = nivelFase;
    }

    /**
     * Retorna a lista de ações de combate disponíveis para o monstro.
     * Este campo é marcado como transiente para JAXB, pois as ações são agregadas e inicializadas após a desserialização.
     * @return A lista de {@link AcaoDeCombate}.
     */
    @XmlTransient // ADICIONADO PARA JAXB: JAXB ignora este campo na serialização
    public List<AcaoDeCombate> getAcoes() {
        return acoes;
    }
    // Não é necessário um setAcoes() público para JAXB, pois a lista é populada pelo inicializarAcoes().
    // Se o JAXB tentar chamá-lo, usará o campo diretamente ou precisará de um setter.
    // Para agregação compartilhada, geralmente este campo não é definido pelo XML.


    /**
     * Apresenta um diálogo especial do monstro. Este método pode ser
     * sobrescrito por subclasses para diálogos específicos.
     * A implementação padrão é vazia.
     */
    // NOTA: Métodos que representam comportamento e não dados não precisam de anotação JAXB.
    public void apresentarDialogoEspecial() {
        // Implementação padrão vazia ou com uma mensagem genérica
        // Pode ser sobrescrito por subclasses para diálogos específicos
    }

    /**
     * Gera uma representação textual do status atual do monstro,
     * incluindo informações da superclasse e a experiência concedida.
     * @return Uma String com as informações do status do monstro.
     */
    @Override
    public String exibirStatus() {
        return super.exibirStatus() + ", XP Concedido = " + xpConcedido;
    }
}