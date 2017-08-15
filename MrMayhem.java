package rcode;

import robocode.*;
import java.awt.*;
import java.util.Random;

public class MrMayhem extends AdvancedRobot {
    boolean stopWhenSeeRobot = true;
    Random random = new Random();
    public void run(){
        //cores do robo
        //corpo preto, radar laranja, rajada branca
        setColors(Color.black, Color.black, Color.orange, Color.white, Color.black);
        Random random = new Random();
        while(true){
            setAdjustRadarForGunTurn(false);
            setAhead(random.nextInt(100));
            setTurnGunRight(360);
            setTurnRight(random.nextInt(360));
            //setTurnGunRight(360);
            setAhead(random.nextInt(150));
            setTurnGunRight(180);
            setTurnGunLeft(180);
            setTurnRight(random.nextInt(100));
            //setTurnGunRight(360);
            execute();
        }
    }

    public void onScannedRobot(ScannedRobotEvent e){
        //
        if (stopWhenSeeRobot) {
            smartFire(e.getDistance());
            setAhead(35);
            //pára tudo
            stop();
            //chama o metodo de disparo
            smartFire(e.getDistance());
            //procura por outro robo
            scan();
            //se nao encontrou outro robo, segue se movimentando
            resume();
        } else {
            //se achou outro robo, anda e chama metodo de disparo
            setAhead(50);
            smartFire(e.getDistance());
        }
    }

    public void onHitRobot(HitRobotEvent e){
        //se o inimigo estiver na frente, volta pra tras
        if (e.getBearing()> -90 && e.getBearing()<90){
            setBack(100);
        }//se o inimigo estiver atras, vai pra frente
        else {
            setAhead(100);
        }

        //determina a força do tiro pra nao inutilizar o robo
        /*if (e.getEnergy()>16){
            fire(3);
        } else if (e.getEnergy()>10){
            fire(2);
        } else if (e.getEnergy()>4){
            fire(1);
        } else if (e.getEnergy()>2){
            fire(.5);
        } else if (e.getEnergy()>.4){
            fire(.1);
        }
        ahead(40);*/
    }

    public void onHitWall(HitWallEvent e){
        //se bater em uma parede
        //back(40);
        setTurnRight(random.nextInt(100));
        setAhead(random.nextInt(200));
    }

    public void onHitByBullet(HitByBulletEvent e){
        //se for atingido
        setTurnLeft(random.nextInt(100));
        setAhead(random.nextInt(200));
    }

    public void smartFire(double robotDistance){
        //disparo inteligente
        //de acordo com a distancia, dispara rajada mais forte
        if (robotDistance > 200 || getEnergy() < 15) {
            fire(1);
        } else if (robotDistance > 50) {
            fire(2);
        } else {
            fire(3);
        }
    }
}
