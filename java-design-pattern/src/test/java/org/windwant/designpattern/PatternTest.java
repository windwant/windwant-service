package org.windwant.designpattern;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.windwant.designpattern.creation.factory.Sender;
import org.windwant.designpattern.creation.factory.factory.Factory;
import org.windwant.designpattern.creation.factory.factory.MailSenderFactory;
import org.windwant.designpattern.creation.factory.factory.SMSSenderFactory;
import org.windwant.designpattern.creation.factory.virtualfactory.MailVirtualFactory;
import org.windwant.designpattern.creation.factory.virtualfactory.SMSVirtualFactory;
import org.windwant.designpattern.creation.factory.virtualfactory.VirtualFactory;
import org.windwant.designpattern.creation.prototype.ProtoTypePattern;
import org.windwant.designpattern.creation.singleton.SingletonWithInnerClass;
import org.windwant.designpattern.creation.singleton.SingletonWithSynchronized;
import org.windwant.designpattern.relations.classes.chain.Father;
import org.windwant.designpattern.relations.classes.chain.Handler;
import org.windwant.designpattern.relations.classes.chain.Husband;
import org.windwant.designpattern.relations.classes.chain.RealWoman;
import org.windwant.designpattern.relations.classes.chain.Son;
import org.windwant.designpattern.relations.classes.chain.Woman;
import org.windwant.designpattern.relations.classes.command.AddRequirementCommand;
import org.windwant.designpattern.relations.classes.command.DeletePageCommand;
import org.windwant.designpattern.relations.classes.command.Executor;
import org.windwant.designpattern.relations.classes.iterator.MyProjectContainer;
import org.windwant.designpattern.relations.classes.iterator.ProjectContainer;
import org.windwant.designpattern.relations.classes.iterator.ProjectIterator;
import org.windwant.designpattern.relations.classes.observer.TheMan;
import org.windwant.designpattern.relations.classes.observer.TheFirstWatcher;
import org.windwant.designpattern.relations.classes.observer.TheSecondWatcher;
import org.windwant.designpattern.relations.classes.observer.TheWoman;
import org.windwant.designpattern.relations.classstate.memento.Origin;
import org.windwant.designpattern.relations.classstate.memento.Storage;
import org.windwant.designpattern.relations.classstate.mediator.AbstractMediator;
import org.windwant.designpattern.relations.classstate.mediator.Mediator;
import org.windwant.designpattern.relations.classstate.mediator.Purchase;
import org.windwant.designpattern.relations.classstate.mediator.Sale;
import org.windwant.designpattern.relations.classstate.mediator.Stock;
import org.windwant.designpattern.relations.classstate.visitor.ELENodeA;
import org.windwant.designpattern.relations.classstate.visitor.ELENodeB;
import org.windwant.designpattern.relations.classstate.visitor.NodeContainer;
import org.windwant.designpattern.relations.classstate.visitor.VisitorMan;
import org.windwant.designpattern.relations.classstate.visitor.VisitorWoman;
import org.windwant.designpattern.relations.parentwithson.strategy.BlockEnemyStrategy;
import org.windwant.designpattern.relations.parentwithson.strategy.Context;
import org.windwant.designpattern.relations.parentwithson.strategy.FindFriendStrategy;
import org.windwant.designpattern.relations.parentwithson.strategy.FindWayStrategy;
import org.windwant.designpattern.relations.parentwithson.template.CarModel;
import org.windwant.designpattern.relations.parentwithson.template.LittleCar;
import org.windwant.designpattern.relations.parentwithson.template.SUVCar;
import org.windwant.designpattern.structure.adapter.ChinaVoltage;
import org.windwant.designpattern.structure.adapter.classadapter.ChinaVoltageClassAdapter;
import org.windwant.designpattern.structure.adapter.HongkongVoltage;
import org.windwant.designpattern.structure.adapter.PowerVoltage;
import org.windwant.designpattern.structure.adapter.interfaceadapter.ManyMethodInterface;
import org.windwant.designpattern.structure.adapter.interfaceadapter.MyMethod;
import org.windwant.designpattern.structure.adapter.objectadapter.ChinaVoltageObjectAdapter;
import org.windwant.designpattern.structure.bridge.BridgeRemoterOfSunsung;
import org.windwant.designpattern.structure.bridge.SonyTV;
import org.windwant.designpattern.structure.bridge.SunsungTV;
import org.windwant.designpattern.structure.composite.Composite;
import org.windwant.designpattern.structure.composite.Leaf;
import org.windwant.designpattern.structure.decorator.Component;
import org.windwant.designpattern.structure.decorator.DecoratedIconTextComponent;
import org.windwant.designpattern.structure.decorator.TextComponent;
import org.windwant.designpattern.structure.facade.ComputerFacade;
import org.windwant.designpattern.structure.proxy.MovieStar;
import org.windwant.designpattern.structure.proxy.MovieStarProxy;
import org.windwant.designpattern.structure.proxy.Star;
import org.windwant.designpattern.structure.proxy.TVStar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Unit test for simple App.
 */
public class PatternTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PatternTest(String testName)
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( PatternTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testSingleton() {
        SingletonWithInnerClass.getInstance().test();
        SingletonWithSynchronized.getInstance().test();
    }

    public void testProtoType() throws IOException, ClassNotFoundException, CloneNotSupportedException {
        ProtoTypePattern protoTypePattern = new ProtoTypePattern();
        protoTypePattern.getList().add("NJL");
        System.out.println("origin: " + protoTypePattern.getList());

        ProtoTypePattern protoTypePatternSimple = (ProtoTypePattern) protoTypePattern.clone();
        protoTypePatternSimple.getList().add("NIE");
        System.out.println("origin: " + protoTypePattern.getList());
        System.out.println("simple: " + protoTypePatternSimple.getList());

        ProtoTypePattern protoTypePatternCopy = (ProtoTypePattern) protoTypePattern.deepClone();
        protoTypePatternCopy.getList().add("WINDWANT");
        System.out.println("origin: " + protoTypePattern.getList());
        System.out.println("copy: " + protoTypePatternCopy.getList());
    }

    public void testFactory(){
        Factory mailFactory = new MailSenderFactory();
        Sender mailSender = mailFactory.produce();
        mailSender.send();
        Factory smsFactory = new SMSSenderFactory();
        Sender smsSender = smsFactory.produce();
        smsSender.send();
    }

    public void testVirtualFactory(){
        VirtualFactory mail = new MailVirtualFactory();
        mail.produceSender().send();
        mail.produceReceiver().receive();
        VirtualFactory sms = new SMSVirtualFactory();
        sms.produceSender().send();
        sms.produceReceiver().receive();
    }

    public void testBuilder(){
//        WarRobustBuilder myRobustBuilder = new WarRobustBuilder();
//        RobustDirector robustDirector = new RobustDirector(myRobustBuilder);
//        Robust robust = robustDirector.buildRobust("robust-head", "robust-body");
//        System.out.println(robust);
    }

    public void testClassAdapter(){
        PowerVoltage standard = new HongkongVoltage();
        standard.givePower();
        PowerVoltage cus = new ChinaVoltageClassAdapter();
        cus.givePower();
    }

    public void testObjectAdapter(){
        PowerVoltage standard = new HongkongVoltage();
        standard.givePower();
        PowerVoltage cus = new ChinaVoltageObjectAdapter(new ChinaVoltage());
        cus.givePower();
    }

    public void testInterfaceAdapter(){
        ManyMethodInterface manyMethodInterface = new MyMethod();
        manyMethodInterface.methodOne();
        manyMethodInterface.methodTwo();
    }

    public void testDecorate(){
        Component tcom = new TextComponent();
        tcom.operate();
        Component itcom = new DecoratedIconTextComponent(tcom);
        itcom.operate();
    }

    public void testProxy(){
        MovieStar movieStar = new MovieStar();
        movieStar.movieShow(10000);
        movieStar.tvShow(20000);
        Star movieStarProxy = new MovieStarProxy(movieStar);
        movieStarProxy.movieShow(10000);
        movieStarProxy.movieShow(20000);
        movieStarProxy.tvShow(10000);
        movieStarProxy.tvShow(20000);

        TVStar tvStar = new TVStar();
        tvStar.movieShow(10000);
        tvStar.tvShow(10000);
        Star tvStarProxy = new MovieStarProxy(tvStar);
        tvStarProxy.movieShow(10000);
        tvStarProxy.movieShow(20000);
        tvStarProxy.tvShow(10000);
        tvStarProxy.tvShow(20000);
    }

    public void testFacade(){
        ComputerFacade computerFacade = new ComputerFacade();
        computerFacade.startup();
        computerFacade.run();
        computerFacade.shutdown();
    }

    public void testBridge(){
        BridgeRemoterOfSunsung sunsung = new BridgeRemoterOfSunsung(new SunsungTV());
        sunsung.turnOn();
        sunsung.turnOff();
        sunsung.switchChannel(10);

        BridgeRemoterOfSunsung sony = new BridgeRemoterOfSunsung(new SonyTV());
        sony.turnOn();
        sony.turnOff();
        sony.switchChannel(10);
    }

    public void testComposite(){
        Composite root = new Composite("root");
        root.add(new Leaf("leafA"));
        root.add(new Leaf("leafB"));
        Composite subrootA = new Composite("subrootA");
        subrootA.add(new Leaf("subleafA"));
        subrootA.add(new Leaf("subleafB"));
        Composite subrootB = new Composite("subrootB");
        subrootB.add(new Leaf("subleafC"));
        subrootB.add(new Leaf("subleafD"));
        subrootB.add(new Leaf("subleafE"));
        root.add(subrootA);
        root.add(subrootB);
        root.display(0);
    }

    public void testStrategy(){
        new Context(new FindFriendStrategy()).operate();
        new Context(new FindWayStrategy()).operate();
        new Context(new BlockEnemyStrategy()).operate();
    }

    public void testMediator(){
        AbstractMediator mediator = new Mediator();
        Purchase purchase = new Purchase(mediator);
        purchase.buyComputer(100);

        Sale sale = new Sale(mediator);
        sale.sellComputer(1);

        Stock stock = new Stock(mediator);
        stock.clearStock();
    }

    public void testTemplate(){
        CarModel little = new LittleCar();
        little.setAlarmFlag(false);
        little.run();

        CarModel suv = new SUVCar();
        suv.setAlarmFlag(true);
        suv.run();
    }

    public void testObserver(){
        TheFirstWatcher theFirstWatcher = new TheFirstWatcher();
        TheSecondWatcher theSecondWatcher = new TheSecondWatcher();
        TheMan theMan = new TheMan("the man");
        theMan.addObserver(theFirstWatcher);
        theMan.addObserver(theSecondWatcher);
        theMan.haveFood();
        theMan.haveFun();
        TheWoman theWoman = new TheWoman("the woman");
        theWoman.addObserver(theFirstWatcher);
        theWoman.addObserver(theSecondWatcher);
        theWoman.haveFood();
        theWoman.haveFun();

    }

    public void testIterator(){
        ProjectContainer project = new MyProjectContainer();

        for(int i = 0; i < 100; i++){
            project.add("the " + i + " project", i*5, i*1000);
        }

        ProjectIterator projectIterator = project.iterator();
        while (projectIterator.hasNext()){
            System.out.println(((ProjectContainer)projectIterator.next()).getProjectInfo());
        }
    }

    public void testChain(){
        List<Woman> arrayList = new ArrayList();
        for(int i = 0; i < 5; i++){
            arrayList.add(new RealWoman(ThreadLocalRandom.current().nextInt(3) + 1,"i need wondering"));
        }
        Handler father = new Father();
        Handler husband = new Husband();
        Handler son = new Son();
        father.setNextHandler(husband);
        husband.setNextHandler(son);
        for (Woman woman: arrayList){
            father.handleMessage(woman);
        }
    }

    public void testCommand(){
        Executor executor = new Executor();
        executor.setCommand(new AddRequirementCommand());
        executor.action();
        executor.setCommand(new DeletePageCommand());
        executor.action();
    }

    public void testMemento(){
        Origin origin = new Origin();
        Storage storage = new Storage();
        storage.saveMemento(origin.setState("state1"));
        System.out.println(origin.toString());
        storage.saveMemento(origin.setState("state2"));
        System.out.println(origin.toString());
        storage.saveMemento(origin.setState("state3"));
        System.out.println(origin.toString());

        origin.reverseMemento(storage.getSavePoint(0));
        System.out.println(origin.toString());
    }

    public void testVisitor(){
        NodeContainer nodeContainer = new NodeContainer();
        nodeContainer.addNode(new ELENodeA());
        nodeContainer.addNode(new ELENodeB());
        nodeContainer.execute(new VisitorMan());
        nodeContainer.execute(new VisitorWoman());
    }
}
