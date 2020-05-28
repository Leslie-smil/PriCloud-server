package edu.scujcc.pircloud.model;

/**
 * @author FSMG
 */
public class Result<T> {
    public static int SUCCESS = 1;
    public static int FAIL = -1;
    private int status = 0;
    private String message;
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
