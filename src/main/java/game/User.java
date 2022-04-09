package game;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class User {
    UUID uuid;
    String name;
    String locate="ja_JP";
    byte view_distance=5;
    boolean chat_color=true;
    byte displayed_skin_parts=0x02;
    int main_hand=1;
    boolean enable_text_filtering=false;
    boolean allow_server_listing=true;
    int chat_mode=0;
    World world=null;
    public User(@NotNull UUID uuid,@NotNull String name){
        this.uuid=uuid;
        this.name=name;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    public String getName() {
        return name;
    }
    public UUID getUUID() {
        return uuid;
    }
    public byte getView_distance() {
        return view_distance;
    }
    public int getChat_mode() {
        return chat_mode;
    }
    public byte getDisplayed_skin_parts() {
        return displayed_skin_parts;
    }
    public int getMain_hand() {
        return main_hand;
    }
    public String getLocate() {
        return locate;
    }
    public boolean isAllow_server_listing() {
        return allow_server_listing;
    }
    public boolean isEnable_text_filtering() {
        return enable_text_filtering;
    }
    public boolean isChat_color() {
        return chat_color;
    }
    public void setChat_color(boolean chat_color) {
        this.chat_color = chat_color;
    }
    public void setView_distance(byte view_distance) {
        this.view_distance = view_distance;
    }
    public void setMain_hand(int main_hand) {
        this.main_hand = main_hand;
    }
    public void setDisplayed_skin_parts(byte displayed_skin_parts) {
        this.displayed_skin_parts = displayed_skin_parts;
    }
    public void setLocate(@NotNull String locate) {
        this.locate = locate;
    }
    public void setChat_mode(int chat_mode) {
        this.chat_mode = chat_mode;
    }
    public void setEnable_text_filtering(boolean enable_text_filtering) {
        this.enable_text_filtering = enable_text_filtering;
    }
    public void setAllow_server_listing(boolean allow_server_listing) {
        this.allow_server_listing = allow_server_listing;
    }
    public boolean equals(Object o){
        if(this==o) return true;
        if(o instanceof User that){
            return that.name.equals(name)&&that.uuid.equals(uuid);
        }
        return false;
    }
}
