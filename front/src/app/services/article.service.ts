import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Article, Summary, NewArticle } from 'src/app/interfaces/article.interfaces';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {
  private pathService = 'api/posts';
  constructor(private httpClient: HttpClient) { }

  public getArticle(article_id: number): Observable<Article> {
    return this.httpClient.get<Article>(`${this.pathService}/${article_id}`);
  }

  public getArticleSummaries(): Observable<Summary[]> {
    return this.httpClient.get<Summary[]>(`${this.pathService}`);
  }

  public createArticle(article: NewArticle): Observable<Article> {
    return this.httpClient.post<Article>(`${this.pathService}`, article);
  }
}

