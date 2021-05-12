package curso.java.tienda.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

//import javax.persistence.Column;

@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name="id_rol")
	@NotBlank(message="El rol es obligatorio")
	private int rol;
	
	@NotBlank(message="El email es obligatorio")
	private String email;
	
	@NotBlank(message="El clave es obligatorio")
	private String clave;
	
	@NotBlank(message="El nombre es obligatorio")
	private String nombre;
	
	@NotBlank(message="El apellido1 es obligatorio")
	private String apellido1;
	
	@NotBlank(message="El apellido2 es obligatorio")
	private String apellido2;
	
	@NotBlank(message="El direccion es obligatorio")
	private String direccion;
	
	@NotBlank(message="El provincia es obligatorio")
	private String provincia;
	
	@NotBlank(message="El nombre es obligatorio")
	private String localidad;
	
	@NotBlank(message="El telefono es obligatorio")
	private String telefono;
	
	@NotBlank(message="El dni es obligatorio")
	private String dni;

	public Usuario() {
	}

	public Usuario(String apellido1, String apellido2, String clave, String direccion, String dni, String email,
			int rol, String localidad, String nombre, String provincia, String telefono) {
		this.rol = rol;
		this.email = email;
		this.clave = clave;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.direccion = direccion;
		this.localidad = localidad;
		this.provincia = provincia;
		this.telefono = telefono;
		this.dni = dni;
	}

	public Usuario(int id, int rol, String email, String clave, String nombre, String apellido1, String apellido2,
			String direccion, String localidad, String provincia, String telefono, String dni) {
		this.id = id;
		this.rol = rol;
		this.email = email;
		this.clave = clave;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.direccion = direccion;
		this.localidad = localidad;
		this.provincia = provincia;
		this.telefono = telefono;
		this.dni = dni;
	}

	public Usuario(String email, String clave, int rol) {
		this.email = email;
		this.clave = clave;
		this.rol = rol;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public int getRol() {
		return rol;
	}

	public void setRol(int rol) {
		this.rol = rol;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDni() {
		return dni;
	}



}
