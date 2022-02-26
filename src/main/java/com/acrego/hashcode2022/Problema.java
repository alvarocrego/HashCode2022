package com.acrego.hashcode2022;

import com.acrego.hashcode2022.beans.Colaborador;
import com.acrego.hashcode2022.beans.Idioma;
import com.acrego.hashcode2022.beans.Proyecto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

public class Problema {
    private Logger logger = LoggerFactory.getLogger(Problema.class);

    public List<Colaborador> colaboradores = new ArrayList<>();
    public List<Proyecto> proyectos = new ArrayList<>();
    public List<String> idiomas = new ArrayList<>();
    public Map<Integer, List<Proyecto>> proyectosMap = new HashMap<>();

    public Map<Integer, Proyecto> proyectosEnEjecucion = new HashMap<>();
    public List<Integer> proyectosTerminados = new ArrayList<>();
    public Map<Integer, Integer> colaboradoresEnUso = new HashMap();

    public Integer puntucacion = 0;

    public void load(String fileName) {
        try {
            URL res = getClass().getClassLoader().getResource(fileName);
            File file = Paths.get(res.toURI()).toFile();

            Scanner scanner = new Scanner(file);

            String line = scanner.nextLine();
            String[] integers = line.split(" ");

            // colaboradores
            for (int i = 0; i < Integer.parseInt(integers[0]); i++) {
                String col = scanner.nextLine();
                Colaborador c = new Colaborador(i);
                c.nombre = col.split(" ")[0];

                Integer numIdiomas = Integer.parseInt(col.split(" ")[1]);

                for (int j = 0; j < numIdiomas; j++) {
                    String idiomaString = scanner.nextLine();
                    Idioma idioma = new Idioma();
                    idioma.nombre = idiomaString.split(" ")[0];
                    idioma.nivel = Integer.parseInt(idiomaString.split(" ")[1]);
                    c.addIdioma(idioma);
                    idiomas.add(idiomaString.split(" ")[0]);
                }
                colaboradores.add(c);
            }

            // proyectos
            for (int i = 0; i < Integer.parseInt(integers[1]); i++) {
                String pry = scanner.nextLine();

                Proyecto p = new Proyecto(i);
                p.nombre = pry.split(" ")[0];
                p.duracion = Integer.parseInt(pry.split(" ")[1]);
                p.puntuacion = Integer.parseInt(pry.split(" ")[2]);
                p.puntuacionExtra = Integer.parseInt(pry.split(" ")[3]);

                Integer numIdiomas = Integer.parseInt(pry.split(" ")[4]);

                for (int j = 0; j < numIdiomas; j++) {
                    String idiomaString = scanner.nextLine();
                    Idioma idioma = new Idioma();
                    idioma.nombre = idiomaString.split(" ")[0];
                    idioma.nivel = Integer.parseInt(idiomaString.split(" ")[1]);
                    p.addIdioma(idioma);
                }
                proyectos.add(p);
            }

        } catch (FileNotFoundException | URISyntaxException e) {
            logger.error("No se ha encontrado el archivo: " + fileName, e);
        } catch (Exception e) {
            logger.error("Error desconocido al procesar el archivo: " + fileName, e);
        }
        logger.info("leido");
    }

    public void solucionar() {

        Boolean control = true;
        Integer dias = 0;
        Integer diasSinNada = 0;
        Integer auxPuntuacion = 0;
        Boolean buscarProyectos = true;

        // cada dia la misma historia
        while(control) {
            Integer auxColaboradoresNoAfk = 0;
            // buscamos proyectos
            if(buscarProyectos) {
                buscarProyectos = false;
                for (int i = 0; i < proyectos.size(); i++) {
                    Proyecto proyecto = proyectos.get(i);
                    if(proyectosTerminados.contains(proyecto.id) || proyectosEnEjecucion.containsKey(proyecto.id)){
                        continue;
                    }

                    List<Integer> colaboradoresCandidatos = new ArrayList<>();
                    Map<Integer, String> colaboradoresIdiomaCandidatos = new HashMap<>();

                    for (Idioma idioma : proyecto.idiomas) {
                        Colaborador can = buscarColaborador(idioma, colaboradoresCandidatos);
                        if(can == null) {
                            colaboradoresCandidatos = new ArrayList<>();
                            colaboradoresIdiomaCandidatos = new HashMap<>();
                            break;
                        }
                        colaboradoresCandidatos.add(can.id);
                        colaboradoresIdiomaCandidatos.put(can.id, idioma.nombre);
                    }

                    if(colaboradoresCandidatos.size() == 0) {
                        continue;
                    }
                    for (Integer id: colaboradoresCandidatos) {
                        proyecto.asignados.add(colaboradores.get(id));
                        colaboradoresEnUso.put(id, id);
                        proyecto.colaboradorIdioma.put(id, colaboradoresIdiomaCandidatos.get(id));
                    }
                    proyectosEnEjecucion.put(proyecto.id, proyecto);
                }
            }


            // proyectos ejecutandose
            List<Integer> proyectosABorrar = new ArrayList<>();
            List<Integer> colaboradoresABorrar = new ArrayList<>();
            for(Integer id : proyectosEnEjecucion.keySet()) {
                Proyecto proyecto = proyectosEnEjecucion.get(id);
                proyecto.ejecucion = proyecto.ejecucion+1;

                if(proyecto.duracion.equals(proyecto.ejecucion)) {
                    proyectosTerminados.add(proyecto.id);
                    proyectosABorrar.add(id);
                    for(Integer idC : proyecto.colaboradorIdioma.keySet()) {
                        String nombreIdioma = proyecto.colaboradorIdioma.get(idC);
                        Integer nivel = 0;
                        for(Idioma idioma : proyecto.idiomas) {
                            if(idioma.nombre.equals(nombreIdioma)) {
                                nivel = idioma.nivel;
                                break;
                            }
                        }
                        if(colaboradores.get(idC).idiomas.get(proyecto.colaboradorIdioma.get(idC)).nivel == nivel) {
                            colaboradores.get(idC).idiomas.get(proyecto.colaboradorIdioma.get(idC)).nivel++;
                        }
                        colaboradoresABorrar.add(idC);
                    }
                    puntucacion = puntucacion + calcularPuntuacion(proyecto, dias);
                }
            }
            for (Integer id : proyectosABorrar) {
                proyectosEnEjecucion.remove(id);
                buscarProyectos = true;
            }
            for (Integer id : colaboradoresABorrar) {
                colaboradoresEnUso.remove(id);
            }

            if(proyectosTerminados.size() == proyectos.size()) {
                control = false;
            }
            dias++;
            if(proyectosEnEjecucion.size() == 0) {
                diasSinNada++;
                logger.info("Dias: " + dias.toString());
                logger.info("Dias sin nada: " + diasSinNada.toString());
            } else {
                diasSinNada = 0;
            }

            if(diasSinNada > 10) {
                control = false;
            }

            if(!puntucacion.equals(auxPuntuacion)) {
                auxPuntuacion = puntucacion;
                logger.info("Puntuacion: " + puntucacion.toString());
                logger.info("Proyectos Terminados: " + proyectosTerminados.size());
                logger.info("Proyectos: " + proyectos.size());
            }
            //logger.info(dias.toString());
        }

        logger.info("fin");
    }

    public Integer calcularPuntuacion(Proyecto proyecto, Integer dia) {
        if(proyecto.puntuacionExtra >= dia) {
            return proyecto.puntuacion;
        } else {
            Integer aux = proyecto.puntuacion - (dia - proyecto.puntuacionExtra);
            if(aux <= 0 ) {
                return 0;
            } else {
                return aux;
            }
        }
    }

    public Colaborador buscarColaborador(Idioma idioma, List<Integer> colaboradoresCandidatos) {
        for (Colaborador colaborador : colaboradores) {
            if(colaboradoresEnUso.get(colaborador.id) != null || colaboradoresCandidatos.contains(colaborador.id)) {
                continue;
            }
            for(String idiomaS : colaborador.idiomas.keySet()) {
                Idioma idiomaCol = colaborador.idiomas.get(idiomaS);
                if(idiomaCol.nombre.equals(idioma.nombre) && idiomaCol.nivel >= idioma.nivel) {
                    return colaborador;
                }
            }
        }
        return null;
    }


    public void writeResult(String fileName) {
        try {
            File outputFile = new File(fileName + "_output.txt");
            FileWriter fileWriter = new FileWriter(outputFile);

            fileWriter.write(Integer.toString(proyectosTerminados.size()));
            fileWriter.write("\n");
            for(Integer id : proyectosTerminados) {
                Proyecto proyecto = proyectos.get(id);
                fileWriter.write(proyecto.nombre);
                fileWriter.write("\n");
                for(Colaborador colaborador : proyecto.asignados) {
                    fileWriter.write(colaborador.nombre + " ");
                }
                fileWriter.write("\n");
            }
           // fileWriter.write(pizza.getIngredients().size()+" ");
           // for(Integer ingredientId : pizza.getIngredients().keySet()) {
           //     fileWriter.write(pizza.getIngredients().get(ingredientId).getName()+" ");
           // }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
