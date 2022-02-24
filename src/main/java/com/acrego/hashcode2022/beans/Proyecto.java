package com.acrego.hashcode2022.beans;

import java.util.ArrayList;
import java.util.List;

public class Proyecto {
    public Integer id;
    public String nombre;
    public Integer duracion;
    public Integer puntuacion;
    public Integer puntuacionExtra;

    public List<Idioma> idiomas = new ArrayList<>();

    public Proyecto(int i) {
        this.id = i;
    }

    public void addIdioma(Idioma idioma) {
        this.idiomas.add(idioma);
    }
}
