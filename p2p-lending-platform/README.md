# Peer-to-Peer Lending Platform

**B.Tech 2nd Year — Advanced Java Mini Project**

A full working demo built to cover every module of the Advanced Java syllabus:

| Syllabus Module | Where it's used in this project |
|---|---|
| **JDBC** | `ReportService.java` — uses raw `JdbcTemplate` (SQL queries, aggregates, ResultSet mapping) instead of JPA |
| **Servlets** (incl. Session Tracking with `HttpSession`) | `servlet/PingServlet.java` (raw `HttpServlet`), `filter/AuthFilter.java` (raw `Filter`), login session handled in `AuthController` via `HttpSession` |
| **JSP** | All views under `src/main/webapp/WEB-INF/views/*.jsp` (login, register, dashboards, EMI schedule) using JSTL `<c:forEach>`, `<c:if>` |
| **Spring Framework (IOC / DI)** | Every `@Service` and `@Controller` is constructor-injected by Spring — see `@Autowired` constructors |
| **Spring MVC** | `@Controller` classes returning JSP view names, `@RestController` for the JSON report API |
| **Spring Boot** | Auto-configuration, embedded Tomcat, `application.properties`, Spring Initializr-style project layout |
| **JPA / Hibernate** | `entity/*.java` + `repository/*.java` (Spring Data JPA repositories: `JpaRepository`) |

---

## 1. Prerequisites

- **JDK 11** (or 8/17 — adjust `java.version` in `pom.xml` if needed)
- **Maven 3.6+**
- **MySQL 8+**
- IntelliJ IDEA / Eclipse / VS Code (any IDE with Maven support)

## 2. Database Setup

Open MySQL Workbench or terminal and run:

```sql
CREATE DATABASE IF NOT EXISTS p2p_lending;
```

(No need to create tables manually — Hibernate creates them automatically because
`spring.jpa.hibernate.ddl-auto=update` is set.)

## 3. Configure Database Credentials

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

## 4. Build the Project

```bash
mvn clean install
```

## 5. Run the Project

```bash
mvn spring-boot:run
```

Or run `P2pLendingPlatformApplication.java` directly from your IDE.

The app starts at: **http://localhost:8080**

## 6. Using the App

1. Go to `http://localhost:8080/register` and create:
   - One user with role **ADMIN** (e.g. admin@test.com)
   - One user with role **BORROWER**
   - One user with role **LENDER**
2. Login as **Borrower** → apply for a loan.
3. Login as **Admin** → approve the loan (see it under "Pending Loan Applications").
4. Login as **Lender** → fund the approved loan.
5. Login as **Borrower** again → go to "View EMIs" → pay EMIs one by one.
6. Login as **Admin** → see updated totals (Total Disbursed, EMIs Collected, status breakdown) — these numbers come from raw JDBC queries in `ReportService`.

## 7. Extra endpoints to demonstrate individual concepts

| URL | Demonstrates |
|---|---|
| `http://localhost:8080/ping` | Raw Servlet (`PingServlet`) — plain text response, shows session ID |
| `http://localhost:8080/api/reports/summary` | REST API (JSON) backed by raw JDBC (`JdbcTemplate`) |

## 8. Project Structure

```
p2p-lending-platform/
├── pom.xml
├── src/main/java/com/p2plending/
│   ├── P2pLendingPlatformApplication.java
│   ├── config/WebConfig.java          # registers Servlet + Filter
│   ├── servlet/PingServlet.java       # raw HttpServlet
│   ├── filter/AuthFilter.java         # raw Filter (session check)
│   ├── entity/                        # JPA entities
│   ├── repository/                    # Spring Data JPA repositories
│   ├── service/                       # business logic (IOC/DI)
│   │   └── ReportService.java         # raw JDBC via JdbcTemplate
│   └── controller/                    # Spring MVC + REST controllers
├── src/main/resources/
│   ├── application.properties
│   └── static/css/style.css
└── src/main/webapp/WEB-INF/views/     # JSP pages
```

## 9. Common Issues

- **"Access denied for user 'root'"** → check your MySQL password in `application.properties`.
- **Port 8080 already in use** → change `server.port` in `application.properties`.
- **JSP not rendering / 404 on views** → make sure you ran with `mvn spring-boot:run` (packaging is `war`, JSPs need Jasper on the classpath — already included in `pom.xml`).
- **Lombok errors in IDE** → install the "Lombok" plugin in IntelliJ/Eclipse and enable annotation processing.
