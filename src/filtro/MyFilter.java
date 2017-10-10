package filtro;

import java.util.Arrays;

public interface MyFilter {

    boolean validar();

    String getInconsistencia();

    public static MyFilter findFirst(MyFilter... filtros) {
        return Arrays.stream(filtros).filter(MyFilter::validar).findFirst().orElse(null);
    }
}
