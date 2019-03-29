package darkversum.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Document
@Data
@NoArgsConstructor
public class Region {

    @Id
    private BigInteger id;

    @NotNull
    private String name;

    public Region(@NotNull BigInteger id, @NotNull String name) {
        this.id = id;
        this.name = name;
    }
}
