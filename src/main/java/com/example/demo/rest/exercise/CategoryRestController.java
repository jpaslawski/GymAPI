package com.example.demo.rest.exercise;

import com.example.demo.entity.ExerciseCategory;
import com.example.demo.rest.ObjectNotFoundException;
import com.example.demo.service.GymService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CategoryRestController {

    @Autowired
    private GymService gymService;

    @GetMapping("/categories")
    public ResponseEntity<List<ExerciseCategory>> getCategories() {
        List<ExerciseCategory> categoryList = gymService.getCategories();

        if(categoryList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(categoryList);
    }

    @PostMapping("/categories")
    public ResponseEntity<?> addExerciseCategory(@RequestBody ExerciseCategory exerciseCategory) {
        if (exerciseCategory.getCategory() == null) {
            throw new ObjectNotFoundException("No category identifier provided!");
        }

        gymService.addExerciseCategory(exerciseCategory);

        return new ResponseEntity<>("Category " + exerciseCategory.getCategory() + " saved successfully!", HttpStatus.ACCEPTED);
    }
}
