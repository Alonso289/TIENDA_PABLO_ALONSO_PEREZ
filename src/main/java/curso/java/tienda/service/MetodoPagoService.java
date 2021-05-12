package curso.java.tienda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.model.MetodoPago;
import curso.java.tienda.repository.MetodoPagoRepository;

@Service
public class MetodoPagoService {

	@Autowired
	private MetodoPagoRepository repository;

	public Iterable<MetodoPago> getMetodoPago() {

		return repository.findAll();
	}
}
