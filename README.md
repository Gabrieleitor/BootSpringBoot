# Spring Boot 2 - Training

## Objetivo

Sentar una base común de las tecnologías mas usadas de Spring. No pretende ser una guía exhaustiva de todos los temas, pero si el punto de partida. 

Está dirigido a todos a aquellos programadores que quieran aprender o ampliar su conocimiento de Spring Boot.


## Requisitos

Para poder aprovechar al máximo el training, será necesario contar con:

* [Git](https://git-scm.com/downloads)
* Java 8+ ([OpenJDK / OracleJDK](https://sdkman.io/))
* [Maven](https://maven.apache.org/)
* IDE (Ej: [IntelliJ](https://www.jetbrains.com/idea/download/), [Eclipse](https://www.eclipse.org/downloads/packages/))
* [Lombok](https://projectlombok.org/) (Activalo en tu IDE [acá](https://projectlombok.org/setup/overview))
* [Editorconfig](https://editorconfig.org/) (Si usas Eclipse debes bajar un plugin, si usas IntelliJ no es necesario)


## Temas

1. [Primeros pasos](wiki/01-PrimerosPasos.md)
    - Introducción a Spring Boot
    - Estructura y configuración general
2. [Nuestro primer CRUD](wiki/02-Crud.md)
    - Creación de un Controller
    - Creación de un Servicio
    - Creación del modelo de datos
    - Creación de un repositorio
    - Configurando JPA con H2
	- Probando la aplicación con Postman
3. [Spring Data - JPA](wiki/03-SpringData.md) 
    - Mapeo de entidades
        - Mapeo de un objeto
        - Mapeo de relaciones
        - Converter atribute
    - Configurando MySQL (Spin-off)
4. [Trabajando con DTOs](wiki/04-DTOs.md)
    - Para que sirve
    - BeanUtils
    - Converters
    - ModelMapper
5. [Paginacion](wiki/05-paginate.md)
    - Implementando paginación
    - Filtros avanzados con specification
6. [Exceptions](wiki/06-exceptions.md)
    - Implementando un exception handler
    - Mejorando el handler
    - HttpClientErrorException y HttpServerErrorException
