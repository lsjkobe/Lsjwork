package lsjkobe.nineimg;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsj on 2016/3/6.
 */
public class ImageUtil {
    static Bitmap bitmap;
    static Bitmap itemBitmap = null;
    static List<Bitmap> pieces;
    public static List<Bitmap> getImageList(Resources res) {
        bitmap = BitmapFactory.decodeResource(res, R.mipmap.bg1);
        pieces = new ArrayList<Bitmap>();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int pieceWidth = width / 3;
        int pieceHeight = height / 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int xValue = j * pieceWidth;
                int yValue = i * pieceHeight;
                itemBitmap = Bitmap.createBitmap(bitmap, xValue, yValue,
                        pieceWidth, pieceHeight);
                pieces.add(itemBitmap);
//                itemBitmap.recycle();
            }
        }

        return pieces;
    }
    public static void cleanBitmap(){

        for(int i = 0; i<9; i++){
            pieces.get(i).recycle();
        }if(itemBitmap != null){
            itemBitmap.recycle();
            itemBitmap = null;
        }
        if(bitmap != null){
            bitmap.recycle();
            bitmap = null;
        }
        System.gc();
    }


}
