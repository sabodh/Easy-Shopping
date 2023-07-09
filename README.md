# Easy Shopping - Android Application - Kotlin
Easy Shopping is an Android application for listing the product details from the server.
## Architecture
This application follows basic SOLID Principles and clean code approach and also uses MVVM pattern.
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


