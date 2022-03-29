package fields.node.properties;

import fields.Identifier;
import fields.node.Property;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.PropertyFunction;
import util.PropertyIOFunction;
import util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class PropertyIO {
    private static HashMap<Identifier,AbstractPropertyIO<? extends Property>> ioProperties=new HashMap<>();
    public static final AbstractPropertyIO<DoubleProperty> DOUBLE_PROPERTY=PropertyIOUtil.getProperty(new Identifier("brigadier", "double"), -Double.MAX_VALUE, Double.MAX_VALUE, new PropertyIOFunction<>() {
        @Override
        public void write(@NotNull Double value, @NotNull DataOutputStream output) throws IOException {
            output.writeDouble(value);
        }

        @Override
        public Double read(@NotNull DataInputStream input) throws IOException {
            return input.readDouble();
        }
    }, DoubleProperty::new);
    public static final AbstractPropertyIO<FloatProperty> FLOAT_PROPERTY=PropertyIOUtil.getProperty(new Identifier("brigadier", "float"), -Float.MAX_VALUE, Float.MAX_VALUE, new PropertyIOFunction<>() {
        @Override
        public void write(@NotNull Float value, @NotNull DataOutputStream output) throws IOException {
            output.writeFloat(value);
        }

        @Override
        public Float read(@NotNull DataInputStream input) throws IOException {
            return input.readFloat();
        }
    }, FloatProperty::new);
    public static final AbstractPropertyIO<IntegerProperty> INTEGER_PROPERTY=PropertyIOUtil.getProperty(new Identifier("brigadier","integer"),Integer.MIN_VALUE,Integer.MAX_VALUE,new PropertyIOFunction<Integer>(){

        @Override
        public Integer read(@NotNull DataInputStream input) throws IOException {
            return input.readInt();
        }

        @Override
        public void write(@NotNull Integer value, @NotNull DataOutputStream output) throws IOException {
            output.writeInt(value);
        }
    },IntegerProperty::new);
    public static final AbstractPropertyIO<LongProperty> LONG_PROPERTY=PropertyIOUtil.getProperty(new Identifier("brigadier", "long"), Long.MIN_VALUE, Long.MAX_VALUE, new PropertyIOFunction<Long>() {
        @Override
        public Long read(@NotNull DataInputStream input) throws IOException {
            return input.readLong();
        }

        @Override
        public void write(@NotNull Long value, @NotNull DataOutputStream output) throws IOException {
            output.writeLong(value);
        }
    },LongProperty::new);
    public static final AbstractPropertyIO<StringProperty> STRING_PROPERTY= new AbstractPropertyIO<>(new Identifier("brigadier", "string")) {
        @Override
        public void write(@NotNull StringProperty value, @NotNull DataOutputStream output) throws IOException {
            Util.writeVarInt(value.getStringType(), output);
        }

        @Override
        public @NotNull StringProperty read(@NotNull DataInputStream input) throws IOException {
            return new StringProperty(Util.readVarInt(input));
        }
    };
    public static final AbstractPropertyIO<EntityProperty> ENTITY_PROPERTY= new AbstractPropertyIO<>(new Identifier("minecraft", "entity")) {
        @Override
        public void write(@NotNull EntityProperty value, @NotNull DataOutputStream output) throws IOException {
            byte flag = 0x00;
            if (value.isAllow_only_single_entity_player()) {
                flag = (byte) (flag | 0x01);
            }
            if (value.isAllow_only_players()) {
                flag = (byte) (flag | 0x02);
                output.writeByte(flag);
            }
        }

        @Override
        public @NotNull EntityProperty read(@NotNull DataInputStream input) throws IOException {
            byte flag = input.readByte();
            boolean a = false;
            boolean b = false;
            if (Util.bitmask(flag, 0x01) == 0x01) {
                a = true;
            }
            if (Util.bitmask(flag, 0x02) == 0x02) {
                b = true;
            }
            return new EntityProperty(a, b);
        }
    };
    public static final AbstractPropertyIO<ScoreHolderProperty> SCORE_HOLDER_PROPERTY= new AbstractPropertyIO<>(new Identifier("minecraft", "score_holder")) {
        @Override
        public void write(@NotNull ScoreHolderProperty value, @NotNull DataOutputStream output) throws IOException {
            output.writeByte(value.isAllow_multiple()?0x01:0x00);
        }

        @Override
        public @NotNull ScoreHolderProperty read(@NotNull DataInputStream input) throws IOException {
            return new ScoreHolderProperty(input.readByte()==0x01);
        }
    };
    public static final AbstractPropertyIO<RangeProperty> RANGE_PROPERTY= new AbstractPropertyIO<>(new Identifier("minecraft", "range")) {
        @Override
        public void write(@NotNull RangeProperty value, @NotNull DataOutputStream output) throws IOException {
            output.writeBoolean(value.isAllow_decimal_values());
        }

        @Override
        public @NotNull RangeProperty read(@NotNull DataInputStream input) throws IOException {
            return new RangeProperty(input.readBoolean());
        }
    };
    public static final AbstractPropertyIO<Property> DEFAULT_PROPERTY = new AbstractPropertyIO<>(new Identifier("", "non")) {
        @Override
        public void write(@NotNull Property value, @NotNull DataOutputStream output) {
        }

        @Override
        public @Nullable Property read(@NotNull DataInputStream input) {
            return null;
        }
    };
    static{
        ioProperties.put(DOUBLE_PROPERTY.getType(),DOUBLE_PROPERTY);
        ioProperties.put(FLOAT_PROPERTY.getType(),FLOAT_PROPERTY);
        ioProperties.put(INTEGER_PROPERTY.getType(),INTEGER_PROPERTY);
        ioProperties.put(LONG_PROPERTY.getType(),LONG_PROPERTY);
        ioProperties.put(STRING_PROPERTY.getType(),STRING_PROPERTY);
        ioProperties.put(ENTITY_PROPERTY.getType(),ENTITY_PROPERTY);
        ioProperties.put(SCORE_HOLDER_PROPERTY.getType(),SCORE_HOLDER_PROPERTY);
        ioProperties.put(RANGE_PROPERTY.getType(),RANGE_PROPERTY);
    }
    public static @Nullable AbstractPropertyIO<? extends Property> getPropertyIO(Identifier identifier){
        return ioProperties.get(identifier);
    }
    public static Property getProperty(DataInputStream input) throws IOException{
        return getProperty(Identifier.getInstance(input),input);
    }
    public static Property getProperty(Identifier identifier,DataInputStream input) throws IOException{
        AbstractPropertyIO<?> propertyIO=getPropertyIO(identifier);
        return propertyIO==null?new Property() {
            @Override
            protected @NotNull Identifier initType() {
                return identifier;
            }
        }:propertyIO.read(input);
    }
}
class PropertyIOUtil{
    static boolean needMin(byte flag){
        return Util.bitmask(flag,0x01)==0x01;
    }
    static boolean needMax(byte flag){
        return Util.bitmask(flag,0x02)==0x02;
    }
    static <V extends MinMaxProperty<T>,T> AbstractPropertyIO<V> getProperty(@NotNull Identifier identifier, @NotNull T min, @NotNull T max, @NotNull PropertyIOFunction<T> io, @NotNull PropertyFunction<V,T> make) {
        return new AbstractPropertyIO<V>(identifier){
            @Override
            public void write(@NotNull V value, @NotNull DataOutputStream output) throws IOException {
                byte flag=0x00;
                boolean tellMin=false;
                boolean tellMax=false;
                if(!value.getMin().equals(min)){
                    flag=(byte)(flag|0x01);
                    tellMin=true;
                }
                if(!value.getMax().equals(max)){
                    flag=(byte)(flag|0x02);
                    tellMax=true;
                }
                output.writeByte(flag);
                if(tellMin){
                    io.write(value.getMin(),output);
                }
                if(tellMax){
                    io.write(value.getMax(),output);
                }
            }
            @Override
            public @Nullable V read(@NotNull DataInputStream input) throws IOException {
                byte flag=input.readByte();
                T min1=min;
                T max1=max;
                if(needMin(flag)){
                    min1=io.read(input);
                }
                if(needMax(flag)){
                    max1=io.read(input);
                }
                return make.get(min1,max1);
            }
        };
    }
}