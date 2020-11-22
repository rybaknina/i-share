package by.ryni.share;

import by.ryni.share.dto.theme.ThemeDto;
import by.ryni.share.dto.theme.ThemeShortDto;
import by.ryni.share.entity.Theme;
import by.ryni.share.mapper.ThemeMapper;
import by.ryni.share.repository.ThemeRepository;
import by.ryni.share.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("themeService")
public class DefaultThemeService extends AbstractService<ThemeDto, ThemeShortDto, Theme, ThemeRepository> implements ThemeService {
    @Autowired
    public DefaultThemeService(@Qualifier("themeRepository") ThemeRepository repository, ThemeMapper mapper) {
        super(repository, mapper);
    }
}
