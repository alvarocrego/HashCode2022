package com.acrego.hashcode2022.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Proyecto {
    public Integer id;
    public String nombre;
    public Integer ejecucion = 0;
    public Integer duracion;
    public Integer puntuacion;
    public Integer puntuacionExtra;

    public List<Idioma> idiomas = new ArrayList<>();

    public List<Colaborador> asignados = new ArrayList<>();
    public Map<Integer, String> colaboradorIdioma = new HashMap<>();

    public Proyecto(int i) {
        this.id = i;
    }

    public void addIdioma(Idioma idioma) {
        this.idiomas.add(idioma);
    }
}
