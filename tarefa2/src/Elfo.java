// Elfo.java

public class Elfo extends Heroi {

    // atributos
    private Arcos arcos; // atribui um objeto de configuração de arco ao elfo
    private String tipoDeArco; // indica o arco utilizado
    private int danoArcoBase; // dano base do arco, antes da força
    private int cura; // atributo único --> faz o elfo se curar um pouco a cada ataque (5% do dano do arco)

    // construtor
    public Elfo(String nome, int pontosDeVida, int forca, int agilidade, int nivel, int experiencia, Arcos arcos) {
        super(nome, pontosDeVida, forca, agilidade, nivel, experiencia);
        this.arcos = arcos;
        atualizarArco(); // Garante que a arma inicial seja equipada
    }

    // métodos
    // Método para atualizar o arco (e consequentemente a arma equipada) com base no NÍVEL
    public void atualizarArco() {
        int nivelAtual = getNivel(); // obtém o nível atual do Elfo
        int danoTemporario = 0;
        if (nivelAtual < 2) { // Nível 1 usa Beta
            tipoDeArco = "Beta";
            danoTemporario = arcos.getDanoBeta();
        } else if (nivelAtual < 3) { // Nível 2 usa Alpha
            tipoDeArco = "Alpha";
            danoTemporario = arcos.getDanoAlpha();
        } else { // Nível 3 ou superior usa Sigma
            tipoDeArco = "Sigma";
            danoTemporario = arcos.getDanoSigma();
        }
        this.danoArcoBase = danoTemporario;
        // Equipar uma nova instância de Arma com o dano e nível mínimo (para o elfo, minNivel é 1)
        this.equiparArma(new Arma(this.danoArcoBase, 1));
        this.cura = (int) (0.05 * this.danoArcoBase); // Atualiza a cura (50% do dano do arco)
        System.out.println(nome + " agora usa Arco " + tipoDeArco + " (Dano Base: " + danoArcoBase + ").");
    }

    @Override
    public void ganharExperiencia(int exp) {
        // primeiro, chama o método da superclasse para processar o ganho de XP e o possível nível-up
        super.ganharExperiencia(exp);
        // dps atualiza o arco pq o nível pode ter mudado
        atualizarArco();
    }

    @Override
    public void atacar(Personagem alvo) { // ataque base (dano do arco + força)
        // Usa o dano da arma equipada (que foi atualizada por atualizarArco) mais a força do Elfo
        int danoTotal = (this.arma != null ? this.arma.getDano() : 0) + forca;
        alvo.receberDano(danoTotal);
        this.receberDano(-cura); // Elfo se cura um pouco ao atacar
        System.out.println("Elfo atacou com arco " + tipoDeArco + " causando " + danoTotal + " de dano e curou " + cura + " de vida!");
    }

    @Override
    public void usarHabilidadeEspecial(Personagem alvo) {
        // Habilidade especial com bônus de sorte
        int danoEspecial = (this.arma != null ? this.arma.getDano() : 0) + getForca() + 20;
        if (getSorte() > 0.75) { // Se a sorte for alta, dano extra
            danoEspecial += 15;
            System.out.println("A sorte favorece! Flecha Mágica causa dano extra!");
        } else if (getSorte() < 0.25) { // Se a sorte for baixa, dano reduzido
            danoEspecial -= 5;
            System.out.println("O Elfo não está com sorte. Flecha Mágica causa menos dano.");
        }
        alvo.receberDano(danoEspecial);
        System.out.println("Elfo usou Flecha Mágica com arco " + tipoDeArco + " causando " + danoEspecial + " de dano especial!");
    }

    public String getTipoDeArco() {
        return tipoDeArco;
    }

    public int getDanoArco() {
        return (this.arma != null ? this.arma.getDano() : 0);
    }
}