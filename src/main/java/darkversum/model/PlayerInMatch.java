package darkversum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerInMatch {

    @DBRef
    @NotNull
    private Player player;
    @DBRef
    @NotNull
    private Faction faction;

}
