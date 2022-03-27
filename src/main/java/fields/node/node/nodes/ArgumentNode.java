package fields.node.node.nodes;

import fields.Identifier;
import fields.node.Flag;
import fields.node.Property;
import fields.node.node.NameableNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 引数ノード
 */
public class ArgumentNode extends NameableNode {
    private Property property;
    private Identifier suggestion_type;
    public ArgumentNode(@NotNull Flag type, @NotNull int[] children,@NotNull String name) {
        super(type, children,name);
    }
    public ArgumentNode(@NotNull Flag type, @NotNull int[] children,@NotNull String name, @Nullable Integer redirect_node, boolean executable) {
        super(type, children,name, redirect_node, executable);
    }
    public ArgumentNode(@NotNull Flag type, @NotNull int[] children,@NotNull String name,@Nullable Property property){
        super(type,children,name);
        this.property=property;
    }
    public ArgumentNode(@NotNull Flag type,@NotNull int[] children,@NotNull String name,@Nullable Identifier suggestions_type){
        super(type,children,name);
        this.suggestion_type=suggestions_type;
    }
    public ArgumentNode(@NotNull Flag type,@NotNull int[] children,@NotNull String name,@Nullable Integer redirect_node,boolean executable,@Nullable Property property,@Nullable Identifier suggestion_type){
        this(type,children,name,redirect_node,executable);
        this.property=property;
        this.suggestion_type=suggestion_type;
    }
    /**
     * プロパティを取得
     * @return プロパティがないならnull
     */
    public final @Nullable Property getProperty() {
        return property;
    }
    /**
     * プロパティを設定
     * @param property 設定するプロパティ
     */
    public final void setProperty(@Nullable Property property) {
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
    public final @Nullable Identifier getPropertyType(){
        return property==null?null:property.getType();
    }
}
