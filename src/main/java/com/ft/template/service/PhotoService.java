package com.ft.template.service;

import com.ft.template.domain.entities.Photo;
import com.ft.template.domain.repositories.PhotoRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Slf4j
@Service
public class PhotoService {

    @Autowired
    PhotoRepository photoRepository;

    public String addPhoto(String title, MultipartFile file) throws IOException {
        byte[] imageArrayByte = formatImage(file);

        Photo photo = new Photo();
        photo.setName(title);
        photo.setImage(new Binary(BsonBinarySubType.BINARY, imageArrayByte));
        photo = photoRepository.insert(photo);
        return photo.getId();
    }

    public Photo getPhoto(String id) {
        return photoRepository.findById(id).get();
    }

    public byte[] formatImage(MultipartFile file) throws IOException {
        log.info("resizePhoto started");

        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        BufferedImage resizedImage = getScaledImage(bufferedImage, 600, 600);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        File output = new File("/Users/skumarsrinivasan/SenthilSHome/Codebase/FreshThymes/github/resized66.png");
        ImageIO.write(resizedImage, "png", output);
        ImageIO.write(resizedImage, "png", byteArrayOutputStream);
        byteArrayOutputStream.flush();
        byte[] returnArray = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();

        log.info("resizePhoto ended");

        return returnArray;

    }

    private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    private BufferedImage getScaledImage(BufferedImage src, int w, int h){
        int original_width = src.getWidth();
        int original_height = src.getHeight();
        int bound_width = w;
        int bound_height = h;
        int new_width = original_width;
        int new_height = original_height;

        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }


        Image tmp = src.getScaledInstance(w, h, Image.SCALE_SMOOTH);

        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setBackground(Color.WHITE);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.clearRect(0,0,w, h);
        g2.drawImage(tmp, (w-new_width)/2, (h-new_height)/2, new_width, new_height, null);
        g2.dispose();
        return resizedImg;
    }


    /**
     * Like {@link #scaleWithMax(BufferedImage, int, int)} but the resulting image will include the scaled
     * image along with transparent (hopefully) background color as a filler to the sides or top/bottom
     * depending on the source img size.
     */
    public static BufferedImage scaleWithPadding(BufferedImage img, int newWidth, int newHeight) {
        int currentWidth = img.getWidth();
        int currentHeight = img.getHeight();

        int scaledWidth;
        int scaledHeight;
        if (currentWidth == 0 || currentHeight == 0
                || (currentWidth == newWidth && currentHeight == newHeight)) {
            return img;
        } else if (currentWidth == currentHeight) {
            scaledWidth = newWidth;
            scaledHeight = newHeight;
        } else if (currentWidth >= currentHeight) {
            scaledWidth = newWidth;
            double scale = (double) newWidth / (double) currentWidth;
            scaledHeight = (int) Math.round(currentHeight * scale);
        } else {
            scaledHeight = newHeight;
            double scale = (double) newHeight / (double) currentHeight;
            scaledWidth = (int) Math.round(currentWidth * scale);
        }

        int x = (newWidth - scaledWidth) / 2;
        int y = (newHeight - scaledHeight) / 2;

        /*
         * This is _very_ painful. I've tried a large number of different permutations here trying to
         * get the white image background to be transparent without success. We've tried different
         * fills, composite types, image types, etc.. I'm moving on now.
         */


        Image tmp = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        BufferedImage newImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImg.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, newWidth, newHeight);
        g.drawImage(tmp, x, y, x + scaledWidth, y + scaledHeight, 0, 0, currentWidth, currentHeight,
                Color.WHITE, null);
        g.dispose();

        return newImg;
    }
}
