package final_project;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImageManager {
    private int N;
    private ImageIcon[] imageIconList;
    private List<Integer> imageIdxList;
    private boolean[] isFlipped;

    public ImageManager(int N) {
        this.N = N;
        this.imageIconList = new ImageIcon[N * N / 2];
        this.isFlipped = new boolean[N * N];
    }

    void setGameImages(String type, int totalCards) {
        int width = 800 / N;
        int height = 800 / N;
        if (type.equals("iu")) {
            for (int i = 0; i < totalCards / 2; i++) {
                Image image = new ImageIcon("src/final_project/images/dlwlrma" + (i + 1) + ".jpg").getImage();

                /*
                reference
                https://eating-coding.tistory.com/5
                 */
                imageIconList[i] = new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_SMOOTH));

            }
        }
        if (type.equals("animal")) {
            for (int i = 0; i < totalCards / 2; i++) {
                Image image = new ImageIcon("src/final_project/images/animal" + (i + 1) + ".jpg").getImage();

                /*
                reference
                https://eating-coding.tistory.com/5
                 */
                imageIconList[i] = new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_SMOOTH));

            }
        }
    }

    void shuffleImages() {
        imageIdxList = new ArrayList<>();
        for (int i = 0; i < (N * N) / 2; i++) {
            // add img pairs
            imageIdxList.add(i);
            imageIdxList.add(i);
        }

        /*
        reference
        https://www.techiedelight.com/ko/shuffle-randomize-list-java/
         */
        Collections.shuffle(imageIdxList);

        for (int i = 0; i < N * N; i++) {
            isFlipped[i] = false;
        }
    }

    public boolean[] getIsFlipped() {
        return isFlipped;
    }

    public List<Integer> getImageIdxList() {
        return imageIdxList;
    }

    public Icon[] getImageIconList() {
        return imageIconList;
    }
}
