/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferramentas;

import java.util.Arrays;
import java.util.stream.Stream;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public abstract class KeyController {

    public static void intervaloNumericoPermitido(int limitCaracteres, long inicio, long fim, TextField campoTexto) {

        campoTexto.setOnKeyTyped(evento -> {
            if (!evento.getCharacter().matches("[0-9]")) {
                evento.consume();
            }
        });

        campoTexto.setOnKeyPressed(
                evento -> {
                    if (campoTexto.getText().length() > limitCaracteres - 1) {
                        String dado = campoTexto.getText().substring(0, limitCaracteres - 1);
                        campoTexto.textProperty().setValue(dado);
                        campoTexto.positionCaret(limitCaracteres);
                    }
                }
        );

        campoTexto.setOnKeyReleased(evento -> {
            if (!campoTexto.getText().isEmpty()) {
                double valor = Double.parseDouble(campoTexto.getText());
                if (!(inicio <= valor && valor <= fim)) {
                    campoTexto.textProperty().setValue("");
                    Mensagem.aviso("Valor fora do intervalo numérico permitido: de " + inicio + " a " + fim + " ");
                }
            }
        });

    }

    public static void campoNominal(TextField campoTexto) {
        campoTexto.setOnKeyTyped(evento -> {
            String letra = evento.getCharacter();
            String texto = campoTexto.getText();
            if (!letra.matches("(?i)[a-z ]")) {
                evento.consume();
            }
            if (texto.isEmpty() && letra.equals(" ")) {
                evento.consume();
            }
            if (texto.endsWith(" ") && letra.equals(" ")) {
                evento.consume();
            }
        });
    }

    public static void compartilharSubmit(Button button, EventHandler<ActionEvent> actionEvent, TextField... camposDeTexto) {
        if (button != null) {
            button.setOnAction(actionEvent);
        }
        Arrays.stream(camposDeTexto).forEach(botao -> botao.setOnAction(actionEvent));
    }

    /**
     * Este método é uma referência ao filme A Sombra e a Escuridão, já que os
     * campos não apresentam uma hierarquia, atuando em mutualismo (se ajudam)
     * Função: quando um campo de texto estiver em uso, será limpo
     *
     * @param ordem String a ser inserida no campo de texto
     * @param campoTextoSombra se digitar neste campo o campoEscuridao insere a
     * String ordem
     * @param campoTextoEscuridao se digitar neste campo, o campoTextoSombra é
     * limpo
     */
    public static void entrelacarAlternar(String ordem, TextField campoTextoSombra, TextField campoTextoEscuridao) {
        campoTextoSombra.setOnKeyReleased(onRelease -> campoTextoEscuridao.textProperty().setValue(ordem));
        campoTextoSombra.setOnKeyReleased(onRelease -> campoTextoEscuridao.textProperty().setValue(ordem));
    }

    public static void entrelacarDominar(String ordem, TextField... camposDeTexto) {
        Stream<TextField> campos = Arrays.stream(camposDeTexto);
        campos.forEach(campoDominante -> campoDominante.setOnKeyReleased(
                onRelease -> campos
                        .filter(campoDominado -> !campoDominante.equals(campoDominado))
                        .forEach(dominado -> dominado.textProperty().setValue(ordem))
        ));
    }

}
