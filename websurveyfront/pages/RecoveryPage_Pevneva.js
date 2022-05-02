import '../App.css'
import React, { useRef } from 'react'
import Helmet from 'react-helmet'
import Particles_Pevneva from '../components/Particles_Pevneva'
import LoginField_Pevneva, { LoginFieldTypes } from '../components/LoginField_Pevneva'
import PasswordField_Pevneva from '../components/PasswordField_Pevneva'
import FormControl from '@mui/material/FormControl'
import FormHelperText from '@mui/material/FormHelperText'
import Strings_Pevneva from '../settings/Strings_Pevneva'
import PageUrls_Pevneva from '../settings/PageUrls_Pevneva'
import Button from '@mui/material/Button'
import Cookies from 'js-cookie'
import RequestUrls_Pevneva from '../settings/RequestUrls_Pevneva'
import Cookies_Pevneva from '../settings/Cookies_Pevneva'
import Box from '@mui/material/Box'

export default function RecoveryPage_Pevneva() {
  const [resultText, setResultText] = React.useState(false)

  const loginField = useRef(null)
  const passwordField = useRef(null)
  const retypePasswordField = useRef(null)
  const codePhraseField = useRef(null)

  async function validate() {
    if (!loginField.current.state.correct) return false
    if (!passwordField.current.state.correct) return false
    if (!retypePasswordField.current.state.correct) return false
    if (!codePhraseField.current.state.correct) return false
    return true
  }

  async function onLoginClick() {
    if (!await validate()) return

    if (passwordField.current.state.password === retypePasswordField.current.state.password) {
      const request_body = {
        'login': loginField.current.state.login,
        'password': passwordField.current.state.password,
        'code_phrase': codePhraseField.current.state.password
      };

      let sessionId = await fetch(RequestUrls_Pevneva.RECOVERY, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(request_body)
      })

      if (sessionId.status !== 200) {
        setResultText(Strings_Pevneva.INCORRECT_KEY_PHRASE)
        return;
      }

      Cookies.set(Cookies_Pevneva.LOGIN, loginField.current.state.login)
      Cookies.set(Cookies_Pevneva.SESSION_ID, (await sessionId.json()))
      Cookies.set(Cookies_Pevneva.AUTHORIZED, 'true')
      window.location.assign(PageUrls_Pevneva.HOME)
    }
  }

  return (
    <div id='RecoveryPage'>
      <Helmet title='Восстановление' />

      <Particles_Pevneva color='#000000' direction='none' />
      <div className='registerContainer'>
        <div style={{padding: '20px', widtgih: '70%', margin: '20px auto'}}>
          <form noValidate autoComplete='off'>
            <LoginField_Pevneva ref={loginField} type={LoginFieldTypes.LOGIN}/>
            <PasswordField_Pevneva ref={codePhraseField} title='Кодовая фраза' required/>
            <PasswordField_Pevneva ref={passwordField} title='Пароль' required />
            <PasswordField_Pevneva ref={retypePasswordField} title='Повторите пароль' required />

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