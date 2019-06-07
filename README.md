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


## Temario

1. [Primeros pasos](wiki/01-PrimerosPasos.md)
    - Introducción a Spring Boot
    - Estructura y configuración general
2. [Nuestro primer CRUD](wiki/02-Crud.md)
    - Creación de un Controller
    - Creación de un Servicio
    - Creación del modelo de datos
    - Creación de un repositorio
    - Configurando JPA con H2
3. [Spring Data - JPA](wiki/03-SpringData) 
    - Mapeo de entidades
        - Mapeo de un objeto
        - Mapeo de relaciones
        - Converter atribute
    - Liquibase
    - Configurando MySQL (Spin-off)
4. [Trabajando con DTOs](wiki/04-DTOs)
    - Para que sirve
    - BeanUtils
    - Converters
    - ModelMapper
5. [Paginacion](wiki/05-paginacion)
6. Exceptions
7. Cache
    - Configuracion con Ehcache
    - Manejo de distintos caches
    - Migrando a Redis (Spin-off)

