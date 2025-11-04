# Task Manager - Spring Boot

Proyecto de ejemplo: **Gestor de Tareas (Task Manager)**

## Qué contiene
- API REST con Spring Boot (Java 17, Maven)
- Entidades: Task, User, Role
- Seguridad: Spring Security con JWT
- Persistencia: Spring Data JPA (MySQL)
- Documentación: OpenAPI/Swagger

## Requisitos
- Java 17+
- Maven
- MySQL (o cambiar a H2 para pruebas locales)

## Configuración rápida
1. Crear base de datos en MySQL: `CREATE DATABASE taskdb;`
2. Editar `src/main/resources/application.properties` con tu usuario y contraseña de MySQL.
3. Construir:
```
mvn clean package
```
4. Ejecutar:
```
java -jar target/taskmanager-0.0.1-SNAPSHOT.jar
```
5. Endpoints:
- `POST /auth/login` → Body: `{ "username":"user", "password":"password" }` devuelve `token`.
- `GET /tasks` → listar tareas (USER ve las suyas, ADMIN ve todas).
- `POST /tasks` → crear tarea (USER o ADMIN).
- `PUT /tasks/{id}` → actualizar (ADMIN only).
- `DELETE /tasks/{id}` → eliminar (ADMIN only).

## Usuarios creados al iniciar
- `user` / `password` (ROLE_USER)
- `admin` / `adminpass` (ROLE_ADMIN)

> Nota: Por simplicidad los roles se crean al iniciar pero no se asignaron automáticamente a los usuarios en este ejemplo. En una mejora, se debe asignar roles mediante código o migración.

## Probar con Postman
Se incluye un archivo `postman_collection.json` con las peticiones: primero autenticar en `/auth/login`, usar `Authorization: Bearer <token>` en las siguientes peticiones.

## Qué agregué y por qué (explicación paso a paso)
1. **pom.xml**: Dependencias necesarias para web, JPA, seguridad, JWT y MySQL.
2. **application.properties**: Configuración de conexión a MySQL y JWT.
3. **Entidades**: `Task`, `User`, `Role` — representan tablas en la BD.
4. **Repositorios**: Interfaces `JpaRepository` para CRUD automático.
5. **Servicios**: `TaskService` centraliza lógica de tareas.
6. **Controladores**: `AuthController` para login + `TaskController` para endpoints CRUD.
7. **Seguridad**: `JwtUtil`, `JwtFilter`, `SecurityConfig`, `UserDetailsServiceImpl` — autenticación y autorización por roles.
8. **README**: instrucciones para ejecutar.
9. **Postman**: colección con pruebas listadas (login, crear, listar, actualizar, eliminar).
10. **ZIP**: proyecto empaquetado listo para descargar.

## Observaciones y mejoras recomendadas
- Asignar roles a usuarios al crear (en `CommandLineRunner`) para que `user` tenga `ROLE_USER` y `admin` tenga `ROLE_ADMIN`.
- Manejo de excepciones más robusto y validaciones DTO con `@Valid`.
- Tests unitarios/integración.
- Usar H2 en pruebas locales para no depender de MySQL.

