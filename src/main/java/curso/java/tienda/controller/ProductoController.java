package curso.java.tienda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import curso.java.tienda.model.Producto;
import curso.java.tienda.model.Usuario;
import curso.java.tienda.service.CategoriaService;
import curso.java.tienda.service.ProductoService;
import curso.java.tienda.service.UsuarioService;
import curso.java.tienda.util.Data;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Controller
@RequestMapping("")
public class ProductoController {

	@Autowired
	private CategoriaService cs;
	@Autowired
	private ProductoService ps;
	@Autowired
	private UsuarioService us;

	private static Logger logger = LogManager.getLogger(ProductoController.class);

	
	//MUESTRA LA PAGINA DE INICIO CON EL CATALOGO DE PRODUCTOS POR CATEGORIA
	@GetMapping("")
	public String index(HttpSession session, Model model) {
		
		// CARGA DATOS DE PRUEBA SIMPLONES PUEDE QUE NO FUNCIONE CON VALIDACIONES!!
		// Data.cargaDatos(us, ps);

		Usuario admin = new Usuario(50, 1, "adobe@gmail.com", "a", "admin", "admin", "admin", "admin", "admin", "admin", "12345678", "123456789");
		String clave = admin.getClave();
		clave = UsuarioService.encriptaClave(clave);
		admin.setClave(clave);
		us.addUsuario(admin);
		if (session.getAttribute("carrito") == null) {
			ArrayList<Producto> carrito = new ArrayList<Producto>();
			session.setAttribute("carrito", carrito);
		}

		//ASIGNA DATOS AL MODELO PARA MOSTRARLOS EN LA VISTA
		model.addAttribute("listaProductos", ps.getListaProductos());
		model.addAttribute("listaCategorias", cs.getListaCategorias());

		logger.info("ACCEDIENDO A LA PAGINA DE INICIO");
		return "index";
	}

	//MUESTA LA VISTA DE LISTADO DE PRODUCTOS (NO ES EL CATALOGO)
	@GetMapping("/producto/list")
	public String listaProducto(Model model) {

		logger.info("OBTENIENDO LA LISTA DE PRODUCTOS");

		//ASIGNA DATOS AL MODELO PARA MOSTRALOS EN LA VISTA
		model.addAttribute("listaProductos", ps.getListaProductos());
		model.addAttribute("listaCategorias", cs.getListaCategorias());

		logger.info("MOSTRANDO LA LISTA DE PRODUCTOS");
		return "/producto/list";
	}

	//MUESTRA UN PRODUCTO EN CONCRETO
	@GetMapping("/producto/verProducto/{id}")
	public String verProducto(Model model, @PathVariable(value = "id") int id) {

		logger.info("OBTENIENDO PRODUCTO");
		
		//ASIGNA DATOS AL MODELO PARA MOSTRAR EN LA VISTA
		model.addAttribute("producto", ps.getProducto(id));
		model.addAttribute("listaCategorias", cs.getListaCategorias());

		logger.info("MOSTRANDO VISTA DEL PRODUCTO");
		return "/producto/verProducto";
	}

	//MUESTRA EL FORMULARIO PARA CREAR UN NUEVO PRODUCTO
	@GetMapping("/producto/new")
	public String nuevo(Model model) {

		//ASIGNA DATOS AL MODELO PARA MOSTRAR EN LA VISTA
		model.addAttribute("listaCategorias", cs.getListaCategorias());
		model.addAttribute("producto", new Producto());

		logger.info("MOSTRANDO VISTA DE NUEVO PRODUCTO");
		return "/producto/new";
	}

	//ANADE EL PRODUCTO
	@PostMapping("/producto/new/submit")
	public String nuevoSubmit(Model model, @Valid @ModelAttribute Producto producto,
			@RequestParam("imagen") MultipartFile file) {

		logger.info("ANADIENDO NUEVO PRODUCTO");

		//OBTIENE LA IMAGEN SELECCIONADA Y LA GUARDA
		try {
			if (!file.isEmpty()) {
				producto.setImagen(file.getOriginalFilename());

				ClassLoader loader = Thread.currentThread().getContextClassLoader();
				URL appResourceURL = loader.getResource("static");
				String dbConfigFileRoute = appResourceURL.getPath();
				dbConfigFileRoute = dbConfigFileRoute.substring(1, dbConfigFileRoute.length());

				String ruta = dbConfigFileRoute + "/img/" + file.getOriginalFilename();

				// guardar en el fichero
				Files.copy(file.getInputStream(), Paths.get(ruta));

			}
		} catch (IOException e) {
			System.out.println(e);

		}

		ps.addProducto(producto);

		logger.info("PRODUCTO ANADIDO");
		return "redirect:/producto/list";

	}
	
	//MUESTRA EL FORMULARIO DE EDICION DEL PRODUCTO
	@GetMapping("/producto/edit/{id}")
	public String edit(Model model, @PathVariable(value = "id") int id) {

		//ASIGNA DATOS AL MODELO PARA MOSTRARLOS EN LA VISTA
		model.addAttribute("listaCategorias", cs.getListaCategorias());
		model.addAttribute("producto", ps.getProducto(id));

		logger.info("MOSTRANDO VISTA DE EDICION PRODUCTO");
		return "/producto/new";
	}

	//GUARDA EL PRODUCTO EDITADO
	@PostMapping("/producto/edit/submit")
	public String editSubmit(Model model, @Valid @ModelAttribute Producto producto, BindingResult bindingResult,
			@RequestParam("imagen") MultipartFile file) {

		logger.info("GUARDANDO EDICION DE PRODUCTO");
		
		//OBTIENE LA IMAGEN SELECCIONADA Y LA GUARDA
		try {
			if (!file.isEmpty()) {
				producto.setImagen(file.getOriginalFilename());

				ClassLoader loader = Thread.currentThread().getContextClassLoader();
				URL appResourceURL = loader.getResource("static");
				String dbConfigFileRoute = appResourceURL.getPath();
				dbConfigFileRoute = dbConfigFileRoute.substring(1, dbConfigFileRoute.length());

				String ruta = dbConfigFileRoute + "/img/" + file.getOriginalFilename();

				// guardar en el fichero
				Files.copy(file.getInputStream(), Paths.get(ruta));

			}
		} catch (IOException e) {
			System.out.println(e);

		}
		ps.addProducto(producto);

		logger.info("PRODUCTO GUARDADO");
		return "redirect:/producto/list";

	}

	//ELIMINA EL PRODUCTO
	@GetMapping("producto/del/{id}")
	public String eliminarProducto(@PathVariable("id") int id, Model model) {

		logger.info("ELIMINANDO PRODUCTO");

		ps.deleteById(id);

		logger.info("PRODUCTO ELIMINADO");
		return "redirect:/producto/list";
	}
	
	//BUSQUEDA POR NOMBRE
	
	@GetMapping("/busqueda/nombre")
	public String buscarNombre(@RequestParam(required = true) String buscar, Model model) {

		logger.info("BUSQUEDA POR NOMBRE PRODUCTO");

		//ASIGNA DATOS AL MODELO PARA MOSTRARLOS EN LA VISTA
		model.addAttribute("listaProductos", ps.getListaProductosPorNombre(buscar));
		model.addAttribute("listaCategorias", cs.getListaCategorias());
		
		logger.info("BUSQUEDA REALIZADA");
		return "index";
	}
}
