package com.c0220h1_project.model;

import com.c0220h1_project.model.test.Test;
import com.c0220h1_project.model.user.User;
import com.fasterxml.jackson.annotation.JsonIdentityReference;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "Exam")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer examId;
    @NotEmpty
    private String examDate;
    private Double mark;
    @NotEmpty
    private String answer;
    private Integer times;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    @JsonIdentityReference(alwaysAsId = true)
    private User user;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "test_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Test test;

    public Exam() {
    }

    public Exam(Integer examId, String examDate, Double mark, String answer, Integer times, User user, Test test) {
        this.examId = examId;
        this.examDate = examDate;
        this.mark = mark;
        this.answer = answer;
        this.times = times;
        this.test = test;
        this.user = user;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public Double getMark() {
        return mark;
    }

    public void setMark(Double mark) {
        this.mark = mark;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
