package ling.originalSources;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class WarningSounds extends Thread {
    private File file;
    WarningSounds(File file) {
        this.file = file;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            try {
                play();
                sleep(1000);
                break;
            } catch (FileNotFoundException | JavaLayerException | InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void play() throws FileNotFoundException, JavaLayerException {
        BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(file));
        Player player = new Player(buffer);
        player.play();
    }


}


