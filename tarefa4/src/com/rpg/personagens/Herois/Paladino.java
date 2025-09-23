// Paladino.java
package com.rpg.personagens.herois; // Pacote correto para heróis

import com.rpg.combate.AtaqueEspada;
import com.rpg.combate.GolpeSagrado;
import com.rpg.exceptions.NivelInsuficienteException; // Importa a exceção
import com.rpg.itens.Arma;
import com.rpg.itens.EspadaDiamante;
import com.rpg.itens.EspadaFerro;
import com.rpg.itens.EspadaMadeira;
import com.rpg.personagens.Heroi; // Paladino estende Heroi

public class Paladino extends Heroi {
    // atributos
    // private Espada espadaConfig; // Antigo: Objeto de configuração da espada. Agora os danos são passados diretamente.
    private String tipoDeEspada; // indica o tipo de espada utilizada (Madeira, Ferro, Diamante)
    private int danoEspadaBase; // dano base da espada, antes da força (agora reflete o dano da arma equipada)
    private int carisma; // atributo único --> afeta a chance de ataque mágico
    // Novos atributos para armazenar os danos configuráveis de cada tipo de espada
    private int configDanoMadeira;
    private int configDanoFerro;
    private int configDanoDiamante;

    // construtor
    // O construtor agora recebe os valores de dano para cada tipo de espada diretamente
    public Paladino(String nome, int pontosDeVida, int forca, int agilidade, int
                    nivel, int experiencia,
                    int danoMadeira, int danoFerro, int danoDiamante) { // Parâmetro Espada espadaConfig foi removido
        super(nome, pontosDeVida, forca, agilidade, nivel, experiencia);
        this.carisma = 26; // Valor inicial de carisma
        // Armazena os danos de configuração para uso no método atualizarEspada()
        this.configDanoMadeira = danoMadeira;
        this.configDanoFerro = danoFerro;
        this.configDanoDiamante = danoDiamante;
        atualizarEspada(); // Garante que a arma inicial seja equipada
    }

    @Override
    protected void inicializarAcoes() {
        // Adicionando as ações específicas do Paladino
        // CONVENÇÃO: Índice 0 é o ataque básico, Índice 1 é o especial.
        this.acoes.add(new AtaqueEspada()); // Adicionado na posição 0
        this.acoes.add(new GolpeSagrado()); // Adicionado na posição 1
    }

    public int getCarisma() {
        return this.carisma;
    }

    public Arma getArma() {
        return this.arma;
    }

    // Atualiza a espada
    public void atualizarEspada() {
        int nivelAtual = getNivel();
        Arma novaArmaPadrao; // A arma padrão que o Paladino pode criar neste nível

        // 1. Determina qual é a arma padrão para o nível atual
        if (nivelAtual < 2) { // Nível 1 usa Madeira
            novaArmaPadrao = new EspadaMadeira(configDanoMadeira);
        } else if (nivelAtual < 3) { // Nível 2 usa Ferro
            novaArmaPadrao = new EspadaFerro(configDanoFerro);
        } else { // Nível 3 ou superior usa Diamante
            novaArmaPadrao = new EspadaDiamante(configDanoDiamante);
        }

        // 2. Compara a arma padrão com a arma atualmente equipada
        Arma armaAtual = this.getArma();
        if (armaAtual == null) {
            // CASO 1: O Paladino não tem arma. Equipa a nova arma padrão.
            System.out.println(this.getNome() + " sente sua fe renovada e consagra uma " +
                               "nova arma: " + novaArmaPadrao.getNomeCompleto() + "!");
            try { // <-- MUDANÇA AQUI: try-catch para NivelInsuficienteException
                this.equiparArma(novaArmaPadrao);
            } catch (NivelInsuficienteException e) {
                System.out.println("Erro ao equipar arma padrao para " + this.getNome() + ": " + e.getMessage());
                // Em um cenário de arma padrão, é improvável que isso ocorra,
                // mas a exceção precisa ser tratada ou propagada.
            }
        } else if (novaArmaPadrao.getDano() > armaAtual.getDano()) {
            // CASO 2: A nova arma padrão é mais forte que a atual.
            System.out.println("Guiado por sua conviccao, " + this.getNome() + " forja uma " +
                               "arma mais poderosa: uma " + novaArmaPadrao.getNomeCompleto() + "!");
            try { // <-- MUDANÇA AQUI: try-catch para NivelInsuficienteException
                this.equiparArma(novaArmaPadrao);
            } catch (NivelInsuficienteException e) {
                System.out.println("Erro ao aprimorar arma para " + this.getNome() + ": " + e.getMessage());
            }
        } else {
            // CASO 3: A arma atual (possivelmente de um drop) é melhor ou igual. Não faz nada.
            System.out.println(this.getNome() + " poderia forjar uma " +
                               novaArmaPadrao.getNomeCompleto() + ", mas sua arma atual (" +
                               armaAtual.getNomeCompleto() + ") ja serve bem ao seu proposito sagrado.");
        }
        // 3. Atualiza o dano base do Paladino com base na arma que ele REALMENTE tem equipada
        if (this.getArma() != null) {
            this.danoEspadaBase = this.getArma().getDano();
        }
    }

    @Override
    public void ganharExperiencia(int exp) {
        super.ganharExperiencia(exp);
        atualizarEspada(); // Atualiza a espada caso o nível mude
    }

    /*
    Métodos agora implementados via interface (AcaoDeCombate)
    @Override
    public void atacar(Personagem alvo) { // ataque base (dano da espada + força)
    // Usa o dano da arma equipada (que foi atualizada por atualizarEspada) mais a
    força do Paladino
    int danoTotal = (this.arma != null ? this.arma.getDano() : 0) + getForca();
    alvo.receberDano(danoTotal);
    // Usa o nome completo da arma para a mensagem
    System.out.println("Paladino atacou com " + (this.arma != null ?
    this.arma.getNomeCompleto() : "punhos") + " causando " + danoTotal + " de dano!");
    }
    @Override
    public void usarHabilidadeEspecial(Personagem alvo) {
    // Habilidade especial com bônus de sorte e carisma
    int danoEspecial = (this.arma != null ? this.arma.getDano() : 0) + getForca()
    + 25;
    if (getSorte() > 0.6) { // Se a sorte for alta, tem chance de golpe sagrado
    mais forte
    danoEspecial += carisma; // Adiciona carisma ao dano
    System.out.println("A fé do Paladino potencializa o Golpe Sagrado! Dano
    extra de " + carisma + "!");
    } else {
    System.out.println("Paladino usou Golpe Sagrado!");
    }
    alvo.receberDano(danoEspecial);
    System.out.println("Paladino causou " + danoEspecial + " de dano especial!");
    }
    */

    public String getTipoDeEspada() {
        return tipoDeEspada; // Mantido, para indicar o tipo geral (Madeira, Ferro, Diamante)
    }

    public int getDanoEspada() {
        return (this.arma != null ? this.arma.getDano() : 0);
    }
}