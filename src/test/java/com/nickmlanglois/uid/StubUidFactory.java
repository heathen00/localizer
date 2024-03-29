package com.nickmlanglois.uid;

import com.nickmlanglois.uid.Uid;
import com.nickmlanglois.uid.UidFactory;
import com.nickmlanglois.uid.UidInternalImp;

public final class StubUidFactory implements UidFactory {
  public static StubUidFactory createStubUidFactory() {
    return new StubUidFactory();
  }

  private StubUidFactory() {}

  @Override
  public void resetAll() {}

  @Override
  public <T> Uid<T> createUid(String key, T component) {
    return new UidInternalImp<T>(key, component);
  }
}
