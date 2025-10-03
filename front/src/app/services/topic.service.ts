import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Topic } from "../interfaces/topic.interfaces";

@Injectable({
  providedIn: 'root'
})
export class TopicService {
  private pathService = 'api/topics';
  constructor(private httpClient: HttpClient) { }

  public getAll(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(`${this.pathService}`);
  }

  public get(id: number): Observable<Topic> {
    return this.httpClient.get<Topic>(`${this.pathService}/${id}`);
  }
}
