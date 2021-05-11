package curso.java.tienda.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "opciones_menu")
public class OpcionesMenu {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int id_rol;
	private String opcion;
	private String url_opcion;

	public OpcionesMenu() {

	}

	public OpcionesMenu(int id_rol, String opcion, String url_opcion) {
		this.setId_rol(id_rol);
		this.opcion = opcion;
		this.url_opcion = url_opcion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_rol() {
		return id_rol;
	}

	public void setId_rol(int id_rol) {
		this.id_rol = id_rol;
	}

	public String getOpcion() {
		return opcion;
	}

	public void setOpcion(String opcion) {
		this.opcion = opcion;
	}

	public String getUrl_opcion() {
		return url_opcion;
	}

	public void setUrl_opcion(String url_opcion) {
		this.url_opcion = url_opcion;
	}

}
