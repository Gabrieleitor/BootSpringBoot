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
Dentro de las carpetas `src.main.java` agregar un nuevo package `com.training.controller`. Crear la clase `MovieController` teniendo en cuenta la annotation `@RestController` explicada en el paso 1.
Para modelar las operaciones CRUD se tendrán en cuenta las buenas prácticas del diseño de una API REST, separando nuestra API en recursos lógicos y accediendo a ellos a través de peticiones HTTP.

C (Create): POST. Crea una nueva película.
R (Read): GET. Devuelve la información de una película.
U (Update): PUT. Actualiza la información de una película.
D (Delete): DELETE. Elimina una película existente.

## 2.2. Creación de un Servicio
Dentro de las carpetas `src.main.java` agregar un nuevo package `com.training.service`. Crear la clase `MovieService` teniendo en cuenta la annotation `@Service`.
El estereotipo `@Service` indica que la clase pertenece a la capa de negocio.

## 2.3. Creación de un modelo
Dentro de las carpetas `src.main.java` agregar un nuevo package `com.training.model` y agregar las clases necesarias para modelar una película y su información.

## 2.3. Mapeando un Repositorio
Dentro de las carpetas `src.main.java` agregar un nuevo package `com.training.repository`. 

## 2.5. Configurando JPA con H2

## Referencias
