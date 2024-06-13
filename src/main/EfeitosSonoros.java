package main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

class EfeitosSonoros {
    private static final Logger logger = Logger.getLogger(EfeitosSonoros.class.getName());
    private static final String CAMINHO_MUSICA_DE_FUNDO = "src/main/sounds/MusicaDeFundo.wav";
    private static final String CAMINHO_SOM_DO_HIT = "src/main/sounds/SomDoHit.wav";
    private static final String CAMINHO_MACHUCADO_HIT = "src/main/sounds/MachucadoHit.wav";
    private static final String CAMINHO_TEMPO_ACABOU = "src/main/sounds/TempoAcabou.wav";

    // Carrega o áudio e toca em loop
    void tocarMusicaDeFundo() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(CAMINHO_MUSICA_DE_FUNDO));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            logger.log(Level.SEVERE, "Erro ao tocar música de fundo!", e);
        }
    }

    // Toca o som do hit
    void tocarSomDoHit() {
        tocarSom(CAMINHO_SOM_DO_HIT);
    }

    // Toca o som do machucado hit
    void tocarMachucadoHit() {
        tocarSom(CAMINHO_MACHUCADO_HIT);
    }

    // Toca o som de tempo acabou
    void tocarTempoAcabou() {
        tocarSom(CAMINHO_TEMPO_ACABOU);
    }

    // Método genérico para tocar um som
    private void tocarSom(String caminhoArquivo) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(caminhoArquivo));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            logger.log(Level.SEVERE, "Erro ao tocar som: " + caminhoArquivo, e);
        }
    }
}
