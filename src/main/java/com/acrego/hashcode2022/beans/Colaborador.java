package com.acrego.hashcode2022.beans;

import java.util.ArrayList;
import java.util.List;

public class Colaborador {
    public Integer id;
    public String nombre;

    private List<Idioma> idiomas = new ArrayList<>();

    public Colaborador(Integer i) {
        this.id = i;
    }

    public void addIdioma(Idioma idioma) {
        this.idiomas.add(idioma);
    }
}
