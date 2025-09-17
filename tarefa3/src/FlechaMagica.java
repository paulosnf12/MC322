// FlechaMagica.java (NOVO ARQUIVO)
public class FlechaMagica implements AcaoDeCombate {

    @Override
    public void executar(Combatente usuario, Combatente alvo) {
        if (usuario instanceof Elfo) {
            Elfo elfo = (Elfo) usuario;
            
            // Mesma lógica antigo método usarHabilidadeEspecial()
            int danoEspecial = (elfo.getArma() != null ? elfo.getArma().getDano() : 0) + elfo.getForca() + 20;

            if (elfo.getSorte() > 0.4) {
                danoEspecial += 15;
                System.out.println("A sorte favorece! Flecha Mágica causa dano extra!");
            } 
            
            System.out.println(elfo.getNome() + " usou Flecha Mágica com " + (elfo.getArma() != null ? elfo.getArma().getNomeCompleto() : "punhos") + "!");
            alvo.receberDano(danoEspecial);
            System.out.println("A habilidade causou " + danoEspecial + " de dano especial!");
        }
    }
}