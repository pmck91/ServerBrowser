package it.elklabs.serverbrowser;

/**
 * ENUM that handles sql results display order
 */
public enum Order {
    ASSENDING(true),
    DECENDING(false);

    private Boolean value;

    /**
     * internal constructor
     *
     * @param value defined above
     */
    private Order(Boolean value) {
        this.value = value;
    }

    public Boolean getValue() {
        return this.value;
    }
}
