package org.senla.share.repository.impl;

import org.senla.share.repository.ThemeRepository;
import org.senla.share.entity.Theme;
import org.springframework.stereotype.Repository;

@Repository("themeRepository")
public class HibernateThemeRepository extends AbstractHibernateRepository<Theme> implements ThemeRepository {
    public HibernateThemeRepository() {
        super(Theme.class);
    }
}
