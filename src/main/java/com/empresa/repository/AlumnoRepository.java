package com.empresa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.empresa.entity.Alumno;

public interface AlumnoRepository extends JpaRepository<Alumno, Integer> {
	
	public abstract List<Alumno> findByDni(String dni);
	
	@Query("select e from Alumno e where e.dni = ?1 and e.idAlumno <> ?2")
	public abstract List<Alumno> listaUsuarioPorDniDiferenteDelMismo(String dni, int idAlumno);
	
	@Query("select e from Alumno e where e.nombre = ?1 or e.correo = ?2")
	public abstract List<Alumno> listaUsuarioPorNombreOCorreo(String nombre, String correo);


}