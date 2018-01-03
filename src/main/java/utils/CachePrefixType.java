package utils;

public enum CachePrefixType {
    BCOMP("bcomp:");

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
