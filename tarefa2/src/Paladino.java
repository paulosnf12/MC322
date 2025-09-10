//Paladino.java

public class Paladino extends Heroi {
    // atributos
    private Espada espadaConfig; // Objeto de configuração da espada
    private String tipoDeEspada; // indica o tipo de espada utilizada
    private int danoEspadaBase; // dano base da espada, antes da força
    private int carisma; // atributo único --> afeta a chance de ataque mágico

    // construtor
    public Paladino(String nome, int pontosDeVida, int forca, int agilidade, int nivel, int experiencia, Espada espadaConfig) {
        super(nome, pontosDeVida, forca, agilidade, nivel, experiencia);
        this.espadaConfig = espadaConfig;
        this.carisma = 10; // Valor inicial de carisma
        atualizarEspada(); // Garante que a arma inicial seja equipada
    }

    // Atualiza a espada conforme o nível/experiência (mas usaremos o nível para consistência)
    public void atualizarEspada() {
        int nivelAtual = getNivel();
        int danoTemporario = 0;
        if (nivelAtual < 2) { // Nível 1 usa Madeira
            tipoDeEspada = "Madeira";
            danoTemporario = espadaConfig.getDanoMadeira();
        } else if (nivelAtual < 3) { // Nível 2 usa Ferro
            tipoDeEspada = "Ferro";
            danoTemporario = espadaConfig.getDanoFerro();
        } else { // Nível 3 ou superior usa Diamante
            tipoDeEspada = "Diamante";
            danoTemporario = espadaConfig.getDanoDiamante();
        }
        this.danoEspadaBase = danoTemporario;
        // Equipar uma nova instância de Arma com o dano e nível mínimo (para o paladino, minNivel é 1)
        this.equiparArma(new Arma(this.danoEspadaBase, 1));
        System.out.println(nome + " agora usa Espada de " + tipoDeEspada + " (Dano Base: " + danoEspadaBase + ").");
    }

    @Override
    public void ganharExperiencia(int exp) {
        super.ganharExperiencia(exp);
        atualizarEspada(); // Atualiza a espada caso o nível mude
    }

    @Override
    public void atacar(Personagem alvo) { // ataque base (dano da espada + força)
        // Usa o dano da arma equipada (que foi atualizada por atualizarEspada) mais a força do Paladino
        int danoTotal = (this.arma != null ? this.arma.getDano() : 0) + getForca();
        alvo.receberDano(danoTotal);
        System.out.println("Paladino atacou com espada de " + tipoDeEspada + " causando " + danoTotal + " de dano!");
    }

    @Override
    public void usarHabilidadeEspecial(Personagem alvo) {
        // Habilidade especial com bônus de sorte e carisma
        int danoEspecial = (this.arma != null ? this.arma.getDano() : 0) + getForca() + 25;
        if (getSorte() > 0.6) { // Se a sorte for alta, tem chance de golpe sagrado mais forte
            danoEspecial += carisma; // Adiciona carisma ao dano
            System.out.println("A fé do Paladino potencializa o Golpe Sagrado! Dano extra de " + carisma + "!");
        } else {
            System.out.println("Paladino usou Golpe Sagrado!");
        }
        alvo.receberDano(danoEspecial);
        System.out.println("Paladino causou " + danoEspecial + " de dano especial!");
    }

    public String getTipoDeEspada() {
        return tipoDeEspada;
    }

    public int getDanoEspada() {
        return (this.arma != null ? this.arma.getDano() : 0);
    }
}