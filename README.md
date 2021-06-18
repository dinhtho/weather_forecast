# Weather Forecast Application

- A simple application to check weather forecast information using [openweathermap](https://openweathermap.org/) api
- App Features:
    - Get weather forecast information in next 6 days and current date using search by name.
    - Forecast information will be saved until end of date and will be updated in begining of date.


## Sections
* [Architecture and Flow](#architecture-and-flow).
* [Project Folder Structure](#project-folder-structure).
* [Plugins and Libraries](#plugins-and-libraries).
* [Check list](#check-list).
* [Run](#run).
* [Launch](#launch).
* [Setup](#setup).
* [Something needs to improve in the future](#something-needs-to-improve-in-the-future).


## Architecture and Flow:
![alt text](https://miro.medium.com/max/1050/1*a-AUcEVdyRJhIepo9JyJBw.png)


Base on the application flow image, we follow Clean Architecture by using 3 layers:
#### 1. Presentation layer:
The presentation layer provides the UI implementation of the application. It is the dumb layer which only performs instruction with no logic in it. This layer internally implements architecture like MVC, MVP, MVVM, MVI etc. This is the layer where everything connects.
#### 2. Domain layer:
This will be the most generic layer of the three. It will connect the presentation layer with the data layer. This is the layer where app-related business logic will be executed.
#### 3. Data layer:
This layer is responsible for providing the data required by the application. Data layer should be designed such data it can be re-used by any application without modification in their presentation logic.


## Project Folder Structure:
The app uses 3 modules for implementation Clean Architecture
### 1. Presentation:
- Main module includes 2 remaining modules: Domain and Data and connects them.
- Contains UI components, UI testings and its extension (E.g MainActivity,ListAdapter,ViewModel,ForecastLoadingState,.. ).

### 2. Domain:
- Middle module connects Presentation and Data.
- Contains usecases, repository interfaces defining for data source layer and UI models for presentation layer(E.g GetWeatherInfoUseCase, RemoveWeatherInfoLocalUseCase, IForecastRepository, WeatherInfoDisplay,..).

### 3. Data:
- Data source module provides data to Domain module.
- Implement Domain's repository interfaces (E.g ForecastRepository).
- Constains database for caching data, shared preferences and network service for APIs(E.g WeatherInfoDatabase, SecuredLocalStore,WeatherForecastService,...).
- Constains data model using in raw data(local and remote source), tranformation data for Domain module(E.g WeatherInfoRawResponse,LocalWeatherForecastInfo, LocalWeatherInfoMapper)


## Plugins and Libraries:
- For build project
    - Kotlin -v 1.4.32
    - Retrofit -v 2.9.0
    - Coroutines -v 1.4.3
    - Hilt -v 2.35
    - Room -v 2.3.0
    - Sercured Shared Preferences -v 1.1.0-alpha03
    - Scale dp& sp -v 1.0.6
    - Firebase Crashlytics -v 18.0.1

- For testing
    - Unit test
        - Coroutines Test -v 1.4.3
        - Mockito -v 3.9.0
        - Junit -v 4.13.2
        - PowerMock -v 2.0.2
    - UI test
        - Espresso -v 3.3.0


## Check list:
1. Programming language: Kotlin is required, Java is optional. ✅
2. Design app's architecture (suggest MVVM) ✅
3. Apply LiveData mechanism ✅
4. UI should be looks like in attachment. ✅
5. Write UnitTests ✅
6. Acceptance Tests
7. Exception handling ✅
8. Caching handling ✅
9. Secure Android app from:
    a. Decompile APK ✅.
    b. Rooted device ✅.
    c. Data transmission via network ✅.
    d. Encryption for sensitive information ✅.
10. Accessibility for Disability Supports:
    a. Talkback: Use a screen reader ✅.
    b. Scaling Text: Display size and font size: To change the size of items on your screen, adjust the display size or font size ✅.
11. Entity relationship diagram for the database and solution diagrams for the components, infrastructure design if any ✅.
12. Readme file includes: ✅
    a. Brief explanation for the software development principles, patterns & practices being applied
    b. Brief explanation for the code folder structure and the key Java/Kotlin libraries and frameworks being used
    c. All the required steps in order to get the application run on local computer
    d. Checklist of items the candidate has done.

## Run:
* Android studio 4.1.1 or newer
* Gradle v6.5
* Min SDK support: 21

## Setup:
Install app to connected device:
- Windows
    - `gradlew installDebug`
- Mac OS
     - `./gradlew installDebug`

## Something needs to improve in the future:
- Using proguard to obfuscate code for preventing decompile APK and read code. But it is not a perfect one.
- Need to write more unit tests to strictly check logic.
