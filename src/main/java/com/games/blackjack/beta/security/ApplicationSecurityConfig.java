//package com.games.blackjack.beta.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    // security settings
//    @Override
//    protected void configure(final HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/api/v1/register/newPlayer").permitAll()
//                .antMatchers("/api/v1/player/**").hasRole("ADMIN")
//                .antMatchers("/api/v1/blackjack/**").hasRole("PLAYER")
////                .antMatchers("/anonymous*").anonymous()
////                .antMatchers("/login*").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic();
////                .and()
////                .formLogin()
////                .loginPage("/login.html")
////                .loginProcessingUrl("/perform_login")
////                .defaultSuccessUrl("/homepage.html", true)
//                //.failureUrl("/login.html?error=true")
////                .failureHandler(authenticationFailureHandler())
////                .and()
////                .logout()
////                .logoutUrl("/perform_logout")
////                .deleteCookies("JSESSIONID");
////                .logoutSuccessHandler(logoutSuccessHandler());
//    }
//
//    // verifies if username and password match in database
//    @Override
//    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("steven").password(passwordEncoder().encode("password")).roles("PLAYER")
//                .and()
//                .withUser("mike").password(passwordEncoder().encode("password")).roles("PLAYER")
//                .and()
//                .withUser("admin").password(passwordEncoder().encode("adminPass")).roles("ADMIN");
//    }
//
//    // encodes password
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
