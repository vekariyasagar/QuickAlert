package com.msd.project_group_7.model;

import java.io.Serializable;

public class TaskModel implements Serializable {

    int taskId;
    String taskName, taskCategory, taskType, taskDate, taskTime;
    Long taskMilliseconds, taskLatitude, taskLongitude;
    boolean taskCompleted;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskCategory() {
        return taskCategory;
    }

    public void setTaskCategory(String taskCategory) {
        this.taskCategory = taskCategory;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public Long getTaskMilliseconds() {
        return taskMilliseconds;
    }

    public void setTaskMilliseconds(Long taskMilliseconds) {
        this.taskMilliseconds = taskMilliseconds;
    }

    public Long getTaskLatitude() {
        return taskLatitude;
    }

    public void setTaskLatitude(Long taskLatitude) {
        this.taskLatitude = taskLatitude;
    }

    public Long getTaskLongitude() {
        return taskLongitude;
    }

    public void setTaskLongitude(Long taskLongitude) {
        this.taskLongitude = taskLongitude;
    }

    public boolean isTaskCompleted() {
        return taskCompleted;
    }

    public void setTaskCompleted(boolean taskCompleted) {
        this.taskCompleted = taskCompleted;
    }
}