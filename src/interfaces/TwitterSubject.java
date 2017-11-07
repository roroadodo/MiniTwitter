package interfaces;

public interface TwitterSubject {
	public void notifyObservers();
	public void addObserver(TwitterObserver observer);
}
