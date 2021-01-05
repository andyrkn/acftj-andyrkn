package events;

import interceptors.Log;

import javax.enterprise.event.Observes;
import javax.inject.Named;

@Named
public class EventObserver {

    @Log
    public void observeEvent(@Observes @TimeframeEvent boolean status) { }
}
