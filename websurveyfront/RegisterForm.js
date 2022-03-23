import './App.css';
import React, { useRef } from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Container from '@mui/material/Container';
import Paper from '@mui/material/Paper';
import MenuItem from '@mui/material/MenuItem';
import PasswordField from './PasswordField';

export default function RegisterForm () {
  const roles = [{
      label: 'User',
      value: false
    },{
      label: 'Admin',
      value: true
  }]

  const[login_text, setLoginText] = React.useState('');
  const[role, setRole] = React.useState(false);

  const passwordField = useRef(null)
  const retypePasswordField = useRef(null)
  const codePhraseField = useRef(null)

  function roleHandleChange(event) {
    setRole(event.target.value)
  }

  function validate() {
    //console.log(passwordField.current.state.password)
    //console.log(retypePasswordField.current.state.password)
    //console.log(codePhraseField.current.state.password)
    //console.log(role)
  }

  function onRegisterClick() {
    if (passwordField.current.state.password === retypePasswordField.current.state.password) {
      const request_body = {
        'login': login_text,
        'password': passwordField.current.state.password,
        'role': role,
        'code_phrase': codePhraseField.current.state.password
      };

      fetch('http://localhost:8080/user/add', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(request_body)
      }).then(response => {
        alert('Register Successful');
      })
    } else {
      alert('Password does not match')
    }
  }

  const paperStyle = {padding: '20px', width: '70%', margin: '20px auto'}

  return (
    <Container>
      <Paper elevation={3} style={paperStyle}>
        <form className='register' noValidate autoComplete='off'>
          <TextField className='formField' id="login" label="Login" variant="standard" margin="dense"
            required
            value={login_text}
            onChange={(e) => setLoginText(e.target.value)} />

          <PasswordField ref={passwordField} id="password" title='Password' onChange={(event) => {validate()}} required />
          <PasswordField ref={retypePasswordField} id="retypepassword" title='Retype Password' onChange={(event) => {validate()}} required />

          <TextField className='formField' margin="dense"
            select
            required
            label="Role"
            value={role}
            onChange={roleHandleChange}
            variant="standard">
            {roles.map((option) => (
              <MenuItem key={option.value} value={option.value}>
                {option.label}
              </MenuItem>
            ))}
          </TextField>

          <PasswordField ref={codePhraseField} title='Code phrase' onChange={(event) => {validate()}} required/>

          <Button variant="contained" onClick={() => {onRegisterClick()}}>Register</Button>
        </form>
      </Paper>
    </Container>
  )
}