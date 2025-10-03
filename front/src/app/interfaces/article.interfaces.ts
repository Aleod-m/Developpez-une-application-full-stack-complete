
export interface Article {
  id: number,
  title: string,
  topic_id: number,
  author_id: number,
  content: string,
  created_at: Date,
  updated_at: Date,
}

export interface Summary {
  id: number,
  title: string,
  author_name: string,
  topic_name: string,
  content: string,
  created_at: Date,
}

export interface NewArticle {
  title: string,
  topic_id: number,
  author_id: number,
  content: string,
}
