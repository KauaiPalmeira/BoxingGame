package main;

import java.awt.event.KeyEvent;
import java.util.Set;

public class Jogador1 extends JogadorBase {
    public Jogador1(int x, int y, int velocidade, String spritePath) {
        super(x, y, velocidade, spritePath);

        String[] golpeSprites = {
                "src/main/assets/BrancoGolpe01.png",
                "src/main/assets/BrancoGolpe02.png",
                "src/main/assets/BrancoGolpe03.png"
                                };
        this.golpePersonagem = new GolpePersonagem(this, golpeSprites);
    }

    @Override
    public void atualizarPosicao(Set<Integer> teclasPressionadas, int larguraTela, int alturaTela) {
        if (teclasPressionadas.contains(KeyEvent.VK_W)) {
            y = Math.max(0, y - velocidade);
        }
        if (teclasPressionadas.contains(KeyEvent.VK_S)) {
            y = Math.min(alturaTela - sprite.getHeight(), y + velocidade);
        }
        if (teclasPressionadas.contains(KeyEvent.VK_A)) {
            x = Math.max(0, x - velocidade);
        }
        if (teclasPressionadas.contains(KeyEvent.VK_D)) {
            x = Math.min(larguraTela - sprite.getWidth(), x + velocidade);
        }
    }
}
