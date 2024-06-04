package main;
import javax.swing.*;
import java.awt.*;

public class PainelJogo extends JPanel {
    final int tamanhoCacOriginal = 40; //
    final int scale = 10;

    final int tamanhoCac = tamanhoCacOriginal * scale;
    final int maxTelaColunas = 10;
    final int maxTelaLinhas = 10;
    final int telaLargura = tamanhoCac * maxTelaColunas;
    final int telaAltura = tamanhoCac * maxTelaLinhas;

    public PainelJogo(){
        this.setPreferredSize(new Dimension(telaLargura, telaAltura));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);

    }

}
