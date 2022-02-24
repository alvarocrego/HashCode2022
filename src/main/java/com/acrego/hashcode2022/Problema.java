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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Problema {
    private Logger logger = LoggerFactory.getLogger(Problema.class);

    private List<Colaborador> colaboradores = new ArrayList<>();
    private List<Proyecto> proyectos = new ArrayList<>();
    private List<String> idiomas = new ArrayList<>();

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

    }

    public void writeResult(String fileName) {
        try {
            File outputFile = new File(fileName + "_output.txt");
            FileWriter fileWriter = new FileWriter(outputFile);

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
