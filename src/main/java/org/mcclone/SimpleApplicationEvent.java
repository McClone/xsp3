package org.mcclone;

import org.springframework.context.ApplicationEvent;

/**
 * @author McClone
 */
public class SimpleApplicationEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public SimpleApplicationEvent(Object source) {
        super(source);
    }
}
