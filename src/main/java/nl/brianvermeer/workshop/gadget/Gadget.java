package nl.brianvermeer.workshop.gadget;

public class Gadget {
    private Runnable command;

    public Gadget(String value) {
        this.command = new Command(value);
        this.command.run();
    }
}
