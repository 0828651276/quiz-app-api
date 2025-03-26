package com.quiszerver.controller;

import com.quiszerver.model.Quiz;
import com.quiszerver.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/quiz")
@CrossOrigin("*")
public class QuizController {

    @Autowired
    private QuizRepository quizRepository;

    @GetMapping("/quizzes")
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    @PostMapping("/quizzes")
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @GetMapping("/quizzes/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long id) {
        Optional<Quiz> quizOptional = quizRepository.findById(id);
        return quizOptional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/quizzes/{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable Long id, @RequestBody Quiz updatedQuiz) {
        return quizRepository.findById(id)
                .map(existingQuiz -> {
                    existingQuiz.setTitle(updatedQuiz.getTitle() != null ? updatedQuiz.getTitle() : existingQuiz.getTitle());
                    existingQuiz.setDescription(updatedQuiz.getDescription() != null ? updatedQuiz.getDescription() : existingQuiz.getDescription());
                    existingQuiz.setUsername(updatedQuiz.getUsername() != null ? updatedQuiz.getUsername() : existingQuiz.getUsername());
                    existingQuiz.setQuestions(updatedQuiz.getQuestions() != null ? updatedQuiz.getQuestions() : existingQuiz.getQuestions());

                    Quiz savedQuiz = quizRepository.save(existingQuiz);
                    return ResponseEntity.ok(savedQuiz);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/quizzes/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        Optional<Quiz> quizOptional = quizRepository.findById(id);

        if (quizOptional.isPresent()) {
            quizRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

