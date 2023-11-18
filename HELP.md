# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

- [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
- [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.5/maven-plugin/reference/html/)
- [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.5/maven-plugin/reference/html/#build-image)
- [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.1.5/reference/htmlsingle/index.html#using.devtools)
- [Java Mail Sender](https://docs.spring.io/spring-boot/docs/3.1.5/reference/htmlsingle/index.html#io.email)
- [Validation](https://docs.spring.io/spring-boot/docs/3.1.5/reference/htmlsingle/index.html#io.validation)
- [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.1.5/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
- [Thymeleaf](https://docs.spring.io/spring-boot/docs/3.1.5/reference/htmlsingle/index.html#web.servlet.spring-mvc.template-engines)
- [Spring Web](https://docs.spring.io/spring-boot/docs/3.1.5/reference/htmlsingle/index.html#web)

### Guides

The following guides illustrate how to use some features concretely:

- [Validation](https://spring.io/guides/gs/validating-form-input/)
- [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
- [Handling Form Submission](https://spring.io/guides/gs/handling-form-submission/)
- [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
- [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
- [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### 1. Lưu ý: trước khi chạy project lần đầu tiên thì cần vào SQL server tạo CSDL tên là art_devs -> Sau đó mở file data.sql trong thư mục db của project và insert data

### 2. Khi code xong phần được giao thì tiến hành tạo nhánh với tên là MSSV của mình và push code lên git

### Lỗi. Trong package service file AuthInterceptor cần chỉnh hàm hasAccess

- lý do: 1 account có nhiều role dẫn đến việc check phức tạp, còn hiện tại trong hàm chỉ lấy role đầu tiên của tài khoản
