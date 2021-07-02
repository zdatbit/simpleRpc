import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Rsp {
    private int code;
    private Object data;
}
