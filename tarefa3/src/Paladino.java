// Paladino.java

public class Paladino extends Heroi {
    // atributos
    // private Espada espadaConfig; // Antigo: Objeto de configuração da espada. Agora os danos são passados diretamente.
    private String tipoDeEspada; // indica o tipo de espada utilizada (Madeira, Ferro, Diamante)
    private int danoEspadaBase; // dano base da espada, antes da força (agora reflete o dano da arma equipada)
    private int carisma; // atributo único --> afeta a chance de ataque mágico

    // Novos atributos para armazenar os danos configuráveis de cada tipo de espada
    private int configDanoMadeira;
    private int configDanoFerro;
    private int configDanoDiamante;

    // construtor
    // O construtor agora recebe os valores de dano para cada tipo de espada diretamente
    public Paladino(String nome, int pontosDeVida, int forca, int agilidade, int nivel, int experiencia,
                    int danoMadeira, int danoFerro, int danoDiamante) { // Parâmetro Espada espadaConfig foi removido
        super(nome, pontosDeVida, forca, agilidade, nivel, experiencia);
        this.carisma = 26; // Valor inicial de carisma
        // Armazena os danos de configuração para uso no método atualizarEspada()
        this.configDanoMadeira = danoMadeira;
        this.configDanoFerro = danoFerro;
        this.configDanoDiamante = danoDiamante;
        atualizarEspada(); // Garante que a arma inicial seja equipada
    }


    @Override
    protected void inicializarAcoes() {
        // 2. Adicionando as ações específicas do Paladino
        this.acoes.add(new AtaqueEspada());
        this.acoes.add(new GolpeSagrado());
    }


    public int getCarisma() {
        return this.carisma;
    }

    public Arma getArma() {
        return this.arma;
    }

    // Atualiza a espada 
    public void atualizarEspada() {
        int nivelAtual = getNivel();
        // int danoTemporario = 0; // Não mais necessário, o dano vem da instância de Arma
        Arma novaArma; // A arma agora é uma instância de uma subclasse concreta de Arma

        if (nivelAtual < 2) { // Nível 1 usa Madeira
            tipoDeEspada = "Madeira";
            novaArma = new EspadaMadeira(configDanoMadeira); // Cria uma instância de EspadaMadeira
        } else if (nivelAtual < 3) { // Nível 2 usa Ferro
            tipoDeEspada = "Ferro";
            novaArma = new EspadaFerro(configDanoFerro); // Cria uma instância de EspadaFerro
        } else { // Nível 3 ou superior usa Diamante
            tipoDeEspada = "Diamante";
            novaArma = new EspadaDiamante(configDanoDiamante); // Cria uma instância de EspadaDiamante
        }

        this.danoEspadaBase = novaArma.getDano(); // Atualiza o dano base com o dano da arma criada

        // Equipar a nova instância de Arma. O nível mínimo é intrínseco à nova classe de Arma.
        this.equiparArma(novaArma);
        
        // Exibe o nome completo da arma, que é obtido da própria instância de Arma
        System.out.println(nome + " agora usa " + novaArma.getNomeCompleto() + " (Dano Base: " + danoEspadaBase + ").");
    }

    @Override
    public void ganharExperiencia(int exp) {
        super.ganharExperiencia(exp);
        atualizarEspada(); // Atualiza a espada caso o nível mude
    }

    /*              Métodos agora implementados via interface

    @Override
    public void atacar(Personagem alvo) { // ataque base (dano da espada + força)
        // Usa o dano da arma equipada (que foi atualizada por atualizarEspada) mais a força do Paladino
        int danoTotal = (this.arma != null ? this.arma.getDano() : 0) + getForca();
        alvo.receberDano(danoTotal);
        // Usa o nome completo da arma para a mensagem
        System.out.println("Paladino atacou com " + (this.arma != null ? this.arma.getNomeCompleto() : "punhos") + " causando " + danoTotal + " de dano!");
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

    */

    public String getTipoDeEspada() {
        return tipoDeEspada; // Mantido, para indicar o tipo geral (Madeira, Ferro, Diamante)
    }

    public int getDanoEspada() {
        return (this.arma != null ? this.arma.getDano() : 0);
    }
}
