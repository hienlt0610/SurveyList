package dev.hienlt.surveylist;

import java.util.ArrayList;
import java.util.List;

public class DataSource {

    public static List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        Question q1 = new Question();
        q1.setQuestionId("Câu 1:");
        q1.setQuestionContent("1 + 1 = ?");
        q1.setQuestionType(Question.TYPE_INPUT);
        questions.add(q1);

        Question q2 = new Question();
        q2.setQuestionId("Câu 2:");
        q2.setQuestionContent("1 + 2 = ?");
        q2.setQuestionType(Question.TYPE_INPUT);
        questions.add(q2);

        Question q3 = new Question();
        q3.setQuestionId("Câu 3:");
        q3.setQuestionContent("1 + 3 = ?");
        q3.setQuestionType(Question.TYPE_SELECT);
        questions.add(q3);

        Question q4 = new Question();
        q4.setQuestionId("Câu 4:");
        q4.setQuestionContent("1 + 4 = ?");
        q4.setQuestionType(Question.TYPE_INPUT);
        questions.add(q4);

        Question q5 = new Question();
        q5.setQuestionId("Câu 5:");
        q5.setQuestionContent("1 + 5 = ?");
        q5.setQuestionType(Question.TYPE_SELECT);
        questions.add(q5);

        Question q6 = new Question();
        q6.setQuestionId("Câu 6:");
        q6.setQuestionContent("1 + 6 = ?");
        q6.setQuestionType(Question.TYPE_SELECT);
        questions.add(q6);

        Question q7 = new Question();
        q7.setQuestionId("Câu 7:");
        q7.setQuestionContent("1 + 7 = ?");
        q7.setQuestionType(Question.TYPE_INPUT);
        questions.add(q7);

        Question q8 = new Question();
        q8.setQuestionId("Câu 8:");
        q8.setQuestionContent("1 + 8 = ?");
        q8.setQuestionType(Question.TYPE_SELECT);
        questions.add(q8);

        Question q9 = new Question();
        q9.setQuestionId("Câu 9:");
        q9.setQuestionContent("1 + 9 = ?");
        q9.setQuestionType(Question.TYPE_SELECT);
        questions.add(q9);

        Question q10 = new Question();
        q10.setQuestionId("Câu 10:");
        q10.setQuestionContent("1 + 10 = ?");
        q10.setQuestionType(Question.TYPE_INPUT);
        questions.add(q10);

        return questions;
    }

    public static List<Answer> getAnswer() {
        List<Answer> answers = new ArrayList<>();

        Answer a1 = new Answer();
        a1.setQuestionId("Câu 3:");
        a1.setAnswer("3");
        answers.add(a1);

        Answer a2 = new Answer();
        a2.setQuestionId("Câu 3:");
        a2.setAnswer("4");
        answers.add(a2);

        Answer a3 = new Answer();
        a3.setQuestionId("Câu 3:");
        a3.setAnswer("5");
        answers.add(a3);

        Answer a4 = new Answer();
        a4.setQuestionId("Câu 5:");
        a4.setAnswer("5");
        answers.add(a4);

        Answer a5 = new Answer();
        a5.setQuestionId("Câu 5:");
        a5.setAnswer("6");
        answers.add(a5);

        Answer a6 = new Answer();
        a6.setQuestionId("Câu 6:");
        a6.setAnswer("6");
        answers.add(a6);

        Answer a7 = new Answer();
        a7.setQuestionId("Câu 6:");
        a7.setAnswer("7");
        answers.add(a7);

        Answer a8 = new Answer();
        a8.setQuestionId("Câu 6:");
        a8.setAnswer("8");
        answers.add(a8);

        Answer a9 = new Answer();
        a9.setQuestionId("Câu 6:");
        a9.setAnswer("9");
        answers.add(a9);

        Answer a10 = new Answer();
        a10.setQuestionId("Câu 8:");
        a10.setAnswer("1");
        answers.add(a10);

        Answer a11 = new Answer();
        a11.setQuestionId("Câu 8:");
        a11.setAnswer("9");
        answers.add(a11);

        return answers;
    }

    public static List<Question> getSurvey() {
        List<Question> questions = getQuestions();
        for (Question question : questions) {
            List<Answer> answers = getAnswer();
            for (Answer answer : answers) {
                if (answer.getQuestionId().equals(question.getQuestionId())) {
                    question.getAnswers().add(answer);
                }
            }
        }
        return questions;
    }

}
