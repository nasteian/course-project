import '../App.css'
import React from 'react'
import PageUrls_Pevneva from '../settings/PageUrls_Pevneva'
import AssessmentIcon from '@mui/icons-material/Assessment'
import IconButton from '@mui/material/IconButton'

export default function SurveyCard_Pevneva(props) {
  function onClick() {
    window.location.assign(PageUrls_Pevneva.SURVEY + '/' + props.survey['id'] + (props.type === 'my' ? PageUrls_Pevneva.MODIFY : ''))
  }

  function onStatisticsClick() {
    window.location.assign(PageUrls_Pevneva.SURVEY + '/' + props.survey['id'] + PageUrls_Pevneva.STATISTICS)
  }

  return (
    <div className={(props.type === 'my' ?  'mySurveyCard' : 'surveyCard')}>
      <div className='cardClickArea' onClick={onClick} />
      <div className='cardTitle'>{props.survey['title']}</div>
      <div className='cardDescription'>{props.survey['description']}</div>
      {(props.type !== 'my') ? (
        <div className='cardAuthor'>{props.survey['author']}</div>
      ) : (
        <div className='cardMenus'>
          <div className='cardStatistics'>
            <IconButton onClick={onStatisticsClick} component='span'>
              <AssessmentIcon />
            </IconButton>
          </div>
        </div>
      )}
    </div>
  )
}