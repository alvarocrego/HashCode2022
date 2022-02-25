package com.acrego.hashcode2022.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Colaborador {
    public Integer id;
    public String nombre;

    public Map<String, Idioma> idiomas = new HashMap<>();

    public Colaborador(Integer i) {
        this.id = i;
    }

    public void addIdioma(Idioma idioma) {
        this.idiomas.put(idioma.nombre, idioma);
    }
}
