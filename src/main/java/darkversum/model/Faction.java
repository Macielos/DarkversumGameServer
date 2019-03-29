package darkversum.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Document
@Data
@NoArgsConstructor
public class Faction {

    @Id
    private BigInteger id;

    @TextIndexed
    @NotNull
    private String name;

    public Faction(@NotNull BigInteger id, @NotNull String name) {
        this.id = id;
        this.name = name;
    }
}
