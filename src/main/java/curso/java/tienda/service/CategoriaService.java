package curso.java.tienda.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.model.Categoria;
import curso.java.tienda.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public Iterable<Categoria> getAll() {

		return categoriaRepository.findAll();

	}

	public void saveListaCategorias(ArrayList<Categoria> lista) {
		categoriaRepository.saveAll(lista);
	}

	public Iterable<Categoria> getListaCategorias() {

		return categoriaRepository.findAll();
	}

}
