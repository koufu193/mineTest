package fields.node.properties;

import fields.Identifier;
import fields.node.Property;

public class DoubleProperty extends Property {
    double min;
    double max;
    Identifier type=new Identifier("brigadier","double");
    public DoubleProperty(double min,double max){
        super();
        this.min=min;
        this.max=max;
    }

    @Override
    public Identifier initType() {
        return type;
    }
}
