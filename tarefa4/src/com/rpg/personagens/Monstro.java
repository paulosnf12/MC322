package com.rpg.personagens; // Declaração do pacote para monstros

import com.rpg.combate.AcaoDeCombate;
import com.rpg.combate.Combatente;
import com.rpg.itens.Arma;
import com.rpg.itens.Item;
import com.rpg.itens.Lootavel; // Embora herdado de Personagem, mantido para clareza em casos de uso direto
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Agora implementa Lootavel (e herda Combatente de Personagem)
public abstract class Monstro extends Personagem implements Lootavel {
    protected int xpConcedido;
    protected ArrayList<Arma> listaDeArmasParaLargar;
    // 1. Atributo para armazenar as ações do monstro
    protected List<AcaoDeCombate> acoes;

    public Monstro(String nome, int pontosDeVida, int forca, int agilidade, int
                   xpConcedido, ArrayList<Arma> listaDeArmasParaLargar) {
        super(nome, pontosDeVida, forca, agilidade);
        this.xpConcedido = xpConcedido;
        // NOTA: A lista de armas passada aqui já deve ter sido populada no GeradorDeFases
        // levando em conta a dificuldade do jogo.
        this.listaDeArmasParaLargar = listaDeArmasParaLargar;
        // Inicializa a lista e chama o método que as subclasses irão implementar
        this.acoes = new ArrayList<>();
        inicializarAcoes();
    }

    // 2. Método abstrato que força as subclasses (Goblin, Vampiro e Espírito)
    // definirem suas ações
    protected abstract void inicializarAcoes();

    @Override
    public void escolherAcao(Combatente alvo) {
        // 3. IA do monstro: escolhe e executa uma ação aleatória
        if (acoes != null && !acoes.isEmpty()) {
            Random rand = new Random();
            int indiceAcao = rand.nextInt(acoes.size());
            AcaoDeCombate acaoEscolhida = acoes.get(indiceAcao);
            acaoEscolhida.executar(this, alvo);
        } else {
            System.out.println(this.getNome() + " observa, sem saber o que fazer!");
        }
    }

    @Override
    public Item droparLoot() { // Antigamente era largarArma()
        if (listaDeArmasParaLargar == null || listaDeArmasParaLargar.isEmpty()) {
            System.out.println(nome + " não largou nenhuma arma.");
            return null;
        }
        Random rand = new Random();
        int idx = rand.nextInt(listaDeArmasParaLargar.size());
        Arma armaLargada = listaDeArmasParaLargar.get(idx); // Pega uma Arma...
        System.out.println(nome + " largou uma arma! (" + // Removido "encantada" para ser mais genérico
                           armaLargada.getNomeCompleto() + ")"); // Alterado para exibir apenas o nome completo
        return armaLargada; // ...e a retorna como um Item.
    }

    public int getXpConcedido() {
        return xpConcedido;
    }

    public ArrayList<Arma> getListaDeArmasParaLargar() {
        return listaDeArmasParaLargar;
    }

    public void apresentarDialogoEspecial() {
        // Implementação padrão vazia ou com uma mensagem genérica
        // Pode ser sobrescrito por subclasses para diálogos específicos
    }

    @Override
    public String exibirStatus() {
        return super.exibirStatus() + ", XP Concedido = " + xpConcedido;
    }
}