export interface Comment {
  id: number,
  post_id: number,
  commenter_id: number,
  content: string,
  created_at: Date,
}

export interface CommentCreation {
  post_id: number,
  content: string,
}
