package com.springboot.blog;

import com.springboot.blog.entity.Role;
import com.springboot.blog.repository.RoleRepository;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot Blog REST API",
				description = "Spring Boot Blog REST API Information",
				version = "1.0.0",
				contact = @Contact(
						name = "Geronimo",
						email = "masafesio.10@gmail.com"
				),
				license = @License(
                        name = "Apache 2.0",
						url = "http://springdoc.org"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Wiki Documentation",
				url = "https://github.com/Warryoz/spring-boot-rest-api-blog"
		)
)
@AllArgsConstructor
public class SpringbootBlogRestApiApplication implements CommandLineRunner {
	private final RoleRepository roleRepository;
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
//		Role adminRole = new Role();
//		adminRole.setName("ADMIN");
//		roleRepository.save(adminRole);
//
//		Role userRole = new Role();
//		userRole.setName("USER");
//		roleRepository.save(userRole);
	}
}
