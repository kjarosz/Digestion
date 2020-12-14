package com.kjarosz.digestion.core.messaging;

import java.util.HashMap;


public class MessageSystem {
   private HashMap<String, Channel> mChannels;

   public MessageSystem() {
      mChannels = new HashMap<>();
   }
   
   public void registerReceiver(String channel, Receiver receiver) {
      if(mChannels.containsKey(channel)) {
         mChannels.get(channel).register(receiver);
      } else {
         Channel newChannel = new Channel();
         newChannel.register(receiver);
         mChannels.put(channel, newChannel);
      }
   }
   
   public void queueMessage(String channel, Message message) {
      if(mChannels.containsKey(channel)) {
         mChannels.get(channel).queueMessage(message);
      }
   }
   
   public void dispatchMessages() {
      mChannels.forEach((key, channel) -> channel.dispatchQueue());
   }
}
