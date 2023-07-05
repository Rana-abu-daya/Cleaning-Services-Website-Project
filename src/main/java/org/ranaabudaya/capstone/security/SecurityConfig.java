package org.ranaabudaya.capstone.security;


import org.ranaabudaya.capstone.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserServiceImp userService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider authenticationProvider= new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder ()
    {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(
                        (auth) -> auth
                                .requestMatchers("/", "/login*","/home","/homey","/checkAvailability","/bookings",
                                       "/assets/**", "/css/**", "/js/**", "/sign-up", "/signup-process","/index","/booking","/message/add","/cleaners/sign-up","/cleaners/signup-process","/customers/sign-up","/customers/signup-process").permitAll()
                                .requestMatchers("/profile","/profile/**","/dashboard","/dashboard/**","/reviews","/bookings").authenticated()
                                .requestMatchers("/bookings/edit-booking/**","/bookings/updateBooking/**").hasAnyRole("CLIENT","ADMIN")
                                .requestMatchers("/admins", "/admins/**"
                                ,"/cleaners", "/cleaners/**","/customers", "/customers/**","/services","/services/**","/admins/sign-up","/admins/signup-process",
                                              "/api","/api/**","/customers/admin/sign-up","/customers/admin/signup-process").hasAnyRole( "ADMIN")
                                .requestMatchers("/bookings/start/**").hasAnyRole( "CLEANER")
                                .requestMatchers( "/rating/**").hasAnyRole( "CLIENT")
                                .anyRequest().authenticated()
                )
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .successForwardUrl("/login-process")
                                .failureUrl("/login?error=true")
                                .permitAll()
                )
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .permitAll()
                );


        return http.build();

    }
}