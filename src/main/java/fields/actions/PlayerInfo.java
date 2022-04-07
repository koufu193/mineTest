package fields.actions;

import fields.Chat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerInfo {
    public static class Property {
        private String name;
        private String value;
        private String signature;
        public Property(@NotNull String name, @NotNull String value, @Nullable String signature){
            this.name=name;
            this.value=value;
            this.signature=signature;
        }

        public @NotNull String getName() {
            return name;
        }

        public @Nullable String getSignature() {
            return signature;
        }

        public @NotNull String getValue() {
            return value;
        }

        public void setName(@NotNull String name) {
            this.name = name;
        }

        public void setSignature(@Nullable String signature) {
            this.signature = signature;
        }

        public void setValue(@NotNull String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Property{" +
                    "name='" + name + '\'' +
                    ", value='" + value + '\'' +
                    ", signature='" + signature + '\'' +
                    '}';
        }
        public static void write(@NotNull Property property,@NotNull DataOutputStream output) throws IOException{
            Util.writeString(property.getName(),StandardCharsets.UTF_8,output);
            Util.writeString(property.getValue(),StandardCharsets.UTF_8,output);
            if(property.getSignature()!=null){
                output.writeBoolean(true);
                Util.writeString(property.getSignature(),StandardCharsets.UTF_8,output);
            }else{
                output.writeBoolean(false);
            }
        }
        public static Property read(@NotNull DataInputStream input) throws IOException{
            String name=Util.readString(input);
            String value=Util.readString(input);
            String signature=(input.readBoolean()?Util.readString(input):null);
            return new Property(name,value,signature);
        }
    }
    public static class Player{
        public static abstract class AbstractPlayer{
            protected UUID uuid;
            public void setUUID(@NotNull UUID uuid){
                this.uuid=uuid;
            }

            public UUID getUuid() {
                return uuid;
            }
        }
        public static class AddPlayer extends AbstractPlayer{
            private String name;
            private List<Property> properties;
            private int gamemode;
            private int ping;
            private Chat display_name;
            public AddPlayer(@NotNull String name,@NotNull List<Property> properties,int gamemode,int ping,@Nullable Chat display_name){
                this.name=name;
                this.properties=properties;
                this.gamemode=gamemode;
                this.ping=ping;
                this.display_name=display_name;
            }
            public int getPing() {
                return ping;
            }
            public @Nullable Chat getDisplay_name() {
                return display_name;
            }
            public @NotNull String getName() {
                return name;
            }
            public int getGamemode() {
                return gamemode;
            }
            public @NotNull List<Property> getProperties() {
                return properties;
            }
            public void setPing(int ping) {
                this.ping = ping;
            }
            public void setName(@NotNull String name) {
                this.name = name;
            }
            public void setDisplay_name(@Nullable Chat display_name) {
                this.display_name = display_name;
            }
            public void setGamemode(int gamemode) {
                this.gamemode = gamemode;
            }
            public void setProperties(@NotNull List<Property> properties) {
                this.properties = properties;
            }
            public static void write(@NotNull AddPlayer value, @NotNull DataOutputStream output) throws IOException{
                Util.writeString(value.getName(),StandardCharsets.UTF_8,output);
                Util.writeVarInt(value.getProperties().size(),output);
                for(Property property:value.getProperties()){
                    Property.write(property,output);
                }
                Util.writeVarInt(value.getGamemode(),output);
                Util.writeVarInt(value.getPing(),output);
                if(value.getDisplay_name()!=null){
                    output.writeBoolean(true);
                    Util.writeString(value.getDisplay_name().toString(),StandardCharsets.UTF_8,output);
                }else{
                    output.writeBoolean(false);
                }
            }
            public static AddPlayer read(@NotNull DataInputStream input) throws IOException{
                String name=Util.readString(input);
                int len=Util.readVarInt(input);
                List<Property> properties=new ArrayList<>(len);
                for(int i=0;i<len;i++){
                    properties.add(Property.read(input));
                }
                return new AddPlayer(name,properties,Util.readVarInt(input),Util.readVarInt(input),(input.readBoolean()?Chat.read(input):null));
            }
            @Override
            public String toString() {
                return "AddPlayer{" +
                        "name='" + name + '\'' +
                        ", properties=" + properties +
                        ", gamemode=" + gamemode +
                        ", ping=" + ping +
                        ", display_name=" + display_name +
                        '}';
            }
        }
        public static class UpdateGamemode extends AbstractPlayer{
            private int gamemode;
            public UpdateGamemode(int gamemode){
                this.gamemode=gamemode;
            }
            public int getGamemode() {
                return gamemode;
            }
            public void setGamemode(int gamemode) {
                this.gamemode=gamemode;
            }
            public static void write(@NotNull UpdateGamemode value,@NotNull DataOutputStream output) throws IOException{
                Util.writeVarInt(value.getGamemode(),output);
            }
            public static UpdateGamemode read(@NotNull DataInputStream input) throws IOException{
                return new UpdateGamemode(Util.readVarInt(input));
            }

            @Override
            public String toString() {
                return "UpdateGamemode{" +
                        "gamemode=" + gamemode +
                        '}';
            }
        }
        public static class UpdateLatency extends AbstractPlayer{
            private int ping;
            public UpdateLatency(int ping){
                this.ping=ping;
            }
            public int getPing() {
                return ping;
            }
            public void setPing(int ping) {
                this.ping = ping;
            }
            public static void write(@NotNull UpdateLatency value,@NotNull DataOutputStream output) throws IOException{
                Util.writeVarInt(value.getPing(),output);
            }
            public static UpdateLatency read(@NotNull DataInputStream input) throws IOException{
                return new UpdateLatency(Util.readVarInt(input));
            }

            @Override
            public String toString() {
                return "UpdateLatency{" +
                        "ping=" + ping +
                        '}';
            }
        }
        public static class UpdateDisplayName extends AbstractPlayer{
            private Chat display_name;
            public UpdateDisplayName(@Nullable Chat display_name){
                this.display_name=display_name;
            }
            public @Nullable Chat getDisplay_name() {
                return display_name;
            }
            public void setDisplay_name(@Nullable Chat display_name) {
                this.display_name = display_name;
            }
            @Override
            public String toString() {
                return "UpdateDisplayName{" +
                        "display_name=" + display_name +
                        '}';
            }
            public static void write(@NotNull UpdateDisplayName value,@NotNull DataOutputStream output) throws IOException{
                if(value.getDisplay_name()!=null){
                    output.writeBoolean(true);
                    Util.writeString(value.display_name.toString(), StandardCharsets.UTF_8,output);
                }else{
                    output.writeBoolean(false);
                }
            }
            public static UpdateDisplayName read(@NotNull DataInputStream input) throws IOException{
                if(input.readBoolean()){
                    return new UpdateDisplayName(new Chat(Util.readString(input)));
                }
                return new UpdateDisplayName(null);
            }
        }
        public static class RemovePlayer extends AbstractPlayer{
            @Override
            public String toString() {
                return "RemovePlayer{}";
            }
        }
    }
}
