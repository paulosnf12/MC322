public class Elfo extends Heroi {


    // atributos

    private Arcos arcos; // terá um tipo de arco da classe Arco
    private String tipoDeArco; // string para indicar tipo de arco (nome)
    private int danoAtual;

    // construtor

    public Elfo(String nome, int pontosDeVida, int forca, int nivel, int experiencia, Arcos arcos) {
        super(nome, pontosDeVida, forca, nivel, experiencia);
        this.arcos = arcos; // exemplo arcoBeta no inicio (ou quando for atualizar)
        atualizarArco(); // metodo atualizar arco se subir de experiencia (não sei se precisa ser no construtor)
    }


    // métodos


    // Atualiza o arco conforme o nível/experiência
    public void atualizarArco() {
        int experiencia = getExperiencia(); // vai verificar experiência do elfo
        if (experiencia < 100) { 
            tipoDeArco = "Beta";
            danoAtual = arcos.getDanoBeta();
        } else if (experiencia < 200) {
            tipoDeArco = "Alpha";
            danoAtual = arcos.getDanoAlpha();
        } else {
            tipoDeArco = "Sigma";
            danoAtual = arcos.getDanoSigma();
        }
    }

    @Override
    public void ganharExperiencia(int exp) {
        super.ganharExperiencia(exp);
        atualizarArco(); // chama atualizar arco a cada vez que ele (elfo) ganhar experiencia
    }

    @Override
    public void atacar(Personagem alvo) {
        alvo.receberDano(danoAtual);
        System.out.println("Elfo atacou com arco " + tipoDeArco + " causando " + danoAtual + " de dano!");
    }

    // Adicione getters se necessário
}