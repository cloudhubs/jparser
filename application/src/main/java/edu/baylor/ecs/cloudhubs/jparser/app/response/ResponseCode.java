package edu.baylor.ecs.cloudhubs.jparser.app.response;

public enum ResponseCode {

    INTERNAL_ERROR(500, "Internal Server Error"),
    OK(200, "OK"),
    CREATED(201, "Created"),
    NOT_FOUND(404, "Not Found");

    private final int code;

    private final String label;

    private ResponseCode(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public int getCode() {
        return code;
    }

    public String getReason() {return label; }
}
