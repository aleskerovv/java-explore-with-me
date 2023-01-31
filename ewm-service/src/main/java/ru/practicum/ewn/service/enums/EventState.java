package ru.practicum.ewn.service.enums;

public enum EventState {
    PENDING("PENDING"),
    PUBLISHED("PUBLISHED"),
    CANCELED("CANCELED");

    private final String state;

    EventState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public static EventState fromState(String state) {
        for (EventState eventState : EventState.values()) {
            if (eventState.getState().equals(state))
                return eventState;
        }
        return null;
    }

    @Override
    public String toString() {
        return "EventState{" +
                "state='" + state + '\'' +
                '}';
    }
}