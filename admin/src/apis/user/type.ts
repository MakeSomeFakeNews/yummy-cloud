export interface UserInfo {
  id: string
  nickname: string
  avatar: string
  roles: string[]
  permissions: string[]
}

export interface Login {
  token: string
}

export interface CaptchaResponse {
  captchaId: string
  captchaImage: string
}

export interface LoginRequest {
  username: string
  password: string
  captcha: string
  captchaId: string
}

export interface ChangePasswordRequest {
  currentPassword: string
  newPassword: string
}
