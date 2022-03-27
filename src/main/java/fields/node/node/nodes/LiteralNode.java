package fields.node.node.nodes;

import fields.Node;
import fields.node.Flag;
import fields.node.node.NameableNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * リテラエルノード
 */
public class LiteralNode extends NameableNode {
    public LiteralNode(@NotNull Flag type, int[] children,@NotNull String name) {
        super(type, children,name);
    }
    public LiteralNode(@NotNull Flag type, int[] children,@NotNull String name, @Nullable Integer redirect_node,boolean executable){
        super(type,children,name,redirect_node,executable);
    }
}
