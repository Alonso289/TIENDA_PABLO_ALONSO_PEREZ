package curso.java.tienda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.model.Usuario;
import curso.java.tienda.repository.UsuarioRepository;
import util.Data;

import org.apache.commons.codec.binary.Base64;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Iterable<Usuario> getListaUsuarios() {
		
		return Data.listaUsuarios();
		//return usuarioRepository.findAll();
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

	public Usuario validarLogin(String email, String password) {

		Usuario usuario = getUsuario(email);

		if (usuario != null) {

			if (compruebaCredenciales(usuario, password)) {

				return usuario;
			}

		} else {
			System.out.print("El usuario no es correcto");
		}

		usuario = null;

		return usuario;
	}

	// COMPRUEBA CREDENCIALES DE UN USUARIO
	public static boolean compruebaCredenciales(Usuario usuario, String passIntro) {
		// ENCRIPTA LAS PASS
		Base64 base64 = new Base64();
		passIntro = new String(base64.encode(passIntro.getBytes()));
		boolean correcto = false;

		String passBd = new String(base64.encode(usuario.getClave().getBytes()));

		if (passIntro.equals(passBd))
			correcto = true;

		return correcto;
	}
}
