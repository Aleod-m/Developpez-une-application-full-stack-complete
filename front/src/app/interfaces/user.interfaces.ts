export interface User {
  id: number,
  username: string,
  email: string,
  admin: boolean,
  subscription_ids: number[]
  created_at: Date,
  updated_at: Date,
}

export interface UserUpdate {
  username?: string,
  email?: string,
  admin?: boolean,
  subscription_ids?: number[]
  password?: string,
}
