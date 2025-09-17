// GolpeSagrado.java (NOVO ARQUIVO)
public class GolpeSagrado implements AcaoDeCombate {

    @Override
    public void executar(Combatente usuario, Combatente alvo) {
        if (usuario instanceof Paladino) {
            Paladino paladino = (Paladino) usuario;

            // Lógica do método usarHabilidadeEspecial() original
            int danoEspecial = (paladino.getArma() != null ? paladino.getArma().getDano() : 0) + paladino.getForca() + paladino.getCarisma();

            // A sorte alta aumenta o dano com base no carisma
            if (paladino.getSorte() > 0.4) {
                double boostDano = danoEspecial * 1.2; // Boost de 20% no dano
                danoEspecial = (int) Math.round(boostDano); // Arrendondar dano para inteiro
                System.out.println("A fé do Paladino potencializa o Golpe Sagrado! [atributo de sorte ativado!]");
            } else {
                System.out.println("Paladino usou Golpe Sagrado!");
            }
            
            alvo.receberDano(danoEspecial);
            System.out.println(paladino.getNome() + " causou " + danoEspecial + " de dano especial!");
        }
    }
}