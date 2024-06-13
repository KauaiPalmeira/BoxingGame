package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        String nomeJogador1 = JOptionPane.showInputDialog("Digite o nome do jogador 1:");
        String nomeJogador2 = JOptionPane.showInputDialog("Digite o nome do jogador 2:");
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Boxing Atari");

        PainelJogo painelJogo = new PainelJogo("src/main/assets/background.png", nomeJogador1, nomeJogador2, window);
        window.getContentPane().add(painelJogo);

        window.setSize(640, 480);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
