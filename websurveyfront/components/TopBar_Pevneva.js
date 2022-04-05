import '../App.css'
import React from 'react'
import ReactDOM from 'react-dom'
import RequestUrls_Pevneva from '../settings/RequestUrls_Pevneva'
import PageUrls_Pevneva from '../settings/PageUrls_Pevneva'
import Cookies_Pevneva from '../settings/Cookies_Pevneva'
import Button from '@mui/material/Button'
import Cookies from 'js-cookie';

export default class TopBar_Pevneva extends React.Component {
  constructor(props) {
    super(props)

    this.onLogOutClick = this.onLogOutClick.bind(this)
  }

  onLogOutClick = async () => {
    let request_body = {
      'login': Cookies.get(Cookies_Pevneva.LOGIN),
      'session_id': Cookies.get(Cookies_Pevneva.SESSION_ID)
    }

    await fetch(RequestUrls_Pevneva.REMOVE_SESSION, {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify(request_body)
    })

    Cookies.remove(Cookies_Pevneva.LOGIN)
    Cookies.remove(Cookies_Pevneva.SESSION_ID)
    window.location.reload(false)
  }

  componentDidMount = async() => {
    let login = Cookies.get(Cookies_Pevneva.LOGIN)
    let sessionId = Cookies.get(Cookies_Pevneva.SESSION_ID)
    
    let request_body = {
      'login': login,
      'session_id': sessionId
    }
  
    let sessionIsGood = false
    if (login != null && sessionId != null) {
      sessionIsGood = await fetch(RequestUrls_Pevneva.VERIFY_SESSION, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(request_body)
      })
      sessionIsGood = await sessionIsGood.json()
    }

    ReactDOM.render(
      sessionIsGood ? this.loggedInComponent : this.notLoggedInComponent,
      document.getElementById('topBar')
    )
  }

  loggedInComponent = (
    <div id='rightBar'>
      <Button color='inherit'>{Cookies.get(Cookies_Pevneva.LOGIN)}</Button>
      <Button color='inherit' onClick={(e) => {this.onLogOutClick()}}>Выйти</Button>
    </div>
  )

  notLoggedInComponent = (
    <div id='rightBar'>
      <Button color='inherit' href={PageUrls_Pevneva.LOGIN}>Войти</Button>
      <Button color='inherit' href={PageUrls_Pevneva.REGISTER}>Зарегистрироваться</Button>
    </div>
  )

  render() {
    return (
      <div id='topBar'>
        
      </div>
    )
  }
}