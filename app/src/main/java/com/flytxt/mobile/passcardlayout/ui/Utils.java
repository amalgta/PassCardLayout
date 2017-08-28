package com.flytxt.mobile.passcardlayout.ui;

import android.graphics.Path;

/**
 * Created by amalg on 26-08-2017.
 */

public class Utils {
       public static Path addRoundedRect(Path path,
                                            float left, float top, float right, float bottom,
                                            float radiusX, float radiusY,
                                            boolean topLeft, boolean topRight, boolean bottomRight, boolean bottomLeft) {

        if (radiusX < 0) radiusX = 0;
        if (radiusY < 0) radiusY = 0;
        float width = right - left;
        float height = bottom - top;
        if (radiusX > width / 2) radiusX = width / 2;
        if (radiusY > height / 2) radiusY = height / 2;
        float widthMinusCorners = (width - (2 * radiusX));
        float heightMinusCorners = (height - (2 * radiusY));

        path.moveTo(right, top + radiusY);


        if (topRight)
            //top-right corner
            path.rQuadTo(0, -radiusY, -radiusX, -radiusY);
        else {
            path.rLineTo(0, -radiusY);
            path.rLineTo(-radiusX, 0);
        }

        path.rLineTo(-widthMinusCorners, 0);


        if (topLeft)
            //top-left corner
            path.rQuadTo(-radiusX, 0, -radiusX, radiusY);
        else {
            path.rLineTo(-radiusX, 0);
            path.rLineTo(0, radiusY);
        }

        path.rLineTo(0, heightMinusCorners);

        if (bottomLeft)
            //bottom-left corner
            path.rQuadTo(0, radiusY, radiusX, radiusY);
        else {
            path.rLineTo(0, radiusY);
            path.rLineTo(radiusX, 0);
        }

        path.rLineTo(widthMinusCorners, 0);

        if (bottomRight)
            //bottom-right corner
            path.rQuadTo(radiusX, 0, radiusX, -radiusY);
        else {
            path.rLineTo(radiusX, 0);
            path.rLineTo(0, -radiusY);
        }

        path.rLineTo(0, -heightMinusCorners);

        path.close();
        //Given close, last lineTo can be removed.

        return path;
    }
}
