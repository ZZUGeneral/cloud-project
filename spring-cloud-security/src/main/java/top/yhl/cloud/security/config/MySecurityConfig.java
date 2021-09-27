package top.yhl.cloud.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import top.yhl.cloud.security.filter.LoginFilter;
import top.yhl.cloud.security.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

@Configuration
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;
    @Autowired
    UserService userService;
    @Autowired
    MyAuthenticationDetailSource myAuthenticationDetailSource;

    @Autowired
    MyAccessDecisionManager myAccessDecisionManager;
    @Autowired
    MySecurityMetadataSource mySecurityMetadataSource;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JdbcTokenRepositoryImpl jdbcTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Bean
    public MyAuthenticationProvider myAuthenticationProvider() {
        MyAuthenticationProvider myAuthenticationProvider = new MyAuthenticationProvider();
        myAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        myAuthenticationProvider.setUserDetailsService(userService);
        return myAuthenticationProvider;
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(Arrays.asList(myAuthenticationProvider()));
    }

//    @Bean
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        auth.inMemoryAuthentication().withUser("root").password("123456").roles("admin");
//        auth.userDetailsService(userService);
//    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**", "/vc.jpg");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(mySecurityMetadataSource);
                        o.setAccessDecisionManager(myAccessDecisionManager);
                        return o;
                    }
                })
                .antMatchers("/admin/**").fullyAuthenticated()
                .antMatchers("/rememberme").rememberMe()
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/user/**").hasRole("user")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .authenticationDetailsSource(myAuthenticationDetailSource)
                .loginPage("/login.html")
                .loginProcessingUrl("/doLogin")
                .successHandler((request, response, authentication) -> {
                    Object principal = authentication.getPrincipal();
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(principal));
                    out.flush();
                    out.close();
                })
                .failureHandler((req, resp, e) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(e.getMessage());
                    out.flush();
                    out.close();
                })

//                .defaultSuccessUrl("/index")
//                .successForwardUrl("/index")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
//                .logoutSuccessUrl("/login.html")
                .logoutSuccessHandler((req, resp, auth) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write("注销成功");
                    out.flush();
                    out.close();
                })
                .deleteCookies()
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .permitAll()
                .and()
                .rememberMe()
                .tokenRepository(jdbcTokenRepository())
                .and()
                .csrf().disable().exceptionHandling()
                .authenticationEntryPoint(((req, resp, e) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write("尚未登陆，请先登陆！");
                    out.flush();
                    out.close();
                }))
                .and()
                .sessionManagement()
                .maximumSessions(1)
//                .maxSessionsPreventsLogin(true);
        ;
//        http.addFilterAt(new ConcurrentSessionFilter(sessionRegistryImpl(), event -> {
//            HttpServletResponse response = event.getResponse();
//            response.setContentType("application/json;charset=utf-8");
//            response.setStatus(401);
//            PrintWriter out = response.getWriter();
//            out.write("您已在另一台设备登陆，本设备已下线！");
//            out.flush();
//            out.close();
//        }), ConcurrentSessionFilter.class);
//        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public SessionRegistryImpl sessionRegistryImpl() {
        return new SessionRegistryImpl();
    }

    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                httpServletResponse.setContentType("application/json;charset=utf-8");
                PrintWriter out = httpServletResponse.getWriter();
                out.write("登陆成功！");
                out.flush();
                out.close();
            }
        });

        loginFilter.setAuthenticationFailureHandler((req, resp, auth) -> {
            resp.setContentType("application/json;charset=utf-8");
            PrintWriter out = resp.getWriter();
            out.write("登陆失败");
            out.flush();
            out.close();
        });
        loginFilter.setAuthenticationManager(authenticationManager());
        loginFilter.setFilterProcessesUrl("/doLogin");
        ConcurrentSessionControlAuthenticationStrategy sessionStrategy = new ConcurrentSessionControlAuthenticationStrategy(sessionRegistryImpl());
        sessionStrategy.setMaximumSessions(1);
        loginFilter.setSessionAuthenticationStrategy(sessionStrategy);
        return loginFilter;
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

}
