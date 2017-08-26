package com.revature.application.dao.beans.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PostTypeForm {
    
    @NotNull
    @Size(min = 1, max = 32)
    private String type;
    
    public PostTypeForm() {
    }
    
    public PostTypeForm(String type) {
        super();
        this.type = type;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
        return "PostTypeForm [type=" + type + "]";
    }
    
}
