package fit.lunevale.server;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Main class for the Project Server Application.
 * This class contains the main method to run the Spring Boot application and bean configurations.
 */
@SpringBootApplication
public class ProjectServerApplication {

	/**
	 * The entry point of the Project Server Application.
	 *
	 * @param args the command line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(ProjectServerApplication.class, args);
	}

	/**
	 * Bean configuration for {@link ModelMapper}.
	 * This bean is used for mapping between Entity and DTO objects.
	 *
	 * @return the {@link ModelMapper} bean.
	 */
	@Bean
	ModelMapper modelMapper(){
		return new ModelMapper();
	}

	/**
	 * Bean configuration for {@link ObjectMapper}.
	 * This bean is used for configuring the JSON deserialization behavior, ensuring that
	 * unknown properties in JSON input cause a failure.
	 *
	 * @return the configured {@link ObjectMapper} bean.
	 */
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		return objectMapper;
	}

}
