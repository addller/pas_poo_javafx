package tela.cadastro_passageiro;

import ambiente.Terminal;
import ferramentas.Imagem;
import ferramentas.KeyController;
import tela.TipoController;
import ferramentas.Mensagem;
import filtro.CampoFilter;
import filtro.MyFilter;
import filtro.StringFilter;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import tela.Controller;
import tela.Formulario;
import tela.inicial.InicialController;
import usuario.Passageiro;
import voo.Voo;

public class CadastroPassageiroController extends Controller implements Formulario {

    @FXML
    private TextField txtNomeCliente, txtOrigem, txtDestino;
    @FXML
    private Button btnComprarPassagem, btnVoltar;
    @FXML
    private TableView<LinhaTabelaVoo> tableVoosDisponiveis;
    @FXML
    private ImageView imagePesquisar;
    private final BooleanProperty temVoo = new SimpleBooleanProperty();
    private final ObservableList<Voo> listVoosDisponiveis = FXCollections.observableArrayList();

    private final Terminal terminal = Terminal.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Arrays.asList(txtNomeCliente, txtOrigem, txtDestino).forEach(KeyController::campoNominal);
        configurarTabelaVoo();
    }

    @FXML
    private void lupaAtiva() {
        imagePesquisar.setImage(Imagem.toFXImage(Imagem.carregarImagem("lupa_25_on.png")));

    }

    @FXML
    private void lupaInativa() {
        imagePesquisar.setImage(Imagem.toFXImage(Imagem.carregarImagem("lupa_25_off.png")));
    }

    private void configurarTabelaVoo() {
        Label informativoTabelaVoo = new Label("Informe a origem ou o destino para consultarmos os vôos disponíveis");
        tableVoosDisponiveis.setPlaceholder(informativoTabelaVoo);

        listVoosDisponiveis.addListener((ListChangeListener.Change<? extends Voo> voo) -> {
            boolean informouLocal = StringFilter.temAlgo(txtOrigem) || StringFilter.temAlgo(txtDestino);
            String auxilio = informouLocal
                    ? "Não há vôos disponíveis para os locais pesquisados"
                    : "Informe a origem ou o destino, para consultarmos os vôos disponíveis";
            informativoTabelaVoo.textProperty().setValue(auxilio);
        });
    }

    private void filtrar() {
        limparTabelaVoos();
        updateList();
        FXCollections.sort(listVoosDisponiveis, Comparator.comparing(Voo::getOrigem));
        Terminal.filtrarVooPorOrigem(listVoosDisponiveis, txtOrigem.getText());
        Terminal.filtraVooPorDestino(listVoosDisponiveis, txtDestino.getText());
    }

    private void updateList() {
        listVoosDisponiveis.clear();
        listVoosDisponiveis.addAll(terminal.listarVoos());
    }

    @FXML
    private void pesquisarVoos() {
        if (StringFilter.temAlgo(txtOrigem) || StringFilter.temAlgo(txtDestino)) {
            filtrar();
            carregarTabela();
        } else {
            Mensagem.aviso("Informe a origem ou o destino para realizar uma busca");
        }

    }

    private void carregarTabela() {

        limparTabelaVoos();
        listVoosDisponiveis.forEach(
                voo -> tableVoosDisponiveis.getItems().add(new LinhaTabelaVoo(ambienteExecucao, voo))
        );
    }

    public TextField getTxtNomeCliente() {
        return txtNomeCliente;
    }

    private static MyFilter campoVazio(TextField campoTexto, String nomeDoCampo) {
        return CampoFilter.campoVazio(campoTexto, nomeDoCampo);
    }

    @Override
    public boolean validar() {

        MyFilter[] testes = {
            campoVazio(txtNomeCliente, "nome do cliente"),
            new MyFilter() {
                @Override
                public boolean validar() {
                    return !StringFilter.temAlgo(txtOrigem) && !StringFilter.temAlgo(txtDestino);
                }

                @Override
                public String getInconsistencia() {
                    return "É necessário informar a origem ou o destino da viagem";
                }
            },
            new MyFilter() {
                @Override
                public boolean validar() {
                    return !temVooSelecionado();
                }

                @Override
                public String getInconsistencia() {
                    if (tableVoosDisponiveis.getItems().isEmpty()) {
                        pesquisarVoos();
                    }
                    return !tableVoosDisponiveis.getItems().isEmpty()
                            ? "Selecione um dos vôos disponíveis"
                            : "Não há vôo disponível para o(s) local(is) informado(s)";
                }
            }
        };

        MyFilter campoInvalido = MyFilter.findFirst(testes);
        if (campoInvalido == null) {
            return true;
        }
        Mensagem.aviso(campoInvalido.getInconsistencia());
        return false;
    }

    @Override
    public void submeter() {
        if (validar()) {
            boolean isPassageiro = terminal.cadastrarPassageiro(vooSelelcionado(), new Passageiro(txtNomeCliente.getText(), vooSelelcionado()));
            Mensagem.aviso(isPassageiro ? "Passageiro cadastrado com sucesso" : "Cliente adicionado na fila de espera do Voo");
            clearForm();
            ambienteExecucao.atualizarScene(TipoController.INICIAL);
        }
    }

    private void limparTabelaVoos() {
        tableVoosDisponiveis.getItems().clear();
    }

    @Override
    public void clearForm() {
        limparCampos(txtOrigem, txtDestino, txtNomeCliente);
        limparTabelaVoos();
    }

    private Voo vooSelelcionado() {
        return tableVoosDisponiveis.getSelectionModel().getSelectedItem().getVoo();
    }

    private boolean temVooSelecionado() {
        return tableVoosDisponiveis.getSelectionModel().getSelectedIndex() > -1;
    }

    public void voltar() {
        clearForm();
        ambienteExecucao.atualizarScene(TipoController.INICIAL);
        ((InicialController)findController(TipoController.INICIAL)).atualizarBaseVoos();
    }

    @Override
    public boolean sair() {
        voltar();
        return false;
    }

}
