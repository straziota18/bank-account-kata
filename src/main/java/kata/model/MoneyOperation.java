package kata.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MoneyOperation {
    private final Number value;

    public MoneyOperation(@JsonProperty("value") Number value) {
        this.value = value;
    }

    public Number getValue() {
        return value;
    }
}
