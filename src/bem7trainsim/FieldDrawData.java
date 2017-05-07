package bem7trainsim;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static java.awt.image.BufferedImage.TYPE_CUSTOM;

/**
 * Created by Kristóf on 2017. 05. 07..
 */
public class FieldDrawData {

    private List<String> layers;

    public FieldDrawData(List<String> l) {
        layers = l;
    }

    public String getLayer(int i) {
        return layers.get(i);
    }

    public int getNumberOfLayers() {
        return layers.size();
    }

    public void draw(Graphics g, int x, int y, int width, int height) {
        for (String layer :
                layers) {
            g.drawImage(getImage(layer), x * width, y * height, width, height, null);
        }
    }

    static HashMap<String, BufferedImage> images;

    static {
        try {
            String path = "Piskels/";
            images = new HashMap<>();
            images.put("straightHorizontal",ImageIO.read(new File(path + "/straightHorizontal.png")));
            images.put("straightVertical",ImageIO.read(new File(path + "/straightVertical.png")));
            images.put("curveNE",ImageIO.read(new File(path + "/curveNE.png")));
            images.put("curveNW",ImageIO.read(new File(path + "/curveNW.png")));
            images.put("curveSE",ImageIO.read(new File(path + "/curveSE.png")));
            images.put("curveSW",ImageIO.read(new File(path + "/curveSW.png")));
            images.put("cross",ImageIO.read(new File(path + "/cross.png")));
            images.put("grass1",ImageIO.read(new File(path + "/grass1.png")));
            images.put("grass2",ImageIO.read(new File(path + "/grass2.png")));
            images.put("downStationHorizontalBlue",ImageIO.read(new File(path + "/downStationHorizontalBlue.png")));
            images.put("downStationHorizontalGreen",ImageIO.read(new File(path + "/downStationHorizontalGreen.png")));
            images.put("downStationHorizontalRed",ImageIO.read(new File(path + "/downStationHorizontalRed.png")));
            images.put("downStationHorizontalYellow",ImageIO.read(new File(path + "/downStationHorizontalYellow.png")));
            images.put("downStationVerticallBlue",ImageIO.read(new File(path + "/downStationVerticallBlue.png")));
            images.put("downStationVerticalGreen",ImageIO.read(new File(path + "/downStationVerticalGreen.png")));
            images.put("downStationVerticalRed",ImageIO.read(new File(path + "/downStationVerticalRed.png")));
            images.put("downStationVerticalYellow",ImageIO.read(new File(path + "/downStationVerticalYellow.png")));
            images.put("upStationHorizontalBlue",ImageIO.read(new File(path + "/upStationHorizontalBlue.png")));
            images.put("upStationHorizontalGreen",ImageIO.read(new File(path + "/upStationHorizontalGreen.png")));
            images.put("upStationHorizontalRed",ImageIO.read(new File(path + "/upStationHorizontalRed.png")));
            images.put("upStationHorizontalYellow",ImageIO.read(new File(path + "/upStationHorizontalYellow.png")));
            images.put("upStationVerticallBlue",ImageIO.read(new File(path + "/upStationVerticallBlue.png")));
            images.put("upStationVerticalGreen",ImageIO.read(new File(path + "/upStationVerticalGreen.png")));
            images.put("upStationVerticalRed",ImageIO.read(new File(path + "/upStationVerticalRed.png")));
            images.put("upStationVerticalYellow",ImageIO.read(new File(path + "/upStationVerticalYellow.png")));
            images.put("switchNorthRightCurve",ImageIO.read(new File(path + "/switchNorthRightCurve.png")));
            images.put("switchNorthRightStraight",ImageIO.read(new File(path + "/switchNorthRightStraight.png")));
            images.put("switchNorthLeftCurve",ImageIO.read(new File(path + "/switchNorthLeftCurve.png")));
            images.put("switchNorthLeftStraight",ImageIO.read(new File(path + "/switchNorthLeftStraight.png")));
            images.put("switchSouthRightCurve",ImageIO.read(new File(path + "/switchSouthRightCurve.png")));
            images.put("switchSouthRightStraight",ImageIO.read(new File(path + "/switchSouthRightStraight.png")));
            images.put("switchSouthLeftCurve",ImageIO.read(new File(path + "/switchSouthLeftCurve.png")));
            images.put("switchSouthLeftStraight",ImageIO.read(new File(path + "/switchSouthLeftStraight.png")));
            images.put("switchWestRightCurve",ImageIO.read(new File(path + "/switchWestRightCurve.png")));
            images.put("switchWestRightStraight",ImageIO.read(new File(path + "/switchWestRightStraight.png")));
            images.put("switchWestLeftCurve",ImageIO.read(new File(path + "/switchWestLeftCurve.png")));
            images.put("switchWestLeftStraight",ImageIO.read(new File(path + "/switchWestLeftStraight.png")));
            images.put("switchEastRightCurve",ImageIO.read(new File(path + "/switchEastRightCurve.png")));
            images.put("switchEastRightStraight",ImageIO.read(new File(path + "/switchEastRightStraight.png")));
            images.put("switchEastLeftCurve",ImageIO.read(new File(path + "/switchEastLeftCurve.png")));
            images.put("switchEastLeftStraight",ImageIO.read(new File(path + "/switchEastLeftStraight.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static private Image getImage(String layer) {
        if (images.containsKey(layer))
            return images.get(layer);
        return images.get(" ");
    }

}
