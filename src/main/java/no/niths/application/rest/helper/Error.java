package no.niths.application.rest.helper;

/**
 * 
 * @author NITHs
 *
 */
public enum Error {

    DOES_NOT_EXIST("does not exist");

    private String msg;

    Error(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}