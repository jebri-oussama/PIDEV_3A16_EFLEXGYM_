package gestion_evenement.entities;

import java.util.ArrayList;
import java.util.List;

public class EventBus {
    private static final EventBus instance = new EventBus();
    private final List<EventListener> listeners = new ArrayList<>();

    public static EventBus getInstance() {
        return instance;
    }

    public void register(EventListener listener) {
        listeners.add(listener);
    }

    public void unregister(EventListener listener) {
        listeners.remove(listener);
    }

    public void notifyTableRefreshed() {
        for (EventListener listener : listeners) {
            listener.onTableRefreshed();
        }
    }

    public interface EventListener {
        void onTableRefreshed();
    }
}
