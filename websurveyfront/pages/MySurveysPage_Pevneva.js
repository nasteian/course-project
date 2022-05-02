import '../App.css'
import React from 'react'
import Cookies from 'js-cookie'
import Cookies_Pevneva from '../settings/Cookies_Pevneva'
import RequestUrls_Pevneva from '../settings/RequestUrls_Pevneva'
import SurveyCard_Pevneva from '../components/SurveyCard_Pevneva'
import Popup from 'reactjs-popup'
import AddIcon from '@mui/icons-material/Add'
import CloseIcon from '@mui/icons-material/Close'
import { Button, TextField } from '@mui/material'
import toast from 'react-hot-toast'
import Strings_Pevneva from '../settings/Strings_Pevneva'

export default function MySurveysPage_Pevneva() {
  const [mySurveys, setMySurveys] = React.useState([])
  const [firstUpdate, setFirstUpdate] = React.useState(true)
  const [popupOpen, setPopupOpen] = React.useState(false)
  const [createSurveyTitle, setCreateSurveyTitle] = React.useState('')
  const [createSurveyDescription, setCreateSurveyDescription] = React.useState('')
  
  if (firstUpdate) {
    setFirstUpdate(false)
    loadSurveys()
  }

  async function loadSurveys() {
    const request_body = {
      'login': Cookies.get(Cookies_Pevneva.LOGIN),
      'session_id': Cookies.get(Cookies_Pevneva.SESSION_ID)
    }
  
    let surveys = await fetch(RequestUrls_Pevneva.GET_MY_SURVEYS, {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify(request_body)
    })
    surveys = await surveys.json()

    setMySurveys(surveys)
  }

  function onOpenPopupClick() {
    setPopupOpen(true)
  }

  function onCreateSurveyClick() {
    toast.promise(
      createSurvey(), {
         loading: Strings_Pevneva.SAVING,
         success: <b>{Strings_Pevneva.SURVEY_CREATED}</b>,
         error: <b>{Strings_Pevneva.OCCURED_ERROR}</b>
       }
    )
  }

  function createSurvey() {
    return new Promise((resolve, reject) => {
      const request_body = {
        'login': Cookies.get(Cookies_Pevneva.LOGIN),
        'session_id': Cookies.get(Cookies_Pevneva.SESSION_ID),
        "title": createSurveyTitle,
        "description": createSurveyDescription
      }

      fetch(RequestUrls_Pevneva.CREATE_SURVEY, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(request_body)
      }).then(response => {
        if (response.status === 200) {
          window.location.reload()
          resolve()
        }
        reject()
      }).catch(() => {reject()})
    })
  }
  
  return (
    <div id='SurveysPage'>
      <Popup open={popupOpen} closeOnDocumentClick onClose={() => {setPopupOpen(false)}}>
        <div className='popupModal'>
          <a className='closePopup' onClick={() => {setPopupOpen(false)}}>
            <CloseIcon />
          </a>

          <TextField
            style={{'width': '60%'}}
            label='Название опроса'
            value={createSurveyTitle}
            onChange={(e) => {setCreateSurveyTitle(e.target.value)}} />

          <TextField
            style={{'width': '60%'}}
            label='Описание'
            value={createSurveyDescription}
            onChange={(e) => {setCreateSurveyDescription(e.target.value)}} />

          <Button variant='outlined' onClick={onCreateSurveyClick}>Создать опрос</Button>
        </div>
      </Popup>

      <div id='surveysList'>
        {mySurveys.map(function(survey) {
          return(
            <SurveyCard_Pevneva survey={survey} type='my'/>
          )
        })}
        <div className='createSurveyCard' onClick={onOpenPopupClick}>
          <AddIcon />
        </div>
      </div>
    </div>
  )
}