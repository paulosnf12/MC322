public class Paladino extends Heroi {

    // atributos
    private Espada espada; // atribui uma espada ao paladino
    private String tipoDeEspada; // indica o tipo de espada utilizada
    private int danoEspada; // dano atual da espada

    // construtor
    public Paladino(String nome, int pontosDeVida, int forca, int nivel, int experiencia, Espada espada) {
        super(nome, pontosDeVida, forca, nivel, experiencia);
        this.espada = espada;
        atualizarEspada();
    }

    // Atualiza a espada conforme o nível/experiência
    public void atualizarEspada() {
        int experiencia = getExperiencia();

        if (experiencia < 100) {
            tipoDeEspada = "Ferro";
            danoEspada = espada.getDanoFerro();
        } else if (experiencia < 200) {
            tipoDeEspada = "Prata";
            danoEspada = espada.getDanoPrata();
        } else {
            tipoDeEspada = "Ouro";
            danoEspada = espada.getDanoOuro();
        }
    }

    @Override
    public void ganharExperiencia(int exp) {
        super.ganharExperiencia(exp);
        atualizarEspada();
    }

    @Override
    public void atacar(Personagem alvo) { // ataque base (dano da espada + força)
        int danoTotal = danoEspada + getForca();
        alvo.receberDano(danoTotal);
        System.out.println("Paladino atacou com espada de " + tipoDeEspada + " causando " + danoTotal + " de dano!");
    }

    @Override
    public void usarHabilidadeEspecial(Personagem alvo) {
        int danoEspecial = danoEspada + getForca() + 25; // dano extra
        alvo.receberDano(danoEspecial);
        System.out.println("Paladino usou Golpe Sagrado com espada de " + tipoDeEspada + " causando " + danoEspecial + " de dano especial!");
    }

    public String getTipoDeEspada() {
        return tipoDeEspada;
    }

    public int getDanoEspada() {
        return danoEspada;
    }
}