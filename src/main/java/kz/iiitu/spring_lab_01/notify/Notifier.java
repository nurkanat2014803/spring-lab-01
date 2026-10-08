package kz.iiitu.spring_lab_01.notify;

public interface Notifier {
    String send(String message);
    String channel();
}
