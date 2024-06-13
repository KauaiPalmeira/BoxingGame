package main;

import java.awt.event.KeyEvent;
import java.util.Set;

public class Jogador2 extends JogadorBase {
    private final String nomeJogador;

    public Jogador2(int x, int y, int velocidade, String spritePath) {
        super(x, y, velocidade, spritePath);
        this.nomeJogador = "Jogador 2";
        String[] golpeSprites = {
                "src/main/assets/GolpePreto01.png",
                "src/main/assets/GolpePreto02.png"
                                };
        this.golpePersonagem = new GolpePersonagem(this, golpeSprites);
    }

    @Override
    public void atualizarPosicao(Set<Integer> teclasPressionadas, int larguraTela, int alturaTela) {
        if (teclasPressionadas.contains(KeyEvent.VK_UP)) {
            y = Math.max(0, y - velocidade);
        }
        if (teclasPressionadas.contains(KeyEvent.VK_DOWN)) {
            y = Math.min(alturaTela - sprite.getHeight(), y + velocidade);
        }
        if (teclasPressionadas.contains(KeyEvent.VK_LEFT)) {
            x = Math.max(0, x - velocidade);
        }
        if (teclasPressionadas.contains(KeyEvent.VK_RIGHT)) {
            x = Math.min(larguraTela - sprite.getWidth(), x + velocidade);
        }
    }

    public String getNome() {

        return nomeJogador;
    }
}
