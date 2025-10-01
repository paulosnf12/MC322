// Personagem.java
package com.rpg.personagens; // Pacote correto para a classe base de personagens

// Importa as classes necessárias dos novos pacotes
import com.rpg.combate.AcaoDeCombate;
import com.rpg.combate.Combatente;
import com.rpg.itens.Arma; // Personagem tem uma Arma

public abstract class Personagem implements Combatente {
    protected String nome;
    protected int pontosDeVida;
    protected int forca;
    protected int agilidade; // influencia na chance de acerto
    protected Arma arma; // **NOVO: arma equipada**

    // construtor
    public Personagem(String nome, int pontosDeVida, int forca, int agilidade) {
        this.nome = nome;
        this.pontosDeVida = pontosDeVida;
        this.forca = forca;
        this.agilidade = agilidade;
        this.arma = null; // Inicialmente sem arma equipada, pode ser ajustado em subclasses
    }

    // metodos interface Combatente
    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public boolean estaVivo() {
        return this.pontosDeVida > 0;
    }

    @Override
    public void receberDano(int dano) {
        this.pontosDeVida -= dano;
        if (this.pontosDeVida < 0) {
            this.pontosDeVida = 0;
        }
    }

    @Override
    public void receberCura(int cura) {
        this.pontosDeVida += cura;
    }

    @Override
    public abstract AcaoDeCombate escolherAcao(Combatente alvo);

    // metodos especificos da classe Personagem
    public void setpontosdevida(int pontosDeVida) {
        this.pontosDeVida = pontosDeVida;
    }

    public int getpontosdevida() {
        return pontosDeVida;
    }

    public int getForca() {
        return forca;
    }

    public String exibirStatus() {
        return "Nome = " + nome + ", Vida = " + pontosDeVida + "\nForca = " + forca +
               ", Agilidade = " + agilidade;
    }

    public int getAgilidade() {
        return agilidade;
    }

    public Arma getArma() {
        return arma;
    }

    // O método setArma() foi removido/substituído por equiparArma() em Heroi para controle de nível
    // public void setArma(Arma arma) {
    //     this.arma = arma;
    // }
}