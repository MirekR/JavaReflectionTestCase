package cz.mirek.reflection.domain;

public final class FinalObject {
    private final String name;

    private FinalObject(Builder builder) {
        this.name = builder.name;
    }

    public String getName() {
        return name;
    }

    public static class Builder {
        private String name;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public FinalObject build() {
            return new FinalObject(this);
        }
    }
}
