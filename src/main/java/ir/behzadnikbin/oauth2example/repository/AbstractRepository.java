package ir.behzadnikbin.oauth2example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/*
    An abstract repository as a parent of each repository
    T: entity class
    ID: class of id of entity class
 */
@NoRepositoryBean
public interface AbstractRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    T findOneById(ID id);
    long deleteDistinctById(ID id);
}
