package by.ryni.share.dto;

import java.sql.Timestamp;

public class FeedbackDto extends AbstractDto {
    private String text;
    private Timestamp postedDate;
    private LessonDto lesson;
    private CourseDto course;
    private UserDto user;

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

    public LessonDto getLesson() {
        return lesson;
    }

    public void setLesson(LessonDto lesson) {
        this.lesson = lesson;
    }

    public CourseDto getCourse() {
        return course;
    }

    public void setCourse(CourseDto course) {
        this.course = course;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
