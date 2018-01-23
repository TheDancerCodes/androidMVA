# Android Architecture MVA

This repo contains an Android App that demonstrates the Minimum-Viable-Architecture Pattern using the following frameworks:
1. RxJava 2
2. Retrolambda
3. RxBinding
4. Okhttp
5. Gson

## MVA (Minimum Viable Architecture)
It is MVP with additional things like:
* VIPER Entities.
* Router - This follows the Coordinator Pattern
* Interactor - The model layer
* Injection - Simplifies how our classes work and makes us follow S.O.L.I.D principles.

## Is MVA new?
* NO. We’ve just broken MVC, MVP, MVVM and VIPER to their different components and applying them as we need and see fit.

## Project Setup

This project is built with Gradle, the [Android Gradle plugin](http://tools.android.com/tech-docs/new-build-system/user-guide). Follow the steps below to setup the project localy.

* Clone [Android MVP](https://github.com/TheDancerCodes/androidMVA) inside your working folder.
* Start Android Studio
* Select "Open Project" and select the generated root Project folder
* You may be prompted with "Unlinked gradle project" -> Select "Import gradle project" and select 
the option to use the gradle wrapper
* You may also be prompted to change to the appropriate SDK folder for your local machine
* Once the project has compiled -> run the project!