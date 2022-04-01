package com.empresa.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.entity.Alumno;
import com.empresa.service.AlumnoService;

@RestController
@RequestMapping("/rest/alumno")
public class AlumnoController {

	@Autowired
	private AlumnoService service;

	@GetMapping
	@ResponseBody
	public ResponseEntity<List<Alumno>> listaAlumno() {
		List<Alumno> lista = service.listaAlumno();
		return ResponseEntity.ok(lista);
	}
	

	@GetMapping("/dni/{dni}")
	@ResponseBody
	public ResponseEntity<List<Alumno>> listaAlumnoPorDni(@PathVariable("dni")String dni) {
		List<Alumno> lista = service.listaAlumnoPorDni(dni);
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/filtro/{nombre}-{correo}")
	@ResponseBody
	public ResponseEntity<List<Alumno>> listaAlumnoPorNombreOCorreo(@PathVariable String nombre,@PathVariable String correo) {
		List<Alumno> lista = service.listaUsuarioPorNombreOCorreo(nombre,correo);
		return ResponseEntity.ok(lista);
	}

	@PostMapping
	@ResponseBody
	public ResponseEntity<HashMap<String, Object>> insertaAlumno(@RequestBody Alumno obj) {
		HashMap<String, Object> salida = new HashMap<String, Object>();
		try {
			List<Alumno> lstAlumno = service.listaAlumnoPorDni(obj.getDni());
			if (CollectionUtils.isEmpty(lstAlumno)) {
				obj.setIdAlumno(0);// Para que registre, sino actualiza
				Alumno objSalida = service.insertaActualizaAlumno(obj);
				if (objSalida == null) {
					salida.put("mensaje", "Error en el registro");
				} else {
					salida.put("mensaje", "Se registro correctamente");
				}
			} else {
				salida.put("mensaje", "El dni " + obj.getDni() + " ya existe.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", "Error en el registro");
		}
		return ResponseEntity.ok(salida);
	}
	
	@PutMapping
	@ResponseBody
	public ResponseEntity<HashMap<String, Object>> actualizaAlumno(@RequestBody Alumno obj){
		HashMap<String, Object> salida = new HashMap<String, Object>();
		try {
			Optional<Alumno> optional = service.buscaPorId(obj.getIdAlumno());
			if (optional.isPresent()) {
				 List<Alumno> list = service.listaAlumnoPorDniDiferenteDelMismo(obj.getDni(), obj.getIdAlumno());
				 if (CollectionUtils.isEmpty(list)) {
					 Alumno objSalida = service.insertaActualizaAlumno(obj);
					 if (objSalida == null) {
						 salida.put("mensaje", "Error en la actualización");
					 }else {
						 salida.put("mensaje", "Éxito en la actualización");
					 }
				 }else {
					 salida.put("mensaje", "El Dni ya existe : " + obj.getDni());
				 }
			}else {
				salida.put("mensaje", "No existe el ID: " + obj.getIdAlumno());
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", "Error en la actualización");
		}
		return ResponseEntity.ok(salida);
	}
	
	
	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<HashMap<String, Object>> eliminaAlumno(@PathVariable("id")int idAlumno){
		HashMap<String, Object> salida = new HashMap<String, Object>();
		try {
			Optional<Alumno> optional = service.buscaPorId(idAlumno);
			if (optional.isEmpty()) {
				salida.put("mensaje", "No existe el ID: " + idAlumno);
			}else {
				service.eliminaPorId(idAlumno);
				salida.put("mensaje", "Éxito en la eliminación");
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", "Error en la eliminación");
		}
		return ResponseEntity.ok(salida);
	}
	
}








