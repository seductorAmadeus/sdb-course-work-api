package utils;

public enum CachePrefixType {
    BCOMP("bcomp:"),
    BCOMP_SETTINGS("bcomp_settings:");

    private String text;

    CachePrefixType(String text) {
        this.text = text;
    }

    private String getText() {
        return text;
    }

    @Override
    public String toString() {
        return this.getText();
    }

}
