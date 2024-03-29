package com.nickmlanglois.localizer;

import com.nickmlanglois.uid.Uid;
import com.nickmlanglois.uid.UidFactory;
import com.nickmlanglois.wrap.ResourceBundleWrapper;
import com.nickmlanglois.wrap.WrapperFactory;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;

final class LocalizerFactoryInternalImp implements LocalizerFactoryInternal {
  private WrapperFactory wrapperFactory;
  private UidFactory uidFactory;
  private final Map<Uid<Localizer>, LocalizerInternal> localizerInternalMap;
  private final LocalizerInternal undefinedLocalizer;

  LocalizerFactoryInternalImp(WrapperFactory wrapperFactory, UidFactory uidFactory) {
    this.wrapperFactory = wrapperFactory;
    this.uidFactory = uidFactory;
    localizerInternalMap = new HashMap<>();
    undefinedLocalizer = new UndefinedLocalizerInternalImp(uidFactory);
  }

  private void guardNotNull(String parameterName, Object parameter) {
    if (null == parameter) {
      throw new NullPointerException(parameterName + " cannot be null");
    }
  }

  private void guardNamingConvention(String constructorParameterName,
      String constructorParameterValue) throws LocalizerException {
    if (!constructorParameterValue.matches("^[a-z0-9][a-z0-9.]+$")
        || constructorParameterValue.endsWith(".")) {
      throw new LocalizerException(constructorParameterName
          + " can only contain the characters: lowercase letters, numbers, and period and cannot start or end with a period");
    }
  }

  @Override
  public LocalizerBundle createLocalizerBundle(Localizer localizer, String resourceBundleName)
      throws LocalizerException {
    guardNotNull("localizer", localizer);
    guardNotNull("resourceBundleName", resourceBundleName);
    LocalizerInternal localizerInternal = null;
    if (localizer instanceof LocalizerInternal) {
      localizerInternal = (LocalizerInternal) localizer;
    } else {
      throw new LocalizerException("unknown Localizer implementation");
    }
    LocalizerBundleInternal targetLocalizerBundle =
        createTargetLocalizerBundle(localizerInternal, resourceBundleName);
    LocalizerBundleInternal rootLocalizerBundle =
        createRootLocaleLocalizerBundle(localizerInternal, resourceBundleName);
    LocalizerBundleInternal undefinedLocalizerBundle = createUndefinedLocalizerBundle();
    CompositeLocalizerBundleImp compositeLocalizerBundleImp = new CompositeLocalizerBundleImp(
        targetLocalizerBundle, rootLocalizerBundle, undefinedLocalizerBundle);
    return (LocalizerBundle) localizerInternal
        .addLocalizerBundleInternal(compositeLocalizerBundleImp);
  }

  @Override
  public LocalizerInternal createLocalizerInternal(String name, Locale locale)
      throws LocalizerException {
    guardNotNull("name", name);
    guardNamingConvention("name", name);
    guardNotNull("locale", locale);
    LocalizerInternal newLocalizerInternal = new LocalizerInternalImp(this, name, locale);
    LocalizerInternal currentLocalizerInternal =
        localizerInternalMap.get(newLocalizerInternal.getUid());
    if (null != currentLocalizerInternal) {
      if (!newLocalizerInternal.getLocale().equals(currentLocalizerInternal.getLocale())) {
        throw new LocalizerException(
            "attempt to create existing Localizer but with different Locale");
      }
      newLocalizerInternal = currentLocalizerInternal;
    } else {
      localizerInternalMap.put(newLocalizerInternal.getUid(), newLocalizerInternal);
    }
    return newLocalizerInternal;
  }

  @Override
  public Localizer createLocalizer(String name, Locale locale) throws LocalizerException {
    return createLocalizerInternal(name, locale);
  }

  @Override
  public LocalizerBundleInternal createTargetLocalizerBundle(LocalizerInternal localizerInternal,
      String resourceBundleName) throws LocalizerException {
    guardNotNull("localizerInternal", localizerInternal);
    guardNotNull("resourceBundleName", resourceBundleName);
    ResourceBundleWrapper resourceBundleWrapper = createResourceBundleWrapperForLocalizerBundle(
        resourceBundleName, localizerInternal.getLocale());
    return new LocalizerBundleInternalImp(this, localizerInternal, resourceBundleWrapper);
  }

  @Override
  public LocalizerBundleInternal createRootLocaleLocalizerBundle(
      LocalizerInternal localizerInternal, String resourceBundleName) throws LocalizerException {
    guardNotNull("localizerInternal", localizerInternal);
    guardNotNull("resourceBundleName", resourceBundleName);
    ResourceBundleWrapper resourceBundleWrapper =
        wrapperFactory.createResourceBundleWrapperForRootLocale(resourceBundleName);
    try {
      resourceBundleWrapper.loadResourceBundle();
    } catch (MissingResourceException mre) {
      throw new LocalizerException(mre);
    }
    return new LocalizerBundleInternalImp(this, localizerInternal, resourceBundleWrapper);
  }

  @Override
  public LocalizerBundleInternal createUndefinedLocalizerBundle() {
    return (LocalizerBundleInternal) undefinedLocalizer.getLocalizerBundleSet().iterator().next();
  }

  @Override
  public LocalizerType createLocalizerType(Localizer localizer, String groupName, String typeName,
      String methodName) throws LocalizerException {
    guardNotNull("localizer", localizer);
    guardNotNull("groupName", groupName);
    guardNamingConvention("groupName", groupName);
    guardNotNull("typeName", typeName);
    guardNamingConvention("typeName", typeName);
    guardNotNull("methodName", methodName);
    guardNamingConvention("methodName", methodName);

    LocalizerInternal localizerInternal = null;
    if (localizer instanceof LocalizerInternal) {
      localizerInternal = (LocalizerInternal) localizer;
    } else {
      throw new LocalizerException("unknown Localizer implementation");
    }
    LocalizerTypeInternal localizerTypeInternal =
        new LocalizerTypeInternalImp(this, localizerInternal, groupName, typeName, methodName);
    return localizerInternal.addLocalizerTypeInternal(localizerTypeInternal);
  }

  @Override
  public LocalizerInstance createLocalizerInstance(LocalizerType localizerType, String instanceName)
      throws LocalizerException {
    guardNotNull("localizerType", localizerType);
    guardNotNull("instanceName", instanceName);
    guardNamingConvention("instanceName", instanceName);
    LocalizerTypeInternal localizerTypeInternal = null;
    if (localizerType instanceof LocalizerTypeInternal) {
      localizerTypeInternal = (LocalizerTypeInternal) localizerType;
    } else {
      throw new LocalizerException("unknown LocalizerType implementation");
    }
    LocalizerInstanceInternal newLocalizerInstanceInternal =
        new LocalizerInstanceInternalImp(this, (LocalizerTypeInternal) localizerType, instanceName);
    return localizerTypeInternal.addLocalizerInstanceInternal(newLocalizerInstanceInternal);
  }

  @Override
  public LocalizerInternal createUndefinedLocalizer() {
    return undefinedLocalizer;
  }

  @Override
  public ResourceBundleWrapper createResourceBundleWrapperForLocalizerBundle(
      String resourceBundleName, Locale targetLocale) throws LocalizerException {
    ResourceBundleWrapper resourceBundleWrapper =
        wrapperFactory.createResourceBundleWrapperForLocale(resourceBundleName, targetLocale);
    try {
      resourceBundleWrapper.loadResourceBundle();
    } catch (MissingResourceException mre) {
      throw new LocalizerException(mre);
    }
    return resourceBundleWrapper;
  }

  @Override
  public Set<Uid<Localizer>> getLocalizerUidSet() {
    return Collections.unmodifiableSet(localizerInternalMap.keySet());
  }

  @Override
  public Localizer getLocalizer(Uid<Localizer> localizerUid) {
    guardNotNull("localizerUid", localizerUid);
    if (null == localizerUid) {
      throw new NullPointerException("localizerUid cannot be null");
    }
    LocalizerInternal localizerInternal = localizerInternalMap.get(localizerUid);
    if (null == localizerInternal) {
      localizerInternal = createUndefinedLocalizer();
    }
    return localizerInternal;
  }

  @Override
  public void resetAll() {
    resetAllInternal();
  }

  private void resetAllInternal() {
    localizerInternalMap.clear();
    wrapperFactory.resetAll();
    uidFactory.resetAll();
  }

  @Override
  public void setWrapperFactory(WrapperFactory wrapperFactory) {
    if (null == wrapperFactory) {
      throw new NullPointerException("wrapperFactory cannot be null");
    }
    this.wrapperFactory = wrapperFactory;
  }

  @Override
  public WrapperFactory getWrapperFactory() {
    return wrapperFactory;
  }

  @Override
  public void setUidFactory(UidFactory uidFactory) {
    if (null == uidFactory) {
      throw new NullPointerException("uidFactory cannot be null");
    }
    this.uidFactory = uidFactory;
  }

  @Override
  public UidFactory getUidFactory() {
    return uidFactory;
  }
}
