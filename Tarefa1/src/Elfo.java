public class Elfo extends Heroi {

    private Arcos arcos;
    private String tipoDeArco;
    private int danoAtual;

    public Elfo(String nome, int pontosDeVida, int forca, int nivel, int experiencia, Arcos arcos) {
        super(nome, pontosDeVida, forca, nivel, experiencia);
        this.arcos = arcos;
        atualizarArco();
    }

    // Atualiza o arco conforme o nível/experiência
    public void atualizarArco() {
        int experiencia = getExperiencia(); // supondo que você tenha um getter em Heroi
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
        atualizarArco();
    }

    @Override
    public void atacar(Personagem alvo) {
        alvo.receberDano(danoAtual);
        System.out.println("Elfo atacou com arco " + tipoDeArco + " causando " + danoAtual + " de dano!");
    }

    // Adicione getters se necessário
}