# mysite

### mysite02
1. Packaging
   - war(Tomcat Deploy)
   - web.xml 기반 배포
     
2. Framework
   - 사용안함
3. MVC
   - Model2(Servlet Programming)
     
4. Data Access
   - Repository(DAO) 구현: RAW JDBC API
   - Transaction: 지원안함
     
5. View
   - JSP
   - JSTL & EL
     
6. Security
   - Authentication(인증): HttpSession(Servlet API)기반
   - Authorization(권한): 지원안함
     
7. Serving Static Resources
   - Default Servlet 지원

### mysite03
1. Packaging
   - war(Tomcat Deploy)
   - web.xml 기반 배포
     
2. Framework **
   - Spring Framework
   - XML-Based Configuration
     
3. MVC **
   - Spring MVC
     
4. Data Access **
   - Repository(DAO) 구현: MyBatis
   - Transaction: DataSourceTransactionManager(Spring JDBC), @Transactional(Transaction AOP Proxy)
     
5. View
   - JSP
   - JSTL & EL
     
6. Security **
   - Authentication(인증) & Authorization(권한)
   - Spring Interceptor & ArgumentResolver
   - Declative Annotaions: @Auth, @AuthUser 지원
     
7. Serving Static Resources **
   - Spring Resource Handler: External Resources(files uploaded)
   - Default Servlet Handler: assets(images, css, js, etc..)
     
8. 기능 **
   - Upload File: Spring Multipart Resolver
   - Bean Validation: @Valid Annotaion
   - Exception Handling: @ControllerAdvice Annotation
   - Logging: slf4
   - Spring Application Conext Event Listener
   - 다국어 지원(i18n): Spring Message Source & CookieLocaleResolver
   - Measuring Execution Time: Spring Aspects

### mysite04
1. Packaging
   - war(Tomcat Deploy)
   - web.xml 기반 배포
     
2. Framework
   - Spring Framework
   - Java-Based Configuration **
   - Spring @PropertySource Annotation Based on Environment **
     
3. MVC
   - Spring MVC
     
4. Data Access
   - Repository(DAO) 구현: MyBatis
   - Transaction: DataSourceTransactionManager(Spring JDBC), @Transactional(Transaction AOP Proxy)
     
5. View
   - JSP
   - JSTL & EL
     
6. Security
   - Authentication(인증) & Authorization(권한)
   - Spring Interceptor & ArgumentResolver
   - Declative Annotaions: @Auth, @AuthUser 지원
     
7. Serving Static Resources
   - Spring Resource Handler: External Resources(files uploaded)
   - Default Servlet Handler: assets(images, css, js, etc..)
    
8. 기능
   - mysite03과 동일

### mysite05
1. Packaging
   - war(Tomcat Deploy)
   - Spring WebApplicationInitializer 기반 배포**
     
2. Framework
   - Spring Framework
   - Java-Based Configuration
   - Spring @PropertySource Annotation Based on Environment
     
3. MVC
   - Spring MVC
     
4. Data Access
   - Repository(DAO) 구현: MyBatis
   - Transaction: DataSourceTransactionManager(Spring JDBC), @Transactional(Transaction AOP Proxy)
     
5. View
   - JSP
   - JSTL & EL
     
6. Security **
   - Authentication(인증) & Authorization(권한)
   - Spring Secuirty
     
7. Serving Static Resources
   - Spring Resource Handler: External Resources(files uploaded)
   - Spring Resource Handler: assets(images, css, js, etc..) **
   - Default Servlet Handler: disable **

8. 기능
   - Exception Handling: NoHandlerFoundException 처리 추가 **
   - 이외 기능은 mysite04와 동일

### mysite06
1. Packaging
   - war(Tomcat Deploy)
   - Spring Boot Servlet Initializer 기반 배포**

2. Framework
   - Spring Framework
   - Spring Boot **
   - application.yml(미세설정) **
   - Java-Based Configuration (미세설정) **
   - Spring @PropertySource Annotation Based on Environment

3. MVC
   - Spring MVC

4. Data Access
   - Repository(DAO) 구현: MyBatis
   - Transaction: DataSourceTransactionManager(Spring JDBC), @Transactional(Transaction AOP Proxy)

5. View
   - JSP
   - JSTL & EL

6. Security
   - Authentication(인증) & Authorization(권한)
   - Spring Secuirty

7. Serving Static Resources
   - Spring Resource Handler: External Resources(files uploaded)
   - Spring Resource Handler: assets(images, css, js, etc..) **
   - Default Servlet Handler: disable **

8. 기능
   - mysite05와 동일

### mysite07
1. Packaging
   - jar(Embeded Tomcat) **

2. Framework
   - Spring Framework
   - Spring Boot
   - application.yml
   - Java-Based Fine Configuration
   - Spring @PropertySource Annotation Based on Environment

3. MVC
   - Spring MVC

4. Data Access
   - Repository(DAO) 구현: MyBatis
   - Transaction: DataSourceTransactionManager(Spring JDBC), @Transactional(Transaction AOP Proxy)

5. View
   - Thymeleaf Template View **

6. Security
   - Authentication(인증) & Authorization(권한)
   - Spring Secuirty

7. Serving Static Resources
   - Spring Resource Handler: External Resources(files uploaded)
   - Spring Resource Handler: assets(images, css, js, etc..)
   - Default Servlet Handler: disable
8. 기능

   - mysite06와 동일
