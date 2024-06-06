package main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PainelJogo extends JPanel {
    private BufferedImage backgroundImage;

    public PainelJogo(String imagePath) {
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        loadImage(imagePath);
    }

    private void loadImage(String imagePath) {
        backgroundImage = ImageLoader.loadImage(imagePath);
        if (backgroundImage == null) {
            System.err.println("Imagem de fundo não encontrada.");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), null);
        }
    }
}
