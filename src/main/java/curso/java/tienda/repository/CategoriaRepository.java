package curso.java.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import curso.java.tienda.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
