package com.jim.productdemo.api;

/**
 * The error message of API
 */
public class ApiError {
  private int status;
  private String message;
  private Throwable throwable;

  public ApiError(int status, String message, Throwable throwable) {
    this.status = status;
    this.message = message;
    this.throwable = throwable;
  }

  public ApiError(String message) {
    this(0, message, null);
  }

  public int getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public Throwable getThrowable() {
    return throwable;
  }

  @Override public String toString() {
    return "ApiError{" +
        "status=" + status +
        ", message='" + message + '\'' +
        ", throwable=" + throwable +
        '}';
  }
}
