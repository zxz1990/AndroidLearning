package com.example.buildsrc;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class MyTestPlugin implements Plugin<Project> {
    @Override
    public void apply(Project target) {
        System.out.println("MyTestPlugin apply() called with: target = [" + target + "]");
    }
}
