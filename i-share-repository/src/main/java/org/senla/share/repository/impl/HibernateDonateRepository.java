package org.senla.share.repository.impl;

import org.senla.share.entity.Donate;
import org.senla.share.repository.DonateRepository;
import org.springframework.stereotype.Repository;

@Repository("donateRepository")
public class HibernateDonateRepository extends AbstractHibernateRepository<Donate> implements DonateRepository {
    public HibernateDonateRepository() {
        super(Donate.class);
    }
}
