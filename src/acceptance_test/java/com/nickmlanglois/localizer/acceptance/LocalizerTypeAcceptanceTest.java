package com.nickmlanglois.localizer.acceptance;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import com.nickmlanglois.localizer.Assert;
import com.nickmlanglois.localizer.Localizer;
import com.nickmlanglois.localizer.LocalizerInstance;
import com.nickmlanglois.localizer.LocalizerType;
import com.nickmlanglois.localizer.StubLocalizerFactory;
import com.nickmlanglois.localizer.TestableLocalizerFactory;
import com.nickmlanglois.uid.StubUidFactory;
import com.nickmlanglois.uid.Uid;
import com.nickmlanglois.uid.UidFactory;
import com.nickmlanglois.wrap.WrapperFactory;

public class LocalizerTypeAcceptanceTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private TestableLocalizerFactory testableLocalizerFactory;
  private StubLocalizerFactory stubLocalizerFactory;
  private StubUidFactory stubUidFactory;
  private Localizer stubLocalizer;
  private Assert localizerAssert;

  @Before
  public void setup() throws Exception {
    testableLocalizerFactory = TestableLocalizerFactory.getTestableLocalizerFactory(
        WrapperFactory.createWrapperFactory(), UidFactory.createUidFactory());
    stubUidFactory = StubUidFactory.createStubUidFactory();
    testableLocalizerFactory.setUidFactory(stubUidFactory);
    testableLocalizerFactory.resetAll();
    stubLocalizerFactory = StubLocalizerFactory.createStubLocalizerFactory();
    stubLocalizer = stubLocalizerFactory.createLocalizer("stub.localizer", Locale.CANADA_FRENCH);
    localizerAssert = Assert.createAssert();
  }

  @Test
  public void LocalizerType_createTestingAssets_testingAssetsCreated() {
    assertNotNull(testableLocalizerFactory);
    assertNotNull(stubUidFactory);
    assertNotNull(stubLocalizerFactory);
    assertNotNull(stubLocalizer);
    assertNotNull(localizerAssert);
  }

  @Test
  public void LocalizerType_getLocalizerFieldWithNullUid_nullPointerExceptionThrown()
      throws Exception {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("instanceUid cannot be null");

    LocalizerType localizerType = testableLocalizerFactory.createLocalizerType(stubLocalizer,
        "test.group", "test.type", "test.instance");

    localizerType.getLocalizerInstance(null);
  }

  @Test
  public void LocalizerType_getLocalizerFieldWhenLocalizerTypeDoesNotHaveLocalizerFieldForUid_undefinedLocalizerFieldReturned()
      throws Exception {
    String expectedLocalizerTypeGroup = "undef.group";
    String expectedLocalizerTypeType = "undef.type";
    String expectedLocalizerTypeMethod = "undef.method";
    String expectedLocalizerFieldName = "undef.instance";
    String expectedLocalizerFieldFullyQualifiedName = String.join(".", expectedLocalizerTypeGroup,
        expectedLocalizerTypeType, expectedLocalizerTypeMethod, expectedLocalizerFieldName);
    String expectedLocalizerFieldKey = expectedLocalizerFieldFullyQualifiedName;
    final boolean expectedIsDefinedForField = false;
    final boolean expectedIsDefinedForType = false;
    LocalizerType otherLocalizerType = testableLocalizerFactory.createLocalizerType(stubLocalizer,
        "other.group", "other.name", "other.instance");
    LocalizerInstance otherLocalizerField =
        testableLocalizerFactory.createLocalizerInstance(otherLocalizerType, "field.name");
    LocalizerType localizerType = testableLocalizerFactory.createLocalizerType(stubLocalizer,
        "test.group", "test.type", "test.instance");

    LocalizerInstance localizerField =
        localizerType.getLocalizerInstance(otherLocalizerField.getUid());

    localizerAssert.assertExpectedLocalizerInstance(expectedLocalizerFieldName,
        expectedLocalizerFieldFullyQualifiedName, expectedLocalizerFieldKey,
        expectedIsDefinedForField, localizerField);
    localizerAssert.assertExpectedLocalizerType(expectedLocalizerTypeGroup,
        expectedLocalizerTypeType, expectedLocalizerTypeMethod, expectedIsDefinedForType,
        localizerField.getLocalizerType());
  }

  @Test
  public void LocalizerType_getLocalizerFieldForValidLocalizerFieldUid_specifiedLocalizerFieldIsReturned()
      throws Exception {
    final String expectedGroup = "test.group";
    final String expectedType = "test.type";
    final String expectedInstance = "test.instance";
    final String expectedField = "test.field";
    final String expectedFullyQualifiedName =
        String.join(".", expectedGroup, expectedType, expectedInstance, expectedField);
    final LocalizerType localizerType = testableLocalizerFactory.createLocalizerType(stubLocalizer,
        expectedGroup, expectedType, expectedInstance);
    final LocalizerInstance expectedLocalizerField =
        testableLocalizerFactory.createLocalizerInstance(localizerType, expectedField);
    final boolean expectedIsDefinedForField = true;
    final boolean expectedIsDefinedForType = true;


    LocalizerInstance localizerField =
        localizerType.getLocalizerInstance(expectedLocalizerField.getUid());

    localizerAssert.assertExpectedLocalizerInstance(expectedField, expectedFullyQualifiedName,
        expectedLocalizerField.getUid().getKey(), expectedIsDefinedForField, localizerField);
    localizerAssert.assertExpectedLocalizerType(expectedGroup, expectedType, expectedInstance,
        expectedIsDefinedForType, localizerField.getLocalizerType());
  }

  @Test
  public void LocalizerType_getLocalizerFieldUidSetWhenSetIsEmpty_emptySetIsReturned()
      throws Exception {
    final String expectedGroup = "test.group";
    final String expectedType = "test.type";
    final String expectedInstance = "test.instance";
    final LocalizerType localizerType = testableLocalizerFactory.createLocalizerType(stubLocalizer,
        expectedGroup, expectedType, expectedInstance);

    Set<Uid<LocalizerInstance>> localizerFieldKeySet = localizerType.getLocalizerInstanceUidSet();

    assertNotNull(localizerFieldKeySet);
    assertTrue(localizerFieldKeySet.isEmpty());
  }

  @Test
  public void LocalizerType_getLocalizerFieldUidSetWhenItContainsOneUid_setWithOneUidReturned()
      throws Exception {
    final int expectedLocalizerFieldKeySetSize = 1;
    final String expectedGroup = "test.group";
    final String expectedType = "test.type";
    final String expectedInstance = "test.instance";
    final String expectedField = "test.field";
    final LocalizerType localizerType = testableLocalizerFactory.createLocalizerType(stubLocalizer,
        expectedGroup, expectedType, expectedInstance);
    final LocalizerInstance expectedLocalizerField =
        testableLocalizerFactory.createLocalizerInstance(localizerType, expectedField);

    Set<Uid<LocalizerInstance>> localizerFieldKeySet = localizerType.getLocalizerInstanceUidSet();

    assertNotNull(localizerFieldKeySet);
    assertTrue(localizerFieldKeySet.size() == expectedLocalizerFieldKeySetSize);
    assertTrue(localizerFieldKeySet.contains(expectedLocalizerField.getUid()));
    boolean isModifiable = true;
    try {
      localizerFieldKeySet.remove(expectedLocalizerField.getUid());
    } catch (UnsupportedOperationException uoe) {
      isModifiable = false;
    }
    assertFalse(isModifiable);
  }

  @Test
  public void LocalizerType_getLocalizerFieldUidSetWhenItContainsMultipleUids_setWithMultipleUidsreturned()
      throws Exception {
    final int expectedLocalizerFieldKeySetSize = 2;
    final String expectedGroup = "test.group";
    final String expectedType = "test.type";
    final String expectedInstance = "test.instance";
    final String expectedFieldOne = "test.field.01";
    final String expectedFieldTwo = "test.field.02";
    final LocalizerType localizerType = testableLocalizerFactory.createLocalizerType(stubLocalizer,
        expectedGroup, expectedType, expectedInstance);
    final LocalizerInstance expectedLocalizerFieldOne =
        testableLocalizerFactory.createLocalizerInstance(localizerType, expectedFieldOne);
    final LocalizerInstance expectedLocalizerFieldTwo =
        testableLocalizerFactory.createLocalizerInstance(localizerType, expectedFieldTwo);

    Set<Uid<LocalizerInstance>> localizerFieldKeySet = localizerType.getLocalizerInstanceUidSet();

    assertNotNull(localizerFieldKeySet);
    assertTrue(localizerFieldKeySet.size() == expectedLocalizerFieldKeySetSize);
    assertTrue(localizerFieldKeySet.containsAll(
        Arrays.asList(expectedLocalizerFieldOne.getUid(), expectedLocalizerFieldTwo.getUid())));
    boolean isModifiable = true;
    try {
      localizerFieldKeySet.remove(expectedLocalizerFieldOne.getUid());
    } catch (UnsupportedOperationException uoe) {
      isModifiable = false;
    }
    assertFalse(isModifiable);
  }

  @Test
  public void LocalizerType_addSameLocalizerFieldMultipleTimes_localizerFieldOnlyAddedOnce()
      throws Exception {
    final int expectedLocalizerFieldKeySetSize = 1;
    final String expectedGroup = "test.group";
    final String expectedType = "test.type";
    final String expectedInstance = "test.instance";
    final String expectedField = "test.field";
    final LocalizerType localizerType = testableLocalizerFactory.createLocalizerType(stubLocalizer,
        expectedGroup, expectedType, expectedInstance);
    final LocalizerInstance localizerFieldOne =
        testableLocalizerFactory.createLocalizerInstance(localizerType, expectedField);
    final LocalizerInstance localizerFieldTwo =
        testableLocalizerFactory.createLocalizerInstance(localizerType, expectedField);

    Set<Uid<LocalizerInstance>> localizerFieldKeySet = localizerType.getLocalizerInstanceUidSet();

    assertTrue(localizerFieldOne == localizerFieldTwo);
    assertNotNull(localizerFieldKeySet);
    assertTrue(localizerFieldKeySet.size() == expectedLocalizerFieldKeySetSize);
    assertTrue(localizerFieldKeySet.contains(localizerFieldOne.getUid()));
    boolean isModifiable = true;
    try {
      localizerFieldKeySet.remove(localizerFieldOne.getUid());
    } catch (UnsupportedOperationException uoe) {
      isModifiable = false;
    }
    assertFalse(isModifiable);
  }
}
