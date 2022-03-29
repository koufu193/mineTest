package fields.node.node.nodes;

import fields.Identifier;
import fields.node.Flag;
import fields.node.Property;
import fields.node.node.NameableNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * 引数ノード
 */
public class ArgumentNode extends NameableNode {
    private Property property;
    private Identifier suggestion_type;
    public ArgumentNode(@NotNull Flag type, int[] children,@NotNull String name) {
        super(type, children,name);
    }
    public ArgumentNode(@NotNull Flag type, int[] children,@NotNull String name, @Nullable Integer redirect_node, boolean executable) {
        super(type, children,name, redirect_node, executable);
    }
    public ArgumentNode(@NotNull Flag type, int[] children,@NotNull String name,@Nullable Property property){
        super(type,children,name);
        this.property=property;
    }
    public ArgumentNode(@NotNull Flag type, int[] children, @NotNull String name, @Nullable Identifier suggestions_type){
        super(type,children,name);
        this.suggestion_type=suggestions_type;
    }
    public ArgumentNode(@NotNull Flag type, int[] children, @NotNull String name, @Nullable Integer redirect_node, boolean executable, @NotNull Property property, @Nullable Identifier suggestion_type){
        this(type,children,name,redirect_node,executable);
        this.property=property;
        this.suggestion_type=suggestion_type;
    }
    /**
     * プロパティを取得
     * @return プロパティ
     */
    public final @NotNull Property getProperty() {
        return property;
    }
    /**
     * プロパティを設定
     * @param property 設定するプロパティ
     */
    public final void setProperty(@NotNull Property property) {
        this.property = property;
    }
    /**
     * 提案タイプを取得
     * @return 提案タイプがないならnull
     */
    public final @Nullable Identifier getSuggestion_type() {
        return suggestion_type;
    }
    /**
     * 提案タイプを設定
     * @param suggestion_type 設定する提案タイプ
     */
    public final void setSuggestion_type(@Nullable Identifier suggestion_type) {
        this.suggestion_type = suggestion_type;
    }

    /**
     * プロパティのタイプを取得
     * @return プロパティのタイプ
     */
    public final @NotNull Identifier getPropertyType(){
        return property.getType();
    }

    @Override
    public byte getFlag() {
        return (byte) (super.getFlag()|(getSuggestion_type()!=null?0x10:0x00));
    }
    @Override
    public String toString() {
        return getType()+"{" +
                "name="+getName()+
                ", redirect_node=" + getRedirect_node() +
                ", executable=" + isExecutable() +
                ", property="+getProperty()+
                ", children=" + Arrays.toString(getChildren()) +
                '}';
    }
}
