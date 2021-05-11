package curso.java.tienda.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.model.OpcionesMenu;
import curso.java.tienda.repository.OpcionesMenuRepository;

@Service
public class OpcionesMenuService {

	@Autowired
	private OpcionesMenuRepository opcionesMenuRepository;

	public Iterable<OpcionesMenu> getListaOpciones() {

		return (ArrayList<OpcionesMenu>) opcionesMenuRepository.findAll();
		

		
	}

}
