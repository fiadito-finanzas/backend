
# Sistema de Eventos

Proyecto de práctica para la integración de diversas tecnologias

## Comenzando

Estas instrucciones te ayudarán a obtener una copia del proyecto en funcionamiento en tu máquina local para propósitos de desarrollo y pruebas.

### Prerrequisitos

- Java Development Kit (JDK) 11 o superior.
- Maven 3.6.3 o superior.
- Docker 19.03.8 o superior.

### Instalación y Ejecución

1. Clona el repositorio
2. Navega hasta el directorio raíz del proyecto en la terminal.
3. Ejecuta el siguiente comando para iniciar la base de datos dockerizada:

### Inicializando Docker
Para iniciar la base de datos dockerizada, ejecuta el siguiente comando:
```sh
docker-compose up
```
Con ello, se iniciará una instancia de Mariadb en el puerto 3306 de tu máquina local.
### Ejecutando en Maven
Luego, instala las dependencias y ejecuta la aplicación usando Maven:
```sh
mvn clean install spring-boot:run
```
Abre tu navegador web y accede a la siguiente URL para ver la aplicación:
```sh
http://localhost:8080
```
# integracion-backend
