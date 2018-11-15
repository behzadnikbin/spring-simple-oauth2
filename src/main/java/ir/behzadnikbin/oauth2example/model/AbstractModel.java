package ir.behzadnikbin.oauth2example.model;

import ir.behzadnikbin.oauth2example.service.serviceresult.ValidationError;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@EqualsAndHashCode(of = "id")
@MappedSuperclass
public abstract class AbstractModel implements Serializable {

    @Id
    @Column(length = 16)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private UUID id;        //  primary key

    public abstract List<ValidationError> validate();

}
