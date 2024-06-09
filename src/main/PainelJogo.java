package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
import java.awt.image.BufferedImage;

public class PainelJogo extends JPanel implements KeyListener {
    private final BufferedImage backgroundImage;
    private final BufferedImage personagemJogador;
    private final String nomeJogador;

    private int jogadorX;
    private int jogadorY;
    private final int jogadorVelocidade = 5;

    private final Set<Integer> teclasPressionadas = new HashSet<>();
    private int tempoRestante = 120;
    private final Timer timer;
    private final EfeitosSonoros efeitosSonoros;

    //Inicializa o painel do jogo, configura imagem, som e o jogador
    public PainelJogo(String imagePath, String nomeJogador) {
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.backgroundImage = loadImage(imagePath);
        this.personagemJogador = loadPersonagemJogador("C:\\Users\\kauai\\OneDrive\\Área de Trabalho\\AtariBoxingGame\\src\\main\\bonecoJogador.png");
        this.nomeJogador = nomeJogador;

        this.jogadorX = 126;
        this.jogadorY = 193;

        this.timer = new Timer(1000, e -> {
            if (tempoRestante > 0) {
                tempoRestante--;
                repaint();
            }
        });
        this.timer.start();

        this.efeitosSonoros = new EfeitosSonoros();
        this.efeitosSonoros.tocarMusicaDeFundo();

        this.setFocusable(true);
        this.addKeyListener(this);
    }

    private BufferedImage loadImage(String imagePath) {
        BufferedImage image = ImageLoader.loadImage(imagePath);
        if (image == null) {
            System.err.println("Sem imagem de fundo pae..");
        }
        return image;
    }

    private BufferedImage loadPersonagemJogador(String imagePath) {
        BufferedImage image = ImageLoader.loadImage(imagePath);
        if (image == null) {
            System.err.println("BUGOU O SPRITEEEE");
        }
        return image;
    }


    // Método para desenhar os componentes no painel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), null);
        }
        if (personagemJogador != null) {
            g.drawImage(personagemJogador, jogadorX, jogadorY, null);
        }

        // Borda Header e texto dela
        int borderHeight = (int) (this.getHeight() * 0.07);
        g.setColor(Color.black);
        g.fillRect(0, 0, this.getWidth(), borderHeight);
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 20));

        g.drawString("Jogador: " + nomeJogador, 10, borderHeight - 10);
        FontMetrics fm = g.getFontMetrics();
        int playerTextWidth = fm.stringWidth("Jogador: " + nomeJogador);
        g.drawString("Pontuação: ", playerTextWidth + 20, borderHeight - 10);

        // Desenha o tempo
        String tempo = String.format("%d:%02d", tempoRestante / 60, tempoRestante % 60);
        int tempoTextWidth = fm.stringWidth(tempo);
        g.drawString(tempo, this.getWidth() - tempoTextWidth - 10, borderHeight - 10);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        teclasPressionadas.add(e.getKeyCode());
        atualizarPosicao();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        teclasPressionadas.remove(e.getKeyCode());
        atualizarPosicao();
    }

    private void atualizarPosicao() {
        if (teclasPressionadas.contains(KeyEvent.VK_W)) {
            jogadorY = Math.max(0, jogadorY - jogadorVelocidade);
        }
        if (teclasPressionadas.contains(KeyEvent.VK_S)) {
            jogadorY = Math.min(this.getHeight() - personagemJogador.getHeight(), jogadorY + jogadorVelocidade);
        }
        if (teclasPressionadas.contains(KeyEvent.VK_A)) {
            jogadorX = Math.max(0, jogadorX - jogadorVelocidade);
        }
        if (teclasPressionadas.contains(KeyEvent.VK_D)) {
            jogadorX = Math.min(this.getWidth() - personagemJogador.getWidth(), jogadorX + jogadorVelocidade);
        }
        repaint();
    }
}
