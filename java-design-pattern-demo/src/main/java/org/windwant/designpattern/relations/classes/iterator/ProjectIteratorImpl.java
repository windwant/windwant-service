package org.windwant.designpattern.relations.classes.iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aayongche on 2016/9/22.
 */
public class ProjectIteratorImpl implements ProjectIterator {
    private List<ProjectContainer> projectList = new ArrayList<ProjectContainer>();

    private int currentItem = 0;
    public ProjectIteratorImpl(List<ProjectContainer> projectList) {
        this.projectList = projectList;
    }

    public boolean hasNext() {
        if(this.currentItem >= projectList.size() || this.projectList.get(currentItem) == null){
            return false;
        }
        return true;
    }

    public ProjectContainer next() {
        return this.projectList.get(currentItem++);
    }
}
