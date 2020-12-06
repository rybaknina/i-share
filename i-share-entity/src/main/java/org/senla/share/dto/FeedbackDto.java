package org.senla.share.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class FeedbackDto extends AbstractDto {
    @NotEmpty
    private String text;
    @JsonProperty("lesson")
    private LessonDto lesson;
    @JsonProperty("course")
    private CourseDto course;
    @JsonIgnore
    private UserDto user;

    public FeedbackDto() {
    }

    @JsonCreator
    public FeedbackDto(int id) {
        super.setId(id);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @JsonIgnore
    public LessonDto getLesson() {
        return lesson;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public void setLesson(LessonDto lesson) {
        this.lesson = lesson;
    }

    @JsonIgnore
    public CourseDto getCourse() {
        return course;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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
