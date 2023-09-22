package org.workshop.coffee.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/", "/webjars/**", "/css/**", "/home", "/index", "/register", "/products/direct/**","/image/**").permitAll()
                .antMatchers("/orders/add", "/orders/myorders/**", "/uploadimage").hasAnyRole("ADMIN", "CUSTOMER")
                .antMatchers("/products/**", "/orders/**", "/persons/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin(
                        form -> form.loginPage("/login")
                                .loginProcessingUrl("/login")
                                .failureUrl("/login?error")
                                .defaultSuccessUrl("/")
                                .permitAll()
                ).logout(
                        logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                )
                .csrf().disable().headers().frameOptions().disable()
                .and().requestCache().disable();

        return http.build();
    }

}
