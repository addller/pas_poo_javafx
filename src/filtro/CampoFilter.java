package filtro;

import javafx.scene.control.TextField;

public class CampoFilter implements MyFilter {

    private final String inconsistencia;
    private final boolean teste;

    public CampoFilter(String inconsistencia, boolean teste) {
        this.inconsistencia = inconsistencia;
        this.teste = teste;
    }

    @Override
    public boolean validar() {
        return teste;
    }

    @Override
    public String getInconsistencia() {
        return inconsistencia;
    }

    public static CampoFilter foraDoIntervaloNumerico(String intervalo, String nomeDoCampo, boolean intervaloAceito) {
        return new CampoFilter("O campo " + nomeDoCampo + ", esta fora do intervalo númerico de " + intervalo + ".", !intervaloAceito);
    }

    public static CampoFilter naoNumerico(TextField campoTexto, String nomeDoCampo) {
        return new CampoFilter("O campo " + nomeDoCampo + " dever ser numérico.", !campoTexto.getText().matches("{1,}"));
    }

   
    public static CampoFilter campoVazio(TextField textoAValidar, String nomeDoCampo) {
        return new CampoFilter("O campo " + nomeDoCampo + " deve ser informado.", !StringFilter.temAlgo(textoAValidar.getText()));

    }

}
