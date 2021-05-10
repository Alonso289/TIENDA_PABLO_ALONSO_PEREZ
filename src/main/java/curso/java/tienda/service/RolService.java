package curso.java.tienda.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.model.Rol;
import curso.java.tienda.repository.RolRepository;

@Service
public class RolService {

	@Autowired
	private RolRepository rolRepository;

	public Iterable<Rol> getListaRoles() {
		
		return rolRepository.findAll();
	}
	
	public void saveListaRoles(ArrayList<Rol> lista) {
		rolRepository.saveAll(lista);
	}

	public void addRol(Rol rol) {
		rolRepository.save(rol);
		
	}
}
