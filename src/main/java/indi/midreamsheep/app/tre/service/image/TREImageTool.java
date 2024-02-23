package indi.midreamsheep.app.tre.service.image;

import androidx.compose.ui.graphics.ImageBitmap;
import androidx.compose.ui.res.ImageResources_desktopKt;
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Comment
@Slf4j
public class TREImageTool {
    public ImageBitmap readImage(String path){
        return readImage(new File(path));
    }

    public ImageBitmap readImage(File file){
        try {
            if (file == null || !file.exists() || !file.isFile()) {
                throw new RuntimeException("file is not exist or not a file");
            }
            log.debug("readImage file:{}",file.getAbsolutePath());
            return readImage(new FileInputStream(file));
        } catch (Exception e) {
            assert file != null;
            log.error("readImage error file:{}",file.getAbsolutePath(), e);
            return null;
        }
    }

    public ImageBitmap readImage(InputStream stream){
        try{
            return ImageResources_desktopKt.loadImageBitmap(stream);
        }catch (Exception e){
            log.error("readImage error", e);
            return null;
        }
    }
}