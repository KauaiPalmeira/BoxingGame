package main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


class EfeitosSonoros {
    private static final Logger logger = Logger.getLogger(EfeitosSonoros.class.getName());
    private static final String CAMINHO_ARQUIVO = "C:\\Users\\kauai\\OneDrive\\Área de Trabalho\\AtariBoxingGame\\src\\main\\MusicaDeFundo.wav";


    // carega o audio e toca em loop
    void tocarMusicaDeFundo() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(CAMINHO_ARQUIVO));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            logger.log(Level.SEVERE, "Erro ao tocar musica", e);
        }
    }
}
