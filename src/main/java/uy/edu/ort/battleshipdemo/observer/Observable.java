package uy.edu.ort.battleshipdemo.observer;

import java.util.ArrayList;
import java.util.List;

public class Observable {

    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Object event) {
        List<Observer> copy = new ArrayList<>(observers);
        for (Observer observer : copy) {
            observer.update(this, event);
        }
    }
}
