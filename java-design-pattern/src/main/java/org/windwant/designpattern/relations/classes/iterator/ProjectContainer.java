package org.windwant.designpattern.relations.classes.iterator;

/**
 * Created by aayongche on 2016/9/22.
 */
public interface ProjectContainer {
    void add(String name, int num, int cost);

    String getProjectInfo();

    ProjectIterator iterator();
}
