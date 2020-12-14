package com.kjarosz.digestion.core.messaging;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;


public class Channel {
   private LinkedList<Receiver> mReceivers;
   private ConcurrentLinkedQueue<Message> mMessageQueue;
      
   public Channel() {
      mReceivers = new LinkedList<>();
      mMessageQueue = new ConcurrentLinkedQueue<>();
   }
   
   public void register(Receiver receiver) {
      if(!mReceivers.contains(receiver)) {
         mReceivers.add(receiver);
      }
   }
   
   public void unregister(Receiver receiver) {
      try {
         mReceivers.remove(receiver);
      } catch(NoSuchElementException ex) {
         // asking for forgiveness
      }
   }
   
   public void queueMessage(Message message) {
      mMessageQueue.add(message);
   }
   
   public void dispatchQueue() {
      while(!mMessageQueue.isEmpty()) {
         Message message = mMessageQueue.poll();
         for(Receiver receiver: mReceivers) {
            receiver.processMessage(message);
         }
      }
   }
}
