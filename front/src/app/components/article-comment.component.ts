import { Component, Input, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import { Comment } from "src/app/interfaces/comment.interfaces";

@Component({
  selector: 'article-comment',
  templateUrl: './article-comment.component.html',
  styleUrls: ['./article-comment.component.scss'],
})
export class ArticleCommentComponent implements OnInit {
  @Input()
  comment!: Comment;
  username: string = "";

  constructor(private userService: UserService) {

  }

  ngOnInit(): void {
    this.userService.get(this.comment.commenter_id).subscribe({
      next: (user) => this.username = user.username,
    })
  }


}
