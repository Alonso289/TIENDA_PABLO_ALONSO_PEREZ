package curso.java.tienda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.controller.ProductoController;
import curso.java.tienda.model.Usuario;
import curso.java.tienda.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private static Logger logger = LogManager.getLogger(ProductoController.class);

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

	public List<Usuario> getListaByRol(int rol) {
		return usuarioRepository.findByRol(rol);

	}

	// VALIDA EL LOGIN
	public boolean validaLogin(String email, String clave) {

		Usuario usuario = getUsuario(email);
		boolean correcto = false;
		
		if (usuario != null) {
			logger.info("USUARIO NO ES NULL");
			correcto = compruebaCredenciales(usuario, clave);
			
		}
		return correcto;
	}

	// COMPRUEBA CREDENCIALES DE UN USUARIO
	public static boolean compruebaCredenciales(Usuario usuario, String claveIntro) {

		// ENCRIPTA LA CLAVE INTRODUCIDA
		claveIntro = encriptaClave(claveIntro);
		
		boolean correcto = false;
		String claveBd = usuario.getClave();
		
		// COMPRUEBA COINCIDENCIA
		if (claveIntro.equals(claveBd))
			correcto = true;

		return correcto;
	}

	// ENCRIPTA CLAVE
	public static String encriptaClave(String clave) {

		Base64 base64 = new Base64();
		return new String(base64.encode(clave.getBytes()));
	}
	
	// DESENCRIPTA CLAVE
	public static String desencriptaClave(String clave) {
		Base64 base64 = new Base64();
		return new String(base64.decode(clave.getBytes()));
	}

}
