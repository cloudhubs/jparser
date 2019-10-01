package edu.baylor.ecs.ciljssa.app.response;

public enum ResponseCode {

    INTERNAL_ERROR(500, "Internal server error"),

    NOT_FOUND(404, "Request could not be found");

    private final int code;

    private final String reason;

    private ResponseCode(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public int getCode() {
        return code;
    }

    public String getReason() {return reason; }
}
