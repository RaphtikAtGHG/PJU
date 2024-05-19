package tuxer.org.exceptions;

public class MemoryManagerError extends RuntimeException {
    public MemoryManagerError() {
        throw new RuntimeException("Memory Manager Error!");
    }

    public MemoryManagerError(String message, Throwable cause) {
        throw new RuntimeException("Memory Manager Error: " + message, cause);
    }

    protected MemoryManagerError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        throw new RuntimeException("Memory Manager Error: " + message, cause);
    }

    public MemoryManagerError(String message) {
        throw new RuntimeException("Memory Manager Error: " + message);
    }
}
