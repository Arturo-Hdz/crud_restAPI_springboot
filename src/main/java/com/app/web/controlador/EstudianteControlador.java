package com.app.web.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.app.web.entidad.Estudiante;
import com.app.web.servicio.EstudianteServicio;

@Controller
public class EstudianteControlador {

	@Autowired
	private EstudianteServicio servicio;

	@GetMapping({ "/estudiantes", "/" })
	public String listarEstudiantes(Model modelo) {
		modelo.addAttribute("estudiantes", servicio.listarTodosLosEstudiantes());
		return "estudiantes"; // retorna archivo estudiantes
	}

	@GetMapping("/estudiantes/nuevo")
	public String mostrarEstudianteFormulario(Model modelo) {
		Estudiante estudiante = new Estudiante();
		modelo.addAttribute("estudiante", estudiante);
		return "crear_estudiante";
	}

	@PostMapping("/estudiantes")
	public String guardarEstudiantes(@ModelAttribute("estudiante") Estudiante estudiante) {
		servicio.guardarEstudiante(estudiante);
		return "redirect:/estudiantes";

	}

	@GetMapping("/estudiantes/editar/{id}")
	public String mostrarFormularioDeEditar(@PathVariable Long id, Model modelo) {
		modelo.addAttribute("estudiante", servicio.obtenerEstudianteporID(id));
		return "editar_estudiante";
	}

	@PutMapping("/estudiantes/{id}")
	public String actualizarEstudiante(@PathVariable Long id, @ModelAttribute("estudiante") Estudiante estudiante,
			Model modelo) {
		Estudiante estudianteExistente = servicio.obtenerEstudianteporID(id);
		estudianteExistente.setId(id);
		estudianteExistente.setNombre(estudiante.getNombre());
		estudianteExistente.setApellido(estudiante.getApellido());
		estudianteExistente.setEmail(estudiante.getEmail());
		servicio.actualizarEstudiante(estudianteExistente);
		return "redirect:/estudiantes";
	}

	@DeleteMapping("/estudiantes/{id}")
	public String eliminarEstudiante(@PathVariable Long id) {
		servicio.eliminarEstudiante(id);
		return "redirect:/estudiantes";
	}

}
