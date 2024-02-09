package Pegas.DAO;

import Pegas.entity.User;

import java.util.List;
import java.util.Optional;

public interface DAO<U,K> {
    boolean update(U u);
    List<U> findAll();
    Optional<U> findByID(K id);
    boolean delete(K id);
    U save(U u);
}
