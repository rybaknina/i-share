package by.ryni.share.dto.feedback;

import by.ryni.share.dto.base.AbstractDto;
import by.ryni.share.dto.course.CourseShortDto;
import by.ryni.share.dto.lesson.LessonShortDto;
import by.ryni.share.dto.user.UserShortDto;

import java.sql.Timestamp;

public class FeedbackDto extends AbstractDto {
    private String text;
    private Timestamp postedDate;
    private LessonShortDto lesson;
    private CourseShortDto course;
    private UserShortDto user;

    public FeedbackDto() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Timestamp postedDate) {
        this.postedDate = postedDate;
    }

    public LessonShortDto getLesson() {
        return lesson;
    }

    public void setLesson(LessonShortDto lesson) {
        this.lesson = lesson;
    }

    public CourseShortDto getCourse() {
        return course;
    }

    public void setCourse(CourseShortDto course) {
        this.course = course;
    }

    public UserShortDto getUser() {
        return user;
    }

    public void setUser(UserShortDto user) {
        this.user = user;
    }
}
