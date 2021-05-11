package curso.java.tienda.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.model.Producto;
import curso.java.tienda.model.Usuario;
import curso.java.tienda.repository.ProductoRepository;

@Service
public class ProductoService {

	@Autowired
	private ProductoRepository productoRepository;

	public Iterable<Producto> getListaProductos() {

		return productoRepository.findAll();
	}
	
	public void saveListaProductos(ArrayList<Producto> lista) {
		productoRepository.saveAll(lista);
	}

	public Producto getProducto(int id) {

		return productoRepository.findById(id);
	}

	public Producto getProducto(String nombre) {

		return productoRepository.findByNombre(nombre);
	}

	public void addProducto(Producto producto) {
		productoRepository.save(producto);
		
	}

	public Producto getByNombre(String nombre) {
		return productoRepository.findByNombre(nombre);
	}
}
