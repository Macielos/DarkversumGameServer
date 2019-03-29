package darkversum.matchmaking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerInMatchIds {

    private BigInteger playerId;

    private BigInteger factionId;

}
