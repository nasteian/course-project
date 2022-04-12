import '../App.css'
import React, { useRef } from 'react'
import ReactDOM from 'react-dom'
import RequestUrls_Pevneva from '../settings/RequestUrls_Pevneva'
import PageUrls_Pevneva from '../settings/PageUrls_Pevneva'
import Cookies_Pevneva from '../settings/Cookies_Pevneva'
import Button from '@mui/material/Button'
import Cookies from 'js-cookie'

export default class TopBar_Pevneva extends React.Component {
  constructor(props) {
    super(props)

    this.onLogOutClick = this.onLogOutClick.bind(this)
  }

  onLogOutClick = () => {
    let request_body = {
      'login': Cookies.get(Cookies_Pevneva.LOGIN),
      'session_id': Cookies.get(Cookies_Pevneva.SESSION_ID)
    }

    fetch(RequestUrls_Pevneva.REMOVE_SESSION, {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify(request_body)
    })

    Cookies.remove(Cookies_Pevneva.LOGIN)
    Cookies.remove(Cookies_Pevneva.SESSION_ID)
    Cookies.remove(Cookies_Pevneva.AUTHORIZED)
    window.location.reload(false)
  }

  SurveysButton = (props) => {
    if (props.doNotShowSurveysButton === true) return null
    
    return (
      <Button color='inherit' href={PageUrls_Pevneva.SURVEYS}>Опросы</Button>
    )
  }

  componentDidMount = () => {
    ReactDOM.render(
      Cookies.get(Cookies_Pevneva.AUTHORIZED) === 'true' ? this.loggedInComponent : this.notLoggedInComponent,
      document.getElementById('topBar')
    )
  }

  loggedInComponent = (
    <div className='topBar'>
      <this.SurveysButton doNotShowSurveysButton={this.props.doNotShowSurveysButton} />
      <Button color='inherit'>{Cookies.get(Cookies_Pevneva.LOGIN)}</Button>
      <Button color='inherit' onClick={() => {this.onLogOutClick()}}>Выйти</Button>
    </div>
  )

  notLoggedInComponent = (
    <div className='topBar'>
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