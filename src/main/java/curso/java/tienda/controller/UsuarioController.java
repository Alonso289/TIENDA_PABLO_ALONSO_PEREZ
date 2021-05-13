package curso.java.tienda.controller;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import curso.java.tienda.model.Usuario;
import curso.java.tienda.service.DetallePedidoService;
import curso.java.tienda.service.PedidoService;
import curso.java.tienda.service.RolService;
import curso.java.tienda.service.UsuarioService;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Controller
@RequestMapping("")
public class UsuarioController {

	@Autowired
	private UsuarioService us;
	@Autowired
	private RolService rs;
	@Autowired
	private PedidoService pedidoService;

	private static Logger logger = LogManager.getLogger(UsuarioController.class);
	
	private static final int ROL_EMPLEADO = 2;
	private static final int ROL_CLIENTE = 3;

	//MUESTRA LA VISTA CON EL LISTADO DE USUARIOS
	@GetMapping("/usuario/list")
	public String listaUsuario(HttpSession session, Model model) {

		logger.info("OBTENIENDO LISTA USUARIOS");

		//SI ACCEDE UN EMPLEADO MUESTRA SOLO LOS CLIENTES
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		if (usuario.getRol() == ROL_EMPLEADO) {
			model.addAttribute("listaUsuarios", us.getListaByRol(ROL_CLIENTE));
		}
		else {
			//SI NO ES EMPLEADO TIENE QUE SER ADMIN Y MUESTRA TODOS LOS USUARIOS
			model.addAttribute("listaUsuarios", us.getListaUsuarios());
		}
		
		//ASIGNA DATOS AL MODELO PARA MOSTRAR EN LA VISTA
		model.addAttribute("listaRoles", rs.getListaRoles());

		logger.info("MOSTRANDO LISTA USUARIOS");
		return "/usuario/list";
	}

	//MUESTRA EL FORMULARIO DE CREACION DE NUEVO USUARIO
	@GetMapping("/usuario/new")
	public String nuevo(Model model) {

		//ASIGNA DATOS AL MODELO PARA MOSTRAR EN LA VISTA
		model.addAttribute("listaRoles", rs.getListaRoles());
		model.addAttribute("usuario", new Usuario());

		logger.info("MOSTRANDO VISTA DE NUEVO USUARIO");
		return "/usuario/new";
	}

	//ANADE EL NUEVO USUARIO
	@PostMapping("/usuario/new/submit")
	public String nuevoSubmit(Model model, @Valid @ModelAttribute Usuario usuario, BindingResult bindingResult) {

		logger.info("ANADIENDO NUEVO USUARIO");
		if (bindingResult.hasErrors()) {

			logger.info("NO SE HA PODIDO ANADIR EL USUARIO");
			model.addAttribute("listaRoles", rs.getListaRoles());
			return "/usuario/new";
		} else {
			logger.info("USUARIO ANADIDO");
			
			//ENCRIPTADO DE CLAVE
			String clave = usuario.getClave();
			clave = UsuarioService.encriptaClave(clave);
			usuario.setClave(clave);
			us.addUsuario(usuario);
			return "redirect:/usuario/list";
		}

		// return "redirect:/usuario/list";
	}

	//ELIMINA USUARIO
	@GetMapping("/del/{id}")
	public String eliminarUsuario(@PathVariable("id") int id, Model model) {

		//ELIMINACION EN CASCADA MANUAL PARA EVITAR LA VISUALIZACION VACIA EN LAS LISTAS
		pedidoService.deletePedido(id);
		
		us.deleteById(id);
		return "redirect:/usuario/list";
	}

	//MUESTRA EL FORMULARIO DE EDICION DEL USUARIO
	@GetMapping("/usuario/edit/{id}")
	public String edit(Model model, @PathVariable(value = "id") int id) {

		//ASIGNA DATOS AL MODELO PARA MOSTRAR EN LA VISTA
		model.addAttribute("listaRoles", rs.getListaRoles());
		model.addAttribute("usuario", us.getUsuario(id));

		logger.info("MOSTRANDO VISTA DE EDICION DE USUARIO");
		return "/usuario/new";
	}

	//GUARDA EL USUARIO EDITADO
	@PostMapping("/usuario/edit/submit")
	public String editSubmit(Model model, @Valid @ModelAttribute Usuario usuario, BindingResult bindingResult) {

		logger.info("GUARDANDO USUARIO");

		if (bindingResult.hasErrors()) {
			logger.info("NO SE HA PODIDO GUARDAR EL USUARIO");

			//ASIGNA DATOS AL MODELO PARA MOSTRARLOS EN LA VISTA
			model.addAttribute("listaRoles", rs.getListaRoles());
			model.addAttribute("usuario", usuario);
			
			return "/usuario/new";
		} else {
			logger.info("USUARIO GUARDADO");

			us.addUsuario(usuario);
			return "redirect:/usuario/list";
		}

	}

	//MUESTRA LA VISTA DEL PERFIL
	@GetMapping("/usuario/perfil")
	public String perfil(HttpSession session, Model model) {

		logger.info("MOSTRANDO PERFIL DEL USUARIO");
	
		return "/usuario/perfil";
	}
	
	//EDITA LA VISTA PERFIL
	@GetMapping("/usuario/perfil/modificar")
	public String modificar(HttpSession session, Model model) {

		logger.info("MOSTRANDO EDICION DEL PERFIL DEL USUARIO");
		
		//DESENCRIPTA LA CLAVE PARA LA VISUALIZACION DEL USUARIO
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		String clave = UsuarioService.desencriptaClave(usuario.getClave());
		usuario.setClave(clave);

		model.addAttribute(usuario);
		return "/usuario/editarPerfil";
	}
	
	//GUARDA CAMBIOS DEL PERFIL
	@PostMapping("/usuario/perfil/submit")
	public String guardarCambios(Model model, HttpSession session, @Valid @ModelAttribute Usuario usuario, BindingResult bindingResult) {

		logger.info("GUARDANDO USUARIO");

		if (bindingResult.hasErrors()) {
			
			logger.info("NO SE HA PODIDO GUARDAR EL USUARIO");			
			return "/usuario/editarPerfil";
			
		} else {
			
			logger.info("USUARIO GUARDADO");
			
			//ENCRIPTA LA CLAVE
			String clave = UsuarioService.encriptaClave(usuario.getClave());			
			usuario.setClave(clave);
			us.addUsuario(usuario);
			
			session.setAttribute("usuario", usuario);
			return "redirect:/usuario/perfil";
		}
		
	}
	
}