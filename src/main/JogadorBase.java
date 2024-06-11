package main;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Set;

public abstract class JogadorBase {
    protected int x;
    protected int y;
    protected final int velocidade;
    protected int pontuacao;
    protected BufferedImage sprite;
    private int larguraSprite;
    private int alturaSprite;

    public JogadorBase(int x, int y, int velocidade, String spritePath) {
        this.x = x;
        this.y = y;
        this.velocidade = velocidade;
        this.pontuacao = 0;
        this.sprite = loadSprite(spritePath);
        if (this.sprite != null) {
            this.larguraSprite = this.sprite.getWidth();
            this.alturaSprite = this.sprite.getHeight();
        }
    }

    private BufferedImage loadSprite(String spritePath) {
        BufferedImage image = ImageLoader.loadImage(spritePath);
        if (image == null) {
            System.err.println("cade o sprite??? :(");
        }
        return image;
    }

    public void desenhar(Graphics2D g) {
        if (sprite != null) {
            g.drawImage(sprite.getScaledInstance(larguraSprite, alturaSprite, Image.SCALE_SMOOTH), x, y, null);
        }
    }

    public void redimensionarSprite(int largura, int altura) {
        this.larguraSprite = largura;
        this.alturaSprite = altura;
    }

    public abstract void atualizarPosicao(Set<Integer> teclasPressionadas, int larguraTela, int alturaTela);

    // Getters and setters
    public int getPontuacao() {
        return pontuacao;
    }

    public void incrementarPontuacao() {
        pontuacao++;
    }
}
