package main;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class CenarioJogo extends CenarioPadrao {
    private BufferedImage backgroundImage;

    public CenarioJogo(int largura, int altura, String imagePath) {
        super(largura, altura);
        carregar(imagePath);
    }

    @Override
    public void carregar(String imagePath) {
        backgroundImage = ImageLoader.loadImage(imagePath);
        if (backgroundImage == null) {
            System.err.println("Erro ao carregar imagem de fundo.");
        }
    }

    @Override
    public void carregar() {

    }

    @Override
    public void descarregar() {
        backgroundImage = null;
    }

    @Override
    public void atualizar() {

    }

    @Override
    public void desenhar(Graphics2D g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, largura, altura, null);
        }
    }
}
