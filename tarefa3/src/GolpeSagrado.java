// GolpeSagrado.java (NOVO ARQUIVO)
public class GolpeSagrado implements AcaoDeCombate {

    @Override
    public void executar(Combatente usuario, Combatente alvo) {
        if (usuario instanceof Paladino) {
            Paladino paladino = (Paladino) usuario;

            // Lógica do método usarHabilidadeEspecial() original
            int danoEspecial = (paladino.getArma() != null ? paladino.getArma().getDano() : 0) + paladino.getForca() + 25;

            // A sorte alta aumenta o dano com base no carisma
            if (paladino.getSorte() > 0.6) {
                // Usando o novo getter que vamos criar
                danoEspecial += paladino.getCarisma();
                System.out.println("A fé do Paladino potencializa o Golpe Sagrado! Dano extra de " + paladino.getCarisma() + "!");
            } else {
                System.out.println("Paladino usou Golpe Sagrado!");
            }
            
            alvo.receberDano(danoEspecial);
            System.out.println(paladino.getNome() + " causou " + danoEspecial + " de dano especial!");
        }
    }
}