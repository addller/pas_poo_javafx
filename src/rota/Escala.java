package rota;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Escala implements Serializable{

    private final List<Local> locais;

    public Escala() {
        this(new ArrayList<>());
    }

    public Escala(List<Local> locais) {
        this.locais = locais;
    }

    public List<Local> getLocais() {
        return locais;
    }

    public final boolean adicionarLocal(Local... locais) {
        if (locais.length == 0 || Arrays.stream(locais).anyMatch(local -> local == null)) {
            return false;
        }
        return this.locais.addAll(Arrays.asList(locais));
    }

}
