package hotel;

import java.util.Scanner;
/**
*Clase en la que se muestra un menu interactivo para el hotel
*@author Otilio Gamboa Carrillo
*@author Alejandro Manuel Cota Ayala
*/
public class MainHotel {

	public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        Hotel hotel = new Hotel();

        int opcion;

        do {
            System.out.println("\n=== MENU HOTEL ===");
            System.out.println("1. Registrar Huésped");
            System.out.println("2. Reservar Habitación");
            System.out.println("3. Check-In");
            System.out.println("4. Check-Out");
            System.out.println("5. Ver Disponibilidad de Habitaciones");
            System.out.println("6. Buscar Huésped");
            System.out.println("7. Ver Registro de Huéspedes");
            System.out.println("8. Ver Ingresos Totales");
            System.out.println("9. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero(in);
//______________________________________________________________________________________
            switch (opcion) {

            case 1:
                hotel.registrarHuesped();
                break;

//______________________________________________________________________________________
            case 2:
            	hotel.reservarHabitacion();
                break;
//______________________________________________________________________________________
            case 3:
                hotel.checkIn();
                break;

//______________________________________________________________________________________
            case 4:
            	if (!hotel.hayHabitacionesOcupadas()) {
                    System.out.println("No hay habitaciones ocupadas, no puedes hacer CHECK-OUT");
                    System.out.println("-----------------------------------------");
                    break;
                }
            	
                System.out.println("\n=== CHECK-OUT ===");
                System.out.print("Ingrese la clave de la habitación: ");
                String claveCheckOut = in.nextLine().trim();

                Habitacion habitacion = hotel.buscarHabitacionPorClave(claveCheckOut);

                hotel.checkOut(habitacion);
                break;
//______________________________________________________________________________________
            case 5:
                hotel.verDisponibilidadHabitaciones();
                break;
 //______________________________________________________________________________________
            case 6:
            	if (!hotel.hayHuespedesRegistrados()) {
                    System.out.println("NO HAY HUÉSPEDES REGISTRADOS AÚN");
                    System.out.println("-----------------------------------------");
                    break;
                }
            	System.out.println("\n=== BUSCAR HUÉSPED ===");
            	String buscar;
                while (true) {
                    System.out.print("Nombre a buscar: ");
                    buscar = in.nextLine().trim();

                    if (!buscar.isEmpty()) {
                        break;
                    }

                    System.out.println("El nombre no puede estar vacío");
                    System.out.println("-----------------------------------------");  
                }

                Huesped encontrado = hotel.buscarHuesped(buscar);

                if (encontrado == null) {
                    System.out.println("No se encontró ningún huésped con ese nombre");
                } else {
                    System.out.println("Huésped encontrado:");
                    System.out.println(encontrado); 
                }
                
                break;
  //______________________________________________________________________________________
            case 7:
                hotel.verRegistroHuespedes();
                break;
//______________________________________________________________________________________
            case 8:
                hotel.ingresosTotales();
                break;
//______________________________________________________________________________________
            case 9:
                System.out.println("¡GRACIAS POR VISITAR NUESTRO HOTEL, VUELVA PRONTO!");
                break;

            default:
                System.out.println("Opción inválida, debes elegir una de las 9 opciones");
            }

        } while (opcion != 9);
        
        in.close();
	}
     /**
	 * Método auxiliar que solicita y valida la entrada de un número entero
	 * 
	 * Este método se utiliza en el menú cada vez que el usuario debe ingresar
	 * un valor numérico (como noches, cantidad de huéspedes, etc.)
	 * 
	 * Si el usuario escribe algo que no es un número entero, se muestra un mensaje
	 * de error y se vuelve a pedir la entrada hasta que sea válida
	 *
	 * @param in Objeto Scanner desde el cual se leerá la entrada
	 * @return El número entero validado ingresado por el usuario
	 */
	public static int leerEntero(Scanner in) {
		while (true) {
	        //Se valida que el numero sea entero
	        while (!in.hasNextInt()) {
	        	
	            System.out.println("INGRESE UN NÚMERO ENTERO VÁLIDO");
	            in.next(); 
	        }
	        int numero = in.nextInt();
	        in.nextLine(); // limpia el buffer

	        //Aqui que sea entero positivo
	        if (numero >= 0) {
	            return numero;
	        } else {
	            System.out.println("INGRESE SOLO ENTEROS POSITIVOS");
	         }
	    }
	
	}

	
        
}

	

