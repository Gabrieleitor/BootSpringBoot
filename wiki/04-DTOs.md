# 4- DTOs

Actualizamos el entorno al **paso 4** de la aplicación.
```
$ git checkout -f step-4
```

## 4.1 Para qué sirve

Se utiliza para transferir varios atributos entre el cliente y el servidor o viceversa. Esto permite desacoplar la capa pública, del servicio con el modelo interno utilizado para guardar en la base de datos o backend.

&nbsp;

## 4.2 Implementando DTOs con BeanUtils

Continuando con la aplicación de películas, ahora vamos a estar agregando los DTOs para poder comunicarnos.

Vamos a crear el package `com.training.controller.dto` y dentro crearemos dos clases java `ActorDTO.java` y `MovieDTO.java`.

DTO: `ActorDTO.java`:
```java
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActorDTO {

    private Long id;

    private String name;

    private String lastName;

    private List<MovieDTO> movies;
}
```

DTO: `MovieDTO.java`
```java
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

    private Long id;

    private String name;

    private String genre;

    private List<ActorDTO> actors;

}
```

&nbsp;

### 4.2.1- Modificando los controllers.

Una vez creados nuestros objetos, tenemos que modificar los `controllers` para que tomen los `DTOs` que creamos.

Tomamos el `ActorController.java` y modificamos todos los métodos para que en la entrada/salida devuelva `ActorDTO` y no `Actor`.

Método `view` de un actor.
```java
    @GetMapping(value = "/{id}")
    public ActorDTO getActor(@PathVariable Long id) {
        return actorService.findById(id);
    }

```

Método `create` del actor.
```java
    @PostMapping
    public ActorDTO saveActor(@RequestBody ActorDTO actorDTO) {
        return actorService.save(actorDTO);
    }
```

Debemos modificar todo el controller hasta que no quede referencia al modelo `Actor`.

> **Nota:** Debemos implementar los mismos cambios en `MovieController.java` con `MovieDTO` y terminar los métodos faltantes en `ActorController.java`.

&nbsp;

### 4.2.2- Modificando los service.

Continuando con la modificación, para que el controller se "entienda" con el service, debemos hacer varias modificaciones en los servicios de nuestra aplicación.

Siguiendo con `ActorService.java` tenemos que reemplazar los métodos para que comiencen a utilizar `ActorDTO` y no `Actor`.

#### Método findById
```java
    public ActorDTO findById(Long id) {
        Assert.notNull(id, "The actor id cannot be null");

        Actor actor = actorRepository.findById(id)
            .orElseThrow( () -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Actor not found"));

        ActorDTO actorDTO = new ActorDTO();
        BeanUtils.copyProperties(actor, actorDTO);

        return actorDTO;
    }
```
Como primer paso modificamos el método para que devuelva un objeto tipo `ActorDTO`. Siguiendo con la modificación, obtenemos de `actorRepository` el modelo `Actor` y lo guardamos en una variable local. 

Para copiar los valores de del objeto `Actor` al objeto `ActorDTO` utilizamos la clase `BeanUtils` que provee Spring y llamamos al método `copyProperties` pasándole como primer parámetro la fuente de datos y como segundo el destino.

Una vez hecho esto, nuestro DTO estará listo para ser devuelto al controller.


#### Método save
```java
    public ActorDTO save(ActorDTO actorDTO) {
        Assert.notNull(actorDTO, "The actor cannot be null");
        Actor actor = new Actor();

        BeanUtils.copyProperties(actorDTO, actor);
        PersonName personName = new PersonName(actorDTO.getName(), actorDTO.getLastName());
        actor.setName(personName);

        actor = actorRepository.save(actor);

        ActorDTO rtaDTO = new ActorDTO();
        BeanUtils.copyProperties(actor, rtaDTO);

        if (Objects.nonNull(actor.getName())) {
            rtaDTO.setName(actor.getName().getName());
            rtaDTO.setLastName(actor.getName().getLastName());
        }

        return rtaDTO;
    }
```
Al igual que el anterior, modificamos la firma del método para que obtenga y devuelva un `ActorDTO`. Ahora como queremos crear o modificar un actor de la base de datos, creamos un objeto `Actor` y copiamos los valores del DTO utilizando `BeanUtils.copyProperties`. 

Como podemos ver, el modelo es ligeramente diferente de DTO y tenemos que copiar los valores del nombre y apellido a mano.
Una vez guardado el `actor`, hacemos el camino inverso para devolver el DTO con los valores persistidos al controller.

La utilización del `BeanUtils.copyProperties` nos evita estar copiando uno a uno los valores de un objeto a otro, pero no nos abtrae por completo de la conversión de un objeto a otro. Debido a esto, tenemos que estar agregando flujos de control o copiando algunos valores de forma programática.

> **Importante:** El `BeanUtils.copyProperties` copia los atributos de un objeto a otro siempre que tengan el mismo nombre y tipo. Cuando difieren alguno de los dos, estos no son copiados al objeto destino.

Otro punto a ver, es que estamos agregando demasiado código de conversión en la capa de servicio. Para evitar esto, podemos utilizar la capa de converters que provee Spring y desacoplar la conversión de un objeto de la capa de lógica.

&nbsp;

> **Nota:** Para finalizar los cambios, debemos implementar los mismos cambios en `MovieService.java` con `MovieDTO` y terminar los métodos faltantes en `ActorService.java`. Revalidar los tests y adaptarlos para que no tengan errores.

&nbsp;

## 4.3 Usando Converters

Actualizamos el entorno al **paso 4.2** de la aplicación.
```
$ git checkout -f step-4.2
```

Estos están pensados para encapsular toda la lógica que se necesita para convertir un objeto en otro, sin importar de que forma se realize. De esta forma evitaremos agregar código a nuestra capa de servicio, quedando mucho mas simple, prolijo y mantenible.

### 4.3.1 Creando los Converters

Para comenzar creamos el package `com.training.service.converter` en donde agregaremos todos los `Converters`. Para el `ActorService.java` necesitaremos dos, uno de `ActorDTO` a `Actor` y otro que haga exactamente lo contrario.

Dentro del package nuevo, creamos dos clases: 


#### Clase `ActorDTOToActorConverter.java`

Permite convertir el DTO a un modelo.

```java
@Component
public class ActorDTOToActorConverter implements Converter<ActorDTO, Actor> {

    @Override
    public Actor convert(ActorDTO actorDTO) {
        Actor actor = new Actor();
        BeanUtils.copyProperties(actorDTO, actor);

        PersonName personName = new PersonName(actorDTO.getName(), actorDTO.getLastName());
        actor.setName(personName);

        return actor;
    }
}
```

Para trabajar con la conversión, debemos implementar la interface `Converter<ActorDTO, Actor>` (Dependencia de Spring) pasandole al primer generic el valor de entrada, en este caso `ActorDTO` y como segundo la resultante del converter `Actor`.

Implementamos el método que exige la interface `convert` e implementamos la lógica necesaria para transformar el DTO en un modelo de base de datos. 

Como queremos utilizarlo dentro de nuestro service, le agregamos la annotation `@Component` a la clase.


#### Clase `ActorToActorDTOConverter.java`

Permite convertir un modelo a un DTO

```java
@Component
public class ActorToActorDTOConverter implements Converter<Actor, ActorDTO> {

    @Override
    public ActorDTO convert(Actor actor) {
        ActorDTO actorDTO = new ActorDTO();
        BeanUtils.copyProperties(actor, actorDTO);

        if (Objects.nonNull(actor.getName())) {
            actorDTO.setName(actor.getName().getName());
            actorDTO.setLastName(actor.getName().getLastName());
        }

        return actorDTO;
    }
}
```

Al igual que el anterior, implementamos la interface `Converter<Actor, ActorDTO>` pero esta vez como valor de entrada `Actor` y como valor de salida `ActorDTO`. Implementamos el método `convert` y agregamos la lógica necesaria de conversión.

Como queremos utilizarlo dentro de nuestro service, le agregamos la annotation `@Component` a la clase.

> **Nota:** Como en el resto de los casos, hace falta crear los converters para `Movie`.


En estos momentos los converters son completamente funcionales y basta con inyectarlos en el servicio deseado para comenzar a utilizarlos.
```java
    @Autowired
    private ActorDTOToActorConverter actorDTOToActorConverter;
```

&nbsp;

### 4.3.2 ConversionService 

Spring posee el servicio `ConversionService` que permite centralizar todos los converters y formatters que haya en la aplicación. Con esto logramos encapsular cualquier conversión en un único servicio y no necesitamos estar inyectando múltiples veces el mismo converter en distintos servicios.

Para agregar nuestros converters, crearemos el package `com.training.config` y creamos la clase `ConverterConfig.java`.

```java
@Configuration
public class ConverterConfig implements WebMvcConfigurer {

    @Autowired
    private ActorDTOToActorConverter actorDTOToActorConverter;

    @Autowired
    private ActorToActorDTOConverter actorToActorDTOConverter;

    @Autowired
    private MovieDTOToMovieConverter movieDTOToMovieConverter;

    @Autowired
    private MovieToMovieDTOConverter movieToMovieDTOConverter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(actorDTOToActorConverter);
        registry.addConverter(actorToActorDTOConverter);
        registry.addConverter(movieDTOToMovieConverter);
        registry.addConverter(movieToMovieDTOConverter);
    }
}
```
La annotation `@Configuration` le indica a Spring que es una clase donde se configuran Beans al momento del bootstrap.
La implementación de la interface `WebMvcConfigurer` nos indica que estaremos configurando parte del framework MVC de Spring.

Inyectamos todos los converters que disponemos y en el método `addFormatters` los agregamos al registro de converters disponibles en la aplicación.


Ahora vamos al `ActorService` y agregamos el servicio `ConversionService`
```java
    @Autowired
    private ConversionService conversionService;
```

Ahora modificamos los métodos para que utilicen nuestro conversor.

&nbsp;

#### Método findById
```java
    public ActorDTO findById(Long id) {
        Assert.notNull(id, "The actor id cannot be null");

        Actor actor = actorRepository.findById(id)
            .orElseThrow( () -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Actor not found"));

        return conversionService.convert(actor, ActorDTO.class);
    }
```
Vemos que se elimina todo el código extra para la conversión y sólo se invoca al service `conversionService.convert` indicando el origen de datos y la class a la que se quiere convertir. 

&nbsp;

#### Método save
```java
    public ActorDTO save(ActorDTO actorDTO) {
        Assert.notNull(actorDTO, "The actor cannot be null");

        Actor actor = conversionService.convert(actorDTO, Actor.class);
        actor = actorRepository.save(actor);

        return conversionService.convert(actor, ActorDTO.class);
    }
```
Si comparamos cómo quedó finalmente el método `save` con el que contábamos anteriormente, vemos que se eliminó todo rastro de lógica de conversión de objetos, gracias al servicio de `conversionService`.

&nbsp;

## 4.4 ModelMapper

La bibioteca [ModelMapper](http://modelmapper.org/) permite un mapeo mucho más eficiente y dinámico que `BeanUtils` visto en este curso. 

La combinación del [ModelMapper](http://modelmapper.org/) en conjunto con el servicio `conversionService` hacen una herramienta muy poderosa de conversión y mapeo de objetos.


---
[Siguiente paso](05-paginate.md)
