package fit.lunevale.server.service;

import fit.lunevale.server.data.dao.EngineerRepositoryCrud;
import fit.lunevale.server.data.domain.Engineer;
import fit.lunevale.server.data.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class EngineerServiceImpl extends CrudServiceImpl<Engineer, Long> implements EngineerService {
    protected EngineerRepositoryCrud engineerRepository;
    private final PasswordEncoder passwordEncoder;

    public EngineerServiceImpl(EngineerRepositoryCrud engineerRepository, PasswordEncoder passwordEncoder) {
        this.engineerRepository = engineerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected JpaRepository<Engineer, Long> getRepository() {
        return engineerRepository;
    }

    @Override
    @Transactional
    public Engineer create (Engineer engineer){
        engineer.setPassword(passwordEncoder.encode(engineer.getPassword()));
        engineer.setRole(Role.User);
        var createdEngineer = getRepository().save(engineer);
        entityManager.flush();
        entityManager.refresh(createdEngineer);
        return createdEngineer;
    }

    @Override
    @Transactional
    public Engineer update(Long id, Engineer newEngineer) {
        var engineer = getRepository().findById(id).orElseThrow(IllegalArgumentException::new);
        copyNonNullProperties(newEngineer, engineer);
        engineer.setPassword(passwordEncoder.encode(newEngineer.getPassword()));
        var updatedEntity = getRepository().save(engineer);
        entityManager.flush();
        entityManager.refresh(updatedEntity);
        return updatedEntity;
    }

    @Override
    public Engineer findByEmail(String email) {
        return engineerRepository.findByEmail(email).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<Engineer> readAllEngineersByProjectId(Long projectId){
        return engineerRepository.findAllEngineerByProjectId(projectId);
    }
}
