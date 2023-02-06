package ru.practicum.ewn.service.enums;

public enum RequestStatus {
    PENDING("PENDING"),
    CONFIRMED("CONFIRMED"),
    REJECTED("REJECTED"),
    CANCELED("CANCELED");

    private final String status;

    RequestStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static RequestStatus fromStatus(String status) {
        for (RequestStatus requestStatus : RequestStatus.values()) {
            if (requestStatus.getStatus().equals(status))
                return requestStatus;
        }
        return null;
    }

    @Override
    public String toString() {
        return "RequestStatus{" +
                "status='" + status + '\'' +
                '}';
    }
}