package by.ryni.share;

import by.ryni.share.dto.chapter.ChapterDto;
import by.ryni.share.dto.chapter.ChapterShortDto;
import by.ryni.share.entity.Chapter;
import by.ryni.share.mapper.ChapterMapper;
import by.ryni.share.repository.ChapterRepository;
import by.ryni.share.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("chapterService")
public class DefaultChapterService extends AbstractService<ChapterDto, ChapterShortDto, Chapter, ChapterRepository> implements ChapterService {
//    private UserService userService;
//    private UserMapper userMapper;
//    private ChapterRepository chapterRepository;
//    private ChapterMapper chapterMapper;

    @Autowired
    public DefaultChapterService(@Qualifier("chapterRepository") ChapterRepository repository, ChapterMapper mapper) {
        super(repository, mapper);
    }

//    @Autowired
//    public void setUserService(UserService userService) {
//        this.userService = userService;
//    }
//
//    @Autowired
//    public void setUserMapper(UserMapper userMapper) {
//        this.userMapper = userMapper;
//    }
//
//    @Autowired
//    public void setChapterRepository(ChapterRepository chapterRepository) {
//        this.chapterRepository = chapterRepository;
//    }
//
//    @Autowired
//    public void setChapterMapper(ChapterMapper chapterMapper) {
//        this.chapterMapper = chapterMapper;
//    }

//    public void save(ChapterDto chapterDto, Principal principal) throws ServiceException {
//        Optional<User> user = userService.findByUsername(principal.getName());
//        if (!user.isPresent()) {
//            throw new ServiceException("User not found");
//        }
//        chapterDto.setOwner(userMapper.entityToShortDto(user.get()));
//        try {
//            chapterRepository.save(chapterMapper.dtoToEntity(chapterDto));
//        } catch (RepositoryException e) {
//            throw new ServiceException("Service Exception " + e.getMessage());
//        }
//    }
}
