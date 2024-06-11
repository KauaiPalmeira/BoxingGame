package main;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;

public class Texto extends Elemento {

    private Font fonte;

    public Texto(Font fonte) {
        this.fonte = fonte;
    }

    public void desenha(Graphics2D g, String texto) {
        desenha(g, texto, getPx(), getPy());
    }

    public void desenha(Graphics2D g, String texto, int px, int py) {
        if (getCor() != null)
            g.setColor(getCor());

        g.setFont(fonte);
        g.drawString(texto, px, py);
    }

    public Font getFonte() {
        return fonte;
    }

    public void setFonte(Font fonte) {
        this.fonte = fonte;
    }

    public void carregarFontePersonalizada(String caminhoFonte, float tamanho) {
        try {
            InputStream is = getClass().getResourceAsStream(caminhoFonte);
            if (is == null) {
                System.err.println("Erro ao carregar a fonte: " + caminhoFonte);
                return;
            }
            Font fontePersonalizada = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(tamanho);
            setFonte(fontePersonalizada);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }
}
