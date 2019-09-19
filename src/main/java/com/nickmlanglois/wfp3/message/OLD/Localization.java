package com.nickmlanglois.wfp3.message.OLD;

import java.util.Locale;

public interface Localization {

  String UNKNOWN_L10N_KEY = "__UNKNOWN_KEY__";

  Locale getLocale();

  void setLocale(Locale locale);

  void setDefaultLocale();

  String getPriorityName(String priorityUidKey);

  String getTopicName(String topicUidKey);

  String getDescriptionUnformattedText(String descriptionUidKey);

  String getDescriptionFormattedText(String descriptionUidKey, Object... parameters);
}