package pl.edu.agh.view;

import javafx.scene.paint.Color;

import static java.lang.Math.min;

public class IntToColorMapper {
    public IntToColorMapper() {
    }
    public Color mapPercentToColor(float percent)
    {
        if(percent>1) return Color.GREEN;
        return Color.color(min(2.0f * (1 - percent),1),min(2.0f * percent,1),0);
    }


}
