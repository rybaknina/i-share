package by.ryni.share;

import by.ryni.share.entity.Chapter;
import by.ryni.share.repository.ChapterRepository;
import org.springframework.stereotype.Repository;

@Repository("chapterRepository")
public class HibernateChapterRepository extends AbstractHibernateRepository<Chapter> implements ChapterRepository {
    public HibernateChapterRepository() {
        super(Chapter.class);
    }
}
