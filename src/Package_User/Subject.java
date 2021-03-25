package Package_User;

import Package_GUI.Observers.Observer;

public interface Subject {

    void registerObserver(Observer observer);
    void removeObserver();

    public void notifyObservers();


}
