export default class RequestUrls_Pevneva {
  static SERVER_URL = 'http://localhost:8080'
  static REGISTER = this.SERVER_URL + '/user/register' 
  static LOGIN = this.SERVER_URL + '/user/login'  
  static LOGIN_EXIST = this.SERVER_URL + '/user/exist' 
  static VERIFY_SESSION = this.SERVER_URL + '/user/session/verify' 
  static REMOVE_SESSION = this.SERVER_URL + '/user/session/remove' 
}