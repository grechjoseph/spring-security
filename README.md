<h1>Application Config</h1>
<ol>
    <li>Create @Bean PasswordEncoder that returns a new BCryptPasswordEncoder instance. This will be used for encoding/decoding password.</li>
</ol>

<h1>MyUserDetails</h1>
<ol>
<li>Create POJO MyUserDetails extending org.springframework.security.core.userdetails.UserDetails. This will represent a User Object.</li>
<li>Implement UserDetails methods and override as necessary.</li>
</ol>

<h1>MyUserDetailsService</h1>
<ol>
<li>Create Service MyUserDetailsService, implementing UserDetailsService.</li>
<li>Override method loadUserByUsername (eg: Fetch from DB).</li>
</ol>

<h1>MyAuthenticationProvider</h1>
<ol>
<li>Create Service extending org.springframework.security.authentication.dao.DaoAuthenticationProvider.</li>
<li>Override method additionalAuthenticationChecks to customize handling of credentials.</li>
</ol>

<h1>Security Config</h1>
<ol>   
<li>Create SecurityConfig extending org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter.</li>
<li>@EnableWebSecurity - To enable Web Security.</li>
<li>@EnableGlobalMethodSecurity - To enable @PreAuthorize/@PostAuthorize.</li>
<li>@Override configure(AuthenticationManagerBuilder) to define which authentication provider and user details service to use.</li>
<li>@Override configure(HttpSecurity) to define security flow (eg: which endpoints to ingore, and which to authenticate, also, and().formLogin() provided the default login to the user.).</li>
</ol>

<h1>ResourceController</h1>
<ol>
<li>Protected by Default with SecurityConfig.</li>
<li>@PreAuthorize("hasAuthority('PERMISSION_NAME')") to allow access by users having Authority PERMISSION_NAME only.</li>
</ol>

<h1>FilterOne/FilterTwo</h1>
<ol>
<li>These filters are not related to Spring Security, and therefore are not part of the Spring Security Filter Chain. Instead, these are part of the parent chain (ApplicationFilterChain).</li>
<li>Override doFilter, including chain.doFilter.</li>
<li>Add @Bean FilterRegistrationBean< FilterOne > and another for FilterTwo. These will be loaded in the order they are compiled. Alternatively, setOrder(LOWEST_PRECEDENCE) to run last, LOWEST_PRECENDE - 1 to run before last, etc.../li>
</ol>

<h1>SecurityFilter</h1>
<ol>
<li>In order to add a Filter to the SpringSecurityFilterChain, use http...and().addFilterAfter(new YourFilter(), Filter-To-Follow.class).</li>
<li>NOTE: Filter-To-Follow.class must be a filter already registered to the SpringSecurityFilterChain.</li>
</ol>