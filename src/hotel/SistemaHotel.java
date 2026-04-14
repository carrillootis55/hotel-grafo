package hotel;

public class SistemaHotel {
	public static void main(String[] args) {

        Hotel hotel = new Hotel();

        inicializarDatos(hotel);

        System.out.println("\n=== ESTADO DEL HOTEL ===");
        hotel.verDisponibilidadHabitaciones();

        System.out.println("\n=== ANALISIS DE GRAFO ===");
        hotel.habitacionesCercanasDisponibles();

        System.out.println("\n=== ZONAS OCUPADAS ===");
        hotel.habitacionesConVecinosOcupados();

        System.out.println("\n=== INGRESOS ===");
        hotel.ingresosTotales();
    }

    public static void inicializarDatos(Hotel hotel) {

        // Crear huéspedes
        Huesped h1 = new Huesped("Juan Perez", 30, "123", "juan@mail.com", 2, "VIP");
        Huesped h2 = new Huesped("Ana Lopez", 28, "456", "ana@mail.com", 1, "Cliente frecuente");
        Huesped h3 = new Huesped("Carlos Ruiz", 40, "789", "carlos@mail.com", 3, "Ninguna");

        // Servicios
        h1.agregarServicio(new Servicio("Spa", hotel.getPrecioSpa()));
        h2.agregarServicio(new Servicio("Restaurante", hotel.getPrecioRestaurante()));
        h3.agregarServicio(new Servicio("Estacionamiento", hotel.getPrecioEstacionamiento()));

        // Registrar huéspedes
        hotel.agregarHuespedManual(h1);
        hotel.agregarHuespedManual(h2);
        hotel.agregarHuespedManual(h3);

        // Reservar habitaciones
        Habitacion hab1 = hotel.buscarHabitacionPorClave("P1-1");
        Habitacion hab2 = hotel.buscarHabitacionPorClave("P1-2");
        Habitacion hab3 = hotel.buscarHabitacionPorClave("P2-3");

        hab1.reservar(h1, 3, 2);
        hab2.reservar(h2, 2, 1);
        hab3.reservar(h3, 4, 3);

        // Check-in
        hab1.ocupar();
        hab2.ocupar();
        hab3.ocupar();
    }

}
