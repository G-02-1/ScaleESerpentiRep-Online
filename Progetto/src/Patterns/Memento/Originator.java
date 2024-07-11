package Patterns.Memento;

public interface Originator {
    Memento save();
    void backup(Memento memento);

}
