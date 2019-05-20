package cecylb.dsl.model;

public class Amount {

    private final int amount;

    public Amount(final int amount) {
        this.amount = amount - 1;
    }

    public Amount() {
        this.amount = 1;
    }

    public int getAmount() {
        return amount;
    }

}
