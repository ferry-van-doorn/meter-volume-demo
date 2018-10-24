package metervolumedemo.meterreading.csv;

public class CannotProcessCsvException extends RuntimeException {

    public CannotProcessCsvException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
