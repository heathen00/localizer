package com.ht.localizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import com.ht.localizer.LocalizerBundleInternal;
import com.ht.localizer.LocalizerException;
import com.ht.localizer.LocalizerFactoryInternal;
import com.ht.localizer.LocalizerInstance;
import com.ht.localizer.LocalizerInternal;
import com.ht.localizer.LocalizerSystemInternal;
import com.ht.wrap.ResourceBundleWrapperConfigurator;
import com.ht.wrap.StubWrapperFactory;
import java.util.Locale;
import org.junit.Before;
import org.junit.Test;

public class LocalizerBundleUnitTest {
  private LocalizerSystemInternal localizerSystemInternal;
  private LocalizerFactoryInternal localizerFactoryInternal;
  private StubLocalizerFactory stubLocalizerFactory;
  private StubWrapperFactory stubWrapperFactory;
  private Assert localizerAssert;
  private ResourceBundleWrapperConfigurator resourceBundleWrapperForLocaleConfigurator;
  private ResourceBundleWrapperConfigurator resourceBundleWrapperForRootLocaleConfigurator;

  @Before
  public void setup() {
    localizerSystemInternal = LocalizerSystemInternal.getSystemInternal();
    localizerFactoryInternal = localizerSystemInternal.getLocalizerFactoryInternal();
    localizerFactoryInternal.resetAll();
    stubLocalizerFactory = StubLocalizerFactory.createStubLocalizerFactory(localizerSystemInternal);
    stubWrapperFactory = StubWrapperFactory.createStubWrapperFactory();
    localizerFactoryInternal.setWrapperFactory(stubWrapperFactory);
    resourceBundleWrapperForLocaleConfigurator =
        stubWrapperFactory.getResourceBundleWrapperForLocaleConfigurator();
    resourceBundleWrapperForRootLocaleConfigurator =
        stubWrapperFactory.getResourceBundleWrapperForRootLocaleConfigurator();
    localizerAssert = Assert.createAssert();
  }

  @Test
  public void Factory_createTestingAssetts_testingAssetsAreCreated() {
    assertNotNull(localizerSystemInternal);
    assertNotNull(localizerFactoryInternal);
    assertNotNull(stubLocalizerFactory);
    assertNotNull(stubWrapperFactory);
    assertNotNull(resourceBundleWrapperForLocaleConfigurator);
    assertNotNull(resourceBundleWrapperForRootLocaleConfigurator);
    assertNotNull(localizerAssert);
  }

  @Test
  public void LocalizerBundle_createUndefinedLocalizerBundle_undefinedLocalizerBundleCreated()
      throws Exception {
    final String expectedBundleName = "__UNDEFINED__";
    final Locale expectedTargetLocale =
        new Locale.Builder().setLanguage("xx").setRegion("ZZ").build();
    final Locale expectedResolvedLocale = expectedTargetLocale;
    final String expectedFormattedString = "UNDEFINED";
    final String expectedUnformattedString = "UNDEFINED";
    final boolean expectedIsDefined = false;
    LocalizerInstance localizerInstance =
        stubLocalizerFactory.createStubLocalizerInstance("testMethod00", "testInstance00");

    LocalizerBundleInternal undefinedLocalizerBundle =
        localizerFactoryInternal.createUndefinedLocalizerBundle();

    localizerAssert.assertExpectedLocalizerBundle(expectedTargetLocale, expectedResolvedLocale,
        expectedBundleName, expectedIsDefined, undefinedLocalizerBundle);
    assertEquals(expectedUnformattedString,
        undefinedLocalizerBundle.getUnformattedString(localizerInstance));
    assertEquals(expectedFormattedString, undefinedLocalizerBundle
        .getFormattedString(localizerInstance, Integer.valueOf(22), "test"));
  }

  @Test
  public void LocalizerBundle_getStringsForMultipleInstanceInstancesFromUndefinedLocalizerBundle_returnedStringsAlwaysUndefined()
      throws Exception {
    LocalizerInstance localizerInstanceOne =
        stubLocalizerFactory.createStubLocalizerInstance("testMethod01", "testInstance01");
    LocalizerInstance localizerInstanceTwo =
        stubLocalizerFactory.createStubLocalizerInstance("testMethod02", "testInstance02");
    String expectedString = "UNDEFINED";

    LocalizerBundleInternal undefinedLocalizerBundle =
        localizerFactoryInternal.createUndefinedLocalizerBundle();

    assertNotNull(undefinedLocalizerBundle);
    assertEquals(expectedString, undefinedLocalizerBundle.getFormattedString(localizerInstanceOne));
    assertEquals(expectedString,
        undefinedLocalizerBundle.getUnformattedString(localizerInstanceOne));
    assertEquals(expectedString, undefinedLocalizerBundle.getFormattedString(localizerInstanceTwo,
        "blah", Double.valueOf(1.1d)));
    assertEquals(expectedString,
        undefinedLocalizerBundle.getUnformattedString(localizerInstanceTwo));
  }

  @Test
  public void LocalizerBundle_getExistingStringsFromRootLocalizerBundle_stringsAreRetreived()
      throws Exception {
    final Locale expectedTargetLocale = Locale.CANADA_FRENCH;
    final Locale expectedResolvedLocale = Locale.ROOT;
    final LocalizerInternal localizerInternal =
        localizerFactoryInternal.createLocalizerInternal("localizer.name", expectedTargetLocale);
    final String expectedResourceBundleName = "com.resource.bundle.name.DoesNotMatter";
    final boolean expectedIsDefined = true;
    final String expectedUnformattedString =
        "this is a test unformatted string for the root locale";
    final String expectedUnformattedFormattedString =
        "this is a test formatted string for the root locale: %s, %d";
    final String expectedFormattedString =
        "this is a test formatted string for the root locale: test_parameter, 33";
    final LocalizerInstance unformattedInstance =
        stubLocalizerFactory.createStubLocalizerInstance("unformatted", "one");
    final LocalizerInstance formattedInstance =
        stubLocalizerFactory.createStubLocalizerInstance("formatted", "one");
    resourceBundleWrapperForRootLocaleConfigurator.resetAll().doesResourceBundleExist(true)
        .doesLocalizedStringExist(true)
        .addLocalizedString(unformattedInstance.getFullyQualifiedName(), expectedUnformattedString)
        .addLocalizedString(formattedInstance.getFullyQualifiedName(),
            expectedUnformattedFormattedString);

    LocalizerBundleInternal rootLocalizerBundle = localizerFactoryInternal
        .createRootLocaleLocalizerBundle(localizerInternal, expectedResourceBundleName);

    localizerAssert.assertExpectedLocalizerBundle(expectedTargetLocale, expectedResolvedLocale,
        expectedResourceBundleName, expectedIsDefined, localizerInternal, rootLocalizerBundle);;
    assertEquals(expectedUnformattedString,
        rootLocalizerBundle.getUnformattedString(unformattedInstance));
    assertEquals(expectedFormattedString, rootLocalizerBundle.getFormattedString(formattedInstance,
        "test_parameter", Integer.valueOf(33)));
    assertEquals(expectedUnformattedFormattedString,
        rootLocalizerBundle.getUnformattedString(formattedInstance));
    assertEquals(expectedUnformattedString, rootLocalizerBundle
        .getFormattedString(unformattedInstance, "another_parameter", Double.valueOf(33.4d)));
  }

  @Test(expected = LocalizerException.class)
  public void LocalizerBundle_getNonExistentUnformattedStringFromRootLocalizerBundle_localizerExceptionIsThrown()
      throws Exception {
    resourceBundleWrapperForRootLocaleConfigurator.resetAll().doesResourceBundleExist(true)
        .doesLocalizedStringExist(false);
    final LocalizerInternal localizerInternal =
        localizerFactoryInternal.createLocalizerInternal("localizer.name", Locale.CANADA_FRENCH);
    final String resourceBundleName = "com.resource.bundle.name.DoesNotMatter";
    final LocalizerInstance nonExistentUnformattedInstance =
        stubLocalizerFactory.createStubLocalizerInstance("unformatted.does.not.exist", "one");
    LocalizerBundleInternal rootLocalizerBundle = null;

    try {
      rootLocalizerBundle = localizerFactoryInternal
          .createRootLocaleLocalizerBundle(localizerInternal, resourceBundleName);
    } catch (LocalizerException le) {
      fail("exception occurred at wrong place: " + le);
    }

    rootLocalizerBundle.getUnformattedString(nonExistentUnformattedInstance);
  }

  @Test(expected = LocalizerException.class)
  public void LocalizerBundle_getNonExistentFormattedStringFromRootLocalizerBundle_localizerExceptionIsThrown()
      throws Exception {
    resourceBundleWrapperForRootLocaleConfigurator.resetAll().doesResourceBundleExist(true)
        .doesLocalizedStringExist(false);
    final LocalizerInternal localizerInternal =
        localizerFactoryInternal.createLocalizerInternal("localizer.name", Locale.CANADA_FRENCH);
    final String resourceBundleName = "com.resource.bundle.name.DoesNotMatter";
    final LocalizerInstance nonExistentFormattedInstance =
        stubLocalizerFactory.createStubLocalizerInstance("formatted.does.not.exist", "one");
    LocalizerBundleInternal rootLocalizerBundle = null;

    try {
      rootLocalizerBundle = localizerFactoryInternal
          .createRootLocaleLocalizerBundle(localizerInternal, resourceBundleName);
    } catch (LocalizerException le) {
      fail("exception occurred at wrong place");
    }

    rootLocalizerBundle.getFormattedString(nonExistentFormattedInstance, "param01",
        Long.valueOf(23l));
  }

  @Test
  public void LocalizerBundle_getExistingStringsFromLocalizerBundleForLocale_stringsForLocaleAreRetreived()
      throws Exception {
    final Locale expectedTargetLocale = Locale.CANADA_FRENCH;
    final Locale expectedResolvedLocale = Locale.CANADA_FRENCH;
    final LocalizerInternal localizerInternal =
        localizerFactoryInternal.createLocalizerInternal("localizer.name", expectedTargetLocale);
    final String resourceBundleName = "com.resource.bundle.name.DoesNotMatter";
    final String expectedUnformattedString = "this is a test unformatted string for Locale fr_CA";
    final String expectedUnformattedFormattedString =
        "this is a test formatted string for Locale fr_CA: %s, %d";
    final String expectedFormattedString =
        "this is a test formatted string for Locale fr_CA: test_parameter, 33";
    final LocalizerInstance unformattedInstance =
        stubLocalizerFactory.createStubLocalizerInstance("unformatted", "one");
    final LocalizerInstance formattedInstance =
        stubLocalizerFactory.createStubLocalizerInstance("formatted", "one");
    resourceBundleWrapperForLocaleConfigurator.resetAll().doesResourceBundleExist(true)
        .doesLocalizedStringExist(true)
        .addLocalizedString(unformattedInstance.getFullyQualifiedName(), expectedUnformattedString)
        .addLocalizedString(formattedInstance.getFullyQualifiedName(),
            expectedUnformattedFormattedString);

    LocalizerBundleInternal localizerBundle =
        localizerFactoryInternal.createTargetLocalizerBundle(localizerInternal, resourceBundleName);

    assertNotNull(localizerBundle);
    assertEquals(expectedTargetLocale, localizerBundle.getTargetLocale());
    assertEquals(expectedResolvedLocale, localizerBundle.getResolvedLocale());
    assertEquals(expectedUnformattedString,
        localizerBundle.getUnformattedString(unformattedInstance));
    assertEquals(expectedFormattedString, localizerBundle.getFormattedString(formattedInstance,
        "test_parameter", Integer.valueOf(33)));
    assertEquals(expectedUnformattedFormattedString,
        localizerBundle.getUnformattedString(formattedInstance));
    assertEquals(expectedUnformattedString, localizerBundle.getFormattedString(unformattedInstance,
        "another_parameter", Double.valueOf(33.4d)));
  }

  @Test(expected = LocalizerException.class)
  public void LocalizerBundle_getNonExistentUnformattedStringFromLocalizerBundleForLocale_localizerExceptionIsThrown()
      throws Exception {
    resourceBundleWrapperForLocaleConfigurator.resetAll().doesResourceBundleExist(true)
        .doesLocalizedStringExist(false);
    final LocalizerInternal localizerInternal =
        localizerFactoryInternal.createLocalizerInternal("localizer.name", Locale.CANADA_FRENCH);
    final String resourceBundleName = "com.resource.bundle.name.DoesNotMatter";
    final LocalizerInstance nonExistentUnformattedInstance =
        stubLocalizerFactory.createStubLocalizerInstance("unformatted.does.not.exist", "one");
    LocalizerBundleInternal localizerBundle = null;

    try {
      localizerBundle = localizerFactoryInternal.createTargetLocalizerBundle(localizerInternal,
          resourceBundleName);
    } catch (LocalizerException le) {
      fail("exception occurred at wrong place");
    }

    localizerBundle.getUnformattedString(nonExistentUnformattedInstance);
  }

  @Test(expected = LocalizerException.class)
  public void LocalizerBundle_getNonExistentformattedStringFromLocalizerBundleForLocale_localizerExceptionIsThrown()
      throws Exception {
    resourceBundleWrapperForLocaleConfigurator.resetAll().doesResourceBundleExist(true)
        .doesLocalizedStringExist(false);
    final LocalizerInternal localizerInternal =
        localizerFactoryInternal.createLocalizerInternal("localizer.name", Locale.CANADA_FRENCH);
    final String resourceBundleName = "com.resource.bundle.name.DoesNotMatter";
    final LocalizerInstance nonExistentFormattedInstance =
        stubLocalizerFactory.createStubLocalizerInstance("formatted.does.not.exist", "one");
    LocalizerBundleInternal localizerBundle = null;

    try {
      localizerBundle = localizerFactoryInternal.createTargetLocalizerBundle(localizerInternal,
          resourceBundleName);
    } catch (LocalizerException le) {
      fail("exception occurred at wrong place");
    }

    localizerBundle.getFormattedString(nonExistentFormattedInstance, "param01", Long.valueOf(23l));
  }
}