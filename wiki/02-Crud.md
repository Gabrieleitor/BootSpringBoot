# 2- Nuestro primer CRUD


Actualizamos el entorno al **paso 2** de la aplicación.
```
$ git checkout -f step-2
``` 

Para nuestro primer CRUD vamos a utilizar como ejemplo una aplicación basada en películas. 
##### Requerimientos:
- Cada película contará con un nombre y un género.
- Se debe poder consultar la información de una película.
- Se debe podes dar de alta una nueva película.
- Se debe poder eliminar una película.
- Se debe poder actualizar una película existente.

## 2.1. Creación de un Controller
Dentro de las carpetas `src.main.java` agregar un nuevo package `com.training.controller`. Crear la clase `MovieController` teniendo en cuenta la annotation `@RestController` explicada en el paso 1.<br />
Para modelar las operaciones CRUD se tendrán en cuenta las buenas prácticas del diseño de una API REST, separando nuestra API en recursos lógicos y accediendo a ellos a través de peticiones HTTP.

C (Create): POST. Crea una nueva película.<br />
R (Read): GET. Devuelve la información de una película.<br />
U (Update): PUT. Actualiza la información de una película.<br />
D (Delete): DELETE. Elimina una película existente.<br />

Ejemplo de estructura de `MovieController`:

```java
@RestController
@RequestMapping("/movies")
public class MovieController {

    @GetMapping(value = "/{id}")
    public Movie getMovie(@PathVariable Long id) {
        //TODO
    }
    
    @PostMapping
    public Movie saveMovie(@RequestBody Movie movie) {
        //TODO
    }

    @PutMapping("/{id}")
    public Movie updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        //TODO
    }

    @DeleteMapping(value = "/{id}")
    public void deleteMovie(@PathVariable Long id) {
        //TODO
    }

}
```

Annotations a tener en cuenta:
- `@RequestMapping`: se utiliza para asignar solicitudes via web a los métodos de Spring Controller correspondientes.
- `@GetMapping`: se utiliza para manejar el tipo de solicitud HTTP GET.
- `@PostMapping`: se utiliza para manejar el tipo de solicitud HTTP POST.
- `@PutMapping`: se utiliza para manejar el tipo de solicitud HTTP PUT.
- `@DeleteMapping`: se utiliza para manejar el tipo de solicitud HTTP DELETE.
- `@RequestBody`: se utiliza para asignar el body de una solicitud HTTP a un objeto del dominio (deserealización).
- `@PathVariable`: se utiliza para vincular la(s) variable(s) contenida(s) dentro del URI a los parámetros que recibe el método en el Controller.

## 2.2. Creación de un Servicio
Dentro de las carpetas `src.main.java` agregar un nuevo package `com.training.service`. Crear la clase `MovieService` teniendo en cuenta la annotation `@Service`, que indicará que la clase pertenece a la capa de negocio.

Ejemplo de estructura de `MovieService`:

```java
@Service
public class MovieService {

    public Movie findById(Long id) {
        //TODO
    }

    public Movie save(Movie movie) {
        //TODO
    }

    public void deleteById(Long id) {
        //TODO
    }
}
```

## 2.3. Creación del modelo de datos
Dentro de las carpetas `src.main.java` agregar un nuevo package `com.training.model` y agregar las clases necesarias para modelar una película y su información.

Ejemplo de estructura de `Movie`:

```java
public class Movie {

    private Long id;

    private String name;

    private Genre genre;
    
    ...
}
```

## 2.4. Creación de un Repositorio
Dentro de las carpetas `src.main.java` agregar un nuevo package `com.training.repository` y crear la clase `MovieRepository`.
Para indicarle a Spring que la misma pertenece a la capa de datos basta con implementar alguna de las interfaces que provee Spring Data:
- CrudRepository
- PagingAndSortingRepository
- JpaRepository

En este caso usaremos `JpaRepository` ya que la misma proporciona métodos relacionados con JPA que se explicará en el próximo paso.

No nos debemos olvidar de agregar la dependencia a JPA en el archivo `pom.xml`

```xml
...
<dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
...
```

Ejemplo de estructura de `MovieRepository`:

```java
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
```

Por último, nuestra(s) clase(s) del modelo se convertirán en entidades que serán persistidas. Para ello se debe especificar con la annotation `@Entity` y se debe asignar a la misma un ID.

```java
@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Genre genre;
    
    ...
}
```

Nota: Nuestra clase debe contar con getters, setters y constructor para que pueda ser construída en el contexto de Spring. Como requisito se pide utilizar las annotations de lombok.


## 2.5. Configurando JPA con H2
Una vez que tenemos definidos nuestro modelo para ser persistido, necesitamos contar con una base de datos. Utilizaremos H2, que es una base de datos relacional en memoria (esto significa que los datos solo vivirán durante la ejecución de nuestra aplicación y cuando esta termine se perderán).

1) Agregar la dependencia en el archivo  `pom.xml`:
```xml
...
<dependency>
     <groupId>com.h2database</groupId>
     <artifactId>h2</artifactId>
     <scope>runtime</scope>
</dependency>
```
2) Configurar las properties necesarias para la conexión. En este caso de usa la base de datos por default. 
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driverClassName: org.h2.Driver
    username: sa
    password:
```

3) Por último, podemos acceder a la consola de H2 para hacer las consultas necesarias a nuestra base de datos en memoria una vez que iniciamos nuestra aplicación Spring Boot.
```
http://localhost:8080/h2-console/
```

<img src="images/h2_console.PNG" width="250" alt="H2 Console"/>

---
[Siguiente paso](03-SpringData.md)
