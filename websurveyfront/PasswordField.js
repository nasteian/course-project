import './App.css';
import React from 'react';
import FormControl from '@mui/material/FormControl';
import InputLabel from '@mui/material/InputLabel';
import Input from '@mui/material/Input';
import InputAdornment from '@mui/material/InputAdornment';
import IconButton from '@mui/material/IconButton';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';

export default class PasswordField extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      amount: '',
      password: '',
      weight: '',
      weightRange: '',
      showPassword: false
    }

    this.handleChange = this.handleChange.bind(this);
    this.handleClickShow = this.handleClickShow.bind(this);
    this.handleMouseDown = this.handleMouseDown.bind(this);
  }

  handleChange = (prop) => (event) => {
    this.setState(prevState => ({
       ...this.state, [prop]: event.target.value
    }))
    this.props.onChange(null)
  }

  handleClickShow = () => {
    this.setState(prevState => ({
      showPassword: !prevState.showPassword
    }));
  }

  handleMouseDown = (event) => {
    event.preventDefault()
  }

  render() {
    return (
      <FormControl className='formField' variant="standard" margin="dense">
        <InputLabel htmlFor="password">{this.props.title}</InputLabel>
        <Input
          type={this.state.showPassword ? 'text' : 'password'}
          value={this.state.password}
          onChange={this.handleChange('password')}
          endAdornment={
            <InputAdornment position="end">
              <IconButton
                aria-label="toggle password visibility"
                onClick={this.handleClickShow}
                onMouseDown={this.handleMouseDown}
              >
                {this.state.showPassword ? <VisibilityOff /> : <Visibility />}
              </IconButton>
            </InputAdornment>
          }
        />
      </FormControl>
    )
  }
}