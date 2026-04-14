package hotel;
/**Clase Huesped - Hereda de Persona - incluye membresia y servicios contratados 
 * 
 */
public class Huesped extends Persona {
	
	//Atributos
	protected String tipoMembresia; //Si es VIP, cliente frecuente o ninguno
	protected Servicio[] serviciosContratados;
	int contadorServicios;
    protected int numeroAcompaniantes;
//____________________________________________________________________________________________________________________________________________________	
	/**Constructor que inicializa los datos del huesped
    *@param nombre        	Nombre del huesped titular
    *@param edad          	Edad del huesped titular
    *@param telefono      	Numero telefonico del huesped titular
    *@param email			Correo del huesped titular
    *@param tipoMembresia   Tipo de menbresia del huesped titular
    */
    public Huesped(String nombre, int edad, String telefono, String email, int numeroAcompaniantes, String tipoMembresia) {
        super(nombre, edad, telefono,email); 
        this.numeroAcompaniantes = numeroAcompaniantes;
        this.tipoMembresia = tipoMembresia;
        this.serviciosContratados = new Servicio[10];
        this.contadorServicios = 0;
    }
//____________________________________________________________________________________________________________________________________________________	
	
	/**Metodo que calcula el descuento dependiendo del tipo de membresia
	*15% si es VIP
	*10% si es cliente frecuente
	*0% si no es niguno
	*/
	public double obtenerDescuento() {
		if (tipoMembresia.equalsIgnoreCase("VIP")) {
	        return 0.15;
	    }
	    else if (tipoMembresia.equalsIgnoreCase("Cliente frecuente")) {
	        return 0.10;
	    }
	    else {
	        return 0.0;
	    }
	}
//____________________________________________________________________________________________________________________________________________________		
	// Verificar si ya tiene un servicio contratado
	/**
	 * Verifica si el huésped ya tiene contratado un servicio
	 * Se ignoran mayusculas o minusculas
	 *
	 * @param nombre Nombre del servicio que se desea verificar
	 * @return true si el servicio ya está contratado, false si no lo esta
	 */
    public boolean tieneServicio(String nombre) {
        for (int i = 0; i < contadorServicios; i++) {
            if (serviciosContratados[i].getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }
//____________________________________________________________________________________________________________________________________________________	
	//Metodo tipo arreglo que agrega un servicio al huesped
	/**
	 * Agrega un servicio al huésped dentro del arreglo de servicios contratados
	 * Solo se agrega si aún hay espacio disponible, el servicio no es nulo, si no se repite el servicio
	 *
	 * @param servicios Servicio que se desea agregar al huésped
	 */
	public void agregarServicio(Servicio servicios) {
		if (servicios == null) {
			return;
		}
		
		if (tieneServicio(servicios.getNombre())) {
            System.out.println("El servicio ya fue agregado previamente");
            return;
        }

		if(contadorServicios < serviciosContratados.length) {
			serviciosContratados[contadorServicios++] = servicios;
		} else {
            System.out.println("No se pueden agregar más servicios al huésped " + nombre + ". Límite alcanzado");
        }
	}
	
	/**
	 * Es el arreglo de servicios contratados por el huésped
	 * @return Arreglo de objetos Servicio contratados por el huésped
	 */
	public Servicio[] getServicios() {
		return serviciosContratados;
	}
	
	// Obtener cantidad de servicios
	/**
	 * Obtiene la cantidad de servicios que el huésped ha contratado
	 *
	 * @return Número de servicios registrados
	 */
    public int getCantidadServicios() {
        return contadorServicios;
    }


//____________________________________________________________________________________________________________________________________________________

	//Metodo para saber el costo total de todos los servicios contratados
    /**
     * Calcula el costo total de todos los servicios contratados por el huésped
     *
     * @return El costo total de todos los servicios contratados
     */
	public double totalServicios() {
        double total = 0.0;
        for(Servicio servicios : serviciosContratados) {
            if(servicios != null) {
            	total += servicios.getCosto();
            }
        }
        return total;
	}

	
	@Override
    public String toString() {
        return super.toString() + "|" + "Membresia: "+ tipoMembresia;
        
    }

}
