import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { TopicSubscription, SubscriptionService } from 'src/app/services/subscription.service';

@Component({
  selector: 'app-topics',
  templateUrl: './topics.page.html',
  styleUrls: ['./topics.page.scss'],
})
export class TopicsPage implements OnInit {
  subscriptions$!: Observable<TopicSubscription[]>;

  constructor(
    private subscribtionSrvc: SubscriptionService,
  ) { }

  ngOnInit() {
    this.subscribtionSrvc.getAll();
    this.subscriptions$ = this.subscribtionSrvc.subscriptions$;
  }

  ngOnDestroy() {

  }

  onSubscribe(topicId: number): void {
    this.subscribtionSrvc.updateTopicSubscription(topicId, true);
  }

}
