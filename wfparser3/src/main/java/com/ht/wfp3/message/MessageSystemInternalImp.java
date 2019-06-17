package com.ht.wfp3.message;

final class MessageSystemInternalImp implements MessageSystemInternal {
  final static class ConfigImp implements MessageSystem.Config {
    private final static int INIT_UID_KEY_MAXIMUM_LENGTH = 50;
    private final Localization localization;
    private final Constraints constraints;

    ConfigImp() {
      localization = new LocalizationImp();
      constraints = new ConstraintsImp(INIT_UID_KEY_MAXIMUM_LENGTH);
    }

    @Override
    public Localization getLocalization() {
      return localization;
    }

    @Override
    public Constraints getConstraints() {
      return constraints;
    }
  }

  static final MessageSystem SINGLETON = new MessageSystemInternalImp();

  private MessageFactory messageFactory;
  private MessageSystem.Config config;

  MessageSystemInternalImp() {
    internalResetToDefault();
  }

  private void internalResetToDefault() {
    config = new MessageSystemInternalImp.ConfigImp();
    try {
      messageFactory = new MessageFactoryImp(this);
      UID<Priority> undefinedPriorityUid = messageFactory.addPriority("undefined");
      messageFactory.addPriority("debug");
      messageFactory.addPriority("info");
      messageFactory.addPriority("warn");
      messageFactory.addPriority("error");
      UID<Topic> undefinedTopicUid = messageFactory.addTopic("undefined");
      messageFactory.addTopic("system");
      UID<Description> undefinedDescriptionUid = messageFactory.addDescription("undefined");
      messageFactory.addMessage(undefinedTopicUid, undefinedPriorityUid, undefinedDescriptionUid);
    } catch (ConstraintViolationException cve) {
      System.out.println("cve: " + cve);
      messageFactory = new NullMessageFactoryImp();
    }
  }

  @Override
  public MessageFactory getMessageFactory() {
    return messageFactory;
  }

  @Override
  public MessageSystem.Config getConfig() {
    return config;
  }

  @Override
  public Messenger createMessenger() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void resetToDefault() {
    internalResetToDefault();
  }
}