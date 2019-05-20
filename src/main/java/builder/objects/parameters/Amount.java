package builder.objects.elements.parameters;

import builder.objects.elements.parameters.data.IAmount;

public class Amount {

    int amount = IAmount.amount;

    public Amount(int amount){ this.amount = amount-1; }
    public Amount(){}

    public int getAmount(){ return amount; }
}
