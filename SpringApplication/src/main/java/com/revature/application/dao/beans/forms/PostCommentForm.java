package com.revature.application.dao.beans.forms;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PostCommentForm {
    
    @NotNull
    @Min(0)
    private Long employeeId;
    
    @NotNull
    @Min(0)
    private Long postId;
    
    @NotNull
    @Size(min = 1, max = 250)
    private String content;
    
    public PostCommentForm() {
    }
    
    public PostCommentForm(long employeeId, long postId, String content) {
        super();
        this.employeeId = employeeId;
        this.postId = postId;
        this.content = content;
    }
    
    public long getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }
    
    public long getPostId() {
        return postId;
    }
    
    public void setPostId(long postId) {
        this.postId = postId;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    @Override
    public String toString() {
        return "PostCommentForm [employeeId=" + employeeId + ", postId=" + postId + ", content="
                + content + "]";
    }
    
}
