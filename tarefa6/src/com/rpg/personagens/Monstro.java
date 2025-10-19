// src/com/rpg/personagens/Monstro.java
package com.rpg.personagens;

import com.rpg.combate.AcaoDeCombate;
import com.rpg.combate.Combatente;
import com.rpg.itens.Arma;
import com.rpg.itens.ArmaDropSpec;
import com.rpg.itens.Item;
import com.rpg.itens.Lootavel;

import com.rpg.personagens.monstros.Espirito; // Import adicionado para @XmlSeeAlso
import com.rpg.personagens.monstros.Goblin;   // Import adicionado para @XmlSeeAlso
import com.rpg.personagens.monstros.Vampiro;  // Import adicionado para @XmlSeeAlso

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSeeAlso; // JAXB: Para reconhecer subclasses
import jakarta.xml.bind.annotation.XmlTransient; // ADICIONADO: Import para @XmlTransient

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Classe abstrata para todos os inimigos do jogo.
 * Estende {@link Personagem} e implementa {@link Lootavel}, adicionando a
 * capacidade de conceder experiência e dropar itens ao ser derrotado.
 * Esta classe gerencia a lista de possíveis itens a serem dropados,
 * utilizando {@link ArmaDropSpec} para o conceito de agregação de recompensas.
 */
// JAXB: Indica que Monstro pode ter subclasses Goblin, Vampiro e Espirito
@XmlSeeAlso({Goblin.class, Vampiro.class, Espirito.class})
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Monstro extends Personagem implements Lootavel {
    @XmlElement
    protected int xpConcedido;
    @XmlElement(name = "armaDropSpec") // JAXB: Cada elemento da lista será um "armaDropSpec" no XML
    protected ArrayList<ArmaDropSpec> listaDeArmasParaLargar;
    @XmlElement
    protected int nivelFase;
    // A lista de ações de combate dos monstros usa instâncias estáticas para agregação compartilhada.
    // Por isso, não precisa ser serializada diretamente, mas recriada após a deserialização.
    @XmlTransient // ADICIONADO: Anotação para que JAXB ignore este campo
    protected List<AcaoDeCombate> acoes;

    /**
     * JAXB: Construtor sem argumentos exigido pelo JAXB para deserialização.
     * Como Monstro é uma classe abstrata, o construtor é protected.
     */
    protected Monstro() { // ALTERADO: Visibilidade para protected
        super();
        this.listaDeArmasParaLargar = new ArrayList<>();
        this.acoes = new ArrayList<>(); // Garante que a lista não seja null
    }

    /**
     * Construtor da classe abstrata Monstro.
     *
     * @param nome O nome do monstro.
     * @param pontosDeVida Os pontos de vida iniciais do monstro.
     * @param forca A força inicial do monstro.
     * @param agilidade A agilidade inicial do monstro.
     * @param xpConcedido A quantidade de experiência que o monstro concede.
     * @param listaDeArmasParaLargar Uma {@link ArrayList} de {@link ArmaDropSpec} que o monstro pode dropar.
     * @param nivelFase O nível da fase em que o monstro está, usado para escalonamento de loot.
     */
    public Monstro(String nome, int pontosDeVida, int forca, int agilidade, int
            xpConcedido, ArrayList<ArmaDropSpec> listaDeArmasParaLargar, int
            nivelFase) {
        super(nome, pontosDeVida, forca, agilidade);
        this.xpConcedido = xpConcedido;
        this.listaDeArmasParaLargar = listaDeArmasParaLargar;
        this.nivelFase = nivelFase;
        this.acoes = new ArrayList<>();
        inicializarAcoes(); // Popula a lista de ações específicas do monstro
    }

    /**
     * JAXB: Este método é chamado após a desserialização do objeto.
     * Sobrescreve para reinicializar as ações de combate dos monstros.
     * As ações de combate de monstro são estáticas (agregação compartilhada)
     * e não são serializadas. Precisam ser recriadas/re-adicionadas após a deserialização.
     */
    @Override
    public void initializeTransientFields() {
        super.initializeTransientFields(); // Chama a inicialização da superclasse
        if (this.acoes == null) {
            this.acoes = new ArrayList<>();
        } else {
            this.acoes.clear(); // Limpa para evitar duplicatas ou ações antigas
        }
        inicializarAcoes(); // Re-popula as ações com as instâncias estáticas
    }

    /**
     * Método abstrato que força as subclasses (Goblin, Vampiro e Espírito)
     * a definirem e inicializarem suas ações de combate.
     */
    protected abstract void inicializarAcoes();

    @Override
    public AcaoDeCombate escolherAcao(Combatente alvo) {
        if (acoes != null && !acoes.isEmpty()) {
            Random rand = new Random(); // Cria um novo Random pois o original não é serializado
            int indiceAcao = rand.nextInt(acoes.size());
            return acoes.get(indiceAcao);
        } else {
            System.out.println(this.getNome() + " observa, sem saber o que fazer!");
            return null;
        }
    }

    @Override
    public Item droparLoot() {
        if (listaDeArmasParaLargar == null || listaDeArmasParaLargar.isEmpty()) {
            System.out.println(nome + " não largou nenhuma arma.");
            return null;
        }
        Random rand = new Random(); // Cria um novo Random pois o original não é serializado
        List<ArmaDropSpec> availableSpecs = listaDeArmasParaLargar.stream()
                .filter(spec -> this.nivelFase >= spec.getMinLevel())
                .collect(Collectors.toList());

        if (availableSpecs.isEmpty()) {
            System.out.println(nome + " não largou nenhuma arma adequada ao seu nível nesta fase.");
            return null;
        }

        int idx = rand.nextInt(availableSpecs.size());
        ArmaDropSpec selectedSpec = availableSpecs.get(idx);
        Arma armaLargada = selectedSpec.instantiate(this.nivelFase);

        if (armaLargada != null) {
            System.out.println(nome + " largou uma arma! (" + armaLargada.getNomeCompleto() + ")");
        } else {
            System.out.println(nome + " tentou largar uma arma, mas falhou ao instanciar.");
        }
        return armaLargada;
    }

    public int getXpConcedido() {
        return xpConcedido;
    }

    public ArrayList<ArmaDropSpec> getListaDeArmasParaLargar() {
        return listaDeArmasParaLargar;
    }

    public void setListaDeArmasParaLargar(ArrayList<ArmaDropSpec> listaDeArmasParaLargar) {
        this.listaDeArmasParaLargar = listaDeArmasParaLargar;
    }

    public void apresentarDialogoEspecial() {
        // Implementação padrão vazia ou com uma mensagem genérica
    }

    @Override
    public String exibirStatus() {
        return super.exibirStatus() + ", XP Concedido = " + xpConcedido;
    }
}