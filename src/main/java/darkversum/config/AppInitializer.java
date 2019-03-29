package darkversum.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;

@Slf4j
//@Configuration
public class AppInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) {
		log.info("Properties: "+System.getProperties());
		String os = System.getProperty("os.name");
		String profile = os.contains("Windows") ? "Windows" : "Linux";
		log.info("Starting on {} with profile {}", os, profile);
		servletContext.setAttribute("spring.profiles.active", profile);
	}
}
