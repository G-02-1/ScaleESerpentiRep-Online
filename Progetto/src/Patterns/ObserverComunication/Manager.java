package Patterns.ObserverComunication;

import java.util.ArrayList;

public abstract class Manager {

    ArrayList<Subscriber> subscribers = new ArrayList<>();
    public void addSubscriber(Subscriber subscriber) {
        if(!(subscribers.contains(subscriber))) {
            subscribers.add(subscriber);
        }
    }
    public void removeSubscriber(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }
    public void sendNotification(String eventType) {
        for(Subscriber s : subscribers) {
            s.update(eventType);
        }
    }
}
