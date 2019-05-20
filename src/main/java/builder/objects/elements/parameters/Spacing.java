package builder.objects.elements.parameters;

import builder.objects.elements.parameters.data.ISpacing;

public class Spacing {

    int spacing = ISpacing.spacing;

    public Spacing(int spacing){ this.spacing = spacing; }
    public Spacing(){}

    public int getSpacing(){ return spacing; }
}
