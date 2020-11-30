package by.ryni.share;

import by.ryni.share.api.ThemeRepository;
import by.ryni.share.entity.Theme;
import org.springframework.stereotype.Repository;

@Repository("themeRepository")
public class HibernateThemeRepository extends AbstractHibernateRepository<Theme> implements ThemeRepository {
    public HibernateThemeRepository() {
        super(Theme.class);
    }
}
