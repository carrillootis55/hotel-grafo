package hotel;
/**Clase abstracta Persona
*Contiene datos generales del huesped titular
*/
public abstract class Persona {
	//Atributos
	protected String nombre;
	protected int edad;
	protected String telefono;
	protected String email;
	
	//Constructores
	/**
    *Constructor que inicializa los datos de la persona
    * 
    *@param nombre        	Nombre del huesped titular
    *@param edad          	Edad del huesped titular
    *@param telefono      	Numero telefonico del huesped titular
    *@param email			Correo del huesped titular
    */
	public Persona(String nombre, int edad, String telefono, String email) {
	        this.nombre = nombre;
	        this.edad = edad;
	        this.telefono = telefono;
	        this.email = email;
	}
	
	/**
	*Constructor vacio
	*/
	public Persona() {
		
	}
	
	//Getters y Setters
	public String getNombre() {
		return nombre;
	}

	public int getEdad() {
		return edad;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getEmail() {
		return email;
	}

	
	//Metodos
	/**
    *Metodo para informacion del huesped titular
    *@return informacion del huesped titular 
    */
	public String toString() {
        return "Huesped Titular: " + nombre + "|"+ "Edad: "+ edad + "|"+ "Telefono: "+ telefono + "|" + "Email: "+ email;
        
    }


}
 
