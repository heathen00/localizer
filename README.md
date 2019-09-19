# LOCALIZER


## Introduction

A convenience wrapper around the standard Java localization framework.


## Building And IDE Support

The Localizer project uses Gradle and follows standard Gradle conventions. I use Eclipse with the Buildship Gradle plugin. Importing the project using Buildship's "Import existing Gradle project" sets up the project with no additional work.


## Testing

All testing is automated and divided into two categories, "unit tests" and "acceptance tests". All unit tests and testing utilities are in the "src/test/" folder. All acceptance tests, that is automated tests that prove the project implements the user stories' acceptance criteria, are in the "src/acceptance_test/" folder. An easy rule of thumb to make a distinction between these two sets of automated tests is that the acceptance tests test the externally visible or "published" behaviour of the software system, whereas the unit tests focus on testing internal or "implementation" behaviour of the software system. I tend to favour acceptance tests over unit tests so if you run test coverage reports you'll likely find that testing coverage is not ideal.

If you are looking for specific client use cases, you will see them in the acceptance tests.



## ROUGH

work:
   * Move the README.md "Testing" discussion to the Wiki.
   * For LocalizerBundle, I am using an OrderedSet as the data structure.  I think that is wrong.
     I think what I want is for the LocalizerBundle to maintain the order in which they are added
     to the Localizer.  This would be different from OrderedSet which will order the
     LocalizerBundle instances according to their natural order.  Very different.
   * I want some means to validate the localization at test time so that I can compare all defined field instances
     against the configured localization and know EXACTLY where each field is defined AND be able to easily compare
     to ensure it is defined as expected.
   * I don't like Java properties files and never have.  You should implement the localization resource bundle data
     as a JSON file, or something, so that it has more structure and internal validation.  But how would it be
     extendible if someone wants to localize existing data to yet another locale?
   * Not sure I like using a checked exception.  Finish implementation and review.  The objective of the new Localizer
     implementation is to ensure there are no unneeded exceptions during runtime.  This also applies to casting errors.
     From a reuse perspective, it makes more sense to use the standard, conventional Java casting exception instead
     of rolling your own.  But, the API implementation must check itself, early, right when the object passed in by
     the client to follow the "fail-fast" convention.  Thus the consequences of the bad client implementation are
     determined earlier rather than later.
   * WARNING: There could be overlap between different fields that LOOK distinct based on how the
     types and fields are named.  That is, the LocalizerType and LocalizerField instances are all
     unique but the fully qualified names for the fields can be exactly the same.  Since it is the
     FQDN that are used in the localization properties files, this is an issue.  You should solve this
     problem, but get things working, first.
   * Right now the Localizer interface setLocale() throws a LocalizerException, although the CompositeLocalizer
     class should never throw this exception since it will always set SOME localization bundle.  You should
     refactor to remove the throws clause from the published interface and have another internal method
     defined in the internal interface for the Localizer implementations to implement.  The internal class
     implementations should just throw an unsupported exception for their implementations of the published
     API since it does not make sense for them to implement them.  You cannot invert the inheritance
     hierarchy between the published and internal interfaces since this would cause the internal methods
     to be visible in the published API.
   * I think it was a mistake to enforce a specific naming convention / structure within the localizer
     subsystem.  The naming convention should be defined by the system that uses it.  At most the localizer
     should have something like the LocalizerType with one named field and MAYBE the LocalizerInstance.
     Maybe the localizer instance does not even use its name when resolving the localized resource.  The
     role of the LocalizerInstance is just to mark where class data fields use a localized resource.  The
     actual localized resource is defined in the LocalizerType (whatever it should be called) instances.
   * Get rid of UID subsystem.
   * Maybe use that acceptance testing framework written originally by Robert C Martin ... if you ever remember its name.
     Just remembered: fitnesse.  See fitnesse.org.
   
 
