package experiments.waweruu.c19tn.util;

public class StatusReport {

    private Status status;
    private String message;

    public StatusReport(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public static StatusReport makeStatusReport(Status status, String message) {
        return new StatusReport(status, message);
    }
}
