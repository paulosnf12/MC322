// Elfo.java

public class Elfo extends Heroi {

    // atributos
    // private Arcos arcos; // Antigo: atribui um objeto de configuração de arco ao elfo. Agora os danos são passados diretamente.
    private String tipoDeArco; // indica o arco utilizado (Beta, Alpha, Sigma)
    private int danoArcoBase; // dano base do arco, antes da força (agora reflete o dano da arma equipada)
    private int cura; // atributo único --> faz o elfo se curar um pouco a cada ataque (5% do dano do arco)

    // Novos atributos para armazenar os danos configuráveis de cada tipo de arco
    private int configDanoBeta;
    private int configDanoAlpha;
    private int configDanoSigma;

    // construtor
    // O construtor agora recebe os valores de dano para cada tipo de arco diretamente
    public Elfo(String nome, int pontosDeVida, int forca, int agilidade, int nivel, int experiencia,
                int danoBeta, int danoAlpha, int danoSigma) { // Parâmetro Arcos arcos foi removido
        super(nome, pontosDeVida, forca, agilidade, nivel, experiencia);
        // Armazena os danos de configuração para uso no método atualizarArco()
        this.configDanoBeta = danoBeta;
        this.configDanoAlpha = danoAlpha;
        this.configDanoSigma = danoSigma;
        atualizarArco(); // Garante que a arma inicial seja equipada
    }

    // métodos
    // Método para atualizar o arco (e consequentemente a arma equipada) com base no NÍVEL
    public void atualizarArco() {
        int nivelAtual = getNivel(); // obtém o nível atual do Elfo
        // int danoTemporario = 0; // Não mais necessário, o dano vem da instância de Arma
        Arma novaArma; // A arma agora é uma instância de uma subclasse concreta de Arma

        if (nivelAtual < 2) { // Nível 1 usa Beta
            tipoDeArco = "Beta";
            novaArma = new ArcoBeta(configDanoBeta); // Cria uma instância de ArcoBeta
        } else if (nivelAtual < 3) { // Nível 2 usa Alpha
            tipoDeArco = "Alpha";
            novaArma = new ArcoAlpha(configDanoAlpha); // Cria uma instância de ArcoAlpha
        } else { // Nível 3 ou superior usa Sigma
            tipoDeArco = "Sigma";
            novaArma = new ArcoSigma(configDanoSigma); // Cria uma instância de ArcoSigma
        }

        boolean mudouArma = (this.arma == null) || !this.arma.getNomeCompleto().equals(novaArma.getNomeCompleto());
        
        this.danoArcoBase = novaArma.getDano(); // Atualiza o dano base com o dano da arma criada
        // Equipar a nova instância de Arma. O nível mínimo é intrínseco à nova classe de Arma.
        this.equiparArma(novaArma);
        this.cura = (int) (0.02 * this.danoArcoBase); // Atualiza a cura (2% do dano do arco)

        if (mudouArma) {
        System.out.println(nome + " equipou a arma [" + novaArma.getNomeCompleto() + "].");
    }
        
/* 

        // Exibe o nome completo da arma, que é obtido da própria instância de Arma
        System.out.println(nome + " agora usa " + novaArma.getNomeCompleto() + " (Dano Base: " + danoArcoBase + ").");
        */
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
        // Usa o nome completo da arma para a mensagem
        System.out.println("Elfo atacou com " + (this.arma != null ? this.arma.getNomeCompleto() : "punhos") + " causando " + danoTotal + " de dano e curou " + cura + " de vida!");
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
        // Usa o nome completo da arma para a mensagem
        System.out.println("Elfo usou Flecha Mágica com " + (this.arma != null ? this.arma.getNomeCompleto() : "punhos") + " causando " + danoEspecial + " de dano especial!");
    }

    public String getTipoDeArco() {
        return tipoDeArco; // Mantido, para indicar o tipo geral (Beta, Alpha, Sigma)
    }

    public int getDanoArco() {
        return (this.arma != null ? this.arma.getDano() : 0);
    }
}