package org.senla.share.repository.impl;

import org.senla.share.entity.Feedback;
import org.senla.share.repository.FeedbackRepository;
import org.springframework.stereotype.Repository;

@Repository("feedbackRepository")
public class HibernateFeedbackRepository extends AbstractHibernateRepository<Feedback> implements FeedbackRepository {
    public HibernateFeedbackRepository() {
        super(Feedback.class);
    }
}
