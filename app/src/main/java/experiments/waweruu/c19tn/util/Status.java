package experiments.waweruu.c19tn.util;

public enum Status {
    SUCCESS,
    ERROR,
    LOADING;

    boolean isLoading() {
        return this == LOADING;
    }
}
