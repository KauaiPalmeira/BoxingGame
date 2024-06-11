package main;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;

public class Menu extends Texto {

    private short idx;
    private String rotulo;
    private String[] opcoes;
    private boolean selecionado;

    public Menu(String rotulo) {
        // Chama o construtor da superclasse Texto com uma fonte padrão
        super(new Font("ARCADECLASSIC", Font.PLAIN, 16));
        this.rotulo = rotulo;
        setLargura(120);
        setAltura(20);
        setCor(Color.WHITE);
    }

    public void addOpcoes(String... opcao) {
        opcoes = opcao;
    }

    public void centralizar(int larguraPainel, int alturaPainel, Graphics2D g) {
        FontMetrics fm = g.getFontMetrics(getFonte());
        int totalAltura = getAltura() * (opcoes.length + 1);
        int totalWidth = 0;
        for (String opcao : opcoes) {
            int opcaoWidth = fm.stringWidth(opcao);
            if (opcaoWidth > totalWidth) {
                totalWidth = opcaoWidth;
            }
        }
        setLargura(totalWidth);
        setPx((larguraPainel - getLargura()) / 2);
        setPy((alturaPainel - totalAltura) / 2);
    }

    @Override
    public void desenha(Graphics2D g) {
        if (opcoes == null)
            return;

        g.setColor(getCor());
        FontMetrics fm = g.getFontMetrics(getFonte());
        int y = getPy();

        // Desenha o rótulo centralizado
        int rotuloWidth = fm.stringWidth(getRotulo());
        super.desenha(g, String.format("%s", getRotulo()), (getPx() + (getLargura() - rotuloWidth) / 2), y);

        // Desenha as opções centralizadas
        for (int i = 0; i < opcoes.length; i++) {
            String texto = (i == idx) ? String.format("<%s>", opcoes[i]) : opcoes[i];
            int textoWidth = fm.stringWidth(texto);
            super.desenha(g, texto, (getPx() + (getLargura() - textoWidth) / 2), y + getAltura() * (i + 1));
        }

        if (selecionado) {
            g.drawLine(getPx(), y + getAltura() * (idx + 1) + 5, getPx() + getLargura(), y + getAltura() * (idx + 1) + 5);
        }
    }

    public String getRotulo() {
        return rotulo;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }

    public int getOpcaoId() {
        return idx;
    }

    public String getOpcaoTexto() {
        return opcoes[idx];
    }

    public void setTrocaOpcao(boolean esquerda) {
        if (!isSelecionado() || !isAtivo())
            return;

        idx += esquerda ? -1 : 1;

        if (idx < 0)
            idx = (short) (opcoes.length - 1);
        else if (idx == opcoes.length)
            idx = 0;
    }
}
