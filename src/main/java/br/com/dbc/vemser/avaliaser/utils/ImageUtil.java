package br.com.dbc.vemser.avaliaser.utils;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtil {

    public static byte[] compressImage(byte[] imageBytes){

        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(imageBytes);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);
        byte[] tmp = new byte[10*1024];
        while (!deflater.finished()){
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try{
            outputStream.close();
        }catch (Exception e){
        }
        return outputStream.toByteArray();
    }

    public static byte[] decompressImage(byte[] imageBytes){
        Inflater inflater = new Inflater();
        inflater.setInput(imageBytes);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);
        byte[] tmp = new byte[10*1024];
        try{
            while (!inflater.finished()){
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
            }catch (Exception e){

        }
        return outputStream.toByteArray();
    }
}
