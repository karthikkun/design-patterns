package observer;

public interface Observable {
    void attach(Observer observer);
    boolean dettach(Observer observer);
    void notify(JobPost jobPosting);
}

