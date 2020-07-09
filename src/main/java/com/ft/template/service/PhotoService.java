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

        //TO-DO-formatImage method throws IOException. Handle the exception accordingly
        BufferedImage inputBufferedImage = ImageIO.read(file.getInputStream());
        byte[] imageArrayByte = formatImage(inputBufferedImage);

        Photo photo = new Photo();
        photo.setName(title);
        photo.setImage(new Binary(BsonBinarySubType.BINARY, imageArrayByte));
        photo = photoRepository.insert(photo);
        return photo.getId();
    }

    public Photo getPhoto(String id) {
        return photoRepository.findById(id).get();
    }

    public byte[] formatImage(BufferedImage bufferedImage) throws IOException {

        log.info("formatImage method started");
        //TO-DO-move 600 width and 600 height to properties
        BufferedImage resizedBufferedImage = getScaledImage(bufferedImage, 600, 600);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        //TO-DO-remove these lines-added for testing
        File output = new File("/Users/senthilkumarsrinivasan/Downloads/resizedImage.png");
        ImageIO.write(resizedBufferedImage, "png", output);
        //TO-DO-end

        ImageIO.write(resizedBufferedImage, "png", byteArrayOutputStream);
        byteArrayOutputStream.flush();
        byte[] resizedImageByteArray = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();

        log.info("formatImage method ended");
        return resizedImageByteArray;

    }

    private BufferedImage getScaledImage(BufferedImage inputBufferedImage, int w, int h){
        int originalWidth = inputBufferedImage.getWidth();
        int originalHeight = inputBufferedImage.getHeight();
        int boundWidth = w;
        int boundHeight = h;
        int newWidth = originalWidth;
        int newHeight = originalHeight;
        int borderSize = 30;//TO-DO-move to properties
        BufferedImage resizedBufferedImage;

        if (originalWidth == boundWidth && originalHeight == boundHeight) {
            resizedBufferedImage = inputBufferedImage;
        } else {
            // first check if we need to scale width
            if (originalWidth > boundWidth) {
                //scale width to fit
                newWidth = boundWidth;
                //scale height to maintain aspect ratio
                newHeight = (newWidth * originalHeight) / originalWidth;
            }
            // then check if we need to scale even with the new height
            if (newHeight > boundHeight) {
                //scale height to fit instead
                newHeight = boundHeight;
                //scale width to maintain aspect ratio
                newWidth = (newHeight * originalWidth) / originalHeight;
            }
            //resizing to get border
            newWidth = newWidth - borderSize;
            newHeight = newHeight - borderSize;

            Image tempScaledImage = inputBufferedImage.getScaledInstance(w, h, Image.SCALE_SMOOTH);
            resizedBufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = resizedBufferedImage.createGraphics();
            g2.setBackground(Color.WHITE);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.clearRect(0,0,w, h);
            g2.drawImage(tempScaledImage, (w-newWidth)/2, (h-newHeight)/2, newWidth, newHeight, null);
            g2.dispose();
            tempScaledImage.flush();
        }
        return resizedBufferedImage;
    }


//    private static BufferedImage resize(BufferedImage img, int height, int width) {
//        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
//        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//        Graphics2D g2d = resized.createGraphics();
//        g2d.setColor(Color.WHITE);
//        g2d.drawImage(tmp, 0, 0, null);
//        g2d.dispose();
//        return resized;
//    }

//    /**
//     * Like {@link #scaleWithMax(BufferedImage, int, int)} but the resulting image will include the scaled
//     * image along with transparent (hopefully) background color as a filler to the sides or top/bottom
//     * depending on the source img size.
//     */
//    public static BufferedImage scaleWithPadding(BufferedImage img, int newWidth, int newHeight) {
//        int currentWidth = img.getWidth();
//        int currentHeight = img.getHeight();
//
//        int scaledWidth;
//        int scaledHeight;
//        if (currentWidth == 0 || currentHeight == 0
//                || (currentWidth == newWidth && currentHeight == newHeight)) {
//            return img;
//        } else if (currentWidth == currentHeight) {
//            scaledWidth = newWidth;
//            scaledHeight = newHeight;
//        } else if (currentWidth >= currentHeight) {
//            scaledWidth = newWidth;
//            double scale = (double) newWidth / (double) currentWidth;
//            scaledHeight = (int) Math.round(currentHeight * scale);
//        } else {
//            scaledHeight = newHeight;
//            double scale = (double) newHeight / (double) currentHeight;
//            scaledWidth = (int) Math.round(currentWidth * scale);
//        }
//
//        int x = (newWidth - scaledWidth) / 2;
//        int y = (newHeight - scaledHeight) / 2;
//
//
//        Image tmp = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
//
//        BufferedImage newImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
//        Graphics2D g = newImg.createGraphics();
//        g.setColor(Color.WHITE);
//        g.fillRect(0, 0, newWidth, newHeight);
//        g.drawImage(tmp, x, y, x + scaledWidth, y + scaledHeight, 0, 0, currentWidth, currentHeight,
//                Color.WHITE, null);
//        g.dispose();
//
//        return newImg;
//    }
}
