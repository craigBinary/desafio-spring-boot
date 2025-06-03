# API RESTful - NUEVO SPA
API RESTful para la empresa NUEVO SPA, encargada de administrar usuarios,tareas y estados con autenticación de usuarios.

## 💻 Implementación Craig Fernández

### 🔧 Tecnologías Utilizadas

- Java 17
- Spring Boot 3.4.6
- Maven
- Spring Security con JWT
- JPA con H2 Database (modo memoria)
- Swagger/OpenAPI 3 (`springdoc-openapi`)
- JUnit 5 + Mockito

### 🚀 Endpoints Principales

| Método | Endpoint               | Descripción                         |
|--------|------------------------|-------------------------------------|
| POST   | `/login`               | Autenticación y generación de token |
| GET    | `/api/v1/usuarios`     | Listar usuarios                     |
| GET    | `/api/v1/usuarios/{id}`| Obtener usuario por ID              |
| POST   | `/api/v1/usuarios`     | Agregar usuario                     |
| PUT    | `/api/v1/usuarios/{id}`| Actualizar usuario por ID           |
| DELETE | `/api/v1/usuarios/{id}`| Eliminar usuario por ID             |
| GET    | `/api/v1/tareas/{id}`  | Obtener tarea por ID                |
| GET    | `/api/v1/tareas`       | Listar tareas                       |
| POST   | `/api/v1/tareas`       | Agregar tarea                       |
| PUT    | `/api/v1/tareas/{id}`  | Actualizar tarea por ID             |
| DELETE | `/api/v1/tareas/{id}`  | Eliminar tarea por ID               |
| GET    | `/api/v1/estados`      | Listar estados                      |
| POST   | `/api/v1/estados`      | Agregar estados                     |
| DELETE | `/api/v1/estados/{id}` | Eliminar estado                     |

---
## Configuración
1. Clonar el repositorio:
    ```bash
    git clone https://github.com/craigBinary/SpringBoot-Previred.git
    ```
2. Compilar y ejecutar el proyecto en el directorio ("taskmanagementapi"): cd taskmanagementapi;
    ```bash
    ./mvnw clean install
    ./mvnw.cmd clean spring-boot:run
    ```

### 🧪 Pruebas Unitarias

Incluye pruebas para:
- `EstadoTareaController`
- `TareaController`
- `UsuarioController`
- `Authentication`

ir a la ruta del proyecto ("taskmanagementapi"): cd taskmanagementapi;
Ejecuta los tests con:

```bash
.\mvnw.cmd clean test
```

---

### 🔐 Autenticación

1. Realizar `POST` a `/login` con JSON:

```json
{
    "nombreUsuario": "Craig Fernandez",
    "clave": "12345678"
}
```

2. Copiar el token JWT de la respuesta.

3. Agregarlo en los headers de tus peticiones como:

```
Authorization: Bearer <token>
```

---

### 📄 Swagger UI

Accede a la documentación interactiva en:  
[http://localhost:8080/swagger-ui.html](http://localhost:8080/v3/api-docs)

---

### 📂 Archivos Incluidos

- `Api_Gestion_de_Tareas.postman_collection.json` → colección de Postman con token propagado automáticamente.
- `openapi.yml` → documentación OpenAPI del sistema de tareas.