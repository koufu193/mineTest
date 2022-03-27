package fields.node.node;

import fields.Node;
import fields.node.Flag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class NameableNode extends Node {
    private String name;
    public NameableNode(@NotNull Flag type, @NotNull int[] children,@NotNull String name) {
        super(type, children);
        this.name=name;
    }
    public NameableNode(@NotNull Flag type, @NotNull int[] children,@NotNull String name, @Nullable Integer redirect_node,boolean executable){
        super(type,children,redirect_node,executable);
        this.name=name;
    }
    /**
     * 名前を取得
     * @return 名前
     */
    public final @NotNull String getName() {
        return name;
    }

    /**
     * 名前を設定
     * @param name 設定する名前
     */
    public final void setName(@NotNull String name) {
        this.name = name;
    }
}
