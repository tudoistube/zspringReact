#Spring and React Web Application Part5
#Source Document : [React.js and Spring Data REST :: Part 5 - Securing the UI and the API ](https://spring.io/guides/tutorials/react-and-spring-data-rest/ "Example Sources by tudoistube@gmail" )  
##Reference Document : Self Summary on [React.js and Spring Data REST :: Part 5 - Securing the UI and the API ](https://drive.google.com/open?id=16_7Pk9byKYa-obxdjzqzB94vvY7h4MvIGGptoOxPnBI "Example Sources by tudoistube@gmail" )  
* spring-boot-starter-security, thymeleaf-extras-springsecurity4 의존성을 주입함.  
* AccessManager 를 작성해서 role 을 설정하고 password 를 드러나지 않게 하고 AccessManagerRepository 로 접근함.
* Employee DTO 클래스와 AccessManager 가 n : 1 이 되도록 변수를 추가하고, 권한이 있는 role  manager 마 EmployeeRepository 를 이용하여 데이터를 변경하게 함.  
* UserDetailService 는 AuthorityUtils 를 사용하여 스트링 기반의 role 을 Java 의  List 형태의 GrantedAuthority 로 변환함.  
* SecurityConfiguration 은 접근 규칙을 적용함.  
* SpringDataRestEventHandler 는 Employee DTO 를 특정해서 DB 에 저장되기 전에  해당 데이터를 찾아서 manager 를 지정함.  
* DatabaseLoader 에서 Employee 데이터에 AccessManager 를 할당하고 데이터를 생성함.  
* app.js 의 JSON Schema 에서 manager 는 편집할 수 없게 함.  
    
책 보면서 실습한 소스임.  
공부하면서 올리는 것이어서 틀린 부분이 있을수도 있음(알려주시면 반영하겠음)  
정확한 내용은 위의 저자의 링크를 참조하시기 바람.  

---
React.js 와 Java Spring 을 사용해서 뭔가 공익적인 측면에 도움이 될 수 있는 것을
함께 만드실 분을 찾음.

I hope to make something good to make our society better.  
If you are interested in my idea, please send your mail to me.  
e-mail : tudoistube@gmail.com
---
