# juegos-moviles
Aplicación que implementa MVVM con Jetpack Compose

## Backend
Las reglas de los juegos se consumen a través de un servicio desplegado en Heroku

* URL del repositorio de backend: https://github.com/Gera-Gal/juegos-back

* URL base: https://juegos-back-b39a6e4b40aa.herokuapp.com/
* Ruta para Espada, Escudo o Dragón: /eed/fight?userChoice=DRAGON
* Ruta para Lotería: /lottery?attempt=22&digitLength=2
* Ruta para Adivina el número: /guess?attempt=0&maxNum=1
