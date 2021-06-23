# Black_Jack_project

# Dependencies Explained
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <version>1.3.156</version>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
These two dependencies are need to read and write to in memory database

    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>
These two dependencies are need to read and write to postgres database

The jdbc dependency is used to configure these values in the application yml files in order to connect to postgres database:

    spring:
      datasource:
        url: ${JDBC_DATABASE_URL}
        username: ${JDBC_DATABASE_USERNAME}
        password: ${JDBC_DATABASE_PASSWORD}
        driverClassName: org.postgresql.Driver