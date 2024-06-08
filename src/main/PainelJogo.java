package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class PainelJogo extends JPanel implements KeyListener {
    private BufferedImage backgroundImage;
    private BufferedImage personagemJogador;
    private String nomeJogador;

    private int jogadorX, jogadorY;
    private int jogadorVelocidade = 5;

    public PainelJogo(String imagePath, String nomeJogador) {
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        loadImage(imagePath);
        loadPersonagemJogador("C:\\Users\\kauai\\OneDrive\\Área de Trabalho\\AtariBoxingGame\\src\\main\\bonecoJogador.png");
        this.nomeJogador = nomeJogador;

        jogadorX = (640 - personagemJogador.getWidth()) / 2;  // Inicializa o personagem no centro
        jogadorY = (480 - personagemJogador.getHeight()) / 2;

        this.setFocusable(true);
        this.addKeyListener(this);
    }

    private void loadImage(String imagePath) {
        backgroundImage = ImageLoader.loadImage(imagePath);
        if (backgroundImage == null) {
            System.err.println("Imagem de fundo não encontrada.");
        }
    }

    private void loadPersonagemJogador(String imagePath) {
        personagemJogador = ImageLoader.loadImage(imagePath);
        if (personagemJogador == null) {
            System.err.println("BUGOU O SPRITEEEE");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), null);
        }
        if (personagemJogador != null) {
            g.drawImage(personagemJogador, jogadorX, jogadorY, null);  // Linha alterada
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

    @Override
    public void keyTyped(KeyEvent e) {}  // Linha alterada

    @Override
    public void keyPressed(KeyEvent e) {  // Linha alterada
        int key = e.getKeyCode();  // Linha alterada

        if (key == KeyEvent.VK_W) {  // Linha alterada
            jogadorY = Math.max(0, jogadorY - jogadorVelocidade);  // Move para cima
        }

        if (key == KeyEvent.VK_S) {  // Linha alterada
            jogadorY = Math.min(this.getHeight() - personagemJogador.getHeight(), jogadorY + jogadorVelocidade);  // Move para baixo
        }

        if (key == KeyEvent.VK_A) {  // Linha alterada
            jogadorX = Math.max(0, jogadorX - jogadorVelocidade);  // Move para esquerda
        }

        if (key == KeyEvent.VK_D) {  // Linha alterada
            jogadorX = Math.min(this.getWidth() - personagemJogador.getWidth(), jogadorX + jogadorVelocidade);  // Move para direita
        }

        repaint();  // Linha alterada
    }

    @Override
    public void keyReleased(KeyEvent e) {}  // Linha alterada
}
