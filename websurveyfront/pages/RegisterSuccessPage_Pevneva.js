import '../App.css';
import React from 'react';
import Strings_Pevneva from '../settings/Strings_Pevneva';
import PageUrls_Pevneva from '../settings/PageUrls_Pevneva';
import { Container } from '@mui/material';
import Helmet from 'react-helmet';

export default function RegisterSuccessPage_Pevneva () {
  return(
      <div className='App'>
        <Helmet title='Register success!' />
        <Container>
          {Strings_Pevneva.REGISTER_SUCCESS}
        </Container>
        <Container>
          <a href={PageUrls_Pevneva.LOGIN}>{Strings_Pevneva.NOW_YOU_CAN_LOGIN}</a>
        </Container>
      </div>
  )
}