import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { LoginInformation, SignUpInformation, JwtToken } from '../interfaces/auth.interfaces';
import { User } from '../interfaces/user.interfaces';

@Injectable({
  providedIn: 'root'
})
export class AuthService implements OnInit {

  private userSubject = new BehaviorSubject<User | undefined>(undefined);
  public user(): User | undefined {
    return this.userSubject.getValue();
  }
  public $user(): Observable<User | undefined> {
    return this.userSubject.asObservable();
  }

  public isLogged(): boolean {
    return localStorage.getItem('jwt_token') !== null;
  }

  private isLoggedSubject = new BehaviorSubject<boolean>(false);
  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }


  private pathService = '/api/auth';

  constructor(private httpClient: HttpClient) {
    this.me()
  }

  ngOnInit() {
    this.me();
  }

  public register(registerRequest: SignUpInformation): Observable<void> {
    return this.httpClient.post<void>(`${this.pathService}/register`, registerRequest);
  }

  public login(loginRequest: LoginInformation): Observable<JwtToken> {
    return this.httpClient.post<JwtToken>(`${this.pathService}/login`, loginRequest).pipe(tap((response) => {
      localStorage.setItem('jwt_token', response.token);
      this.me().subscribe();
    }));
  }

  public logout() {
      localStorage.removeItem('jwt_token');
      this.userSubject.next(undefined);
      this.isLoggedSubject.next(false);
  }

  public me(): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService}/me`).pipe(tap((user) => {
      this.userSubject.next(user);
      this.isLoggedSubject.next(true);
    }))
  }
}
