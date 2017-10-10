package tela;

import java.util.Arrays;
import javafx.scene.control.TextField;

public interface Formulario {

    boolean validar();

    void submeter();

    void clearForm();

    default void limparCampos(TextField... campos) {
        Arrays.stream(campos).forEach(campo -> campo.setText(""));
    }
}
