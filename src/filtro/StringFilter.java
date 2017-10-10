package filtro;

import javafx.scene.control.TextField;

public final class StringFilter{

    private StringFilter() {
    }

    public static boolean isNull(String string) {
        return string == null;
    }
    
    public static boolean temAlgo(TextField campoTexto) {
        return StringFilter.temAlgo(campoTexto.getText());
    }
    
    
    public static boolean temAlgo(String string) {
        if (!isNull(string)) {
            return haveSameThing(string, true);
        }
        return false;
    }

    public static boolean haveSameThing(String string, boolean excludeSpace) {
        if (!isNull(string)) {
            return excludeSpace ? !string.isEmpty() : !string.replaceAll(" {1,}", "").isEmpty();
        }
        return false;
    }
}
