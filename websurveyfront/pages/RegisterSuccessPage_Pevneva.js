import '../App.css'
import React from 'react'
import Strings_Pevneva from '../settings/Strings_Pevneva'
import PageUrls_Pevneva from '../settings/PageUrls_Pevneva'
import { Container } from '@mui/material'
import Helmet from 'react-helmet'
import Box from '@mui/material/Box'

export default function RegisterSuccessPage_Pevneva () {
  return(
    <div id='RegisterSuccessPage'>
      <Helmet title='Регистрация выполнена успешно!' />
      
      <Box sx={{ paddingTop: 20 }} />
      <div>
        <a className='title'>
          {Strings_Pevneva.REGISTER_SUCCESS}
        </a>
      </div>
      <Box sx={{ paddingTop: 4 }} />
      <div>
        <a className='subtitle'>
          <a href={PageUrls_Pevneva.LOGIN}>{Strings_Pevneva.NOW_YOU_CAN_LOGIN}</a>
        </a>
      </div>
    </div>
  )
}