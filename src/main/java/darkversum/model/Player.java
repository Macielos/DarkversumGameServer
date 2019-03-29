package darkversum.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.Date;

@Document
@Data
@NoArgsConstructor
public class Player {

    public enum Status { OFFLINE, WAITING, INGAME }

    @Id
    private BigInteger id;

    @TextIndexed
    @NotNull
    private String name;

    @Field
    private Status status;

    @Field
    private int level;

    @DBRef
    private Region region;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private Date createdDate;

    @LastModifiedBy
    private String updatedBy;

    @LastModifiedDate
    private Date updatedDate;

    public Player(BigInteger id, @NotNull String name, Region region, Status status, Date createdDate, Date updatedDate) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.status = status;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
