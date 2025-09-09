public class Elfo extends Heroi {


    // atributos

    private Arcos arcos; // atribui um arco ao elfo

    private String tipoDeArco; // indica o arco utilizado

    private int danoArco; // dano atual do arco 

    private int cura; // atributo único --> faz o elfo se curar um pouco a cada ataque (5% do dano do arco)


    // construtor
    public Elfo(String nome, int pontosDeVida, int forca, int agilidade, int nivel, int experiencia, Arcos arcos) {
        super(nome, pontosDeVida, forca, agilidade, nivel, experiencia);
        this.arcos = arcos; // exemplo arcoBeta no inicio (ou quando for atualizar)
        atualizarArco(); // metodo atualizar arco se subir de experiencia
    }


    // métodos
    // Método para atualizar o arco com base no NÍVEL
    public void atualizarArco() {
        int nivelAtual = getNivel(); // obtém o nível atual do Elfo
        if (nivelAtual < 2) { // Nível 1 usa Beta
            tipoDeArco = "Beta";
            danoArco = arcos.getDanoBeta();
        } else if (nivelAtual < 3) { // Nível 2 usa Alpha
            tipoDeArco = "Alpha";
            danoArco = arcos.getDanoAlpha();
        } else { // Nível 3 ou superior usa Sigma
            tipoDeArco = "Sigma";
            danoArco = arcos.getDanoSigma();
        }
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
        int danoTotal = danoArco + forca;
        alvo.receberDano(danoTotal);
        System.out.println("Elfo atacou com arco " + tipoDeArco + " causando " + danoTotal + " de dano!");
    }

    @Override
    public void usarHabilidadeEspecial(Personagem alvo) {
        int danoEspecial = danoArco + getForca() + 20; // dano extra
        alvo.receberDano(danoEspecial);
        System.out.println("Elfo usou Flecha Mágica com arco " + tipoDeArco + " causando " + danoEspecial + " de dano especial!");
    }

    public String getTipoDeArco() {
        return tipoDeArco;
    }

    public int getDanoArco() {
        return danoArco;
    }


    // Adicione getters se necessário
}