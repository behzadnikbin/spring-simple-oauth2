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

/*
    An abstract class used as a parent of each entity of database
 */
@Getter
@Setter
@EqualsAndHashCode(of = "id")       //  objects are equal when their ids are equal
@MappedSuperclass
public abstract class AbstractModel implements Serializable {

    @Id
    @Column(length = 16)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private UUID id;        //  primary key
}
