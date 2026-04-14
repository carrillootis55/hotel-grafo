package hotel;
import java.util.Scanner;

/**
 * Clase Hotel Gestiona todas las habitaciones, huéspedes y operaciones del
 * sistema.
 * @author Otilio Gamboa Carrillo, Alejandro Manuel Cota Ayala
 */
public class Hotel {

	// Atributos
	private double precioSpa;
	private double precioRestaurante;
	private double precioEstacionamiento;
	private Habitacion[][] habitaciones; //Matriz de habitaciones del hotel (4x5), cada habitación puede interpretarse como un NODO dentro de un grafo.
	Huesped[] registroHuespedes;

//_____________________________________________________________________________________________
	/**
	 * Constructor del hotel. Inicializa la matriz de habitaciones y el registro de huéspedes.
	 */
	public Hotel(double spa, double restaurante, double estacionamiento) {
		this.precioSpa = spa;
		this.precioRestaurante = restaurante;
		this.precioEstacionamiento = estacionamiento;

		registroHuespedes = new Huesped[20];
		inicializarHabitaciones();
	}

//_____________________________________________________________________________________________
	/**
	 * Constructor vacío con valores por defecto. Inicializa habitaciones y registro
	 * de huéspedes.
	 */
	public Hotel() {
		this.precioSpa = 400.0;
		this.precioRestaurante = 300.0;
		this.precioEstacionamiento = 100.0;

		registroHuespedes = new Huesped[20];
		inicializarHabitaciones();
	}

//_____________________________________________________________________________________________
	/**
	 * Lee un número entero desde el Scanner. Valida que la entrada sea correcta.
	 * 
	 * @param in Scanner para leer la entrada.
	 * @return Número entero ingresado por el usuario.
	 */
	public int leerEntero(Scanner in) {
		while (true) {
			// Se valida que el numero sea entero
			while (!in.hasNextInt()) {
				System.out.println("INGRESE UN NÚMERO ENTERO VÁLIDO");
				in.next();
			}

			int numero = in.nextInt();
			in.nextLine(); // limpia el buffer

			// Aqui que sea entero positivo
			if (numero >= 0) {
				return numero;
			} else {
				System.out.println("INGRESE SOLO ENTEROS POSITIVOS");
			}
		}
	}
	// _____________________________________________________________________________________________

	/**
	 * Metodo que registra un huésped.
	 * 
	 * @param nombre        Nombre del huésped.
	 * @param edad          Edad del huésped.
	 * @param acompaniantes Número de acompañantes (0 a 3).
	 * @return Huesped creado o null si no cumple rango.
	 */
	/**
	 * Método para registrar un nuevo huésped en el hotel. Se solicitarán datos
	 * básicos, membresía y servicios que desea contratar.
	 */
	public void registrarHuesped() {
		Scanner in = new Scanner(System.in);

		System.out.println("\n=== REGISTRAR HUÉSPED ===");
		// nombre completo del huesped titular
		String nombre;
		while (true) {
			System.out.print("Nombre Completo: ");
			nombre = in.nextLine().trim();

			if (nombre.isEmpty()) {
				System.out.println("El nombre no puede estar vacio");
				System.out.println("-----------------------------------------");
				continue;
			}
			
			// Validar que el nombre no este repetido, aqui se busca el nombre y si devuelve un objeto significa que se esta repitiendo, si esta vacio el nombre aun no esta registrado
	        if (buscarHuesped(nombre) != null) {
	            System.out.println("Ya hay un huésped registrado con ese nombre");
	            System.out.println("-----------------------------------------");
	            continue;
	        }
	        break;
			
		}

		// se pide edad
		int edad;
		while (true) {
			System.out.print("Edad: ");
			edad = leerEntero(in);
			if (edad < 18) {
				System.out.println("Debes ser mayor de edad para reservar una habitación");
				System.out.println("-----------------------------------------");
			} else {
				break;
			}
		}

		// se pide su telefono
		String telefono;
		while (true) {
			System.out.print("Telefono: ");
			telefono = in.nextLine().trim();
			if (!telefono.isEmpty()) {
				break;
			}
			System.out.println("El telefono no puede estar vacio");
			System.out.println("-----------------------------------------");
		}

		// se pide su email
		String email;
		while (true) {
			System.out.print("Email: ");
			email = in.nextLine().trim();
			if (!email.isEmpty()) {
				break;
			}
			System.out.println("El email no puede estar vacio");
			System.out.println("-----------------------------------------");
		}

		// se pide el numero de acompañantes
		int numeroAcompaniantes;
		while (true) {
			System.out.print("Número de acompañantes (0 a 3): ");
			numeroAcompaniantes = leerEntero(in);

			if (numeroAcompaniantes >= 0 && numeroAcompaniantes <= 3) {
				break;
			}

			System.out.println("El número de acompañantes debe estar entre 0 y 3");
			System.out.println("-----------------------------------------");
		}

		// Preguntar por membresía
		String respuestaMembresia;
		while (true) {
			System.out.print("¿Tienes membresía? (si/no): ");
			respuestaMembresia = in.nextLine().trim();

			if (respuestaMembresia.equalsIgnoreCase("si") || respuestaMembresia.equalsIgnoreCase("no")) {
				break;
			}

			System.out.println("Debes responder únicamente 'si' o 'no'");
			System.out.println("-----------------------------------------");
		}

		String tipoMembresia = "Ninguna";

		// Si tiene membresía, pedir tipo validando que no esté vacío
		if (respuestaMembresia.equalsIgnoreCase("si")) {
			while (true) {
				System.out.print("Tipo de membresía (VIP / Cliente frecuente): ");
				tipoMembresia = in.nextLine().trim();

				if (!tipoMembresia.isEmpty() && (tipoMembresia.equalsIgnoreCase("VIP") || tipoMembresia.equalsIgnoreCase("Cliente frecuente"))) {
					break;
				}

				System.out.println("El tipo de membresía no puede estar vacío y debe ser VIP o Cliente frecuente");
				System.out.println("-----------------------------------------");
			}
		}

		// Crear huésped
		Huesped nuevoHuesped = new Huesped(nombre, edad, telefono, email, numeroAcompaniantes, tipoMembresia);

		// Preguntar por servicios
		System.out.println("Seleccione los servicios a contratar:");
		System.out.println("1. Spa");
		System.out.println("2. Restaurante");
		System.out.println("3. Estacionamiento");
		System.out.println("Ingrese cada servicio o 0 para cerrar :");

		while (true) {
			// Si ya agregaste los 3 servicios se cierra automaticamente el menu para elegir
			if (nuevoHuesped.getCantidadServicios() == 3) {
				System.out.println("Ya seleccionaste todos los servicios disponibles");
				break;
			}

			System.out.print("Servicio (1, 2, 3 ó 0 para terminar): ");
			int opcionServicio = leerEntero(in);

			if (opcionServicio == 0) {
				break;
			}

			switch (opcionServicio) {
			case 1:
				if (!nuevoHuesped.tieneServicio("Spa")) {
					nuevoHuesped.agregarServicio(new Servicio("Spa", precioSpa));
				} else {
					System.out.println("El servicio Spa ya fue agregado");
				}
				break;

			case 2:
				if (!nuevoHuesped.tieneServicio("Restaurante")) {
					nuevoHuesped.agregarServicio(new Servicio("Restaurante", precioRestaurante));
				} else {
					System.out.println("El servicio Restaurante ya fue agregado");
				}
				break;

			case 3:
				if (!nuevoHuesped.tieneServicio("Estacionamiento")) {
					nuevoHuesped.agregarServicio(new Servicio("Estacionamiento", precioEstacionamiento));
				} else {
					System.out.println("El servicio Estacionamiento ya fue agregado");
				}
				break;

			default:
				System.out.println("Opción inválida.");
			}
		}

		// Guardar en registro
		boolean registrado = false;
		for (int i = 0; i < registroHuespedes.length; i++) {
			if (registroHuespedes[i] == null) {
				registroHuespedes[i] = nuevoHuesped;
				registrado = true;
				break;
			}
		}

		if (registrado) {
			System.out.println("Huésped registrado con éxito");
		} else {
			System.out.println("No hay espacio para más huéspedes");
		}
	}

//_____________________________________________________________________________________________
	/**
	 * Método que inicializa las 20 habitaciones (4x5)
	 */
	private void inicializarHabitaciones() {
		habitaciones = new Habitacion[4][5];

		for (int j = 0; j < 5; j++) {
			habitaciones[0][j] = new JardinPrivado("P1-" + (j + 1), 4);
			habitaciones[1][j] = new Terraza("P2-" + (j + 1), 4);
			habitaciones[2][j] = new VistaAlberca("P3-" + (j + 1), 4);
			habitaciones[3][j] = new VistaAlMar("P4-" + (j + 1), 4);
		}
	}

//_____________________________________________________________________________________________

	// se devuelve la matriz completa de habitaciones
	public Habitacion[][] getHabitaciones() {
		return habitaciones;
	}

	// se obtienen los precios de cada servicio
	public double getPrecioSpa() {
		return precioSpa;
	}

	public double getPrecioRestaurante() {
		return precioRestaurante;
	}

	public double getPrecioEstacionamiento() {
		return precioEstacionamiento;
	}

//_____________________________________________________________________________________________
	/**
	 * Ver registro de huéspedes: Muestra datos del huésped titular y su habitación.
	 */
	public void verRegistroHuespedes() {
		boolean hayHuespedes = false;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 5; j++) {

				if (habitaciones[i][j].getHuespedTitular() != null) {
					hayHuespedes = true;

					System.out.println("Datos del Huesped:");
					System.out.println(habitaciones[i][j].getHuespedTitular());

					System.out.println("\nInformación de la Habitación Reservada:");
					System.out.println(habitaciones[i][j]);
					System.out.println("------------------------------------------");
				}
			}
		}

		if (!hayHuespedes) {
			System.out.println("NO HAY HUESPEDES HOSPEDADOS");
			System.out.println("-----------------------------------------");
		}
	}

//_____________________________________________________________________________________________
	/**
	 * Método que busca un huésped por nombre. Compara ignorando mayúsculas y
	 * minúsculas usando equalsIgnoreCase(). Elimina espacios al inicio y al final
	 * con trim(), en caso de que el usuario los ponga. Si el huésped se encuentra
	 * se regresa el objeto correspondiente. Si no se encuentra, regresa null.
	 * 
	 * @param nombre Nombre del huésped a buscar.
	 * @return El objeto Huesped encontrado, o null si no existe.
	 */
	// Se usa trim() : String, el cual sirve para eliminar espacios al inicio y al
	// final,
	// en caso de que el usuario los ponga.
	public Huesped buscarHuesped(String nombre) {

		if (nombre == null || nombre.trim().isEmpty()) {
			return null;
		}

		String nombreBuscado = nombre.trim();

		// Búsqueda en el registro de huéspedes
		for (Huesped huespedRegistrado : registroHuespedes) {
			if (huespedRegistrado != null && huespedRegistrado.getNombre().equalsIgnoreCase(nombreBuscado)) {
				return huespedRegistrado; // Huésped encontrado
			}
		}

		// Si no se encontró, se regresa null
		return null;
	}

//_____________________________________________________________________________________________
	/**
	 * Busca una habitación por clave (P1-1, P3-5, etc.)
	 * 
	 * @param clave Clave de habitación
	 * @return Habitacion encontrada o null
	 */
	public Habitacion buscarHabitacionPorClave(String clave) {
		for (int i = 0; i < habitaciones.length; i++) {
			for (int j = 0; j < habitaciones[i].length; j++) {
				if (habitaciones[i][j].getClave().equalsIgnoreCase(clave)) {
					return habitaciones[i][j];
				}
			}
		}
		return null; 
	}

	// _____________________________________________________________________________________________
	/**
	 * Verifica si hay huéspedes registrados.
	 * 
	 * @return true si existe al menos uno, false en caso contrario.
	 */
	public boolean hayHuespedesRegistrados() {
		for (Huesped h : registroHuespedes) {
			if (h != null)
				return true;
		}
		return false;
	}

	// _____________________________________________________________________________________________
	/**
	 * Muestra las habitaciones que están disponibles para ser reservadas
	 *
	 * Si existen habitaciones disponibles, se imprime su clave y su tipo
	 * 
	 */
	public void mostrarClavesHabitacionesDisponibles() {
		System.out.println("\n=== HABITACIONES DISPONIBLES PARA RESERVA ===");
		boolean hayDisponibles = false;

		for (int i = 0; i < habitaciones.length; i++) {
			for (int j = 0; j < habitaciones[i].length; j++) {
				Habitacion h = habitaciones[i][j];

				if (!h.isReservada() && !h.isOcupada()) {
					System.out.println(h.getClave() + " - " + h.getTipo());
					hayDisponibles = true;
				}
			}
		}

		if (!hayDisponibles) {
			System.out.println("NO HAY HABITACIONES DISPONIBLES PARA RESERVAR");
		}

		System.out.println("-----------------------------------------");
	}

	// _____________________________________________________________________________________________
	/**
	 * Método para reservar una habitación.
	 * 
	 */
	public void reservarHabitacion() {

		Scanner in = new Scanner(System.in);

		// Validar si hay huéspedes registrados
		if (!hayHuespedesRegistrados()) {
			System.out.println("No hay huéspedes registrados, registre un huésped para poder reservar");
			System.out.println("-----------------------------------------");
			return;
		}

		// Validar si el hotel está lleno
		if (hotelLleno()) {
			System.out.println("El hotel está lleno. No se puede reservar ninguna habitación");
			System.out.println("-----------------------------------------");
			return;
		}

		// Mostrar habitaciones libres
		mostrarClavesHabitacionesDisponibles();

		System.out.println("\n=== RESERVAR HABITACIÓN ===");

		// Solicitar clave
		System.out.print("Clave de habitación (P1-1, P3-4, etc): ");
		String claveHabitacion = in.nextLine().trim();

		// Buscar habitación por clave
		Habitacion habitacionSeleccionada = buscarHabitacionPorClave(claveHabitacion);

		if (habitacionSeleccionada == null) {
			System.out.println("La habitación no existe");
			return;
		}

		// Solicitar nombre del titular
		System.out.print("Nombre del huésped titular: ");
		String nombreTitular = in.nextLine().trim();

		// Se busca que exista el huesped
		Huesped huespedTitular = buscarHuesped(nombreTitular);

		if (huespedTitular == null) {
			System.out.println("No existe un huésped registrado con ese nombre");
			return;
		}

		// Se pide total de huéspedes
		System.out.print("Total de huéspedes (incluye titular): ");
		int totalHuespedes = leerEntero(in);

		// Se pide número de noches
		System.out.print("Cantidad de noches: ");
		int cantidadNoches = leerEntero(in);

		// Intentar reservar
		boolean reservaExitosa = habitacionSeleccionada.reservar(huespedTitular, totalHuespedes, cantidadNoches);

		if (reservaExitosa) {
			System.out.println("Habitación reservada con éxito");
		} else {
			System.out.println("No se pudo reservar la habitación");
		}
	}

	// _____________________________________________________________________________________________
	/**
	 * Método para realizar check-in.
	 */
	public void checkIn() {
		Scanner in = new Scanner(System.in);
		if (!hayHuespedesRegistrados()) {
			System.out.println("No puedes hacer CHECK-IN sin antes registrar y reservar una habitación");
			System.out.println("-----------------------------------------");
			return;
		}

		if (!hayHabitacionesReservadas()) {
			System.out.println("No hay habitaciones reservadas para hacer CHECK-IN");
			System.out.println("-----------------------------------------");
			return;
		}

		System.out.print("Clave de habitación a ocupar: ");
		String clave = in.nextLine().trim();

		Habitacion habitacion = buscarHabitacionPorClave(clave);

		if (habitacion == null) {
			System.out.println("La habitación no existe");
			return;
		}

		if (!habitacion.isReservada()) {
			System.out.println("La habitación NO está reservada aún");
			return;
		}

		if (habitacion.isOcupada()) {
			System.out.println("La habitación ya está ocupada");
			return;
		}

		System.out.print("Nombre del huésped titular para confirmar: ");
		String nombreTitular = in.nextLine().trim();

		Huesped titular = habitacion.getHuespedTitular();

		if (titular == null || !titular.getNombre().equalsIgnoreCase(nombreTitular)) {
			System.out.println("El nombre no coincide con la reserva");
			return;
		}

		// Si llegó aquí, solo necesita ocupar: NO recibe más parámetros
		boolean exito = habitacion.ocupar();

		if (exito) {
			System.out.println("CHECK-IN COMPLETADO para " + titular.getNombre());
		} else {
			System.out.println("No se pudo realizar el check-in");
			System.out.println("-----------------------------------------");
		}
	}

	// _____________________________________________________________________________________________
	/**
	 * Método para realizar check-out.
	 * 
	 * @param habitacion Habitación a desocupar
	 */
	public void checkOut(Habitacion habitacion) {
		if (habitacion == null) {
			System.out.println("La habitación no existe");
			System.out.println("-----------------------------------------");
			return;
		}

		Huesped huesped = habitacion.getHuespedTitular();

		if (huesped == null) {
			System.out.println("La habitación no está ocupada, no hay nadie para hacer check-out");
			System.out.println("-----------------------------------------");
			return;
		}

		double base = habitacion.calcularCostoBase();
		double servicios = huesped.totalServicios();

		double totalSinDescuento = base + servicios;
		double descuento = huesped.obtenerDescuento() * totalSinDescuento;
		double totalFinal = totalSinDescuento - descuento;

		boolean exito = habitacion.desocupar();

		if (exito) {

			System.out.println("El check-out se realizó exitosamente");
			System.out.println("\n========== TICKET ==========");
			System.out.println("Huésped: " + huesped.getNombre());
			System.out.println("Habitación: " + habitacion.getClave());
			System.out.println("__________________________________________");
			System.out.println("Costo por habitación  $" + base);
			System.out.println("Servicios             $" + servicios);
			System.out.println("Descuento aplicado    $" + descuento);
			System.out.println("___________________________________________");
			System.out.println("TOTAL A PAGAR         $" + totalFinal);
			System.out.println("___________________________________________");

		} else {
			System.out.println("La habitación no está ocupada, por ende NO se puede hacer check-out");
		}

	}

	// _____________________________________________________________________________________________
	/**
	 * Muestra visualmente la disponibilidad de todas las habitaciones del hotel,
	 * indicando cuáles están ocupadas y cuáles se encuentran libres
	 *
	 * Representación: [X] = Habitación ocupada [ ] = Habitación libre P de piso y H
	 * de habitacion Al finalizar la impresión del mapa general, se muestra una
	 * lista detallada exclusivamente con las habitaciones ocupadas, muestra: La
	 * fila y columna donde se encuentra (F#C#) La clave real de la habitación (por
	 * ejemplo, P2 de 3) El nombre del huésped titular que la está ocupando
	 */
	public void verDisponibilidadHabitaciones() {
		System.out.println("\n===DISPONIBILIDAD DE HABITACIONES===\n");

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 5; j++) {
				String coordenada = "P" + (i + 1) + "H" + (j + 1);
				String estado = (habitaciones[i][j].isOcupada() ? "[X]" : "[ ]");
				System.out.print(coordenada + " " + estado + "   ");
			}
			
			System.out.println();
		}

		System.out.println("\n===INFORMACION HABITACIONES OCUPADAS===\n");

		boolean ocupadas = false;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 5; j++) {
				// Se muestra solo si está ocupada y tiene registrado un huésped titular
				if (habitaciones[i][j].isOcupada() && habitaciones[i][j].getHuespedTitular() != null) {

					String coordenada = "P" + (i + 1) + "H" + (j + 1);
					System.out.println(coordenada + " (" + habitaciones[i][j].getClave() + ") Huesped Titular: " + habitaciones[i][j].getHuespedTitular().getNombre());
					ocupadas = true;
				}
			}
		}

		if (!ocupadas) {
			System.out.println("NO HAY HABITACIONES OCUPADAS");
			System.out.println("-----------------------------------------");
		}

		System.out.println();
	}

	// _____________________________________________________________________________________________
	/**
	 * Genera reporte de ingresos. Calcula ingresos por: Tipo de habitación (Jardin
	 * Privado, Terraza, Vista Alberca, Vista al Mar) Servicios contratados por los
	 * huespedes Se calcula el total por habitacion para despues hacer un total
	 * general
	 */
	public void ingresosTotales() {

		double ingresoHabitacionJardin = 0;
		double ingresoHabitacionTerraza = 0;
		double ingresoHabitacionAlberca = 0;
		double ingresoHabitacionMar = 0;
		double ingresoServicios = 0;
		double ingresoTotalConDescuento = 0;
		double totalDescuentos = 0;

		boolean hayOcupadas = false;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 5; j++) {

				Habitacion habitacion = habitaciones[i][j];

				if (habitacion.isOcupada() && habitacion.getHuespedTitular() != null) {
					hayOcupadas = true;
					Huesped h = habitacion.getHuespedTitular();

					double base = habitacion.calcularCostoBase();
					double servicios = h.totalServicios();

					double totalSinDescuento = base + servicios;
					double descuento = h.obtenerDescuento() * totalSinDescuento;
					double totalConDescuento = totalSinDescuento - descuento;

					switch (habitacion.getTipo()) {
					case "P1":
						ingresoHabitacionJardin += base;
						break;
					case "P2":
						ingresoHabitacionTerraza += base;
						break;
					case "P3":
						ingresoHabitacionAlberca += base;
						break;
					case "P4":
						ingresoHabitacionMar += base;
						break;
					}
					ingresoServicios += servicios;

					ingresoTotalConDescuento += totalConDescuento;
					totalDescuentos += descuento;

				}
			}
		}

		if (!hayOcupadas) {
			System.out.println("\n====REPORTE DE INGRESOS====");
			System.out.println("No hay habitaciones ocupadas actualmente");
			return;
		}

		double totalHabitaciones = ingresoHabitacionJardin + ingresoHabitacionTerraza + ingresoHabitacionAlberca + ingresoHabitacionMar;

		System.out.println("===REPORTE DE INGRESOS===");
		System.out.println("Habitaciones P1 - Jardin Privado: $" + ingresoHabitacionJardin);
		System.out.println("Habitaciones P2 - Terraza:        $" + ingresoHabitacionTerraza);
		System.out.println("Habitaciones P3 - Vista Alberca:  $" + ingresoHabitacionAlberca);
		System.out.println("Habitaciones P4 - Vista al Mar:   $" + ingresoHabitacionMar);
		System.out.println("TOTAL HABITACIONES:               $" + totalHabitaciones);
		System.out.println("___________________________________________________________________");
		System.out.println("Ingresos por Servicios:           $" + ingresoServicios);
		System.out.println("___________________________________________________________________");
		System.out.println("TOTAL DESCUENTOS:                 $" + totalDescuentos); 
	    System.out.println("___________________________________________________________________");
		System.out.println("TOTAL GENERAL:                    $" + ingresoTotalConDescuento);
	}

	// _____________________________________________________________________________________________
	/**
	 * Verifica si existe al menos una habitación reservada en el hotel.
	 *
	 * @return true si hay al menos una habitación reservada, false si no hay
	 *         ninguna
	 */
	public boolean hayHabitacionesReservadas() {
		for (Habitacion[] fila : habitaciones) {
			for (Habitacion h : fila) {
				if (h.isReservada()) {
					return true;
				}
			}
		}
		return false;
	}

	// _____________________________________________________________________________________________
	/**
	 * Verifica si existe al menos una habitación ocupada en el hotel.
	 *
	 * @return true si al menos una habitación está ocupada, false si no hay ninguna
	 */
	public boolean hayHabitacionesOcupadas() {
		for (Habitacion[] fila : habitaciones) {
			for (Habitacion h : fila) {
				if (h.isOcupada()) {
					return true;
				}
			}
		}
		return false;
	}
	// _____________________________________________________________________________________________

	/**
	 * Verifica si el hotel esta lleno La habitación NO está disponible si está
	 * reservada o está ocupada.
	 *
	 * @return true si todas las habitaciones están ocupadas o reservadas, false si
	 * existe al menos una habitación disponible
	 */
	public boolean hotelLleno() {
		for (Habitacion[] fila : habitaciones) {
			for (Habitacion h : fila) {
				if (!h.isReservada() && !h.isOcupada()) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	// _____________________________________________________________________________________________
	public void agregarHuespedManual(Huesped h) {
	    for (int i = 0; i < registroHuespedes.length; i++) {
	        if (registroHuespedes[i] == null) {
	            registroHuespedes[i] = h;
	            return;
	        }
	    }
	}
	
	// _____________________________________________________________________________________________
	/**
	 * Método que obtiene las habitaciones vecinas de una habitación específica.
	 * 
	 * Esta parte representa el grafo:
	 * - Cada habitación es un nodo.
	 * - Sus vecinos (arriba, abajo, izquierda, derecha) son los nodos adyacentes.
	 * - Las conexiones entre habitaciones cercanas funcionan como aristas.
	 *
	 * @param fila fila donde se encuentra la habitación en la matriz
	 * @param col columna donde se encuentra la habitación en la matriz
	 * @return arreglo con las habitaciones vecinas 
	 */
	public Habitacion[] obtenerVecinos(int fila, int col) {

	    Habitacion[] vecinos = new Habitacion[4];
	    int contador = 0;
	    
	    //Arreglo para guardar hasta 4 vecinos posibles (arriba, abajo, izquierda, derecha)
	    if (fila > 0) vecinos[contador++] = habitaciones[fila - 1][col];//Si no está en la primera fila, existe vecino arriba
	    
	    if (fila < 3) vecinos[contador++] = habitaciones[fila + 1][col];//Si no está en la última fila, existe vecino abajo
	    
	    if (col > 0) vecinos[contador++] = habitaciones[fila][col - 1]; //Si no está en la primera columna, existe vecino a la izquierda
	    
	    if (col < 4) vecinos[contador++] = habitaciones[fila][col + 1];//Si no está en la última columna, existe vecino a la derecha

	    return vecinos;
	}
	
	// _____________________________________________________________________________________________
	/**
	 * Recorre el grafo de habitaciones para encontrar pares de habitaciones disponibles que estén conectadas entre sí (adyacentes).
	 *
	 * Aquí se usa el grafo porque:
	 * - Se toma una habitación como nodo actual.
	 * - Se consultan sus vecinos con obtenerVecinos().
	 * - Se verifica si algún nodo vecino también está libre.
	 */
	public void habitacionesCercanasDisponibles() {

	    System.out.println("\n=== HABITACIONES CERCANAS DISPONIBLES ===");

	    boolean hay = false;
	    
	    //Recorre todos los nodos del grafo (habitaciones)
	    for (int i = 0; i < 4; i++) {
	        for (int j = 0; j < 5; j++) {

	            Habitacion h = habitaciones[i][j];

	            if (!h.isOcupada() && !h.isReservada()) {

	                Habitacion[] vecinos = obtenerVecinos(i, j);

	                for (int k = 0; k < vecinos.length; k++) {

	                    if (vecinos[k] != null &&
	                        !vecinos[k].isOcupada() &&
	                        !vecinos[k].isReservada()) {

	                        System.out.println(h.getClave() + " y " + vecinos[k].getClave());
	                        hay = true;
	                    }
	                }
	            }
	        }
	    }

	    if (!hay) {
	        System.out.println("No hay habitaciones cercanas disponibles");
	    }
	}
	// _____________________________________________________________________________________________
	/**
	 * Recorre el grafo de habitaciones para encontrar habitaciones ocupadas que estén conectadas con otras habitaciones ocupadas
	 *
	 *Se usa el grafo porque:
	 * - Cada habitación ocupada es un nodo 
	 * - Se consultan sus nodos vecinos 
	 */
	public void habitacionesConVecinosOcupados() {

	    System.out.println("\n=== ZONAS OCUPADAS ===");

	    boolean hay = false;

	    for (int i = 0; i < 4; i++) {
	        for (int j = 0; j < 5; j++) {

	            if (habitaciones[i][j].isOcupada()) {

	                Habitacion[] vecinos = obtenerVecinos(i, j);

	                for (int k = 0; k < vecinos.length; k++) {

	                    if (vecinos[k] != null && vecinos[k].isOcupada()) {

	                        System.out.println(
	                            habitaciones[i][j].getClave() +
	                            " está junto a " +
	                            vecinos[k].getClave()
	                        );
	                        hay = true;
	                    }
	                }
	            }
	        }
	    }

	    if (!hay) {
	        System.out.println("No hay zonas ocupadas conectadas");
	    }
	}
	
	// _____________________________________________________________________________________________
	
	
	
	
	
	// _____________________________________________________________________________________________

}
