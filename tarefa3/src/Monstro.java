//Monstro.java

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


// Agora implementa Combate e Lootavel
public abstract class Monstro extends Personagem implements Lootavel {
    protected int xpConcedido;
    protected ArrayList<Arma> listaDeArmasParaLargar;

    // 1. Atributo para armazenar as ações do monstro
    protected List<AcaoDeCombate> acoes;

    public Monstro(String nome, int pontosDeVida, int forca, int agilidade, int xpConcedido, ArrayList<Arma> listaDeArmasParaLargar) {
        super(nome, pontosDeVida, forca, agilidade);
        this.xpConcedido = xpConcedido;
        this.listaDeArmasParaLargar = listaDeArmasParaLargar;

        // Inicializa a lista e chama o método que as subclasses irão implementar
        this.acoes = new ArrayList<>();
        inicializarAcoes();
    }

    // 2. Método abstrato que força as subclasses (Goblin, Vampiro e Espirito) definirem suas ações
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

        System.out.println(nome + " largou uma arma encantada! (" + armaLargada.getNomeCompleto() + " [Encantada])");
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

    /*          Antigo método largarArma() substituído por droparLoot() da interface Lootavel
     
     ------------- CÓDIGO ANTIGO -------------

    // Método corrigido para largar uma arma aleatória
    public Arma largaArma() {
        if (listaDeArmasParaLargar == null || listaDeArmasParaLargar.isEmpty()) {
            System.out.println(nome + " não largou nenhuma arma.");
            return null; // Não há armas para largar
        }
        Random rand = new Random();
        int idx = rand.nextInt(listaDeArmasParaLargar.size());
        Arma armaLargada = listaDeArmasParaLargar.get(idx);
        System.out.println(nome + " largou uma arma! (Dano: " + armaLargada.getDano() + ", Nível Mínimo: " + armaLargada.getMinNivel() + ")");
        return armaLargada;
    }

     */

}
