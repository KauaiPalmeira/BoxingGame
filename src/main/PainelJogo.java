package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class PainelJogo extends JPanel implements KeyListener {

    private final Jogador1 jogador1;
    private final Jogador2 jogador2;
    private final String nomeJogador1;
    private final String nomeJogador2;

    private final Set<Integer> teclasPressionadas = new HashSet<>();
    private int tempoRestante = 30;
    private Timer timer;
    private final EfeitosSonoros efeitosSonoros;
    private final Ranking ranking;
    private final JFrame window;

    private final CenarioJogo cenario;
    private final Menu menu;

    private boolean jogoIniciado = false;

    private final Font font;
    private final Texto textoJogador1;
    private final Texto textoJogador2;
    private final Texto textoTempo;

    private int jogador1TextoX = 10;
    private int jogador1TextoY = 30;
    private int jogador2TextoX = 400;
    private int jogador2TextoY = 30;
    private int tempoTextoX = 250;
    private int tempoTextoY = 40;

    public PainelJogo(String imagePath, String nomeJogador1, String nomeJogador2, JFrame window) {
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.cenario = new CenarioJogo(640, 480, imagePath);
        this.jogador1 = new Jogador1(126, 193, 5, "src/main/assets/bonecoJogador.png");
        this.jogador2 = new Jogador2(450, 193, 5, "src/main/assets/bonecoJogador2.png");
        this.jogador1.redimensionarSprite(70, 77);
        this.jogador2.redimensionarSprite(62, 77);
        this.nomeJogador1 = nomeJogador1;
        this.nomeJogador2 = nomeJogador2;
        this.window = window;

        this.font = new Font("ARCADECLASSIC", Font.PLAIN, 24);

        this.textoJogador1 = new Texto(font);
        this.textoJogador2 = new Texto(font);
        this.textoTempo = new Texto(font);

        this.efeitosSonoros = new EfeitosSonoros();

        this.ranking = new Ranking();

        this.setFocusable(true);
        this.addKeyListener(this);

        this.menu = new Menu("Opcoes");
        this.menu.addOpcoes("Iniciar", "Configuracoes", "Sair");
        this.menu.setSelecionado(true);
    }

    private void iniciarTimer() {
        this.timer = new Timer(1000, e -> {
            if (tempoRestante > 0) {
                tempoRestante--;
                repaint();
            } else {
                timer.stop();
                efeitosSonoros.tocarTempoAcabou();
                ranking.adicionarJogador(nomeJogador1, jogador1.getPontuacao());
                ranking.adicionarJogador(nomeJogador2, jogador2.getPontuacao());
                ranking.exibirRanking(window, this::reiniciarFase);
            }
        });
        this.timer.start();
    }

    private void reiniciarFase() {
        jogador1.x = 126;
        jogador1.y = 193;
        jogador1.pontuacao = 0;
        jogador2.x = 461;
        jogador2.y = 193;
        jogador2.pontuacao = 0;
        this.tempoRestante = 60;
        iniciarTimer();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (jogoIniciado) {
            cenario.desenhar((Graphics2D) g);
            jogador1.desenhar((Graphics2D) g);
            jogador2.desenhar((Graphics2D) g);

            jogador1.getGolpePersonagem().atualizarPosicao();
            jogador2.getGolpePersonagem().atualizarPosicao();

            jogador1.getGolpePersonagem().desenha((Graphics2D) g);
            jogador2.getGolpePersonagem().desenha((Graphics2D) g);

            g.setColor(Color.white);

            textoJogador1.desenha((Graphics2D) g, nomeJogador1 + "    SCORE    " + jogador1.getPontuacao(), jogador1TextoX, jogador1TextoY);
            textoJogador2.desenha((Graphics2D) g, nomeJogador2 + "    SCORE    " + jogador2.getPontuacao(), jogador2TextoX, jogador2TextoY);

            String tempo = String.format("%02d %02d", tempoRestante / 60, tempoRestante % 60);
            textoTempo.desenha((Graphics2D) g, tempo, tempoTextoX, tempoTextoY);
        } else {
            g.setColor(Color.black);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            menu.centralizar(this.getWidth(), this.getHeight(), (Graphics2D) g);
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
            jogador1.atualizarPosicao(teclasPressionadas, this.getWidth(), this.getHeight());
            jogador2.atualizarPosicao(teclasPressionadas, this.getWidth(), this.getHeight());

            if (e.getKeyCode() == KeyEvent.VK_F) {
                jogador1.getGolpePersonagem().iniciarAnimacao();
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                jogador2.getGolpePersonagem().iniciarAnimacao();
            }
            if (jogador1.getGolpePersonagem().checarColisao(jogador2)) {
                jogador2.x += 40;
            }
            if (jogador2.getGolpePersonagem().checarColisao(jogador1)) {
                jogador1.x -= 40;
            }
            if (teclasPressionadas.contains(KeyEvent.VK_W) || teclasPressionadas.contains(KeyEvent.VK_S) ||
                    teclasPressionadas.contains(KeyEvent.VK_A) || teclasPressionadas.contains(KeyEvent.VK_D)) {
                jogador1.incrementarPontuacao();
            }
            if (teclasPressionadas.contains(KeyEvent.VK_UP) || teclasPressionadas.contains(KeyEvent.VK_DOWN) ||
                    teclasPressionadas.contains(KeyEvent.VK_LEFT) || teclasPressionadas.contains(KeyEvent.VK_RIGHT)) {
                jogador2.incrementarPontuacao();
            }

            if (teclasPressionadas.contains(KeyEvent.VK_W) || teclasPressionadas.contains(KeyEvent.VK_S) ||
                    teclasPressionadas.contains(KeyEvent.VK_A) || teclasPressionadas.contains(KeyEvent.VK_D)) {
                jogador1.incrementarPontuacao();
            }
            if (teclasPressionadas.contains(KeyEvent.VK_UP) || teclasPressionadas.contains(KeyEvent.VK_DOWN) ||
                    teclasPressionadas.contains(KeyEvent.VK_LEFT) || teclasPressionadas.contains(KeyEvent.VK_RIGHT)) {
                jogador2.incrementarPontuacao();
            }
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        teclasPressionadas.remove(e.getKeyCode());
    }

    private void iniciarJogo() {
        jogoIniciado = true;
        iniciarTimer();
        efeitosSonoros.tocarMusicaDeFundo();
    }
}
