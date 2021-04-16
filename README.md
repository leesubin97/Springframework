# springframework

## Spring MVC 프로젝트의 기본 구조

![https://blog.kakaocdn.net/dn/7bW1X/btqzIzP4aFQ/90cA6zceTunIylLqPZ6QyK/img.png](https://blog.kakaocdn.net/dn/7bW1X/btqzIzP4aFQ/90cA6zceTunIylLqPZ6QyK/img.png)

## Spring의 전체적인 실행 순서

> Request -> DispatcherServlet -> HandlerMapping -> (Controller -> Service -> DAO -> DB -> DAO -> Service -> Controller) -> DispatcherServlet -> ViewResolver -> View -> DispatcherServlet -> Response

### 예시 1. 일반적인 기본 동작 순서

![https://blog.kakaocdn.net/dn/bqbjtc/btqzIzvAeCQ/XckK9KCaZvUxDatEYfggfk/img.png](https://blog.kakaocdn.net/dn/bqbjtc/btqzIzvAeCQ/XckK9KCaZvUxDatEYfggfk/img.png)

### 예시 2. 위 예시에서 Controller 뒷부분의 과정을 생략함

![https://blog.kakaocdn.net/dn/ess5g5/btqzIf5bvL0/ZRj35tyYr8auXnIPSFmm4k/img.png](https://blog.kakaocdn.net/dn/ess5g5/btqzIf5bvL0/ZRj35tyYr8auXnIPSFmm4k/img.png)

### 자세한 스프링 실행 순서

1. 클라이언트가 Request 요청을 하면, **DispatcherServlet**이 요청을 가로챈다. 이 때 DispatcherServlet이 모든 요청을 가로채는 건 아니고 web.xml에 등록된 내용만 가로챈다. 최초의 web.xml 에서는 <url-pattern>이 '/'와 같이 해당 애플리케이션의 모든 URL로 등록돼있기 때문에, 만약 *. do와 같이 특정 URL만 적용하고 싶다면 <url-pattern>의 내용을 바꿔주어 범위를 변경하면 된다.

2. DispatcherServlet이 가로챈 요청을 **HandlerMapping**에게 보내 해당 요청을 처리할 수 있는 Controller를 찾는다.

3. 실제 로직 처리 (Controller -> Service -> DAO -> DB -> DAO -> Service -> Controller)

4. 로직 처리 후 **ViewResolver**를 통해 view 화면을 찾는다.

5. 찾은 view 화면을 **View**에 보내면 이 결과를 다시 DispatcherServlet에 보내고, **DispatcherServlet**는 최종 클라이언트에게 전송한다.

## web.xml 설정 파일

web.xml은 WAS가 최초 구동될 때 WEB-INF 디렉토리에 존재하는 web.xml을 읽고, 그에 해당하는 웹 애플리케이션 설정을 구성한다. 한마디로 **각종 설정을 위한 설정파일**이다.

```xml

<?xml version="1.0" encoding="UTF-8"?>

<web-app version="2.5" xmlns="[http://java.sun.com/xml/ns/javaee"](http://java.sun.com/xml/ns/javaee)

xmlns:xsi="[http://www.w3.org/2001/XMLSchema-instance"](http://www.w3.org/2001/XMLSchema-instance)

xsi:schemaLocation="[http://java.sun.com/xml/ns/javaee](http://java.sun.com/xml/ns/javaee) [https://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"](https://java.sun.com/xml/ns/javaee/web-app_2_5.xsd)>

<!-- The definition of the Root Spring Container shared by all Servlets and Filters -->

<context-param>

<param-name>contextConfigLocation</param-name>

<param-value>/WEB-INF/spring/root-context.xml</param-value>

</context-param>

<!-- Creates the Spring Container shared by all Servlets and Filters -->

<listener>

<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>

</listener>

<!-- Processes application requests -->

<servlet>

<servlet-name>appServlet</servlet-name>

<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>

<init-param>

<param-name>contextConfigLocation</param-name>

<param-value>/WEB-INF/spring/appServlet/servlet-context.xml</param-value>

</init-param>

<load-on-startup>1</load-on-startup>

</servlet>

<servlet-mapping>

<servlet-name>appServlet</servlet-name>

<url-pattern>/</url-pattern>

</servlet-mapping>

</web-app>
```

각 태그를 세부적으로 살펴보자.

- <servlet> : DispatcherServlet을 구현하기 위해 어떤 클래스를 이용해야 할지와 초기 파라미터 정보를 포함하고 있다.
- <servlet-name> : 해당 서블렛의 이름을 지정하면 이 지정된 이름을 가지고 다른 설정 파일에서 해당 서블릿 정보를 참조한다.
- <servlet-class> : 어떤 클래스를 가지고 DispatcherServlet을 구현할 것인지를 명시하고 있다.
- <init-param> : 초기화 파라미터에 대한 정보. servlet에 대한 설정 정보가 여기에 들어간다. 만약 초기화 파라미터에 대한 정보를 기술하지 않을 경우 스프링이 자동적으로 appServlet-context.xml을 이용하여 스프링 컨테이너를 생성한다.
- <load-on-startup> : 서블릿이 로딩될 때 로딩 순서를 결정하는 값. 톰캣이 구동되고 서블릿이 로딩되기 전 해당 서블릿에 요청이 들어오면 서블릿이 구동되기 전까지 기다려야 한다. 이 중 우선순위가 높은 서블릿부터 구동할 때 쓰이는 값이다.
- <servlet-mapping> : 서블렛이 <url-pattern>에서 지정한 패턴으로 클라이언트 요청이 들어오면 해당 <servlet-name>을 가진 servlet에게 이 요청을 토스하는 정보를 기술한다.

## root-context.xml 설정 파일

<?xml version="1.0" encoding="UTF-8"?>

<beans xmlns="[http://www.springframework.org/schema/beans"](http://www.springframework.org/schema/beans)

xmlns:xsi="[http://www.w3.org/2001/XMLSchema-instance"](http://www.w3.org/2001/XMLSchema-instance)

xsi:schemaLocation="[http://www.springframework.org/schema/beans](http://www.springframework.org/schema/beans) [https://www.springframework.org/schema/beans/spring-beans.xsd"](https://www.springframework.org/schema/beans/spring-beans.xsd)>

<!-- Root Context: defines shared resources visible to all other web components -->

</beans>

## servlet-context.xml 설정 파일

servlet-context는 서블릿 관련 설정이다. 여기서 주목해야 하는 부분은 prefix와 suffix 부분이다. 서블릿 설정으로 prefix(접두사)와 suffix(접미사)를 붙여주는 역할을 담당한다. 즉, 우리가 일일이 전체경로와 .jsp를 붙이지 않아도 되도록 도와준다.

그다음으로 <context:component-scan base-package="com.company.first" /> 부분을 보자. 이 부분은 스프링에서 사용하는 bean을 일일이 xml에 선언하지 않고도 필요한 것을 어노테이션(Annotation)을 자동으로 인식하게 하는 역할을 한다.

<?xml version="1.0" encoding="UTF-8"?>

<beans:beans xmlns="[http://www.springframework.org/schema/mvc"](http://www.springframework.org/schema/mvc)

xmlns:xsi="[http://www.w3.org/2001/XMLSchema-instance"](http://www.w3.org/2001/XMLSchema-instance)

xmlns:beans="[http://www.springframework.org/schema/beans"](http://www.springframework.org/schema/beans)

xmlns:context="[http://www.springframework.org/schema/context"](http://www.springframework.org/schema/context)

xsi:schemaLocation="[http://www.springframework.org/schema/mvc](http://www.springframework.org/schema/mvc) [https://www.springframework.org/schema/mvc/spring-mvc.xsd](https://www.springframework.org/schema/mvc/spring-mvc.xsd)

[http://www.springframework.org/schema/beans](http://www.springframework.org/schema/beans) [https://www.springframework.org/schema/beans/spring-beans.xsd](https://www.springframework.org/schema/beans/spring-beans.xsd)

[http://www.springframework.org/schema/context](http://www.springframework.org/schema/context) [https://www.springframework.org/schema/context/spring-context.xsd"](https://www.springframework.org/schema/context/spring-context.xsd)>

<!-- DispatcherServlet Context: defines this servlet's request-processing infrastructure -->

<!-- Enables the Spring MVC @Controller programming model -->

<annotation-driven />

<!-- Handles HTTP GET requests for /resources/** by efficiently serving up static resources in the ${webappRoot}/resources directory -->

<resources mapping="/resources/**" location="/resources/" />

<!-- Resolves views selected for rendering by @Controllers to .jsp resources in the /WEB-INF/views directory -->

<beans:bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">

<beans:property name="prefix" value="/WEB-INF/views/" />

<beans:property name="suffix" value=".jsp" />

</beans:bean>

<context:component-scan base-package="com.company.devpad" />

</beans:beans>

각 태그를 세부적으로 살펴보자.

- <annotation-driven> : @Controller 어노테이션을 감지하여 해당 클래스를 Controller로 등록할 수 있도록 해주는 태그
- <resources> : 정적인 html문서 같은 웹 리소스들의 정보를 기술하는 태그
- <beans:bean class="org.springframework.web.servlet.view.InternalResourceBiewResolver"> : Controller가 Model를 리턴하고 DispatcherServlet이 jsp 파일을 찾을 때 쓰이는 정보를 기술하는 태그. "home"이라는 문자열을 반환하면 /WEB-INF/views/ 경로에서 접미사가 .jsp인 해당 파일을 찾는다. /WEB-INF/views/home.jsp
- <context:component-scan> : Java 파일의 @Component로 등록된 Bean 객체를 찾도록 해주는 태그

## 자바 컨트롤러 파일(Java Controller File)

HomeController.java

package com.company.devpad;

import java.text.DateFormat; ...

@Controller

public class HomeController {

private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

@RequestMapping(value = "/", method = RequestMethod.GET)

public String home(Locale locale, Model model) {

logger.info("Welcome home! The client locale is {}.", locale);

Date date = new Date();

DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

String formattedDate = dateFormat.format(date);

model.addAttribute("serverTime", formattedDate);

return "home";

}

}

**@Controller** : 어노테이션을 붙이면 servlet-context.xml에서 이것을 인식하여 컨트롤러로 등록함.

**@RequestMapping** : 스프링은 HandlerMppaing에 의해 컨트롤러가 결정된다. 이 컨트롤러에서 HandlerAdapter에 의해 실행 메서드가 결정되는 데 @RequestMapping 어노테이션이 그 정보를 제공해 준다. value에 해당하는 url이 GET 방식으로 요청이 들어올 때 해당 메서드를 실행한다.

home 메서드는 serverTime이라는 속성을 Model에 추가하고 이 값은 formattedDate 변수 안에 담긴 현재 날짜 정보를 담고 있다. 이 정보는 JSP에서 클라이언트에게 전달할 HTML 문서를 만들 때 쓰인다. 여기서 모델은 어떤 구조화된 데이터를 담는 객체라고 보면 된다.

마지막으로 "home" 문자열을 반환하는 데 이 문자열은 나중에 servlet-context.xml에 설정된 prefix와 suffix 정보를 참조하여 /WEB-INF/views/home.jsp 파일을 찾는 정보를 제공한다.
