package com.formacion.springboot.app.productos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootServicioProductosApplication {

	public static void main(String[] args) {
		// Arrancar así por bug en nueva versión de STS
//		SpringApplication app = new SpringApplication(SpringbootServicioProductosApplication.class);
//        app.setDefaultProperties(Collections.singletonMap("server.port", "8001"));
//        app.run(args);
        
		SpringApplication.run(SpringbootServicioProductosApplication.class, args);
	}

}
