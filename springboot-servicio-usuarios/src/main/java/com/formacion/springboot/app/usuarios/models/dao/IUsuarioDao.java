package com.formacion.springboot.app.usuarios.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.formacion.springboot.app.commons.usuarios.models.entity.Usuario;

@RepositoryRestResource(path = "usuarios")
public interface IUsuarioDao extends PagingAndSortingRepository<Usuario, Long> {
	
	public Usuario findById(Long id);
	
	@RestResource(path="buscar-username")
	public Usuario findByUsername(@Param("username") String username);
	
	@Query("select u from Usuario u where u.username=?1")
	public Usuario obtenerPorUsername(String username);
	
	public Usuario save(Usuario usuario);
	
	public void deleteById(Long id);

}
