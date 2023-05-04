package org.itstep.advanced;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        var userDetailsManager = new InMemoryUserDetailsManager();
        //Без кодирования пароля
        userDetailsManager.createUser(User.withUsername("admin").password("admin").authorities("write", "read").build());
        userDetailsManager.createUser(User.withUsername("user").password("user").authorities("read").build());
        return userDetailsManager;
    }

    @Bean
    //Deprecated по причине, что это небезопасно - не шифровать пароли
    public PasswordEncoder passwordEncoder() {
        //return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configuration(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeRequests()
                .antMatchers("/guest/**").permitAll()
                .antMatchers("/root/**").hasAuthority("write")
                .antMatchers("/user/**").hasAuthority("read")
                .antMatchers("/common/**").hasAnyAuthority("write","read")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .build();
    }
}
