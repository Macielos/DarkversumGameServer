package darkversum.dto;

import darkversum.model.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto {

	private String id;
	private String name;
	private String region;
	private Player.Status status;
	private Date createdDate;
	private Date updatedDate;

}
