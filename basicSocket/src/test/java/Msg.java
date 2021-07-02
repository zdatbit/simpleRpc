import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class Msg {

    private int id;
    private String name;
    private Rsp rsp;

    private List<Rsp> rspList;

    private Map<String,Rsp> rspMap;
}
