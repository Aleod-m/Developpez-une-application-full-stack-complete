import { Injectable } from '@angular/core';
import { BehaviorSubject, combineLatest, filter, map, switchMap, forkJoin, Observable } from 'rxjs';
import { Topic } from '../interfaces/topic.interfaces';
import { User } from '../interfaces/user.interfaces';
import { AuthService } from './auth.service';
import { TopicService } from './topic.service';
import { UserService } from './user.service';

export interface TopicSubscription {
  topic: Topic,
  subscribed: boolean,
};

@Injectable({ providedIn: 'root' })
export class SubscriptionService {
  private subscriptionsSubject = new BehaviorSubject<TopicSubscription[]>([]);
  public subscriptions$: Observable<TopicSubscription[]> = this.subscriptionsSubject.asObservable();

  constructor(
    private authSrvc: AuthService,
    private userSrvc: UserService,
    private topicSrvc: TopicService,
  ) { }


  getSubsciptionForUser() {
    this.authSrvc.me().pipe(
      switchMap((user) =>
        forkJoin(user.subscription_ids.map(id => this.topicSrvc.get(id).pipe(
          map((topic: Topic) => ({ topic, subscribed: true })),
        )))
      )
    ).subscribe({ next: (subs) => this.subscriptionsSubject.next(subs) });
  }

  getAll() {
    var userSubscriptions$ = this.authSrvc.me().pipe(
      filter((user: User): user is User => !!user),
      map((user: User) => user.subscription_ids)
    );

    combineLatest([userSubscriptions$, this.topicSrvc.getAll()])
      .pipe(
        map(([userSubscriptions, allTopics]) =>
          allTopics.map(topic => ({ topic, subscribed: userSubscriptions.includes(topic.id) }))
        )
      ).subscribe({ next: (subs) => this.subscriptionsSubject.next(subs) });
  }

  updateTopicSubscription(topicId: number, isSubscribed: boolean) {
    const current = this.subscriptionsSubject.value;
    const updated = current.map(sub =>
      sub.topic.id === topicId ? { ...sub, subscribed: isSubscribed } : sub
    );


    console.log('Updated subscriptions:', updated); // Debug
    this.subscriptionsSubject.next(updated);

    this.authSrvc.me()
      .pipe(
        map((user): [number, number[]] => [user.id, user.subscription_ids]),
        map(([userId, userSubscription]): [number, number[]] => {
          if (isSubscribed) {
            userSubscription.push(topicId);
          } else {
            userSubscription = userSubscription.filter((subscribedTopicId) => subscribedTopicId != topicId);
          }
          return [userId, userSubscription];
        })
      )
      .subscribe({
        next: ([userId, subscriptions]) => this.userSrvc.update(userId, { subscription_ids: subscriptions }).subscribe()
      })
  }
}
