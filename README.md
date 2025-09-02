#Default Servlet

## 1. 기본 개념

- DefaultServlet은 Tomcat과 같은 서블릿 컨테이너에서 기본적으로 제공하는 서블릿이다.

- 주로 정적 자원(HTML, CSS, JS, 이미지 등)을 처리하는 역할을 합니다.

- 개발자가 직접 구현하지 않아도 Tomcat이 내부적으로 등록해 두었기 때문에 특별히 설정하지 않아도 동작한다.


## 2. 동작 원리

- 클라이언트가 요청한 URL이 특정 매핑된 서블릿이나 JSP와 일치하지 않을 경우, 마지막으로 default servlet이 해당 요청을 처리한다.

- 예를 들어 `/images/logo.png` 요청이 들어오면, 매핑된 서블릿이 없으므로 
default servlet이 `/images/logo.png` 파일을 찾아 응답으로 돌려준다.


## 3. 특징

1. 정적 리소스 제공
- HTML, CSS, JS, 이미지, PDF 등과 같은 파일을 서블릿 대신 바로 브라우저로 전달.

2.컨테이너에 의존적
- Tomcat에서는 `org.apache.catalina.servlets.DefaultServlet` 클래스가 담당.

3. web.xml 설정 가능
- `<servlet-mapping>`을 이용해 default servlet의 URL 패턴을 변경할 수도 있음.

- 일반적으로 `/`에 매핑되며, 다른 서블릿 매핑과 충돌이 없을 경우 동작.


## 4. 요약

- DefaultServlet은 **정적 자원을 제공하는 톰캣 내장 서블릿**입니다.

- 요청이 특정 서블릿이나 JSP에 매핑되지 않으면 default servlet이 동작합니다.

- 개발자가 직접 만드는 것이 아니라 톰캣이 기본 제공하므로 보통은 별도 설정이 필요 없습니다.

- 단, 스프링 MVC 같은 프레임워크를 사용할 경우 정적 리소스 처리를 위해 default servlet을 명시적으로 활성화하는 경우가 있습니다.