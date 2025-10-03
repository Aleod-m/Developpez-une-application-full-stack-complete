import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { UserUpdate, User } from "../interfaces/user.interfaces";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private pathService = 'api/users';
  constructor(private httpClient: HttpClient) { }

  public update(id: number, user_update: UserUpdate): Observable<User> {
    return this.httpClient.put<User>(`/${this.pathService}/${id}`, user_update);
  }

  public get(id: number): Observable<User> {
    return this.httpClient.get<User>(`/${this.pathService}/${id}`);
  }

}
