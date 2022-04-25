export default class RequestUrls_Pevneva {
  static SERVER_URL = 'http://localhost:8080'
  static REGISTER = this.SERVER_URL + '/user/register' 
  static LOGIN = this.SERVER_URL + '/user/login'  
  static LOGIN_EXIST = this.SERVER_URL + '/user/exist' 
  static VERIFY_SESSION = this.SERVER_URL + '/user/session/verify' 
  static REMOVE_SESSION = this.SERVER_URL + '/user/session/remove' 
  static GET_AVALIBLE_SURVEYS = this.SERVER_URL + '/user/surveys/uncompleted' 
  static GET_MY_SURVEYS = this.SERVER_URL + '/user/surveys/my' 
  static SURVEY = this.SERVER_URL + '/survey' 
  static GET_ALL_QUESTIONS = '/questions' 
}