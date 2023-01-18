package nl.brianvermeer.workshop.coffee.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/", "/webjars/**", "/css/**", "/home", "/index", "/register").permitAll()
                .antMatchers("/orders/add").hasAnyRole("ADMIN", "CUSTOMER")
                .antMatchers("/products/**", "/orders/**", "/persons/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin(
                        form -> form.loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/")
                                .permitAll()
                ).logout(
                        logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                )
                .csrf().disable().headers().frameOptions().disable();
        return http.build();
    }

}
