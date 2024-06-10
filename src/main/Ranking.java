package main;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ranking {
    private final List<Jogador> jogadores;
    private static final String ARQUIVO_RANKING = "C:\\Users\\kauai\\OneDrive\\Área de Trabalho\\AtariBoxingGame\\src\\main\\TabelaRanking.txt";

    public Ranking() {
        jogadores = new ArrayList<>();
        carregarRanking();
    }

    public void adicionarJogador(String nome, int pontuacao) {
        jogadores.add(new Jogador(nome, pontuacao));
        Collections.sort(jogadores, Comparator.comparingInt(Jogador::getPontuacao).reversed());
        if (jogadores.size() > 10) {
            jogadores.remove(10);
        }
        salvarRanking();
    }

    public void exibirRanking(JFrame parentFrame, Runnable reiniciarFase) {
        String[] colunas = {"Posição", "Nome", "Pontuação"};
        String[][] dados = new String[jogadores.size()][3];

        for (int i = 0; i < jogadores.size(); i++) {
            Jogador jogador = jogadores.get(i);
            dados[i][0] = String.valueOf(i + 1);
            dados[i][1] = jogador.getNome();
            dados[i][2] = String.valueOf(jogador.getPontuacao());
        }

        JTable tabela = new JTable(dados, colunas);
        tabela.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(tabela);

        JButton reiniciarButton = new JButton("Reiniciar fase");
        reiniciarButton.addActionListener(e -> {
            reiniciarFase.run();
            ((JFrame) SwingUtilities.getWindowAncestor(reiniciarButton)).dispose();
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(reiniciarButton, BorderLayout.SOUTH);

        JFrame rankingFrame = new JFrame("Ranking");
        rankingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        rankingFrame.setSize(400, 300);
        rankingFrame.add(panel);
        rankingFrame.setLocationRelativeTo(parentFrame);
        rankingFrame.setVisible(true);
    }

    private void salvarRanking() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_RANKING))) {
            for (Jogador jogador : jogadores) {
                writer.write(jogador.getNome() + ";" + jogador.getPontuacao());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarRanking() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_RANKING))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 2) {
                    String nome = partes[0];
                    int pontuacao = Integer.parseInt(partes[1]);
                    jogadores.add(new Jogador(nome, pontuacao));
                }
            }
            Collections.sort(jogadores, Comparator.comparingInt(Jogador::getPontuacao).reversed());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Jogador {
        private final String nome;
        private final int pontuacao;

        public Jogador(String nome, int pontuacao) {
            this.nome = nome;
            this.pontuacao = pontuacao;
        }

        public String getNome() {
            return nome;
        }

        public int getPontuacao() {
            return pontuacao;
        }
    }
}
