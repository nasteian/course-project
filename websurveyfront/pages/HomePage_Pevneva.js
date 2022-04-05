import '../App.css'
import React from 'react'
import Helmet from 'react-helmet'
import TopBar_Pevneva from '../components/TopBar_Pevneva'
import Strings_Pevneva from '../settings/Strings_Pevneva'
import Particles_Pevneva from '../components/Particles_Pevneva'

export default function HomePage_Pevneva() {  
  return (
    <div className='App'>
      <Helmet title='Веб опросы' />

      <div id='homePageMainBar'>  
        <Particles_Pevneva color='#000000' direction='none' />
        <div> <a className='title'>{Strings_Pevneva.HOME_PAGE_TITLE}</a> </div>
        <div> <a className='subtitle'></a> </div>
      </div>
      
      <TopBar_Pevneva />

      <div id='homePageSecondBar'>
        
      </div>
    </div>
  )
}
  