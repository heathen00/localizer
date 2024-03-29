package com.nickmlanglois.localizer;

import com.nickmlanglois.uid.Uid;
import com.nickmlanglois.uid.UidFactory;

final class UndefinedLocalizerInstanceInternalImp implements LocalizerInstanceInternal {
  private static final String UNDEFINED_LOCALIZED_STRING = "UNDEFINED";

  private final UndefinedLocalizerTypeInternalImp undefinedLocalizerTypeInternalImp;
  private final String instanceName;
  private final Uid<LocalizerInstance> undefinedLocalizerInstanceUid;

  UndefinedLocalizerInstanceInternalImp(UidFactory uidFactory,
      UndefinedLocalizerTypeInternalImp undefinedLocalizerTypeImp) {
    undefinedLocalizerTypeInternalImp = undefinedLocalizerTypeImp;
    instanceName = "undef.instance";
    undefinedLocalizerInstanceUid = uidFactory.createUid(getFullyQualifiedName(), this);
  }

  @Override
  public Uid<LocalizerInstance> getUid() {
    return undefinedLocalizerInstanceUid;
  }

  @Override
  public LocalizerType getLocalizerType() {
    return undefinedLocalizerTypeInternalImp;
  }

  @Override
  public String getInstanceName() {
    return instanceName;
  }

  @Override
  public String getFullyQualifiedName() {
    return String.join(".", undefinedLocalizerTypeInternalImp.getGroupName(),
        undefinedLocalizerTypeInternalImp.getTypeName(),
        undefinedLocalizerTypeInternalImp.getMethodName(), instanceName);
  }

  @Override
  public String getUnformattedString() throws LocalizerException {
    return UNDEFINED_LOCALIZED_STRING;
  }

  @Override
  public String getFormattedString(Object... parameters) throws LocalizerException {
    return UNDEFINED_LOCALIZED_STRING;
  }

  @Override
  public boolean isDefined() {
    return false;
  }
}
