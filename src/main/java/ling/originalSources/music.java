package ling.originalSources;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class music extends Thread {
    private static final Lock lock = new ReentrantLock();
    private static Condition con = lock.newCondition();
    private static MainPanel mainPanel = new MainPanel();
    private File music;


    music(File file) {
        this.music = file;
    }


    @Override
    public void run() {
        lock.lock();
        super.run();
        while (MainPanel.exists) {
            try {
                play();
            } catch (FileNotFoundException | JavaLayerException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                lock.unlock();

            }
        }
    }


    private void play() throws FileNotFoundException, JavaLayerException {

        BufferedInputStream buffer =
                new BufferedInputStream(new FileInputStream(music));
        Player player = new Player(buffer);
        player.play();
    }


}


