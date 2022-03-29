import '../App.css';
import React, { useRef } from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Container from '@mui/material/Container';
import Paper from '@mui/material/Paper';
import FormHelperText from '@mui/material/FormHelperText';
import FormControl from '@mui/material/FormControl';
import PasswordField_Pevneva from '../components/PasswordField_Pevneva'
import LoginField_Pevneva, { LoginFieldTypes } from '../components/LoginField_Pevneva'
import RequestUrls_Pevneva from '../settings/RequestUrls_Pevneva'
import Strings_Pevneva from '../settings/Strings_Pevneva';
import { useNavigate } from 'react-router';
import PageUrls_Pevneva from '../settings/PageUrls_Pevneva';
import Helmet from 'react-helmet';

export default function LoginPage_Pevneva () {
  const navigate = useNavigate()

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

    let loginSuccess = await fetch(RequestUrls_Pevneva.LOGIN, {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify(request_body)
    })
    loginSuccess = await loginSuccess.json()

    if (loginSuccess) navigate(PageUrls_Pevneva.HOME)
    else setResultText(Strings_Pevneva.INCORRECT_PASSWORD) 
  }

  const paperStyle = {padding: '20px', width: '70%', margin: '20px auto'}

  return (
    <div className='App'>
      <Helmet title='Login' />
      <Container>
        <Paper elevation={3} style={paperStyle}>
          <form className='register' noValidate autoComplete='off'>
            <LoginField_Pevneva ref={loginField} type={LoginFieldTypes.LOGIN}/>

            <PasswordField_Pevneva ref={passwordField} title='Password' required />

            <FormControl className='formField' variant='standard' margin='dense'>
              <Button variant='contained' onClick={(e) => {onLoginClick()}}>Login</Button>
              <FormHelperText error={resultText !== ''}>{resultText}</FormHelperText>
            </FormControl>
          </form>
        </Paper>
      </Container>
    </div>
  )
}