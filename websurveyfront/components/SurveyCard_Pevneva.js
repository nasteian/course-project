import '../App.css'
import React from 'react'
import PageUrls_Pevneva from '../settings/PageUrls_Pevneva'

export default function SurveyCard_Pevneva(props) {
  function onClick() {
    window.location.assign(PageUrls_Pevneva.SURVEY + '/' + props.survey['id'])
  }

  return (
    <div className={(props.type === 'my' ?  'mySurveyCard' : 'surveyCard')} onClick={() => {onClick()}}>
      <div className='cardTitle'>{props.survey['title']}</div>
      <div className='cardDescription'>{props.survey['description']}</div>
      {(props.type !== 'my') ? (<div className='cardAuthor'>{props.survey['author']}</div>) : (<></>)}
    </div>
  )
}