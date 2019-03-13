package hello;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;

import static java.util.Arrays.asList;

public class PermitAllCorsConfigurationSource implements CorsConfigurationSource {

    /**
     * {@inheritDoc}
     *
     * Returns a pre-configured configuration that allows everything from everywhere.
     */
    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.setAllowedMethods(asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(asList("Content-Type", "X-REQUESTED-WITH", "Authorization"));
        corsConfiguration.setMaxAge(1800L);
        return corsConfiguration;
    }
}