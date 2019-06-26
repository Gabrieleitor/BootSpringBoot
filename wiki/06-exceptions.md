# 6- Exceptions

Actualizamos el entorno al **paso 6** de la aplicación.
```
$ git checkout -f step-6
```

En este paso vamos a ver como implementar el manejo global de exceptions y errores en nuestras APIs y cómo enviar una respuesta al cliente en Spring Boot.

## 6.1 Implementando un exception handler

La idea de un handler global que maneje los errores de nuestra API, es poder darle el mismo tratamiento a los errores. Para que nuestra respuesta sea consistente en toda la aplicación.

Para lograrlo, Spring Boot nos brinda la annotation `@RestControllerAdvice` que es una especie de aspecto que está "escuchando" cuando ocurren las exceptions que nosotros hayamos definido y en el caso que no hayan sido tratadas, las toma para poder dar una respuesta.

&nbsp;

Vamos a crear el package `com.training.exception` y dentro crearemos el archivo `GlobalExceptionHandler.java` que será nuestro `@RestControllerAdvice`.

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MyCustomException.class)
    public ResponseEntity<?> handleCustomException(final MyCustomException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
```
Lo primero que hacemos, es indicarle que nuestra class será un `@RestControllerAdvice`. Después crearemos un método que sea un `@ExceptionHandler` y le indicamos que tipo de `exception` estará manejando, en este caso, una creada por nosotros `MyCustomException`. Cada vez que sea atrapada, responderá al cliente con un status code 400 y un mensaje.

```java
public class MyCustomException extends RuntimeException {   
    public MyCustomException(String msg) {
        super(msg);
    }
}
```
Con esto ya es suficiente para que en nuestro código hagamos `throw new MyCustomException("Ups! an error occurred");`.

&nbsp;

## 6.2 Mejorando el handler

Ahora que entendimos como es que funciona nuestro `GlobalExceptionHandler` vamos a mejorarlo para que sea más funcional y nos brinde mayores facilidades.

Lo primero que tenemos que pensar, es que queremos devolverle al cliente cuando ocurre un error. Para esto vamos a crear un objeto de respuesta para los errores. Vamos a crear `ErrorResponse.java` en el package que creamos anteriormente.

```java
@Getter
@Builder
public class ErrorResponse {

    private Instant timestamp;

    private Integer status;

    private String error;

    private String message;

    private String path;
    
}
```
En este caso agregue algunos campos que pueden ser interesantes a la hora de devolver el error. Cada uno puede crear su objeto de respuesta de error y agregar los atributos que quieran.

Ahora vamos a ajustar nuestro código para que devuelva el modelo que acabamos de crear.

```java
    @ExceptionHandler(MyCustomException.class)
    public ResponseEntity<ErrorResponse> handleApiException(final MyCustomException ex,
                                                            final WebRequest request ) {
        final ErrorResponse errorDetails = ErrorResponse.builder()
            .timestamp(Instant.now())
            .message(ex.getMessage())
            .path(request.getDescription(false))
            .status(HttpStatus.BAD_REQUEST.value())
            .error(HttpStatus.BAD_REQUEST.name())
            .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
```
Agregamos un segundo parámetro al método que nos dará la inforamción del request que esta fallando y después creamos nuestro objeto `ErrorResponse` y lo enviamos al cliente como un status code `400`.

&nbsp;

## 6.3 HttpClientErrorException y HttpServerErrorException

Estas dos exceptions que nos provee Spring, nos sirven para devolver errores del cliente o del servidor sin necesidad de estar generando nuestras propias exceptions.

En el proyecto que construimos, ya hay algunas exceptions de este tipo, como por ejemplo `findById` del servicio `ActorService`.

```java
    public ActorDTO findById(Long id) {
        Assert.notNull(id, "The actor id cannot be null");

        Actor actor = actorRepository.findById(id)
            .orElseThrow( () -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Actor not found"));

        return conversionService.convert(actor, ActorDTO.class);
    }
```

Ahora solo basta armar el handler en nuestro `GlobalExceptionHandler` para poder manejar que le devolvemos a nuestros clientes ante el error.

```java
    @ExceptionHandler(HttpStatusCodeException.class)
    public ResponseEntity<ErrorResponse> handleApiException(final HttpStatusCodeException ex,
                                                            final WebRequest request ) {
        log.error(ex.getMessage());
        final ErrorResponse errorDetails = ErrorResponse.builder()
            .timestamp(Instant.now())
            .message(ex.getStatusText())
            .path(request.getDescription(false ))
            .status(ex.getStatusCode().value())
            .error(ex.getStatusCode().name())
            .build();
        return new ResponseEntity<>(errorDetails, ex.getStatusCode());
    }
```
Agregando este `ExceptionHandler` apuntando a la clase padre de las exceptions `HttpStatusCodeException`, de esta forma podremos manejar tanto las que sean tipo `client` y tipo `server`.

Con esto habremos manejado de forma eficiente los errores que se devuelven en nuestra API. Esto no significa que manejemos a todos los errores de esta manera, hay que aclarar que los errores que si requieren un tratamiento especial por parte de la apalicación, deben ser tratados dentro de nuestro código.

> **Importante:** Cuando implementamos un `ExceptionHandler` le estamos diciendo a Java que no use la cadena generica para las exceptions sino que nosotros nos haremos cargo de su tratamiento. Esto quiere decir, por ejemplo, que si nosotros no logeamos el error, Java no lo hará por nosotros.


Actualizamos el entorno al **paso 6-final** de la aplicación para ver la aplicación completa de este training.
```
$ git checkout -f step-6-final
```