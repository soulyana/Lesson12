package me.soulyana.lesson12.controller;

import com.cloudinary.utils.ObjectUtils;
import me.soulyana.lesson12.configuration.CloudinaryConfig;
import me.soulyana.lesson12.entitites.Actor;
import me.soulyana.lesson12.repositories.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.io.IOException;

@Controller
public class HomeController {
    @Autowired
    ActorRepository actorRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String listActors(Model model) {
        model.addAttribute("actors", actorRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String newActor(Model model) {
        model.addAttribute("actor", new Actor());
        return "form";
    }

    @PostMapping("/add")
    public String processActor(@ModelAttribute Actor actor, @RequestParam("file")MultipartFile file) {
        if (file.isEmpty()) {
            return "redirect:/add";
        }
        try {
            Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
            actor.setHeadshot(uploadResult.get("url").toString());
            String filename = uploadResult.get("public_id").toString();
            System.out.println("This is the name of the cloudinary file: " + filename);
            String transformed = cloudc.createUrl(filename, 100, 100, "crop");
            String redborder = cloudc.transformThis(filename);
            System.out.println("this is the transformed image" + transformed);
            System.out.println("this is the red border image" + redborder);
            actorRepository.save(actor);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/add";
        }
        return "redirect:/";
    }
}
