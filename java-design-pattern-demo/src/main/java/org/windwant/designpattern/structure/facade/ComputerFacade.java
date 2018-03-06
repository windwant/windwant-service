package org.windwant.designpattern.structure.facade;

/**
 * Created by aayongche on 2016/9/21.
 */
public class ComputerFacade {

    private CPU cpu;

    private Memory memory;

    private Power power;

    public ComputerFacade(){
        cpu = new CPU();
        memory = new Memory();
        power = new Power();
    }

    public void startup(){
        power.startup();
        memory.startup();
        cpu.startup();
    }

    public void run(){
        System.out.println("-");
        System.out.println("---");
        System.out.println("-----");
        System.out.println("-------");
        System.out.println("---------");
        System.out.println("-----------");
        System.out.println("-------------");
        System.out.println("---------------");
        System.out.println("computer run...");
        System.out.println("---------------");
        System.out.println("-------------");
        System.out.println("-----------");
        System.out.println("---------");
        System.out.println("-------");
        System.out.println("-----");
        System.out.println("---");
        System.out.println("-");
    }

    public void shutdown(){
        cpu.shutdown();
        memory.shutdown();
        power.shutdown();


    }
}
