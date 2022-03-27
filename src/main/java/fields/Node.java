package fields;

import fields.node.Flag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Node {
    private Integer redirect_node;
    private boolean executable;
    public Node(@NotNull Flag type, @NotNull int[] children){
        this(type,children,null,false);
    }
    public Node(@NotNull Flag type, @NotNull int[] children,@Nullable Integer redirect_node,boolean executable){
        this.redirect_node=redirect_node;
        this.executable=executable;
    }
    /**
     * リダイレクトするノードを取得
     * @return リダイレクトするノードがなければnull
     */
    public final Integer getRedirect_node() {
        return redirect_node;
    }

    /**
     * 現時点で実行可能か
     * @return 実行可能ならtrue
     */
    public final boolean isExecutable() {
        return executable;
    }

    /**
     * 実行可能か設定する
     * @param executable 実行可能はtrue
     */
    public final void setExecutable(boolean executable) {
        this.executable = executable;
    }

    /**
     * リダイレクトするノードを設定
     * @param redirect_node リダイレクトするノードがなければnull
     */
    public final void setRedirect_node(Integer redirect_node) {
        this.redirect_node = redirect_node;
    }
}
