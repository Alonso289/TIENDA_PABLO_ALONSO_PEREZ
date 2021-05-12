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

	@GetMapping("")
	public String index(HttpSession session, Model model) {
		// Carga de datos inicial
		// Data.cargaDatos(us, ps);

		if (session.getAttribute("carrito") == null) {
			ArrayList<Producto> carrito = new ArrayList<Producto>();
			session.setAttribute("carrito", carrito);
		}

		model.addAttribute("listaProductos", ps.getListaProductos());
		model.addAttribute("listaCategorias", cs.getListaCategorias());

		logger.info("ACCEDIENDO A LA PAGINA DE INICIO");
		return "index";
	}

	@GetMapping("/producto/list")
	public String listaProducto(Model model) {

		logger.info("OBTENIENDO LA LISTA DE PRODUCTOS");

		model.addAttribute("listaProductos", ps.getListaProductos());
		model.addAttribute("listaCategorias", cs.getListaCategorias());

		logger.info("MOSTRANDO LA LISTA DE PRODUCTOS");
		return "/producto/list";
	}

	@GetMapping("/producto/verProducto/{id}")
	public String verProducto(Model model, @PathVariable(value = "id") int id) {

		logger.info("OBTENIENDO PRODUCTO");

		model.addAttribute("producto", ps.getProducto(id));
		model.addAttribute("listaCategorias", cs.getListaCategorias());

		logger.info("MOSTRANDO VISTA DEL PRODUCTO");
		return "/producto/verProducto";
	}

	@GetMapping("/producto/new")
	public String nuevo(Model model) {

		model.addAttribute("listaCategorias", cs.getListaCategorias());
		model.addAttribute("producto", new Producto());

		logger.info("MOSTRANDO VISTA DE NUEVO PRODUCTO");
		return "/producto/new";
	}

	@PostMapping("/producto/new/submit")
	public String nuevoSubmit(Model model, @Valid @ModelAttribute Producto producto, BindingResult bindingResult/*, @RequestParam("file") MultipartFile file*/) {

		logger.info("ANADIENDO NUEVO PRODUCTO");
		if(bindingResult.hasErrors()) {
			logger.info("NO SE PUDO ANADIR NUEVO PRODUCTO");
		
			return "/producto/new";
		}else {
		/*try {
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
*/
		ps.addProducto(producto);

		logger.info("PRODUCTO ANADIDO");
		return "redirect:/producto/list";
		}
	}

	@GetMapping("/producto/edit/{id}")
	public String edit(Model model, @PathVariable(value = "id") int id) {

		model.addAttribute("listaCategorias", cs.getListaCategorias());
		model.addAttribute("producto", ps.getProducto(id));

		logger.info("MOSTRANDO VISTA DE EDICION PRODUCTO");
		return "/producto/new";
	}

	@PostMapping("/producto/edit/submit")
	public String editSubmit(Model model, @Valid @ModelAttribute Producto producto, BindingResult bindingResult/*, @RequestParam("file") MultipartFile file*/) {

		logger.info("GUARDANDO EDICION DE PRODUCTO");
		if(bindingResult.hasErrors()) {
			logger.info("NO SE PUDO GUARDAR PRODUCTO");
		
			return "/producto/edit";
		}else {
		/*try {
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

		}*/
		ps.addProducto(producto);

		logger.info("PRODUCTO GUARDADO");
		return "redirect:/producto/list";
		}
	}

	@GetMapping("producto/del/{id}")
	public String eliminarUsuario(@PathVariable("id") int id, Model model) {

		logger.info("ELIMINANDO PRODUCTO");

		ps.deleteById(id);

		logger.info("PRODUCTO ELIMINADO");
		return "redirect:/producto/list";
	}

}
