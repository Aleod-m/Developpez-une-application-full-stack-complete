import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Topic } from '../interfaces/topic.interfaces';

@Component({
  selector: 'topic-card[topic]',
  templateUrl: './topic-card.component.html',
  styleUrls: ['./topic-card.component.scss'],
})
export class TopicCardComponent {

  @Input() topic!: Topic;
  @Output() onButton= new EventEmitter<number>();
  @Input() subscribed!: boolean;

  constructor(
  ) { }

  onClick() {
    this.subscribed = !this.subscribed;
    this.onButton.emit(this.topic.id);
  }

}
