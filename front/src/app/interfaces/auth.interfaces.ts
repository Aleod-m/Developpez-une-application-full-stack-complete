export interface LoginInformation {
  email: string,
  password: string,
}

export interface SignUpInformation {
  user_name: string,
  email: string,
  password: string,
}

export interface JwtToken {
  token: string,
  type: string,
  id: number,
  username: string,
  admin: boolean,
}
