package me.soulyana.lesson12.configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.Singleton;
import com.cloudinary.Transformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class CloudinaryConfig {
//    an instance of cloudinary class that allows us to do transformations, etc.
    private Cloudinary cloudinary;

    @Autowired
    public CloudinaryConfig(
            @Value("${cloudinary.apikey}") String key,
            @Value("${cloudinary.apisecret}") String secret,
            @Value("${cloudinary.cloudname}") String cloud){
        cloudinary = Singleton.getCloudinary();
        cloudinary.config.cloudName=cloud;
        cloudinary.config.apiSecret=secret;
        cloudinary.config.apiKey=key;
    }
//user created method
    public Map upload(Object file, Map options) {
                try{
                    return cloudinary.uploader().upload(file, options);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
    }
//.generate instead of imageTag just gives you the URL
    public String createUrl(String name, int width, int height, String action) {
                return cloudinary.url()
                        .transformation(new Transformation()
                        .width(width).height(height)
                        .border("2px_solid_black").crop(action)) //can have method that also has other features that you don't pass through the parameter
                        .imageTag(name);
    }

    //CSS type transformations
    public String transformThis(String name) {
                return cloudinary.url().transformation(new Transformation().border("2px_solid_red")).generate(name);
    }



}
