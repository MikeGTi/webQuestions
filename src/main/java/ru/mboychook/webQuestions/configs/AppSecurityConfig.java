package ru.mboychook.webQuestions.configs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.mboychook.webQuestions.services.UsersService;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    public static final String[] ENDPOINTS_WHITELIST = {
            "/webjars/**",
            "/css/**",
            "/images/**",
            "/js/**",
            "/login"
    };
    public static final String LOGIN_URL = "/login";
    public static final String LOGOUT_URL = "/logout";
    public static final String LOGIN_FAIL_URL = "/error"; //LOGIN_URL + "?error=true";
    public static final String DEFAULT_SUCCESS_URL = "/assessments";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    private final UsersService usersService;
    private final AuthenticationConfiguration configuration;

    public AppSecurityConfig(UsersService usersService, AuthenticationConfiguration configuration) {
        this.usersService = usersService;
        this.configuration = configuration;
    }

    /*@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/ignore1", "/ignore2");
    }*/

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            //.csrf(AbstractHttpConfigurer::disable)
        http
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(ENDPOINTS_WHITELIST).permitAll()
                    .requestMatchers("/users/**").hasAuthority("ADMIN")
                    .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                    .loginPage(LOGIN_URL)
                    .loginProcessingUrl(LOGIN_URL)
                    .failureUrl(LOGIN_FAIL_URL)
                    .usernameParameter(USERNAME)
                    .passwordParameter(PASSWORD)
                    .defaultSuccessUrl(DEFAULT_SUCCESS_URL, true)
                    .permitAll()
            )
            .logout(logout -> logout
                    .logoutUrl(LOGOUT_URL)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl(LOGIN_URL + "?logout=true")
            )
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                    .invalidSessionUrl("/invalidSession")
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(true)
            )
            .rememberMe(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(usersService).passwordEncoder(new BCryptPasswordEncoder());
    }

}
