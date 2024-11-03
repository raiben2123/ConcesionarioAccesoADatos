package controller;
import model.Coche;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GestorConcesionario {
    Scanner scanner = new Scanner(System.in);
    File file = new File("src/main/resources/coches.dat");
    ArrayList<Coche> listaCoches = new ArrayList<>();



    public ArrayList<Coche> comprobacionFichero() {
        /*
         * Si "coches.dat" existe, lo lee y añade los objeos a un ArrayList para luego manipularlo.
         * si el fichero no existe, inicia un ArrayList de Coches y lo deja listo para trabajar.
         * */

        ObjectInputStream ois = null;
        if (file.exists()) {
            try {
                /*
                 * Bloque try para capturar la excepcion "END OF FILE" y poder hacer un break dentro
                 * del buble infinito
                 */
                ois = new ObjectInputStream(new FileInputStream(file));
                while (true) {
                    try {
                        listaCoches = (ArrayList<Coche>) ois.readObject();

                    } catch (EOFException e) { //END OF FILE
                        break;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error en la lectura del fichero");
            } finally {
                try {
                    if (ois != null)
                        ois.close(); //Se cierra el flujo de datos entrante
                } catch (IOException e) {
                    System.out.println("Error en el cierre de ObjectInputStream");
                }
            }
        }
        return listaCoches; // Lista de coches que devuelve el metodo
    }

    public boolean crearCoche(ArrayList<Coche> listaCoches, Coche coche) {
        // Comprobamos si la matricula ya existe y si no, lo agrega a la lista
        if (matriculaExiste(coche.getMatricula())) {
            return listaCoches.add(coche);
        } else {
            return false;
        }
    }

    public long nuevaId() {
        // Usamos el metodo gelUltimaId para coger el id y actualizarlo
        Long id = getUltimaId();
        return id;
    }

    public void cerradoAplicacion(ArrayList<Coche> listaCoches) {
        // Preparamos para el cierre de la aplicación escribiendo todos los coches de la lista en *coches.dat*
        try (ObjectOutputStream ous = new ObjectOutputStream(new FileOutputStream(file))) {
            ous.writeObject(listaCoches);
            System.out.println("INFORMACIÓN GUARDADA CORRECTAMENTE. HASTA PRONTO.");
        } catch (IOException e) {
            System.out.println("Error al guardar los datos de coches: " + e.getMessage());
        }
    }

    public void borrarCocheId(ArrayList<Coche> listaCoches) {
        // Cogemos el id del vehiculo que se quiere eliminar y lo eliminamos
        System.out.println("Diga el Identificador único del vehículo a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Limpia el búfer
        // Usamos el lambda para recorrer los coches
        listaCoches.removeIf(coche -> coche.getId() == id);
        System.out.println("Vehículo eliminado");
    }

    public void consultarCocheId(ArrayList<Coche> listaCoches) {
        // Buscamos el coche del que se nos indica la id y lo imprimimos cuando lo encontramos
        System.out.println("Diga el Identificador único del vehículo a consultar: ");
        int id = scanner.nextInt();
        listaCoches.stream()
                .filter(coche -> coche.getId() == id)
                .forEach(System.out::println);
    }

    public void listarCoches(ArrayList<Coche> listaCoches) {
        // Imprimimos todos los coches de nuestra lista con uso del forEach
        listaCoches.forEach(System.out::println);
    }

    private boolean matriculaExiste(String matricula) {
        // Con este metodo comprobamos si la matricula que se ha ingresado existe o no comprobando coche por coche y coincide con la matricula pasada por consola
        return listaCoches.stream().noneMatch(coche -> coche.getMatricula().equals(matricula));
    }

    private Long getUltimaId() {
        // Comprobamos la ultima id que hay en nuestra lista para generar la siguiente
        if (listaCoches.isEmpty()) {
            return 1L;
        }
        return 1+(listaCoches.get(listaCoches.size() - 1).getId());  // Obtiene la ID del último coche
    }

    public void crearcsv(ArrayList<Coche> listaCoches) {
        // Creamos el fichero para el csv, imprimimos las cabeceras de las columnas y luego imprimimos los valores separados de cada columna con el ;
        File filecsv = new File("src/main/resources/coches.csv");
        BufferedWriter bufferedWriter;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(filecsv));
            bufferedWriter.write("id"+";"+"matricula"+";"+"modelo"+";"+"color"+"\n");
            for (Coche coche : listaCoches) {
                Long id = coche.getId();
                String matricula = coche.getMatricula();
                String modelo = coche.getModelo();
                String color = coche.getColor();
                bufferedWriter.write(id + ";" + matricula + ";" + modelo + ";" + color+"\n");
            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error de escritura exportando CSV");
        }
    }
}
