package com.ht.event.core;

import java.util.HashMap;
import java.util.Map;

final class EventFactoryInternalCacherImp implements EventFactoryInternal {
  private final EventFactoryInternal rootEventFactoryInternal;
  private final EventFactoryInternal nextEventFactoryInternal;
  private final Map<String, ChannelCache> channelNameToChannelCacheMap;

  EventFactoryInternalCacherImp(EventFactoryInternal rootEventFactoryInternal,
      EventFactoryInternal nextEventFactoryInternal) {
    this.rootEventFactoryInternal = rootEventFactoryInternal;
    this.nextEventFactoryInternal = nextEventFactoryInternal;
    this.channelNameToChannelCacheMap = new HashMap<>();
  }

  @Override
  public Channel createChannel(String channelName) {
    ChannelInternal channelInternal = null;
    if (!channelNameToChannelCacheMap.containsKey(channelName)) {
      channelInternal = (ChannelInternal) nextEventFactoryInternal.createChannel(channelName);
      channelNameToChannelCacheMap.put(channelName, new ChannelCacheImp(channelInternal));
    } else {
      channelInternal = channelNameToChannelCacheMap.get(channelName).getChannelInternal();
    }
    return channelInternal;
  }

  @Override
  public Event createEvent(Channel eventChannel, String eventFamily, String eventName) {
    return nextEventFactoryInternal.createEvent(eventChannel, eventFamily, eventName);
  }

  @Override
  public Publisher createPublisher(Channel eventChannel) {
    Publisher newPublisher = nextEventFactoryInternal.createPublisher(eventChannel);
    channelNameToChannelCacheMap.get(eventChannel.getName()).addPublisher(newPublisher);
    return newPublisher;
  }

  @Override
  public void addSubscriber(Channel eventChannel, Subscriber eventSubscriber) {
    if (!channelNameToChannelCacheMap.get(eventChannel.getName()).getSubscriberList()
        .contains(eventSubscriber)) {
      channelNameToChannelCacheMap.get(eventChannel.getName()).addSubscriber(eventSubscriber);
    }
  }

  @Override
  public void enableChannel(Channel eventChannel) {
    nextEventFactoryInternal.enableChannel(eventChannel);
  }

  @Override
  public ChannelCache getChannelCache(String channelName) {
    return channelNameToChannelCacheMap.get(channelName);
  }

  @Override
  public EventFactoryInternal getRootEventFactoryInternal() {
    return rootEventFactoryInternal;
  }
}
