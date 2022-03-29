package fields.node.properties;

import fields.Identifier;
import fields.node.Property;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class AbstractPropertyIO<V extends Property>{
    private final Identifier type;
    public AbstractPropertyIO(Identifier type){
        this.type=type;
    }
    /**
     * タイプを取得
     * @return タイプ
     */
    public final @NotNull Identifier getType() {
        return type;
    }

    /**
     * 指定した場所にデータを書きこむ
     * @param value 書き込むデータ
     * @param output 書き込み先
     */
    public abstract void write(@NotNull V value, @NotNull DataOutputStream output) throws IOException;

    /**
     * 指定した場所からデータを読み込む
     * @param input 読み込み先
     * @return 結果
     */
    public abstract @Nullable V read(@NotNull DataInputStream input) throws IOException;
}