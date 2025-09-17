// Elfo.java

// Agora modificado para implementar o uso de interfaces
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

    @Override
    protected void inicializarAcoes() {
        // Adicionando as ações específicas do Elfo à lista de ações
        // CONVENÇÃO: Índice 0 é o ataque básico, Índice 1 é o especial.
        this.acoes.add(new AtaqueArco());      // Adicionado na posição 0
        this.acoes.add(new FlechaMagica());    // Adicionado na posição 1
    }


    // Método para atualizar o arco (e consequentemente a arma equipada) com base no NÍVEL
    public void atualizarArco() {
        int nivelAtual = getNivel();
        Arma novaArmaPadrao; // A arma padrão que o Elfo pode criar neste nível

        // 1. Determina qual é a arma padrão para o nível atual
        if (nivelAtual < 2) { // Nível 1 usa Beta
            novaArmaPadrao = new ArcoBeta(configDanoBeta);
        } else if (nivelAtual < 3) { // Nível 2 usa Alpha
            novaArmaPadrao = new ArcoAlpha(configDanoAlpha);
        } else { // Nível 3 ou superior usa Sigma
            novaArmaPadrao = new ArcoSigma(configDanoSigma);
        }

        // 2. Compara a arma padrão com a arma atualmente equipada
        Arma armaAtual = this.getArma();

        if (armaAtual == null) {
            // CASO 1: O Elfo não tem arma. Equipa a nova arma padrão.
            System.out.println(this.getNome() + " atingiu um novo patamar e forja uma nova arma: " + novaArmaPadrao.getNomeCompleto() + "!");
            this.equiparArma(novaArmaPadrao);

        } else if (novaArmaPadrao.getDano() > armaAtual.getDano()) {
            // CASO 2: A nova arma padrão é mais forte que a atual.
            System.out.println("Com sua nova experiência, " + this.getNome() + " aprimora seu equipamento para uma " + novaArmaPadrao.getNomeCompleto() + "!");
            this.equiparArma(novaArmaPadrao);

        } else {
            // CASO 3: A arma atual (possivelmente de um drop) é melhor ou igual. Não faz nada.
            System.out.println(this.getNome() + " sente que poderia forjar uma " + novaArmaPadrao.getNomeCompleto() + ", mas sua arma atual (" + armaAtual.getNomeCompleto() + ") ainda é superior.");
        }

        // 3. Atualiza os atributos do Elfo com base na arma que ele REALMENTE tem equipada agora
        if (this.getArma() != null) {
            this.danoArcoBase = this.getArma().getDano();
            this.cura = (int) (0.02 * this.danoArcoBase);
        }
    }



    @Override
    public void ganharExperiencia(int exp) {
        // primeiro, chama o método da superclasse para processar o ganho de XP e o possível nível-up
        super.ganharExperiencia(exp);
        // dps atualiza o arco pq o nível pode ter mudado
        atualizarArco();
    }

   /*           Agora implementados via interface

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

    */

    public String getTipoDeArco() {
        return tipoDeArco; // Mantido, para indicar o tipo geral (Beta, Alpha, Sigma)
    }

    public int getDanoArco() {
        return (this.arma != null ? this.arma.getDano() : 0);
    }
}