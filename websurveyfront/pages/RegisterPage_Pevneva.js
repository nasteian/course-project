import '../App.css';
import React, { useRef } from 'react';
import { useNavigate } from 'react-router-dom'
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Container from '@mui/material/Container';
import Paper from '@mui/material/Paper';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import FormHelperText from '@mui/material/FormHelperText';
import PasswordField_Pevneva from '../components/PasswordField_Pevneva'
import LoginField_Pevneva, { LoginFieldTypes } from '../components/LoginField_Pevneva'
import RequestUrls_Pevneva from '../settings/RequestUrls_Pevneva'
import Strings_Pevneva from '../settings/Strings_Pevneva';
import PageUrls_Pevneva from '../settings/PageUrls_Pevneva';
import Helmet from 'react-helmet';

export default function RegisterPage_Pevneva () {
  const roles = [{
      label: 'User',
      value: false
    }, {
      label: 'Admin',
      value: true
  }]

  const navigate = useNavigate();

  const [registerError, setRegisterError] = React.useState('');
  const [role, setRole] = React.useState(false);

  const loginField = useRef(null)
  const passwordField = useRef(null)
  const retypePasswordField = useRef(null)
  const codePhraseField = useRef(null)

  function roleHandleChange(event) {
    setRole(event.target.value)
  }

  async function validate() {
    if (!loginField.current.state.correct) return false;
    if (!passwordField.current.state.correct) return false;
    if (!retypePasswordField.current.state.correct) return false;
    if (!codePhraseField.current.state.correct) return false;
    return true;
  }

  async function onRegisterClick() {
    setRegisterError('');
    if (!await validate()) return
    
    if (passwordField.current.state.password === retypePasswordField.current.state.password) {
      const request_body = {
        'login': loginField.current.state.login,
        'password': passwordField.current.state.password, 
        'role': role, 
        'code_phrase': codePhraseField.current.state.password
      };
      console.log(request_body)

      let response = await fetch(RequestUrls_Pevneva.REGISTER, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(request_body)
      }) 
      response = await response.json()

      if (response){
        navigate(PageUrls_Pevneva.REGISTER_SUCCESS);
      }else{
        setRegisterError(Strings_Pevneva.REGISTER_FAILED);
      }
    } else {
      setRegisterError(Strings_Pevneva.PASSWORDS_NOT_MATCH)
    }
  }

  const paperStyle = {padding: '20px', width: '70%', margin: '20px auto'}

  return (
    <div className='App'>
      <Helmet title='Register' />
      <Container>
        <Paper elevation={3} style={paperStyle}>
          <form className='register' noValidate autoComplete='off'>
            <LoginField_Pevneva ref={loginField} type={LoginFieldTypes.REGISTER}/>

            <PasswordField_Pevneva ref={passwordField} title='Password' required />
            <PasswordField_Pevneva ref={retypePasswordField} title='Retype Password' required />

            <TextField className='formField' margin='dense'
              select
              required
              label='Role'
              value={role}
              onChange={roleHandleChange}
              variant='standard'>
              {roles.map((option) => (
                <MenuItem key={option.value} value={option.value}>
                  {option.label}
                </MenuItem>
              ))}
            </TextField>

            <PasswordField_Pevneva ref={codePhraseField} title='Code phrase' required/>

            <FormControl className='formField' variant='standard' margin='dense'>
              <Button variant='contained' onClick={() => {onRegisterClick()}}>Register</Button>
              <FormHelperText error={registerError !== ''}>{registerError}</FormHelperText>
            </FormControl>
          </form>
        </Paper>
      </Container>
    </div>
  )
}

/*
<TextField className='formField' id='login' label='Login' variant='standard' margin='dense'
              required
              value={loginText}
              error={loginError !== ''}
              helperText={loginError}
              onChange={(e) => onLoginChange(e)} /> 
*/