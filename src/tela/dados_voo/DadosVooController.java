
package tela.dados_voo;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tela.Controller;
import tela.Formulario;
import tela.TipoController;
import voo.Voo;

public final class DadosVooController extends Controller implements Initializable, Formulario {

    @FXML
    private Label lblCodigoVoo, lblOrigem, lblDestino;
    @FXML
    private ListView<String> listEscalas;
    @FXML
    private Button btnVoltar, btnComprarPassagem;
    @FXML
    private TextField txtCodigoCliente;

    private Voo voo;
    private TipoController cenaAnterior;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void popularFormulario(TipoController cenaAnterior, Voo voo) {
        this.cenaAnterior = cenaAnterior;
        lblCodigoVoo.textProperty().setValue(voo.getCodigoVoo() + "");
        lblOrigem.textProperty().setValue(voo.getOrigem());
        lblDestino.textProperty().setValue(voo.getDestino());
        listEscalas.getItems().clear();

        voo.getRota()
                .getEscala()
                .getLocais()
                .forEach(local -> listEscalas.getItems().add(local.getNomeLocal()));

        ambienteExecucao.atualizarScene(TipoController.DADOS_VOO);
    }

    @FXML
    private void voltar() {
        limparCampos(txtCodigoCliente);
        ambienteExecucao.atualizarScene(cenaAnterior);
    }

    @Override
    public boolean sair() {
        voltar();
        return false;
    }

    @Override
    public boolean validar() {
      return false;
    }

    @Override
    public void submeter() {
        throw new UnsupportedOperationException("falta implementar");
    }

    @Override
    public void clearForm() {
        limparCampos(txtCodigoCliente);
    }

}
