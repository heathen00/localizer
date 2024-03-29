package com.nickmlanglois.uid;

import com.nickmlanglois.uid.CanReset;
import com.nickmlanglois.uid.Uid;
import com.nickmlanglois.uid.UidFactory;
import com.nickmlanglois.uid.UidFactoryInternal;

public class TestableUidFactory implements UidFactory, CanReset {
  private static final TestableUidFactory TESTABLE_UID_FACTORY = new TestableUidFactory();

  public static TestableUidFactory getTestableUidFactory() {
    return TESTABLE_UID_FACTORY;
  }

  private final UidFactoryInternal uidFactoryInternal;

  TestableUidFactory() {
    uidFactoryInternal = UidFactoryInternal.createUidFactoryInternal();
  }

  @Override
  public void resetAll() {
    uidFactoryInternal.resetAll();
  }

  @Override
  public <T> Uid<T> createUid(String key, T component) {
    return uidFactoryInternal.createUid(key, component);
  }
}
