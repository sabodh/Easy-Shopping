# Easy Shopping - Android Application - Kotlin
Easy Shopping is an Android application for listing the product details from the server.
## Architecture
This application follows basic SOLID Principles, clean code approach and also uses MVVM pattern.
## Modularization
This application follows modularization strategy know as "by layer"
## Features
A list of technologies/ features used within the project:
* [MVVM]()
* [Shared ViewModel]()
* [Navigation Component]()
* [Navigation Safe Args]()
* [Recyclerview with ListAdapter]()
* [View Binding]()
* [Hilt]()
* [Retrofit]()
* [Robolectric]()
* [Mockito]()
* [JUnit4]()
* [Qualifiers for multiple layout design support(Large/Small Tab, Phone)]()
* [Fake Store API service](https://fakestoreapi.com/)

## Clean architecture using mvvm
- app
    - data
        - local
            - dao
            - entities
            - mappers
        - remote
            - api
            - models(model class used for network operations)
        - repositories(implementations)
    - di
        - modules
    - domain
        - models(model class used for business logic)
        - repositories
        - usecases
    - presentation
        - adapters
        - fragments
        - viewmodel
    - utils
    - App.kt

## Functionality
1. App will display a list of products to purchase.
2. Display the details in another fragment.
3. User can add elements into the cart.
4. Display an error message if the list cannot be loaded (e.g., no network).
5. Instrumentation and unit tests are implemented. (Hilt DI used for instrumentation testing)
6. ** Instrumentation tests - 43 nos. 
7. Multiple screen support implemented, both portrait and landscape



