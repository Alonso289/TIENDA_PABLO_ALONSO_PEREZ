package curso.java.tienda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import curso.java.tienda.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findById(long id);

	Usuario findByNombre(String nombre);

	Usuario findByEmail(String email);
	
	List<Usuario> findByRol(int rol);
}
