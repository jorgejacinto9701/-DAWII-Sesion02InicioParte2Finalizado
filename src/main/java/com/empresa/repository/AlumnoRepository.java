package com.empresa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.empresa.entity.Alumno;

public interface AlumnoRepository extends JpaRepository<Alumno, Integer> {
	
	public abstract List<Alumno> findByDni(String dni);
	
	@Query("select e from Alumno e where e.dni = :x and e.idAlumno <> :y")
	public abstract List<Alumno> listaAlumnnosPorDniDiferenteAlMismo(@Param("x") String dni,@Param("y")  int idAlumno);


}
