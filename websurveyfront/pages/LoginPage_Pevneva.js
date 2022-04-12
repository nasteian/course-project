import '../App.css'
import React, { useRef } from 'react'
import Button from '@mui/material/Button'
import FormHelperText from '@mui/material/FormHelperText'
import FormControl from '@mui/material/FormControl'
import PasswordField_Pevneva from '../components/PasswordField_Pevneva'
import LoginField_Pevneva, { LoginFieldTypes } from '../components/LoginField_Pevneva'
import RequestUrls_Pevneva from '../settings/RequestUrls_Pevneva'
import Strings_Pevneva from '../settings/Strings_Pevneva'
import PageUrls_Pevneva from '../settings/PageUrls_Pevneva'
import Helmet from 'react-helmet'
import Cookies from 'js-cookie';
import Cookies_Pevneva from '../settings/Cookies_Pevneva'
import Particles_Pevneva from '../components/Particles_Pevneva'
import Box from '@mui/material/Box'

export default function LoginPage_Pevneva() {

  const [resultText, setResultText] = React.useState(false)
  const [role, setRole] = React.useState(false)

  const loginField = useRef(null)
  const passwordField = useRef(null)

  function roleHandleChange(event) {
    setRole(event.target.value)
  }

  async function validate() {
    if (!loginField.current.state.correct) return false;
    if (!passwordField.current.state.correct) return false;
    return true;
  }

  async function onLoginClick() {
    if (!await validate()) return

    const request_body = {
      'login': loginField.current.state.login,
      'password': passwordField.current.state.password
    }

    let sessionId = await fetch(RequestUrls_Pevneva.LOGIN, {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify(request_body)
    })
    sessionId = await sessionId.json()
    
    if (sessionId === false) {
      setResultText(Strings_Pevneva.INCORRECT_PASSWORD) 
      return;
    } 
    
    Cookies.set(Cookies_Pevneva.LOGIN, loginField.current.state.login)
    Cookies.set(Cookies_Pevneva.SESSION_ID, sessionId)
    Cookies.set(Cookies_Pevneva.AUTHORIZED, 'true')
    window.location.replace(PageUrls_Pevneva.HOME)
  }

  return (
    <div id='LoginPage'>
      <Helmet title='Вход' />

      <Particles_Pevneva color='#000000' direction='none' />
      <div className='loginContainer'>
        <div style={{padding: '20px', width: '70%', margin: '20px auto'}}>
          <form noValidate autoComplete='off'>
            <LoginField_Pevneva ref={loginField} type={LoginFieldTypes.LOGIN}/>

            <PasswordField_Pevneva ref={passwordField} title='Пароль' required />

            <Box sx={{ paddingTop: 2 }} />
            <FormControl className='formField' variant='standard' margin='dense'>
              <Button variant='contained' onClick={() => {onLoginClick()}}>Войти</Button>
              <FormHelperText error={resultText !== ''}>{resultText}</FormHelperText>
            </FormControl>
          </form>
        </div>

        {Strings_Pevneva.ALSO_YOU_CAN_REGISTER_FIRST_PART}
        <a href={PageUrls_Pevneva.REGISTER}>{Strings_Pevneva.ALSO_YOU_CAN_REGISTER_SECOND_PART}</a>
      </div>
    </div>
  )
}