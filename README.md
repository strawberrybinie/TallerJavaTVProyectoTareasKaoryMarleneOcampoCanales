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

