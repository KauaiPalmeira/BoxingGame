package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class PainelJogo extends JPanel implements KeyListener {
    private final BufferedImage personagemJogador;
    private final String nomeJogador;

    private int jogadorX;
    private int jogadorY;
    private final int jogadorVelocidade = 5;
    private int pontuacao = 0;

    private final Set<Integer> teclasPressionadas = new HashSet<>();
    private int tempoRestante = 120;
    private Timer timer;
    private final EfeitosSonoros efeitosSonoros;
    private final Ranking ranking;
    private final JFrame window;

    private final CenarioJogo cenario;
    private final Menu menu;

    private boolean jogoIniciado = false;

    public PainelJogo(String imagePath, String nomeJogador, JFrame window) {
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.cenario = new CenarioJogo(640, 480, imagePath);
        this.personagemJogador = loadPersonagemJogador("C:\\Users\\kauai\\OneDrive\\Área de Trabalho\\AtariBoxingGame\\src\\main\\bonecoJogador.png");
        this.nomeJogador = nomeJogador;
        this.window = window;

        this.jogadorX = 126;
        this.jogadorY = 193;

        this.efeitosSonoros = new EfeitosSonoros();

        this.ranking = new Ranking();

        this.setFocusable(true);
        this.addKeyListener(this);

        this.menu = new Menu("Opções");
        this.menu.addOpcoes("Iniciar", "Configurações", "Sair");
        this.menu.setSelecionado(true);
    }

    private void iniciarTimer() {
        this.timer = new Timer(1000, e -> {
            if (tempoRestante > 0) {
                tempoRestante--;
                repaint();
            } else {
                timer.stop();
                ranking.adicionarJogador(nomeJogador, pontuacao);
                ranking.exibirRanking(window, this::reiniciarFase);
            }
        });
        this.timer.start();
    }

    private void reiniciarFase() {
        this.jogadorX = 126;
        this.jogadorY = 193;
        this.pontuacao = 0;
        this.tempoRestante = 120;
        iniciarTimer();
        repaint();
    }

    private BufferedImage loadPersonagemJogador(String imagePath) {
        BufferedImage image = ImageLoader.loadImage(imagePath);
        if (image == null) {
            System.err.println("BUGOU O SPRITEEEE");
        }
        return image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (jogoIniciado) {
            cenario.desenhar((Graphics2D) g);
            if (personagemJogador != null) {
                g.drawImage(personagemJogador, jogadorX, jogadorY, null);
            }

            int borderHeight = (int) (this.getHeight() * 0.07);
            g.setColor(Color.black);
            g.fillRect(0, 0, this.getWidth(), borderHeight);
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 20));

            g.drawString("Jogador: " + nomeJogador, 10, borderHeight - 10);
            FontMetrics fm = g.getFontMetrics();
            int playerTextWidth = fm.stringWidth("Jogador: " + nomeJogador);
            g.drawString("Pontuação: " + pontuacao, playerTextWidth + 20, borderHeight - 10);

            String tempo = String.format("%02d:%02d", tempoRestante / 60, tempoRestante % 60);
            int tempoWidth = fm.stringWidth(tempo);
            g.drawString(tempo, this.getWidth() - tempoWidth - 10, borderHeight - 10);
        } else {
            g.setColor(Color.black);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            menu.centralizar(this.getWidth(), this.getHeight(), (Graphics2D) g); //
            menu.desenha((Graphics2D) g);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        teclasPressionadas.add(e.getKeyCode());

        if (!jogoIniciado) {
            if (menu.isSelecionado()) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    menu.setTrocaOpcao(true);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    menu.setTrocaOpcao(false);
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (menu.getOpcaoTexto().equals("Iniciar")) {
                        iniciarJogo();
                    } else if (menu.getOpcaoTexto().equals("Sair")) {
                        System.exit(0);
                    }
                }
            }
        } else {
            atualizarPosicao();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        teclasPressionadas.remove(e.getKeyCode());
        if (jogoIniciado) {
            atualizarPosicao();
        }
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
        pontuacao++;
        repaint();
    }

    private void iniciarJogo() {
        jogoIniciado = true;
        iniciarTimer();
        efeitosSonoros.tocarMusicaDeFundo();
    }
}
