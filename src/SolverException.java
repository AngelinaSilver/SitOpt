public class SolverException extends RuntimeException {
  public SolverException(String errorMessage, Throwable err) {
      super(errorMessage, err);
  }
}
