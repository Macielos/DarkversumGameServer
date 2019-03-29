package darkversum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchDto {

	public enum Status { WAITING_FOR_PLAYERS, READY, STARTED, RESOLVED }

	private String id;
	private Status status;
	private List<PlayerInMatchDto> players;
	private Date createdDate;
	private Date updatedDate;
}
