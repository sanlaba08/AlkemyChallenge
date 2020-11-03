# Alkemy Challenge

Desarrollar una aplicación para administración de presupuesto personal. La misma debe permitir crear y editar ingresos y egresos de dinero, y mostrar un balance resultante de las operaciones registradas.

# | Desarrollo Back-End | : Introduccion.
A traves de Spring Framework realizaremos una API-Rest que será consumida por nuestro Front-End. Además, Spring a traves de JPA Hibernate se conectará con nuestra base de datos relacional (MySQL) con sus querys nativas que nos proporcionará mayor accesibilidad y simplicidad para poder realizar una conexión rapida y exitosa.
Para poder comprobar las peticiones creadas por nuestra RestController necesitaremos de la ayuda de Postman que a traves del request HTTP que le solicitemos nos devolverá un response.

# | Desarrollo Back-End | : Procedimientos para el funcionamiento del mismo.
Nuestra carpeta "src" contiene otros dos folders.
El primero es "schema" donde encontraremos un script de nuestra base de datos MySQL relacional.
La segunda carpeta contiene un json de nuestro Postman donde podremos realizar las peticiones HTTP para poder comprobar el funcionamiento del mismo.
Obviamente, necesitaremos tener Spring en ejecución con la apertura del puerto para que podamos realizar las consultas que querramos.
