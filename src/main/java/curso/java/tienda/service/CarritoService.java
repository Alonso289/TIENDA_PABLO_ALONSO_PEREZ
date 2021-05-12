package curso.java.tienda.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import curso.java.tienda.model.Producto;

@Service
public class CarritoService {

	public double getTotal(ArrayList<Producto> carrito) {

		double total = 0;

		for (int i = 0; i < carrito.size(); i++) {
			Producto producto = carrito.get(i);
			total += producto.getPrecio();
		}
		return total;
	}
}
