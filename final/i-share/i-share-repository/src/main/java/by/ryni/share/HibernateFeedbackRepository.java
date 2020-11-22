package by.ryni.share;

import by.ryni.share.entity.Feedback;
import by.ryni.share.repository.FeedbackRepository;
import org.springframework.stereotype.Repository;

@Repository("feedbackRepository")
public class HibernateFeedbackRepository extends AbstractHibernateRepository<Feedback> implements FeedbackRepository {
    public HibernateFeedbackRepository() {
        super(Feedback.class);
    }
}
