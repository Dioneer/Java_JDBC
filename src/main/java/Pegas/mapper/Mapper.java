package Pegas.mapper;

public interface Mapper<T,E> {
    T mapFrom(E e);
}
