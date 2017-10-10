package ferramentas;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;

public abstract class Imagem {

    private static final int ARGB = BufferedImage.TYPE_INT_ARGB;

    public static Set<Integer> encontrarCores(Image imagem) {

        Set<Integer> cores = new HashSet<>();
        int largura = imagem.getWidth(null),
                altura = imagem.getHeight(null);

        BufferedImage matriz = new BufferedImage(largura, altura, ARGB);
        Graphics2D tela = matriz.createGraphics();
        tela.drawImage(imagem, 0, 0, null);

        for (int coordY = 0; coordY < altura; coordY++) {
            for (int coordX = 0; coordX < largura; coordX++) {
                cores.add(matriz.getRGB(coordX, coordY));
            }
        }
        tela.dispose();
        return cores;
    }

    public static BufferedImage carregarImagem(String nomeArquivo) {
        try {
            return ImageIO.read(Imagem.class.getResource("/imagem/" + nomeArquivo));
        } catch (IOException ex) {
            Logger.getLogger(Imagem.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static BufferedImage redimensionar(Image imagem, int largura, int altura) {
        BufferedImage novaImagem = new BufferedImage(largura, altura, ARGB);
        Graphics2D tela = novaImagem.createGraphics();
        tela.drawImage(imagem, 0, 0, largura, altura, null);
        tela.dispose();
        return novaImagem;
    }

    public static BufferedImage redimensionar(Image imagem, int larguraAjustada) {
        int alturaAjustada = (imagem.getHeight(null) * larguraAjustada / imagem.getWidth(null));
        return redimensionar(imagem, larguraAjustada, alturaAjustada);
    }

    public static BufferedImage substituirCor(Image imagem, int corAntiga, Color corNova) {
        return substituirCor(imagem, corAntiga, corAntiga, corNova);
    }

    public static javafx.scene.image.Image toFXImage(BufferedImage imagem) {
        return SwingFXUtils.toFXImage(imagem, null);
    }

    public static void salvar(BufferedImage imagem, String nomeArquivo) {
        try {
            ImageIO.write(imagem, "png", new FileOutputStream("H:\\netbeans\\pas_poo\\src\\imagem\\" + nomeArquivo + ".png"));
        } catch (IOException ex) {
            Logger.getLogger(Imagem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static BufferedImage substituirCor(Image imagem, int inicioCorValor, int fimCorValor, Color corNova) {
        int largura = imagem.getWidth(null),
                altura = imagem.getHeight(null);

        BufferedImage novaImagem = new BufferedImage(largura, altura, ARGB);
        Graphics2D tela = novaImagem.createGraphics();
        tela.drawImage(imagem, 0, 0, null);
        tela.setColor(corNova);

        int cor;
        for (int coordY = 0; coordY < altura; coordY++) {
            for (int coordX = 0; coordX < largura; coordX++) {
                cor = novaImagem.getRGB(coordX, coordY);
                if (inicioCorValor <= cor && cor <= fimCorValor) {
                    tela.drawLine(coordX, coordY, coordX, coordY);
                }
            }
        }
        tela.dispose();
        return novaImagem;
    }
}
