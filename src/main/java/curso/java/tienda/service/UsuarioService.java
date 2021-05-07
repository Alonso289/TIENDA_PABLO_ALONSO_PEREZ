package curso.java.tienda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.model.Usuario;
import curso.java.tienda.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Iterable<Usuario> getListaUsuarios() {
        return usuarioRepository.findAll();
    }

    public void addUsuario(Usuario usuario) {
    	usuarioRepository.save(usuario);
    }
    
    public Usuario getUsuario(long id) {
    	return usuarioRepository.findById(id);
    }
    
    public Usuario getByNombre(String nombre) {
    	return usuarioRepository.findByNombre(nombre);
    }
    
    public void deleteUsuario(Usuario usuario) {
    	usuarioRepository.delete(usuario);
    }
    
    public void deleteById(long id) {
    	usuarioRepository.deleteById(id);
    }
    
    public void deleteByNombre(String nombre) {
    	usuarioRepository.delete(usuarioRepository.findByNombre(nombre));
    }
}
