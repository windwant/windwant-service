package org.windwant.designpattern.relations.classes.iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by windwant on 2016/9/22.
 */
public class MyProjectContainer implements ProjectContainer {
    private List<ProjectContainer> projectList = new ArrayList<ProjectContainer>();

    private String name;

    private int num;

    private int cost;

    public MyProjectContainer() {
    }

    public MyProjectContainer(String name, int num, int cost) {
        this.name = name;
        this.num = num;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void add(String name, int num, int cost) {
        this.projectList.add(new MyProjectContainer(name, num, cost));
    }

    public String getProjectInfo() {
        StringBuilder info = new StringBuilder();
        info.append("project name: " + this.name);
        info.append("\t project workers: " + this.num);
        info.append("\t project cost: " + this.cost);
        return info.toString();
    }

    public ProjectIterator iterator() {
        return new ProjectIteratorImpl(this.projectList);
    }
}
