package promisor.promisor.domain.BanDate.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.BanDate.domain.PersonalBanDate;

@Transactional(readOnly = true)
public interface PersonalBanDateRepository extends JpaRepository<PersonalBanDate, Long> {

}
