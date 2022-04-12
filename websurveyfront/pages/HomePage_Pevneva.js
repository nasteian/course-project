import '../App.css'
import React from 'react'
import Helmet from 'react-helmet'
import Button from '@mui/material/Button'
import Strings_Pevneva from '../settings/Strings_Pevneva'
import Particles_Pevneva from '../components/Particles_Pevneva'
import PageUrls_Pevneva from '../settings/PageUrls_Pevneva'
import Cookies_Pevneva from '../settings/Cookies_Pevneva'
import Cookies from 'js-cookie'
import Box from '@mui/material/Box'
import Typewriter from 'typewriter-effect'

export default function HomePage_Pevneva() { 
  function onTryNowButton() {
    window.location.replace(Cookies.get(Cookies_Pevneva.AUTHORIZED) === 'true' ? PageUrls_Pevneva.SURVEYS : PageUrls_Pevneva.REGISTER)
  }
  
  return (
    <div id='HomePage'>
      <Helmet title='Веб опросы' />

      <div id='homePageMainPart'>  
        <Particles_Pevneva color='#000000' direction='none' />
        
        <Box sx={{ paddingTop: 24 }} />
        
        <div> 
          <Typewriter
            options={{
              strings: [Strings_Pevneva.HOME_PAGE_TITLE],
              autoStart: true,
              delay: 50, 
              deleteSpeed: Infinity,
              wrapperClassName: 'title',
              cursorClassName: 'Typewriter__cursor title'
            }}
          />  
        </div>
  
        <div> 
          <a className='subtitle'>{Strings_Pevneva.HOME_PAGE_SUBTITLE}</a> 
        </div>
        
        <Box sx={{ paddingTop: 4 }} />
        <Button variant="outlined" color='inherit' onClick={() => {onTryNowButton()}}>{Strings_Pevneva.TRY_NOW}</Button>
      </div>

      <div id='homePageSecondPart'> 
        
      </div>
    </div>
  )
}
  