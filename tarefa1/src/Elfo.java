public class Elfo extends Heroi {


    // atributos

    private Arcos arcos; // atribui um arco ao elfo

    private String tipoDeArco; // indica o arco utilizado

    private int danoArco; // dano atual do arco 


    // construtor
    public Elfo(String nome, int pontosDeVida, int forca, int agilidade, int nivel, int experiencia, Arcos arcos) {
        super(nome, pontosDeVida, forca, agilidade, nivel, experiencia);
        this.arcos = arcos; // exemplo arcoBeta no inicio (ou quando for atualizar)
        atualizarArco(); // metodo atualizar arco se subir de experiencia (não sei se precisa ser no construtor)
    }


    // métodos


    // Atualiza o arco conforme o nível/experiência

    public void atualizarArco() {

        int experiencia = getExperiencia(); // vai verificar experiência do elfo

        if (experiencia < 100) { 
            tipoDeArco = "Beta";
            danoArco = arcos.getDanoBeta();
        } else if (experiencia < 200) {
            tipoDeArco = "Alpha";
            danoArco = arcos.getDanoAlpha();
        } else {
            tipoDeArco = "Sigma";
            danoArco = arcos.getDanoSigma();
        }
    }

    @Override
    public void ganharExperiencia(int exp) {
        super.ganharExperiencia(exp);
        atualizarArco(); // chama atualizar arco a cada vez que ele (elfo) ganhar experiencia
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