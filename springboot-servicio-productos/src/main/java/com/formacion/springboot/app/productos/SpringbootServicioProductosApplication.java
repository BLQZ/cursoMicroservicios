package com.formacion.springboot.app.productos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.formacion.springboot.app.commons.models.entity"})
public class SpringbootServicioProductosApplication {

	public static void main(String[] args) {
		// Arrancar así por bug en nueva versión de STS
//		SpringApplication app = new SpringApplication(SpringbootServicioProductosApplication.class);
//        app.setDefaultProperties(Collections.singletonMap("server.port", "9080"));
//        app.run(args);
        
		SpringApplication.run(SpringbootServicioProductosApplication.class, args);
	}

}
