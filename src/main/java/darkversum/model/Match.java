package darkversum.model;

import darkversum.dto.MatchDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Document
@Data
@NoArgsConstructor
public class Match {

    @Id
    private BigInteger id;

    @Field
    private MatchDto.Status status;

    @Field
    private List<PlayerInMatch> players;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private Date createdDate;

    @LastModifiedBy
    private String updatedBy;

    @LastModifiedDate
    private Date updatedDate;

    public Match(BigInteger id, MatchDto.Status status, List<PlayerInMatch> players) {
        this(id, status, players, null, null);
    }

    public Match(BigInteger id, MatchDto.Status status, List<PlayerInMatch> players, Date createdDate, Date updatedDate) {
        this.id = id;
        this.status = status;
        this.players = players;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
