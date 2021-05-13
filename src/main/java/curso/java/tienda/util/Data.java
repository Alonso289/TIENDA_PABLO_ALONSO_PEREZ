package curso.java.tienda.util;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import curso.java.tienda.model.Producto;
import curso.java.tienda.model.Rol;
import curso.java.tienda.model.Usuario;
import curso.java.tienda.service.ProductoService;
import curso.java.tienda.service.RolService;
import curso.java.tienda.service.UsuarioService;

public class Data {

	//DATOS SIMPLONES DE PRUEBA
	private static ArrayList<Usuario> listaUsuarios() {
		ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
		listaUsuarios.add(new Usuario("user2@tienda.es", "1234", 2));
		listaUsuarios.add(new Usuario("user3@tienda.es", "1234", 3));
		return listaUsuarios;
	}

	private static ArrayList<Producto> listaProductos() {
		ArrayList<Producto> listaProductos = new ArrayList<Producto>();
		listaProductos.add(new Producto(null, "producto1",
				"descripcion producto 1: Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
				null, 1, null, null, null, "producto1.jpg"));
		listaProductos.add(
				new Producto(1, "producto2", "descripcion producto 2", 50.5, 1, null, null, null, "producto1.jpg"));
		return listaProductos;
	}

	public static void cargaDatos(UsuarioService us, ProductoService ps) {

		for (Usuario usuario : listaUsuarios())
			us.addUsuario(usuario);

		for (Producto producto : listaProductos())
			ps.addProducto(producto);


	}
}
