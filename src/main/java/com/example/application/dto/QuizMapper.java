package com.example.application.dto;

import com.example.application.data.Quiz;
import com.example.application.data.Question;

import java.util.stream.Collectors;

public class QuizMapper {

    public static QuizDTO toDTO(Quiz quiz) {
        if (quiz == null) {
            return null;
        }

        QuizDTO quizDTO = new QuizDTO();
        quizDTO.setId(quiz.getId());
        quizDTO.setName(quiz.getName());
        quizDTO.setDescription(quiz.getDescription());
        quizDTO.setCreated(quiz.getCreated());
        quizDTO.setEdited(quiz.getEdited());
        quizDTO.setCreatedBy(quiz.getCreatedBy());
        quizDTO.setQuestions(quiz.getQuestions().stream().map(QuizMapper::toDTO).collect(Collectors.toList()));

        return quizDTO;
    }

    public static Quiz toEntity(QuizDTO quizDTO) {
        if (quizDTO == null) {
            return null;
        }

        Quiz quiz = new Quiz();
        quiz.setId(quizDTO.getId());
        quiz.setName(quizDTO.getName());
        quiz.setDescription(quizDTO.getDescription());
        quiz.setCreated(quizDTO.getCreated());
        quiz.setEdited(quizDTO.getEdited());
        quiz.setCreatedBy(quizDTO.getCreatedBy());
        quiz.setQuestions(quizDTO.getQuestions().stream().map(QuizMapper::toEntity).collect(Collectors.toList()));

        return quiz;
    }

    public static QuestionDTO toDTO(Question question) {
        if (question == null) {
            return null;
        }

        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(question.getId());
        questionDTO.setText(question.getText());
        questionDTO.setQuizId(question.getQuiz().getId());

        return questionDTO;
    }

    public static Question toEntity(QuestionDTO questionDTO) {
        if (questionDTO == null) {
            return null;
        }

        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setText(questionDTO.getText());
        // Note: You need to set the Quiz entity separately as it requires fetching from the database
        // question.setQuiz(...);

        return question;
    }
}