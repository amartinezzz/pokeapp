🚀 PokeApp Pro: Clean Architecture & Compose
PokeApp Pro es una aplicación nativa de Android diseñada como referencia para implementaciones de arquitecturas modernas. Es un ecosistema que demuestra el uso de Clean Architecture, flujos de datos asíncronos con Coroutines/Flow y una UI declarativa con Material 3.

🛠️ Stack Tecnológico
UI: Jetpack Compose (100% Declarativo).

* **Lenguaje:** Kotlin.

* **Arquitectura:** Clean Architecture + MVVM.

* **Inyección de Dependencias:** Hilt (Dagger).

* **Red:** Retrofit + OkHttp.

* **Persistencia:** Room Database.

🏗️ Arquitectura del Proyecto
El proyecto está dividido en tres capas principales para garantizar el desacoplamiento y la testeabilidad:

📱 Capa de Presentación
Organizada de forma modular para escalar sin dolor:

* **Screens:** Contenedores de alto nivel para los destinos de navegación.

*** ViewModels:** Gestión de estado y supervivencia a cambios de configuración.

* **State:** Definición de estados únicos de UI (Single Source of Truth).

* **Navigation:** Grafo de navegación centralizado con Compose Navigation.

* **Components:** Librería de widgets reutilizables y atómicos.

🧠 Capa de Dominio
El corazón de la lógica de negocio, libre de dependencias de Android:

**UseCases:** Ejecución de reglas de negocio específicas (ej. SearchPokemonUseCase).

**Models:** Entidades de dominio puras.

💾 Capa de Datos
Gestión de datos y sincronización:

* **Repository Pattern:** Decisor inteligente entre API (Retrofit) y DB Local (Room).

🎨 Características Destacadas
* UX Fluida: Reseteo automático de scroll al cambiar filtros de búsqueda.

* Adaptive Icons: Icono de lanzamiento diseñado con capas (Foreground/Background) para adaptarse a cualquier launcher.

* Dynamic UI: Paleta de colores que reacciona al tipo de Pokémon seleccionado.

* Auth Flow: Sistema completo de Login y Registro con validación en tiempo real.

🚀 Instalación y Uso
Clona este repositorio:

Bash
git clone https://github.com/tu-usuario/pokeapp-pro.git
Abre el proyecto en Android Studio (Iguana o superior).

Sincroniza Gradle y ejecuta la app en un emulador o dispositivo físico.

📝 Autor
Alexander Martínez - Desarrollo y Arquitectura
