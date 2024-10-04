import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import {useEffect, useState} from "react";
import Quiz from "Frontend/generated/com/example/application/data/Quiz";
import {QuestionService, QuizService} from "Frontend/generated/endpoints";



export const config: ViewConfig = { menu: { order: 0, icon: 'line-awesome/svg/file.svg' }, title: 'Quizes' };

export default function QuizesView() {
  const [quizzes, setQuizzes] = useState<Quiz[]>([]);

  useEffect(() => {
    async function fetchQuizzes() {
      const fetchedQuizzes = await QuizService.getAllQuizzes();
      setQuizzes(fetchedQuizzes);
    }
    fetchQuizzes();
  }, []);

  return (
      <div className="flex flex-col h-full items-start justify-start p-l text-center box-border">
        <div className="grid grid-cols-3 gap-s">
          {quizzes.map((quiz) => (
              <div key={quiz.id} className="card border-primary p-m flex flex-col items-center justify-center"
                   style={{height: '200px',border: '1px solid grey', borderRadius: '5px'}}>
                <h3>{quiz.name}</h3>
                <p>{quiz.description}</p>
              </div>
          ))}
        </div>
      </div>
  );
}
