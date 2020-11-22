package by.ryni.share;

import by.ryni.share.entity.Theme;
import by.ryni.share.repository.ThemeRepository;
import org.springframework.stereotype.Repository;

@Repository("themeRepository")
public class HibernateThemeRepository extends AbstractHibernateRepository<Theme> implements ThemeRepository {
    public HibernateThemeRepository() {
        super(Theme.class);
    }
}
