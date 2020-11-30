package by.ryni.share;

import by.ryni.share.entity.Donate;
import by.ryni.share.api.DonateRepository;
import org.springframework.stereotype.Repository;

@Repository("donateRepository")
public class HibernateDonateRepository extends AbstractHibernateRepository<Donate> implements DonateRepository {
    public HibernateDonateRepository() {
        super(Donate.class);
    }
}
