# Hotel reservation example
***
## Requerimientos

- Java 17
- Maven 3.8.4
- Docker Desktop for WSL2 (Windows)

## Compilacion y ejecucion

*Nota: se debe asegurar que la instancia de Docker esta en ejecucion y no hay ningun contenedor activo asi como sus volumenes*

- mvn clean compile
- mvn install (Ejecucion de Tests)
- mvn spring-boot:run

---
### _importante:_
[Asegurarse que tiene minimamente 4Gb de memoria ram asignada al docker y 1.5gb de memoria disponible al momento del arranque de los contenedores.

El equipo en el que se desarrollo tiene 32gb de ram y 8 nucleos.

Si obtiene errores en los test de integracion al momento de ejecutarlos, asegurese de borrar los contenedores junto a sus volumenes y levantarlos a mano ya que el error se puede provocar por el tiempo que le toma a docker levantar el entorno.

Todos los Test pasan correctamente.]

----

_Curls de prueba validos_

/Search:
```` cURL
curl --location 'http://localhost:8080/search' \
--header 'Content-Type: application/json' \
--data '{
"hotelId": "1234",
"checkIn": "23/09/2023",
"checkOut": "26/09/2023",
"ages": [
10,
20
]
}'
````

/count:
```` cURL
curl --location 'http://localhost:8080/count?searchId=72d1ea96-443d-4881-8ff2-c78a7c46c16c'
````
_IMPORTANTE: el valor del parametro searchId debe ser el resultado sin comillas del search_

## Supuestos

- Se interpreta que segun el enunciado en el siguiente parrafo:
````
Llamada GET al path /count con un único parámetro llamado
searchId. Este parámetro contendrá el valor devuelto por la petición
POST /search anterior y nos devolverá el número de búsquedas
similares
````
que las _'busquedas similares'_ se refieren a que los valores del body son idénticos, pero el orden de los elementos del array 'ages' puede variar.

- Se interpreta que segun el enunciado en el siguiente parrafo:
````
La llamada a /search debe realizar las siguientes acciones:
- Validar el payload remitido
- El payload debe ser recibido en un objeto inmutable
- Debe enviarse ese payload al topic de Kafka
````

que se debe enviar el payload de entrada del controller sin modificacion hasta la entrada del producer de kafka. El cual acarrea un "code-smell" porque se estaria usando un DTO de entrada como objeto de dominio para enviarlo al producer sin modificacion. Lo cual no es correcto.


- Se asume que se pide un unico microservicio que sea el encargado de enviar y recibir los mensajes de Kafka.

- Se asume que se requiere que la llamada al endpoint /count tambien pase por kafka.

- No se testean los modelos de entidad ni los dtos porque se entiende que no es necesario y puede ser redundante.

- Se asume que la cantidad de peticiones por minuto que pueden llegar a recibir los enpoints son los suficientes como para no bloquear la base de datos, porque no esta especificado en el enunciado. En tal caso se hubiera optado por utilizar un circuitBreaker con reintentos y un Semaphoro de Java en el recurso para evitar la perdida de datos.

- Se asume que no es necesario una configuracion separada para la creacion y ejecucion del docker-compose que se utiliza en los tests de IT a la utilizada en la ejecucion local que los mismos contenedores y volumenes.

