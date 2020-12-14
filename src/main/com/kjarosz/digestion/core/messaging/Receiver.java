package com.kjarosz.digestion.core.messaging;

public interface Receiver {
   public void processMessage(Message message);
}
