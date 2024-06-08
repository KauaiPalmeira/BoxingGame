package main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PainelJogo extends JPanel {
    private BufferedImage backgroundImage;
    private String nomeJogador;

    public PainelJogo(String imagePath, String nomeJogador) {
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        loadImage(imagePath);
        this.nomeJogador = nomeJogador;
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
        // Calcula a altura da borda como 10% da altura do painel
        int borderHeight = (int) (this.getHeight() * 0.07);

        // Desenha a borda no topo da tela
        g.setColor(Color.black);
        g.fillRect(0, 0, this.getWidth(), borderHeight);

        // Define a cor e a fonte para o texto
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 20));

        // Desenha o texto "Jogador: " no canto esquerdo
        g.drawString("Jogador: " + nomeJogador, 10, borderHeight - 10);
        // Desenha o texto "Pontuação: " no meio da borda
        FontMetrics fm = g.getFontMetrics();
        int playerTextWidth = fm.stringWidth("Jogador: " + nomeJogador);
        g.drawString("Pontuação: ", playerTextWidth + 20, borderHeight - 10);
    }
}
