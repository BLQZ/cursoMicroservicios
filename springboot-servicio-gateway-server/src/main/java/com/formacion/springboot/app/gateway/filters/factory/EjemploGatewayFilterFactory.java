package com.formacion.springboot.app.gateway.filters.factory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class EjemploGatewayFilterFactory extends AbstractGatewayFilterFactory<EjemploGatewayFilterFactory.Configuracion> {
	
	private final Logger logger = LoggerFactory.getLogger(EjemploGatewayFilterFactory.class);

	public EjemploGatewayFilterFactory() {
		super(Configuracion.class);
	}

	@Override
	public GatewayFilter apply(Configuracion config) {
		return (exchange, chain) -> {
			
			logger.info("ejecutando pre gateway filter factory: " + config.mensaje);
			
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				
				Optional.ofNullable(config.cookieValor).ifPresent(cookie -> {
					exchange.getResponse().addCookie(ResponseCookie.from(config.cookieNombre, cookie).build());
				});
				
				logger.info("ejecutando post gateway filter factory: " + config.mensaje);
				
			}));
		};
	}

	@Override
	public String name() {
		return "EjemploCookie";
	}

	@Override
	public List<String> shortcutFieldOrder() {
		return Arrays.asList("mensaje", "cookieNombre", "cookieValor");
	}
	
	public static class Configuracion {
		
		private String mensaje;
		private String cookieValor;
		private String cookieNombre;
		
		public String getMensaje() {
			return mensaje;
		}
		public void setMensaje(String mensaje) {
			this.mensaje = mensaje;
		}
		public String getCookieValor() {
			return cookieValor;
		}
		public void setCookieValor(String cookieValor) {
			this.cookieValor = cookieValor;
		}
		public String getCookieNombre() {
			return cookieNombre;
		}
		public void setCookieNombre(String cookieNombre) {
			this.cookieNombre = cookieNombre;
		}
	}
	
	/*
	 *FORMA 1 DE CONFIGURAR FILTERS
	 * 
	 * spring:
  cloud:
    gateway:
      routes:
      - id: servicio-productos
        uri: lb://servicio-productos
        predicates:
         - Path=/api/productos/**
        filters:
          - StripPrefix=2
          - name: Ejemplo
            args:
              mensaje: Hola mi mensaje personalizado
              cookienombre: usuario
              cookieValor: AndresGuzman
      - id: servicio-item
        uri: lb://servicio-item
        predicates:
          - Path=/api/items/**
        filters:
          - StripPrefix=2
	 */
	
	/*
	 * PARA DAR UN ORDEN
	 *
	 * @Override
	public GatewayFilter apply(Configuracion config) {
		return new OrderedGatewayFilter((exchange, chain) -> {
			
			logger.info("ejecutando pre gateway filter factory: " + config.mensaje);
			
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				
				Optional.ofNullable(config.cookieValor).ifPresent(cookie -> {
					exchange.getResponse().addCookie(ResponseCookie.from(config.cookieNombre, cookie).build());
				});
				
				logger.info("ejecutando post gateway filter factory: " + config.mensaje);
				
			}));
		}, 2);
	}
	 */
	
	/*
	 * spring:
  cloud:
    gateway:
      routes:
      - id: servicio-productos
        uri: lb://servicio-productos
        predicates:
         - Path=/api/productos/**
         - Header= token, \d+
         - Method=GET, POST
         - Query=color
         - Cookie=color, azul
        filters:
          - StripPrefix=2
          - EjemploCookie=Hola mi mensaje personalizado, usuario, AndresGuzman
      - id: servicio-item
        uri: lb://servicio-item
        predicates:
          - Path=/api/items/**
        filters:
          - StripPrefix=2
          - AddRequestHeader=token-request, 123456
          - AddRequestHeader=token-response, 654321
          - SetResponseHeader=Content-Type, text/plain
          - AddRequestParameter=nombre, andres
	 */

}
