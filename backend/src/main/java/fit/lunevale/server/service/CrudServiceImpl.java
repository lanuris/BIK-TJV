package fit.lunevale.server.service;

import fit.lunevale.server.data.domain.EntityWithId;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.lang.reflect.Field;


public abstract class CrudServiceImpl<T extends EntityWithId<ID>, ID> implements CrudService<T, ID> {
    protected abstract JpaRepository<T, ID> getRepository();

    @Autowired
    protected EntityManager entityManager;

    @Override
    @Transactional
    public T create(T e) {
        T createdEntity = getRepository().save(e);
        entityManager.flush();
        entityManager.refresh(createdEntity);
        return createdEntity;
    }

    @Override
    public T readById(ID id) throws IllegalArgumentException {
        return getRepository().findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public Iterable<T> readAll() {
        return getRepository().findAll();
    }

    @Override
    @Transactional
    public T update(ID id, T e) throws IllegalArgumentException {
        var object = getRepository().findById(id).orElseThrow(IllegalArgumentException::new);
        copyNonNullProperties(e, object);
        T updatedEntity = getRepository().save(object);
        entityManager.flush();
        entityManager.refresh(updatedEntity);
        return updatedEntity;
    }

    @Override
    @Transactional
    public T update(T e) throws IllegalArgumentException{
        var object = getRepository().findById(e.getId()).orElseThrow(IllegalArgumentException::new);
        copyNonNullProperties(e, object);
        T updatedEntity = getRepository().save(object);
        entityManager.flush();
        entityManager.refresh(updatedEntity);
        return updatedEntity;
    }

    @Override
    public void deleteById(ID id) {
        getRepository().deleteById(id);
    }

    protected void copyNonNullProperties(T source, T target) {
        Field[] fields = source.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(source);
                if (value != null) {
                    field.set(target, value);
                }
            } catch (IllegalAccessException ex) {
                throw new RuntimeException("Error copying properties", ex);
            }
        }
    }
}
