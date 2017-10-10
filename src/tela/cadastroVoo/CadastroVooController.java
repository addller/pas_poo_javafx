package tela.cadastroVoo;

import ambiente.Terminal;
import ferramentas.KeyController;
import tela.TipoController;
import ferramentas.Mensagem;
import filtro.CampoFilter;
import filtro.MyFilter;
import filtro.StringFilter;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import rota.Escala;
import rota.Local;
import rota.Rota;
import tela.Controller;
import tela.Formulario;
import voo.Voo;

public final class CadastroVooController extends Controller implements Initializable, Formulario {

    @FXML
    private TextField txtCodigo, txtOrigem, txtDestino, txtMaximoPassageiros, txtEscala;
    @FXML
    private TableView<Local> tableEscala;
    @FXML
    private Button btnInserirEscala, btnVoltar, btnCadastrarVoo;

    @Override
    public boolean validar() {
        MyFilter[] testes = {
            campoVazio(txtCodigo, "código"),
            campoVazio(txtMaximoPassageiros, " número máximo de passageiros"),
            campoVazio(txtOrigem, "origem"),
            campoVazio(txtDestino, "destino"),
            new MyFilter() {
                @Override
                public boolean validar() {
                    return codigoVooExiste(Integer.parseInt(txtCodigo.getText()));
                }

                @Override
                public String getInconsistencia() {
                    return "Já há um vôo cadastrado sob o código: " + txtCodigo.getText();
                }
            }
        };

        MyFilter campoPendente = MyFilter.findFirst(testes);
        if (campoPendente == null) {
            return true;
        }
        Mensagem.aviso(campoPendente.getInconsistencia());
        return false;
    }

    public void cadastrarVoo() {

        Escala escala = new Escala();
        tableEscala.getItems().forEach(local -> escala.adicionarLocal(local));
        Rota rota = new Rota(new Local(txtOrigem.getText()), new Local(txtDestino.getText()), escala);
        Voo voo = new Voo(Integer.parseInt(txtCodigo.getText()), rota, Integer.parseInt(txtMaximoPassageiros.getText()));
        Terminal.getInstance().cadastrarVoo(voo);

        JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso");
        clearForm();
    }

    private static MyFilter campoVazio(TextField campoTexto, String nomeDoCampo) {
        return CampoFilter.campoVazio(campoTexto, nomeDoCampo + " do vôo");
    }

    public void voltar() {
        clearForm();
        ambienteExecucao.atualizarScene(TipoController.INICIAL);
    }

    @Override
    public void clearForm() {
        Arrays.asList(txtDestino, txtMaximoPassageiros, txtOrigem, txtEscala)
                .forEach(campo -> campo.textProperty().setValue(""));
        tableEscala.getItems().clear();
        auxiliarCodificacaoVoo();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Arrays.asList(txtDestino, txtOrigem, txtEscala).forEach(KeyController::campoNominal);
        KeyController.intervaloNumericoPermitido(11, 1L, 99999999999L, txtCodigo);
        KeyController.intervaloNumericoPermitido(4, 1L, 9000L, txtMaximoPassageiros);
        auxiliarCodificacaoVoo();

    }

    private void auxiliarCodificacaoVoo() {
        int codigoVooAuxiliar = Voo.getContadorDeVoos();
        while (codigoVooExiste(codigoVooAuxiliar)) {
            codigoVooAuxiliar++;
        }
        txtCodigo.setText(codigoVooAuxiliar + "");
    }

    private boolean codigoVooExiste(int codigo) {
        return Terminal.getInstance().findVooPorCodigo(codigo) != null;
    }

    private void efeitoAlerta(TextField campoTexto) {
        BooleanProperty alterna = new SimpleBooleanProperty();
        Timeline timer = new Timeline(new KeyFrame(Duration.millis(350), evento -> {
            alterna.set(!alterna.get());
            campoTexto.setStyle(alterna.get() ? "-fx-background-color: red;" : "-fx-text-box-border: rgba(150,150,150, 200);");
        }));
        timer.setCycleCount(6);
        timer.play();
    }

    @FXML
    private void inserirEscala() {
        if (StringFilter.temAlgo(txtEscala)) {
            tableEscala.getItems().add(new Local(txtEscala.getText()));
            txtEscala.textProperty().setValue("");
        } else {
            efeitoAlerta(txtEscala);
        }
    }

    @Override
    public void submeter() {
        if (validar()) {
            cadastrarVoo();
            voltar();
            auxiliarCodificacaoVoo();
        }
    }

    @Override
    public boolean sair() {
        voltar();
        return false;
    }
}
