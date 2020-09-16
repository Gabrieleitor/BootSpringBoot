# 5- Paginate

Actualizamos el entorno al **paso 5** de la aplicación.
```
$ git checkout -f step-5
```

##### Requerimientos:
- Agregar paginación al servicio.


## 5.1 Implementando paginación

En este paso veremos cómo implementar la paginación a los listados de nuestra aplicación de películas.

Primero modificamos el listado del `ActorController` para que devuelva el listado paginado.

```java
    @GetMapping
    public Page<ActorDTO> getActors( final Pageable pageable ) {
        return actorService.findAll( pageable );
    }
```

La primer diferencia es el tipo de dato que retorna, ya no devuelve un tipo `List` sino que lo cambiamos por un tipo `Page`. Lo segundo es agregar como parámetro un objeto `Pageable` que contiene la información necesaria de la página que se está consultando, el tamaño y el ordenamiento. Por último, pasamos el parámetro al servicio.

&nbsp;

Seguimos con la modificación del `ActorService`.

```java
    public Page<ActorDTO> findAll(final Pageable pageable ) {
        return actorRepository.findAll(pageable)
            .map( actor -> conversionService.convert(actor, ActorDTO.class));
    }
```

En el service también cambiamos el tipo de objeto de respuesta a `Page`, agregamos el parámetro `Pageable`. Siguiendo con las modificaciones, le pasamos el parámetro a nuestro `actorRepository` que al ser del tipo `JpaRepository` ya implementa paginación sin que hagamos mayores modificaciones.

Con estas modificaciones ya tenemos funcionando nuestra paginación para el `CRUD` de actor.

> **Nota:** Debemos implementar los mismos cambios en `MovieController.java` y `MovieService.java`. Modificar los tests para que tome la paginación.

&nbsp;

## 5.2 Filtros avanzados con specification

En muchos casos es necesario permitir buscar dentro de nuestro listado paginado, para eso, Spring Data nos da `Specifications` que permite realizar búsquedas avanzadas y se integra de forma automática con nuestra paginación.

Para mas información: [ver mas](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#specifications)

&nbsp;

---
[Siguiente paso](06-exceptions.md)
