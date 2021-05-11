package curso.java.tienda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.model.Producto;
import curso.java.tienda.model.Usuario;
import curso.java.tienda.repository.UsuarioRepository;

import java.util.ArrayList;

import org.apache.commons.codec.binary.Base64;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Iterable<Usuario> getListaUsuarios() {

		return usuarioRepository.findAll();
	}

	public void saveListaUsuarios(ArrayList<Usuario> lista) {
		usuarioRepository.saveAll(lista);
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

	public Usuario getUsuario(String email) {
		return usuarioRepository.findByEmail(email);
	}

	public boolean validaLogin(String email, String clave) {

		Usuario usuario = getUsuario(email);
		boolean correcto = false;

		if (usuario != null) 
			correcto = compruebaCredenciales(usuario, clave);		
			
		return correcto;
	}

	// COMPRUEBA CREDENCIALES DE UN USUARIO
	public static boolean compruebaCredenciales(Usuario usuario, String claveIntro) {
		// ENCRIPTA LAS PASS
		Base64 base64 = new Base64();
		claveIntro = new String(base64.encode(claveIntro.getBytes()));
		boolean correcto = false;

		String claveBd = new String(base64.encode(usuario.getClave().getBytes()));

		if (claveIntro.equals(claveBd))
			correcto = true;

		return correcto;
	}

}
