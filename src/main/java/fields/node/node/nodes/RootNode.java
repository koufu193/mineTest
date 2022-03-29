package fields.node.node.nodes;

import fields.Node;
import fields.node.Flag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * ルートノード
 */
public class RootNode extends Node {
    public RootNode(@NotNull Flag type, int[] children) {
        super(type, children);
    }
    public RootNode(@NotNull Flag type, int[] children, @Nullable Integer redirect_node, boolean executable){
        super(type,children,redirect_node,executable);
    }
}
