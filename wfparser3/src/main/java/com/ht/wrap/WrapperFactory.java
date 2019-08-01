package com.ht.wrap;

import java.util.Locale;

public interface WrapperFactory {
  static WrapperFactory createWrapperFactory() {
    return WrapperFactoryImp.getFactorySingleton();
  }

  ResourceBundleWrapper createResourceBundleWrapperForLocale(String resourceBundleName,
      Locale locale);

  ResourceBundleWrapper createResourceBundleWrapperForRootLocale(String resourceBundleName);
}