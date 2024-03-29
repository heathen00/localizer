package com.nickmlanglois.localizer;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import com.nickmlanglois.uid.Uid;

final class LocalizerInternalImp implements LocalizerInternal {
  private final String name;
  private Locale locale;
  private final LocalizerFactoryInternal factoryInternal;
  private final SortedSet<LocalizerBundle> localizerBundleSet;
  private final Map<Uid<LocalizerType>, LocalizerTypeInternal> localizerTypeMap;
  private final Uid<Localizer> localizerUid;

  public LocalizerInternalImp(LocalizerFactoryInternal factoryInternal, String name,
      Locale locale) {
    this.factoryInternal = factoryInternal;
    this.name = name;
    this.locale = locale;
    localizerUid = factoryInternal.getUidFactory().createUid(name, this);
    localizerBundleSet = new TreeSet<>();
    localizerTypeMap = new HashMap<>();
  }

  @Override
  public Locale getLocale() {
    return locale;
  }

  @Override
  public void setLocale(Locale locale) throws LocalizerException {
    if (null == locale) {
      throw new NullPointerException("locale cannot be null");
    }
    this.locale = locale;
    for (LocalizerBundle localizerBundle : localizerBundleSet) {
      ((LocalizerBundleInternal) localizerBundle).loadL10nResource(this.locale);
    }
  }

  @Override
  public LocalizerTypeInternal getLocalizerTypeInternal(Uid<LocalizerType> typeUid) {
    if (null == typeUid) {
      throw new NullPointerException("typeUid cannot be null");
    }
    LocalizerTypeInternal localizerType = localizerTypeMap.get(typeUid);
    if (null == localizerType) {
      localizerType = factoryInternal.createUndefinedLocalizer().getLocalizerTypeInternal(null);
    }
    return localizerType;
  }

  @Override
  public LocalizerType getLocalizerType(Uid<LocalizerType> typeUid) {
    return getLocalizerTypeInternal(typeUid);
  }

  @Override
  public Set<Uid<LocalizerType>> getLocalizerTypeUidSet() {
    return Collections.unmodifiableSet(localizerTypeMap.keySet());
  }

  @Override
  public LocalizerInstance getLocalizerInstance(Uid<LocalizerInstance> instanceUid) {
    if (instanceUid == null) {
      throw new NullPointerException("instanceUid cannot be null");
    }
    LocalizerInstance localizerInstance = null;
    for (Uid<LocalizerType> localizerTypeKey : localizerTypeMap.keySet()) {
      localizerInstance = localizerTypeMap.get(localizerTypeKey).getLocalizerInstance(instanceUid);
      if (localizerInstance.isDefined()) {
        break;
      }
    }
    if (null == localizerInstance) {
      localizerInstance = factoryInternal.createUndefinedLocalizer().getLocalizerInstance(null);
    }
    return localizerInstance;
  }

  @Override
  public Set<Uid<LocalizerInstance>> getLocalizerInstanceUidSet() {
    Set<Uid<LocalizerInstance>> localizerInstanceUidSet = new HashSet<>();
    for (Uid<LocalizerType> localizerTypeUid : localizerTypeMap.keySet()) {
      localizerInstanceUidSet
          .addAll(localizerTypeMap.get(localizerTypeUid).getLocalizerInstanceUidSet());
    }
    return Collections.unmodifiableSet(localizerInstanceUidSet);
  }

  @Override
  public SortedSet<LocalizerBundle> getLocalizerBundleSet() {
    return Collections.unmodifiableSortedSet(localizerBundleSet);
  }

  @Override
  public boolean isDefined() {
    return true;
  }

  @Override
  public LocalizerBundleInternal addLocalizerBundleInternal(
      LocalizerBundleInternal localizerBundleInternal) {
    LocalizerBundleInternal newLocalizerBundleInternal = localizerBundleInternal;
    LocalizerBundle currentLocalizerBundle = null;
    for (Iterator<LocalizerBundle> localizerBundleIterator =
        localizerBundleSet.iterator(); localizerBundleIterator.hasNext();) {
      currentLocalizerBundle = localizerBundleIterator.next();
      if (currentLocalizerBundle.equals(newLocalizerBundleInternal)) {
        newLocalizerBundleInternal = (LocalizerBundleInternal) currentLocalizerBundle;
        break;
      }
    }
    if (newLocalizerBundleInternal == localizerBundleInternal) {
      localizerBundleSet.add(newLocalizerBundleInternal);
    }
    return newLocalizerBundleInternal;
  }

  @Override
  public LocalizerTypeInternal addLocalizerTypeInternal(
      LocalizerTypeInternal localizerTypeInternal) {
    LocalizerTypeInternal newLocalizerTypeInternal = localizerTypeInternal;
    LocalizerTypeInternal currentLocalizerTypeInternal =
        localizerTypeMap.get(newLocalizerTypeInternal.getUid());
    if (null == currentLocalizerTypeInternal) {
      localizerTypeMap.put(newLocalizerTypeInternal.getUid(), newLocalizerTypeInternal);
    } else {
      newLocalizerTypeInternal = currentLocalizerTypeInternal;
    }
    return newLocalizerTypeInternal;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Uid<Localizer> getUid() {
    return localizerUid;
  }
}
