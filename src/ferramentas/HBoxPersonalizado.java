package ferramentas;

import java.util.Arrays;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public class HBoxPersonalizado<Type> extends HBox {

    public static final Insets DEFAULT_INSET = new Insets(0, 0, 0, 0);
    private final int lenght;
    private final Label[] labels;
    private Type object;

    public HBoxPersonalizado(Type objeto, Label[] labels) {
        this(objeto, DEFAULT_INSET, labels);
    }

    public HBoxPersonalizado(Type objeto, Insets paddin, Label[] labels) {
        this.lenght = labels.length;
        this.labels = labels;
        this.object = objeto;
        setPadding(paddin);
        Arrays.stream(labels).forEach(HBoxPersonalizado.this.getChildren()::add);
    }

    public Label[] getLabels() {
        return labels;
    }

    public int getLenght() {
        return lenght;
    }

    public Type getGeneric() {
        return object;
    }

    public static Label[] gerarLabels(String... textos) {
        return gerarLabels(DEFAULT_INSET, textos);
    }

    public HBoxPersonalizado addClassToLabels(String... classeCSS) {
        for (String classe : classeCSS) {
            Arrays.stream(labels).forEach(label -> label.getStyleClass().add(classe));
        }
        return this;
    }

    public HBoxPersonalizado setStyleToLabels(String estilo) {
        Arrays.stream(labels).forEach(label -> label.setStyle(estilo));
        return this;
    }

    public void setLarguraToLabels(int index, double larguras) {
        try {
            labels[index].setPrefWidth(larguras);
        } catch (ArrayIndexOutOfBoundsException e) {
            Mensagem.aviso("A lista de Labels tem " + labels.length + " index " + index + " inecessível");
        }
    }

    public HBoxPersonalizado setLarguraToLabels(double[] larguras) {
        int limite = larguras.length > labels.length ? labels.length : larguras.length;
        for (int index = 0; index < limite; index++) {
            labels[index].setPrefWidth(larguras[index]);
        }
        return this;
    }

    private static Label[] gerarLabels(Insets padding, String... textos) {
        Label[] labels = new Label[textos.length];
        for (int index = 0; index < labels.length; index++) {
            labels[index] = new Label(textos[index]);
            labels[index].setPadding(padding);
        }
        return labels;
    }
    
}
