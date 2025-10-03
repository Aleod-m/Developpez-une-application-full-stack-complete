import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Comment, CommentCreation } from "../interfaces/comment.interfaces";

@Injectable({
  providedIn: 'root'
})
export class CommentService {
  private pathService = 'api/comments';
  constructor(private httpClient: HttpClient) { }

  public getCommentsForAtricle(article_id: number): Observable<Comment[]> {
    const params = { post_id: article_id };
    return this.httpClient.get<Comment[]>(`${this.pathService}`, { params });
  }

  public create(article: CommentCreation): Observable<Comment> {
    return this.httpClient.post<Comment>(`${this.pathService}`, article);
  }
}
