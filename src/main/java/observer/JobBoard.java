package observer;

import java.util.ArrayList;
import java.util.List;

public class JobBoard implements Observable {
    private List<Observer> observers = new ArrayList<>();

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public boolean dettach(Observer observer) {
        //TODO
        return false;
    }

    @Override
    public void notify(JobPost jobPosting) {
        for (Observer observer : observers) {
            observer.onJobPosted(jobPosting);
        }
    }

    public void addJobPosting(JobPost jobPosting) {
        this.notify(jobPosting);
    }
}