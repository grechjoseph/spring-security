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