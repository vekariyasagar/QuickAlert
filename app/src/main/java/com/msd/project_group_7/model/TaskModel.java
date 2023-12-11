package com.msd.project_group_7.model;

import java.io.Serializable;

public class TaskModel implements Serializable {

    int taskId;
    String taskName, taskCategory, taskType, taskDate, taskTime, taskAddress;
    Long taskMilliseconds;
    double taskLatitude, taskLongitude;
    int taskCompleted;

    public String getTaskAddress() {
        return taskAddress;
    }

    public void setTaskAddress(String taskAddress) {
        this.taskAddress = taskAddress;
    }

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

    public double getTaskLatitude() {
        return taskLatitude;
    }

    public void setTaskLatitude(double taskLatitude) {
        this.taskLatitude = taskLatitude;
    }

    public double getTaskLongitude() {
        return taskLongitude;
    }

    public void setTaskLongitude(double taskLongitude) {
        this.taskLongitude = taskLongitude;
    }

    public int isTaskCompleted() {
        return taskCompleted;
    }

    public void setTaskCompleted(int taskCompleted) {
        this.taskCompleted = taskCompleted;
    }

    @Override
    public String toString() {
        return "TaskModel{" +
                "taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
                ", taskCategory='" + taskCategory + '\'' +
                ", taskType='" + taskType + '\'' +
                ", taskDate='" + taskDate + '\'' +
                ", taskTime='" + taskTime + '\'' +
                ", taskAddress='" + taskAddress + '\'' +
                ", taskMilliseconds=" + taskMilliseconds +
                ", taskLatitude=" + taskLatitude +
                ", taskLongitude=" + taskLongitude +
                ", taskCompleted=" + taskCompleted +
                '}';
    }
}
