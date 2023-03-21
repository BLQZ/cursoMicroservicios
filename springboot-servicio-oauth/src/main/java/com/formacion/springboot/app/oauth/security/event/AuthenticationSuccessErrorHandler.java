package com.formacion.springboot.app.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.formacion.springboot.app.commons.usuarios.models.entity.Usuario;
import com.formacion.springboot.app.oauth.services.IUsuarioService;

import feign.FeignException;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {
	
	private Logger log = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);
	
	@Autowired
	private IUsuarioService usuarioService;

	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		
		if(authentication.getDetails() instanceof WebAuthenticationDetails) {
			return;
		}
		
//		if(authentication.getName().equalsIgnoreCase("frontendapp")) {
//			return;
//		}
		
		UserDetails user = (UserDetails) authentication.getPrincipal();
		
		String msg = "Success Login " + user.getUsername();
		System.out.println(msg);
		
		log.info(msg);
		
		Usuario usuario = usuarioService.findByUsername(authentication.getName());
		
		if(usuario.getIntentos() != null && usuario.getIntentos() < 3) {
			usuario.setIntentos(0);
			usuarioService.update(usuario, usuario.getId());
		}
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		String msg = "Error en el login: " + exception.getMessage();
		
		log.error(msg);
		
		try {
			Usuario usuario = usuarioService.findByUsername(authentication.getName());
			
			if(usuario.getIntentos() == null) {
				usuario.setIntentos(0);
			}
			
			usuario.setIntentos(usuario.getIntentos()+1);
			
			log.error("Intentos: " + String.valueOf(usuario.getIntentos()));
			usuario.setEnabled((usuario.getIntentos() >=3 ) ? false : true);
			
			usuarioService.update(usuario, usuario.getId());
			
		} catch(FeignException e) {
			log.error(String.format("El usuario %s no existe en el sistema", authentication.getName()));
		}
		
	}

}
