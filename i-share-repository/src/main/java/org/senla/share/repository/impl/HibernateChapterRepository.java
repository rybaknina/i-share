package org.senla.share.repository.impl;

import org.senla.share.entity.Chapter;
import org.senla.share.repository.ChapterRepository;
import org.springframework.stereotype.Repository;

@Repository("chapterRepository")
public class HibernateChapterRepository extends AbstractHibernateRepository<Chapter> implements ChapterRepository {
    public HibernateChapterRepository() {
        super(Chapter.class);
    }
}
