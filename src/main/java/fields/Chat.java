package fields;

public class Chat {
    private String json;
    public Chat(String json){
        this.json=json;
    }
    @Override
    public String toString() {
        return json;
    }
}
