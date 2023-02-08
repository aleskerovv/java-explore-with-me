package ru.practicum.ewn.service.enums;

public enum EventSortParam {
    EVENT_DATE("EVENT_DATE"),
    VIEWS("VIEWS");

    private final String sortParam;

    EventSortParam(String sortParam) {
        this.sortParam = sortParam;
    }

    public String getSortParam() {
        return sortParam;
    }

    public static EventSortParam fromParam(String sortParam) {
        for (EventSortParam eventSortParam : EventSortParam.values()) {
            if (eventSortParam.getSortParam().equals(sortParam))
                return eventSortParam;
        }

        return null;
    }
}
