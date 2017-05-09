package it.elklabs.serverbrowser;

/**
 * Created by petermckinney on 05/05/2017.
 */
public enum Order {
    ASSENDING(true),
    DECENDING(false);

    private Boolean value;

    private Order(Boolean value) {
        this.value = value;
    }

    public Boolean getValue() {
        return this.value;
    }
}
