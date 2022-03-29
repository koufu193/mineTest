package fields;

import fields.node.Flag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public abstract class Node {
    private Integer redirect_node;
    private boolean executable;
    private Flag type;
    private int[] children;
    public Node(@NotNull Flag type, int[] children){
        this(type,children,null,false);
    }
    public Node(@NotNull Flag type, int[] children,@Nullable Integer redirect_node,boolean executable){
        this.redirect_node=redirect_node;
        this.executable=executable;
        this.type=type;
        this.children=children;
    }

    /**
     * 子を取得
     * @return 子
     */
    public int[] getChildren() {
        return children;
    }

    /**
     * 子を設定
     * @param children 子
     */
    public void setChildren(int[] children) {
        this.children = children;
    }

    /**
     * タイプを取得する
     * @return タイプ
     */
    public @NotNull Flag getType() {
        return type;
    }

    /**
     * タイプを設定する
     * @param type 設定するタイプ
     */
    public void setType(@NotNull Flag type) {
        this.type = type;
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

    /**
     * フラグを取得
     * @return フラグ
     */
    public byte getFlag(){
        return (byte) (getType().getNum()|(isExecutable()?0x04:0x00)|((getRedirect_node()!=null)?0x08:0x00));
    }

    @Override
    public String toString() {
        return type+"{" +
                "redirect_node=" + redirect_node +
                ", executable=" + executable +
                ", children=" + Arrays.toString(children) +
                '}';
    }
}
