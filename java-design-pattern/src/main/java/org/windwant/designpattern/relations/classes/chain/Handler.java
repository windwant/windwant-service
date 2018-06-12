package org.windwant.designpattern.relations.classes.chain;

/**
 * Created by aayongche on 2016/9/22.
 */
public abstract class Handler {

    private int level = 0;

    private Handler nextHandler;

    public Handler(int level) {
        this.level = level;
    }

    public final void handleMessage(Woman woman){
        if(woman.getType() == this.level){
            this.response(woman);
        }else {
            if(this.nextHandler != null){
                this.nextHandler.handleMessage(woman);
            }else{
                System.out.println("no handler...");
            }
        }
    }

    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract void response(Woman woman);
}
