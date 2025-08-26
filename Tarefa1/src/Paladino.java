public class Paladino extends Heroi {
    
    // construtor

    public Paladino(String nome, int pontosDeVida, int forca, int nivel, int experiencia) {
        super(nome, pontosDeVida, forca, nivel, experiencia);
    }

    // metodos (copilot)

    @Override
    public void atacar(Personagem alvo) {
        int dano = 2 * (int)(Math.random() * 10 + 1);
        alvo.receberDano(dano);
        System.out.println("Paladino atacou " + alvo.exibirStatus() + " com dano de " + dano);
    }
}
