package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class GolpePersonagem extends Elemento {
    private final JogadorBase jogador;
    private BufferedImage[] sprites;
    private int frameIndex = 0;
    private boolean animacaoAtiva = false;
    private Timer timer;
    private EfeitosSonoros efeitosSonoros;

    public GolpePersonagem(JogadorBase jogador, String[] spritePaths) {
        this.jogador = jogador;
        this.sprites = new BufferedImage[spritePaths.length];
        for (int i = 0; i < spritePaths.length; i++) {
            sprites[i] = ImageLoader.loadImage(spritePaths[i]);
        }
        this.setLargura(sprites[0].getWidth());
        this.setAltura(sprites[0].getHeight());
        this.setCor(null);
        this.efeitosSonoros = new EfeitosSonoros();
    }

    public void iniciarAnimacao() {
        if (animacaoAtiva) return;

        animacaoAtiva = true;
        frameIndex = 0;
        this.setAtivo(true);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (frameIndex < sprites.length) {
                    frameIndex++;
                    if (frameIndex == sprites.length) {
                        animacaoAtiva = false;
                        setAtivo(false);
                        timer.cancel();
                    }
                }
            }
        }, 0, 200);
    }

    @Override
    public void desenha(Graphics2D g) {
        if (animacaoAtiva && frameIndex < sprites.length) {
            BufferedImage currentSprite = sprites[frameIndex];
            g.drawImage(currentSprite, getPx(), getPy(), null);
        }
    }

    public void atualizarPosicao() {
        if (jogador instanceof Jogador1) {
            setPx(jogador.x + jogador.sprite.getWidth());
            setPy(jogador.y);
        } else if (jogador instanceof Jogador2) {
            setPx(jogador.x - getLargura());
            setPy(jogador.y);
        }
    }

    public boolean checarColisao(JogadorBase outroJogador) {
        boolean colidiu = isAtivo() && getPx() < outroJogador.x + outroJogador.sprite.getWidth() &&
                getPx() + getLargura() > outroJogador.x &&
                getPy() < outroJogador.y + outroJogador.sprite.getHeight() &&
                getPy() + getAltura() > outroJogador.y;
        if (colidiu) {
            efeitosSonoros.tocarSomDoHit();
            efeitosSonoros.tocarMachucadoHit();
        }
        return colidiu;
    }
}
