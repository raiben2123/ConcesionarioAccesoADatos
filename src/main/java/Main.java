import controller.GestorConcesionario;
import model.Coche;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GestorConcesionario gestorConcesionario = new GestorConcesionario();
        ArrayList<Coche> listaCoches = gestorConcesionario.comprobacionFichero();
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        while (opcion != 6) {
            System.out.println("""
                    ╔════════════════════════════════════════╗
                    ║       🚗 GESTIÓN DE CONCESIONARIO 🚗   ║
                    ╠════════════════════════════════════════╣
                    ║   1. ➕ Añadir nuevo coche             ║
                    ║   2. 🗑️ Borrar coche por ID            ║
                    ║   3. 🔍 Consultar coche por ID         ║
                    ║   4. 📋 Listado de coches              ║
                    ║   5. 📋 Exportar a CSV                 ║
                    ║   6. ❌ Terminar el programa           ║
                    ╚════════════════════════════════════════╝""");
            System.out.println("Elige una opción");
            opcion = Integer.parseInt((scanner.nextLine()));
            System.out.println();
            switch (opcion){
                case 1:
                    Long id = gestorConcesionario.nuevaId();
                    System.out.println("PROCEDIENDO A AÑADIR UN COCHE NUEVO...");
                    System.out.println("Escribe la matricula del vehículo: ");
                    String matricula = scanner.nextLine();
                    System.out.println("Escribe el modelo del vehículo: ");
                    String modelo = scanner.nextLine();
                    System.out.println("Escribe el color del vehículo: ");
                    String color = scanner.nextLine();
                    Coche coche = new Coche(matricula, modelo, color, id);
                    if(gestorConcesionario.crearCoche(listaCoches, coche))
                        System.out.println("Se ha añadió correctamente");
                    else System.out.println("No se ha podido añadir el coche a la lista");
                    break;
                case 2:
                    gestorConcesionario.borrarCocheId(listaCoches);
                    break;
                case 3:
                    gestorConcesionario.consultarCocheId(listaCoches);
                    break;
                case 4:
                    System.out.println("LISTADO DE COCHES" + " (" + listaCoches.size() + ')');
                    if (listaCoches.isEmpty())
                        System.out.println("NO HAY COCHES EN LA LISTA");
                    gestorConcesionario.listarCoches(listaCoches);
                    break;
                case 5:
                    gestorConcesionario.crearcsv(listaCoches);
                    break;
                case 6:
                    gestorConcesionario.cerradoAplicacion(listaCoches);
                    break;
                default:
                    System.out.println("Opción incorrecta. Intente nuevamente");
            }
        }
    }
}