package com.gym.api.rest.exercise;

import com.gym.api.entity.ExerciseCategory;
import com.gym.api.rest.ObjectNotFoundException;
import com.gym.api.service.exercise.ExerciseService;
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
    private ExerciseService exerciseService;

    @GetMapping("/categories")
    public ResponseEntity<List<ExerciseCategory>> getCategories() {
        List<ExerciseCategory> categoryList = exerciseService.getCategories();

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

        exerciseService.addExerciseCategory(exerciseCategory);

        return new ResponseEntity<>("Category " + exerciseCategory.getCategory() + " saved successfully!", HttpStatus.ACCEPTED);
    }
}
