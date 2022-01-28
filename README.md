# AirLine 
AirLines application to display all airlines Company
* can search for Company by (Id-Name - Country)
* can add New Company for Your List 
* can save all data Local in your device 

# Technology 
* Clean Architecture: 3 Layers (Presentation, Domain, Data) Layers.

* Presentation Layer:
   * mvvm Presentation Architecture Pattern.
   * view Model 
 * Domain Layer:
    * UseCases.
    * Repository Interfaces.
 * Data Layer:
   * Repository Implementation.
   * Models.
   * APIs: retrofit for network calling.
   * Database: Room Database .

* Koin for di (Dependency Injection).

* Kotlin Coroutines.

* Unit testing for business logic using Mockatio 
