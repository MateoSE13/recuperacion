import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        http
            .csrf { csrf -> csrf.disable() }
            .authorizeRequests { authz ->
                authz
                    .requestMatchers("/api/users/register").permitAll() // Permite acceso sin autenticación
                    .anyRequest().authenticated() // Requiere autenticación para cualquier otra solicitud
            }
            .formLogin { form ->
                form.permitAll() // Permite acceso a la página de inicio de sesión
            }
            .logout { logout ->
                logout.permitAll() // Permite acceso a la funcionalidad de cierre de sesión
            }
        return http.build()
    }
}
