# LOCALIZER


## Introduction

A convenience wrapper around the standard Java localization framework.


## Building And IDE Support

The Localizer project uses Gradle and follows standard Gradle conventions. I use Eclipse with the Buildship Gradle plugin. Importing the project using Buildship's "Import existing Gradle project" sets up the project with no additional work.


## Testing

All testing is automated and divided into two categories, "unit tests" and "acceptance tests". All unit tests and testing utilities are in the "src/test/" folder. All acceptance tests, that is automated tests that prove the project implements the user stories' acceptance criteria, are in the "src/acceptance_test/" folder. An easy rule of thumb to make a distinction between these two sets of automated tests is that the acceptance tests test the externally visible or "published" behaviour of the software system, whereas the unit tests focus on testing internal or "implementation" behaviour of the software system. I tend to favour acceptance tests over unit tests so if you run test coverage reports you'll likely find that testing coverage is not ideal.

If you are looking for specific client use cases, you will see them in the acceptance tests.

