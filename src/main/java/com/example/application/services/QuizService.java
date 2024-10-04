package com.example.application.services;

import com.example.application.data.Question;
import com.example.application.data.Quiz;
import com.example.application.dto.QuestionDTO;
import com.example.application.dto.QuizDTO;
import com.example.application.dto.QuizMapper;
import com.example.application.repo.QuizRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@BrowserCallable
@AnonymousAllowed
@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    public List<QuizDTO> getAllQuizzes() {
        var quizEntities = quizRepository.findAll();
        return quizEntities.stream().map(QuizMapper::toDTO).toList();
    }

    public Optional<QuizDTO> getQuizById(Long id) {
        var quizEntity = quizRepository.findById(id);
        return quizEntity.map(QuizMapper::toDTO);
    }

    public QuizDTO createQuiz(QuizDTO quizDTO) {
        final Quiz quizEntity = QuizMapper.toEntity(quizDTO);
        final Quiz savedQuiz = quizRepository.save(quizEntity);
        addOrCreateQuestions(savedQuiz, quizDTO.getQuestions());
        return QuizMapper.toDTO(savedQuiz);
    }

    private void addOrCreateQuestions(Quiz savedQuiz, List<QuestionDTO> questions) {
        for (QuestionDTO questionDTO : questions) {
            Optional<Question> questionEntity = Optional.empty();
            if (questionDTO.getId() != null) {
                questionEntity = savedQuiz.getQuestions().stream()
                        .filter(q -> q.getId().equals(questionDTO.getId()))
                        .findFirst();
            }
            if (questionEntity.isEmpty()) {
                final Question entity = QuizMapper.toEntity(questionDTO);
                entity.setQuiz(savedQuiz);
                savedQuiz.getQuestions().add(entity);
            } else {
                questionEntity.get().setText(questionDTO.getText());
            }
        }
    }

    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }
}