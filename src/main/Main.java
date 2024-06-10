package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        String nomeJogador = JOptionPane.showInputDialog("Digite o nome do jogador:");
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Boxing Atari");

        PainelJogo painelJogo = new PainelJogo("C:\\Users\\kauai\\OneDrive\\Área de Trabalho\\AtariBoxingGame\\src\\main\\background.png", nomeJogador, window);
        window.getContentPane().add(painelJogo);

        window.setSize(640, 480);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
