package ir.sharif.model;

public class CommandResult {
    int statusCode;
    String message;

    public CommandResult(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
