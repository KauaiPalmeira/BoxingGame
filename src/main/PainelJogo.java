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
    private final String nomeJogador;

    private final Set<Integer> teclasPressionadas = new HashSet<>();
    private int tempoRestante = 40;
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

    public PainelJogo(String imagePath, String nomeJogador, JFrame window) {
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.cenario = new CenarioJogo(640, 480, imagePath);
        this.jogador1 = new Jogador1(126, 193, 5, "C:\\Users\\kauai\\OneDrive\\Área de Trabalho\\AtariBoxingGame\\src\\main\\bonecoJogador.png");
        this.jogador2 = new Jogador2(450, 193, 5, "C:\\Users\\kauai\\OneDrive\\Área de Trabalho\\AtariBoxingGame\\src\\main\\bonecoJogador2.png");
        this.jogador1.redimensionarSprite(70, 77);
        this.jogador2.redimensionarSprite(62, 77);
        this.nomeJogador = nomeJogador;
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
                ranking.adicionarJogador(nomeJogador, jogador1.getPontuacao()); // considerando somente o jogador 1 por enquanto, mudar isso depois
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

            g.setColor(Color.white);

            textoJogador1.desenha((Graphics2D) g, nomeJogador + "    SCORE    " + jogador1.getPontuacao(), jogador1TextoX, jogador1TextoY);
            textoJogador2.desenha((Graphics2D) g, jogador2.getNome() + "    SCORE    " + jogador2.getPontuacao(), jogador2TextoX, jogador2TextoY);

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
