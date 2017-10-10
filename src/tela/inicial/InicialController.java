package tela.inicial;

import ambiente.Terminal;
import ferramentas.HBoxPersonalizado;
import ferramentas.KeyController;
import ferramentas.Mensagem;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.IntStream;
import tela.TipoController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import rota.Local;
import tela.Controller;
import usuario.Passageiro;
import voo.Voo;

public class InicialController extends Controller implements Initializable {

    @FXML
    private Label lblCadastroPassageiro,
            lblCadastroVoo,
            lblListaPassageiros,
            lblPassageirosEmEspera,
            lblVoosCadastrados,
            lblFiltroEsperaPassageiroVoo,
            lblFiltroPassageiroVoo,
            lblFiltroCancelamentoVoo,
            lblFiltroCancelamentoPassageiro,
            lblCancelaReserva,
            lblCancelaVoo;
    @FXML
    private TextField txtFiltroPassageiroVoo,
            txtFiltroPassageiroNome,
            txtFiltroEsperaPassageiroVoo,
            txtFiltroEsperaPassageiroNome,
            txtFiltroCancelamentoVoo,
            txtFiltroCancelamentoPassageiro;
    @FXML
    private Button btnFiltroPassageiro, btnFiltroEsperaPassageiro, btnCancelarVoo, btnCancelarPassageiro, btnAtualizar;
    @FXML
    private ListView<HBoxPersonalizado<Voo>> listViewVoos, listViewCancelamentoVoo;
    @FXML
    private ListView<HBoxPersonalizado<Passageiro>> listViewPassageiros, listViewEsperaPassageiros, listViewCancelamentoPassageiro;
    @FXML
    private TabPane paneTabs;
    @FXML
    private Tab tabPassageiros, tabVoos, tabFilaEspera, tabCancelamentos;

    private Terminal terminal;
    private static final double[] LARGURA_COLUNAS_LIST_VIEW_VOO = {80, 100, 100, 100, 100, 100, 135},
            LARGURA_COLUNAS_LIST_VIEW_PASSAGEIRO = {100, 250, 189, 189},
            LARGURA_COLUNAS_CANCELAMENTO_LIST_VIEW_VOO = {104, 103, 103},
            LARGURA_COLUNAS_LIST_VIEW_CANCELAMENTO_PASSAGEIRO = {104, 205};

    public void cadastrarPassageiro() {
        if (terminal.listarVoos().isEmpty()) {
            Mensagem.aviso("Não há vôos cadastrados");
        } else {
            navegar(TipoController.CADASTRO_PASSAGEIRO);
        }
    }

    public void cadastrarVoo() {
        navegar(TipoController.CADASTRO_VOO);
    }

    private void navegar(TipoController tipoCena) {
        ambienteExecucao.atualizarScene(tipoCena);

    }

    private void cabecalhoCancelamentoViewVoo() {
        listViewCancelamentoVoo.getItems().add(0,
                new HBoxPersonalizado<>(null, HBoxPersonalizado.gerarLabels("Codigo", "Origem", "Destino"))
                        .addClassToLabels("titleListView", "center")
                        .setLarguraToLabels(LARGURA_COLUNAS_CANCELAMENTO_LIST_VIEW_VOO)
        );
    }

    private void cabecalhoCancelamentoViewPassageiro() {
        listViewCancelamentoPassageiro.getItems().add(0,
                new HBoxPersonalizado<>(null, HBoxPersonalizado.gerarLabels("Codigo do Voo", "Nome do Passageiro"))
                        .addClassToLabels("titleListView", "center")
                        .setLarguraToLabels(LARGURA_COLUNAS_LIST_VIEW_CANCELAMENTO_PASSAGEIRO)
        );
    }

    private void adicionarItemViewCancelamentoPassageiro() {
        listViewCancelamentoPassageiro.getItems().clear();
        if (!txtFiltroCancelamentoPassageiro.getText().isEmpty()) {
            Voo voo = terminal.findVooPorCodigo(Integer.parseInt(txtFiltroCancelamentoPassageiro.getText()));
            if (voo != null) {
                adicionarItemViewCancelaPassageiro(voo.listarFilaEspera());
                adicionarItemViewCancelaPassageiro(voo.listarPassageiros());
            }
        } else {
            adicionarItemViewCancelaPassageiro(terminal.listarPassageiros());
            adicionarItemViewCancelaPassageiro(terminal.listarFilaEsperaPassageiros());
        }
        cabecalhoCancelamentoViewPassageiro();
    }

    private void adicionarItemViewCancelaPassageiro(List<Passageiro> passageiros) {
        passageiros.forEach(pessoa -> listViewCancelamentoPassageiro.getItems().add(
                hBoxPersonalizado(pessoa, pessoa.getVoo().getCodigoVoo() + "", pessoa.getNome()
                ).addClassToLabels("listView").setLarguraToLabels(LARGURA_COLUNAS_LIST_VIEW_CANCELAMENTO_PASSAGEIRO)));
    }

    private void cancelarReservaPassageiro() {
        HBoxPersonalizado<Passageiro> hBoxPassageiro = listViewCancelamentoPassageiro.getSelectionModel().getSelectedItem();
        if (hBoxPassageiro != null) {
            Passageiro passageiro = hBoxPassageiro.getGeneric();
            terminal.cancelarReserva(passageiro.getVoo(), passageiro);
            adicionarItemViewCancelamentoPassageiro();
            Mensagem.aviso("Passageiro(a): " + passageiro.getNome() + " removido(a) com Sucesso");

        } else {
            Mensagem.aviso("Não há passageiro Selecionado");
        }
    }

    private void adicionarItemViewCancelamentoVoo() {
        listViewCancelamentoVoo.getItems().clear();
        if (!txtFiltroCancelamentoVoo.getText().isEmpty()) {
            Voo voo = terminal.findVooPorCodigo(Integer.parseInt(txtFiltroCancelamentoVoo.getText()));
            if (voo != null) {
                adicionarItemViewCancelamentoVoo(voo);
            }
        } else {
            terminal.listarVoos().forEach(voo -> adicionarItemViewCancelamentoVoo(voo));
        }
        cabecalhoCancelamentoViewVoo();
    }

    private void adicionarItemViewCancelamentoVoo(Voo voo) {
        listViewCancelamentoVoo.getItems()
                .add(hBoxPersonalizado(voo, voo.getCodigoVoo() + "", voo.getOrigem(), voo.getDestino())
                        .setLarguraToLabels(LARGURA_COLUNAS_CANCELAMENTO_LIST_VIEW_VOO)
                        .addClassToLabels("listView")
                );
    }

    private void cancelarVoo() {
        HBoxPersonalizado<Voo> hBoxVoo = listViewCancelamentoVoo.getSelectionModel().getSelectedItem();
        if (hBoxVoo != null) {
            terminal.cancelarVoo(hBoxVoo.getGeneric());
            adicionarItemViewCancelamentoVoo();
            Mensagem.aviso("Voo " + hBoxVoo.getGeneric().getCodigoVoo() + " removido com Sucesso.\nTodos os clientes também foram removidos");

        } else {
            Mensagem.aviso("Não há voo Selecionado");
        }
    }

    @Override
    public boolean sair() {
        return Mensagem.confirma("Deseja sair do sistema", "Sair");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        terminal = Terminal.getInstance();
        atualizarBaseVoos();
        configurar();
        cabecalhoCancelamentoViewVoo();
        cabecalhoCancelamentoViewPassageiro();
        listViewVoos.setOnMouseReleased(onRelease -> atualizarTabsPassageiroAndEspera(getVooOfListViewVoo()));
        lblListaPassageiros.setOnMouseReleased(onRelease -> paneTabs.getSelectionModel().select(tabPassageiros));
        lblCadastroVoo.setOnMouseReleased(onRelease -> cadastrarVoo());
        lblCadastroPassageiro.setOnMouseReleased(onRelease -> cadastrarPassageiro());
        lblPassageirosEmEspera.setOnMouseReleased(onRelease -> paneTabs.getSelectionModel().select(tabFilaEspera));
        lblVoosCadastrados.setOnMouseReleased(onRelease -> atualizarBaseVoos());
        gerarColumnsNamesListViewPassageiro(listViewPassageiros);
        gerarColumnsNamesListViewPassageiro(listViewEsperaPassageiros);
        KeyController.entrelacarAlternar("", txtFiltroPassageiroVoo, txtFiltroPassageiroNome);
        KeyController.entrelacarAlternar("", txtFiltroEsperaPassageiroVoo, txtFiltroEsperaPassageiroNome);
        txtFiltroCancelamentoVoo.setOnKeyReleased(onReleased -> adicionarItemViewCancelamentoVoo());
        txtFiltroCancelamentoPassageiro.setOnKeyReleased(onReleased -> adicionarItemViewCancelamentoPassageiro());
        btnCancelarPassageiro.setOnAction(onAction -> cancelarReservaPassageiro());
        btnCancelarVoo.setOnAction(onAction -> cancelarVoo());
        lblCancelaReserva.setOnKeyReleased(onRelease -> paneTabs.getSelectionModel().select(tabCancelamentos));
        Arrays.asList(lblCancelaReserva, lblCancelaVoo).
                forEach(labelCancel -> labelCancel.setOnMouseReleased(onReleased -> paneTabs.getSelectionModel().select(tabCancelamentos)));
        btnAtualizar.setOnAction(onAction -> carregarListViewVoos());

    }

    private void configurar() {
        configurarCompartilhamentoSubmit();
        configurarEntradasCodigoVoo();
        configurarRequestFocus();
    }

    private void configurarRequestFocus() {
        final Node[][] focusPar = {
            {lblFiltroCancelamentoVoo, txtFiltroCancelamentoVoo},
            {lblFiltroCancelamentoPassageiro, txtFiltroCancelamentoPassageiro},
            {lblFiltroEsperaPassageiroVoo, txtFiltroEsperaPassageiroVoo},
            {lblFiltroPassageiroVoo, txtFiltroPassageiroVoo}
        };
        Arrays.stream(focusPar).forEach(node -> requestFocus((Label) node[0], node[1]));
    }

    //o compartilhar submite deve ser colocado em uma classe mais apropriada a ser construída, pois deve abranger mais controles
    private void configurarCompartilhamentoSubmit() {
        KeyController.compartilharSubmit(
                btnFiltroPassageiro,
                onAction -> filtroBtnPassageiro(txtFiltroPassageiroVoo, txtFiltroPassageiroNome, false),
                txtFiltroPassageiroNome, txtFiltroPassageiroVoo
        );
        KeyController.compartilharSubmit(
                btnFiltroEsperaPassageiro,
                onAction -> filtroBtnPassageiro(txtFiltroEsperaPassageiroVoo, txtFiltroEsperaPassageiroNome, true),
                txtFiltroEsperaPassageiroNome, txtFiltroEsperaPassageiroVoo
        );
    }

    private void configurarTabCancelamento() {

    }

    private void configurarEntradasCodigoVoo() {
        TextField[] camposDeTexto = {
            txtFiltroPassageiroVoo, txtFiltroEsperaPassageiroVoo, txtFiltroCancelamentoVoo, txtFiltroCancelamentoPassageiro
        };
        Arrays.stream(camposDeTexto).forEach(campo -> KeyController.intervaloNumericoPermitido(11, 1L, 99999999999L, campo));
    }

    private static void requestFocus(Label labelProvocado, Node focoDestino) {
        labelProvocado.setOnMouseReleased(onRelease -> focoDestino.requestFocus());
    }

    private void gerarColumnsNamesListViewVoo() {
        String[] columns = new String[]{
            "Código", "Origem", "Destino",
            "Passageiros", "Fila de Espera",
            "Total de Assentos", "Escalas"};

        listViewVoos.getItems().add(0,
                new HBoxPersonalizado<>(null, HBoxPersonalizado.gerarLabels(columns))
                        .addClassToLabels("titleListView", "center")
                        .setLarguraToLabels(LARGURA_COLUNAS_LIST_VIEW_VOO)
        );
    }

    private void adicionarItemListViewVoo(List<Voo> listaVoos) {
        listaVoos.forEach(voo -> {
            HBoxPersonalizado linha = hBoxPersonalizado(voo, new String[]{
                "   " + voo.getCodigoVoo() + "",
                "   " + voo.getOrigem(),
                "   " + voo.getDestino(),
                voo.listarPassageiros().size() + "",
                voo.listarFilaEspera().size() + "",
                voo.getNumeroMaximoPassageiros() + ""
            }
            );
            IntStream.rangeClosed(3, 5).forEach(index -> linha.getLabels()[index].getStyleClass().add("center"));
            linha.setLarguraToLabels(LARGURA_COLUNAS_LIST_VIEW_VOO).addClassToLabels("listView");
            linha.getChildren().add(comboEscalas(voo));
            listViewVoos.getItems().add(linha);
        }
        );
    }

    private void gerarColumnsNamesListViewPassageiro(ListView<HBoxPersonalizado<Passageiro>> listViewPassageiro) {
        String[] columns = new String[]{"Código do Vôo", "Nome do Passageiro", "origem", "destino"};

        listViewPassageiro.getItems().add(0,
                new HBoxPersonalizado<>(null, HBoxPersonalizado.gerarLabels(columns))
                        .addClassToLabels("titleListView", "center")
                        .setLarguraToLabels(LARGURA_COLUNAS_LIST_VIEW_PASSAGEIRO));

    }

    private void filtroBtnPassageiro(TextField codigoVoo, TextField nomePassageiro, boolean naFilaDeEspera) {

        ListView<HBoxPersonalizado<Passageiro>> viewListPassageiros = naFilaDeEspera
                ? listViewEsperaPassageiros
                : listViewPassageiros;

        clearView(viewListPassageiros);

        if (!codigoVoo.getText().isEmpty()) {
            Voo voo = terminal.findVooPorCodigo(Integer.parseInt(codigoVoo.getText()));
            atualizarTabsPassageiroAndEspera(voo);

        } else {

            List<Passageiro> listaPassageiros = naFilaDeEspera
                    ? terminal.listarFilaEsperaPassageiros()
                    : terminal.listarPassageiros();

            if (!nomePassageiro.getText().isEmpty()) {
                String nomeLower = nomePassageiro.getText().toLowerCase();
                listaPassageiros.removeIf(passageiro -> !passageiro.getNomeLowerCase().contains(nomeLower));
            }

            listaPassageiros.forEach(passageiro -> adicionarAoListViewPassageiro(LARGURA_COLUNAS_LIST_VIEW_PASSAGEIRO, viewListPassageiros, passageiro));
            gerarColumnsNamesListViewPassageiro(viewListPassageiros);
        }
    }

    private void povoarViewsPassageiroPorNome() {

    }

    public void atualizarBaseVoos() {
        paneTabs.getSelectionModel().select(tabVoos);
        carregarListViewVoos();
    }

    private void carregarListViewVoos() {
        clearView(listViewVoos);
        adicionarItemListViewVoo(new ArrayList<>(terminal.listarVoos()));
        gerarColumnsNamesListViewVoo();
    }

    private void atualizarTabsPassageiroAndEspera(Voo voo) {
        if (voo != null) {
            atualizarViewsPassageiros(voo, true);
            atualizarViewsPassageiros(voo, false);
        } else {
            Mensagem.aviso("O Voo solicitado inexiste");
        }
    }

    private Voo getVooOfListViewVoo() {
        if (listViewVoos.getSelectionModel().getSelectedItem() != null) {
            return listViewVoos.getSelectionModel().getSelectedItem().getGeneric();
        }
        return null;
    }

    private void atualizarViewsPassageiros(Voo voo, boolean naFilaDeEspera) {

        ListView<HBoxPersonalizado<Passageiro>> viewListPassageiros = naFilaDeEspera ? listViewEsperaPassageiros : listViewPassageiros;
        clearView(viewListPassageiros);
        List<Passageiro> filaSelecionada = naFilaDeEspera ? voo.listarFilaEspera() : voo.listarPassageiros();
        filaSelecionada.forEach(passageiro -> adicionarAoListViewPassageiro(LARGURA_COLUNAS_LIST_VIEW_PASSAGEIRO, viewListPassageiros, passageiro));
        gerarColumnsNamesListViewPassageiro(viewListPassageiros);
    }

    private void adicionarAoListViewPassageiro(
            double[] larguras, ListView<HBoxPersonalizado<Passageiro>> viewListPassageiros, Passageiro passageiro
    ) {
        Voo voo = passageiro.getVoo();
        HBoxPersonalizado linha = hBoxPersonalizado(passageiro, new String[]{
            voo.getCodigoVoo() + "",
            "   " + passageiro.getNome(),
            "   " + voo.getOrigem(),
            "   " + voo.getDestino()}
        );
        linha.getLabels()[0].getStyleClass().add("center");
        linha.setLarguraToLabels(larguras).addClassToLabels("listView");
        viewListPassageiros.getItems().add(linha);
    }

    private ComboBox<String> comboEscalas(Voo voo) {
        ComboBox<String> comboEscala = new ComboBox<>();
        comboEscala.getStyleClass().add("comboPadrao");
        List<Local> locais = voo.getRota().getEscala().getLocais();
        if (locais.isEmpty()) {
            comboEscala.setPromptText("Sem escala");
            return comboEscala;
        }
        comboEscala.setPromptText("Consultar");
        locais.forEach(local -> comboEscala.getItems().add(local.getNomeLocal()));
        return comboEscala;
    }

    private static HBoxPersonalizado hBoxPersonalizado(Voo voo, String... colunas) {
        return new HBoxPersonalizado(voo, HBoxPersonalizado.gerarLabels(colunas));
    }

    private static HBoxPersonalizado hBoxPersonalizado(Passageiro passageiro, String... colunas) {
        return new HBoxPersonalizado(passageiro, HBoxPersonalizado.gerarLabels(colunas));
    }

    private static void clearView(ListView listView) {
        listView.getItems().clear();
    }
}
