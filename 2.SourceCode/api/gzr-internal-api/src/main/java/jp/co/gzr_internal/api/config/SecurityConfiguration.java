package jp.co.gzr_internal.api.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import jp.co.gzr_internal.api.security.jwt.JWTConfigurer;
import jp.co.gzr_internal.api.security.jwt.TokenProvider;

/**
 * The Class SecurityConfiguration.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /** The authentication manager builder. */
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /** The user details service. */
    private final UserDetailsService userDetailsService;

    /** The token provider. */
    private final TokenProvider tokenProvider;

    /** The cors filter. */
    private final CorsFilter corsFilter;

    /** The problem support. */
    private final SecurityProblemSupport problemSupport;

    /**
     * Instantiates a new security configuration.
     *
     * @param authenticationManagerBuilder the authentication manager builder
     * @param userDetailsService the user details service
     * @param tokenProvider the token provider
     * @param corsFilter the cors filter
     * @param problemSupport the problem support
     */
    public SecurityConfiguration(AuthenticationManagerBuilder authenticationManagerBuilder,
        UserDetailsService userDetailsService, TokenProvider tokenProvider, CorsFilter corsFilter,
        SecurityProblemSupport problemSupport) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.problemSupport = problemSupport;
    }

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        try {
            authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        } catch (Exception e) {
            throw new BeanInitializationException("Security configuration failed", e);
        }
    }

    /* (non-Javadoc)
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#authenticationManagerBean()
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Password encoder.
     *
     * @return the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* (non-Javadoc)
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.WebSecurity)
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**").antMatchers("/swagger-ui/index.html")
            .antMatchers("/test/**");
    }

    /* (non-Javadoc)
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling().authenticationEntryPoint(problemSupport).accessDeniedHandler(problemSupport).and()
            .headers().frameOptions().deny().and().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests()
            .antMatchers("/gzr-internal/api/register").permitAll()
            .antMatchers("/gzr-internal/api/activate").permitAll()
            .antMatchers("/gzr-internal/api/authenticate").permitAll()
            .antMatchers("/gzr-internal/api/reset-password").permitAll()
            .antMatchers("/gzr-internal/api/**").permitAll().and().apply(securityConfigurerAdapter());
    }

    /**
     * Security configurer adapter.
     *
     * @return the JWT configurer
     */
    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }
}
