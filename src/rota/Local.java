
package rota;

import excessao.Excessao;
import java.io.Serializable;

public class Local implements Serializable{
    private String nomeLocal;

    public Local(String nomeLocal) {
        setNomeLocal(nomeLocal);
    }

    private void setNomeLocal(String nomeLocal) {
        Excessao.stringPadraoInvalida(nomeLocal, "nomeLocal");
        this.nomeLocal = nomeLocal;
    }
    
    public String getNomeLocal() {
        return nomeLocal;
    }
    
}
