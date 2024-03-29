import '../App.css'
import React from 'react'
import Cookies from 'js-cookie'
import Cookies_Pevneva from '../settings/Cookies_Pevneva'
import RequestUrls_Pevneva from '../settings/RequestUrls_Pevneva'
import SurveyCard_Pevneva from '../components/SurveyCard_Pevneva'
import TextField from '@mui/material/TextField'
import { Box, Button, FormControl, FormControlLabel, Radio, RadioGroup } from '@mui/material'

export default function AvalibleSurveysPage_Pevneva() {
  const[avalibleSurveys, setAvalibleSurveys] = React.useState([])
  const[displayedSurveys, setDisplayedSurveys] = React.useState([])
  const[filter, setFilter] = React.useState('')
  const[authorFilter, setAuthorFilter] = React.useState('')
  const[sortValue, setSortValue] = React.useState('')
  const[firstUpdate, setFirstUpdate] = React.useState(true)

  if (firstUpdate) {
    setFirstUpdate(false)
    loadSurveys()
  }

  async function loadSurveys() {
    const request_body = {
      'login': Cookies.get(Cookies_Pevneva.LOGIN),
      'session_id': Cookies.get(Cookies_Pevneva.SESSION_ID)
    }
  
    let surveys = await fetch(RequestUrls_Pevneva.GET_AVALIBLE_SURVEYS, {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify(request_body)
    })
    surveys = await surveys.json()

    setAvalibleSurveys(surveys)
    setDisplayedSurveys(surveys)
  }

  function sortChange(e) {
    setSortValue(e.target.value)
    setDisplayedSurveys(sort(e.target.value, displayedSurveys.slice()))
  }

  function sort(sortBy, array) {
    if (sortBy === 'byAlphabet') {
      array.sort(function(a, b) {
        return a.title.localeCompare(b.title)
      })
    }
    
    if (sortBy === 'byAuthor') {
      array.sort(function(a, b) {
        return a.author.localeCompare(b.author)
      })
    }

    return array
  }

  function filterChanged(e){
    let newSurveys = []
    avalibleSurveys.forEach(survey => {
      if (!survey.title.toLowerCase().includes(e.target.value) && !survey.description.toLowerCase().includes(e.target.value) && !survey.author.toLowerCase().includes(e.target.value)) return
      newSurveys.push(survey)
    })
    setDisplayedSurveys(sort(sortValue, newSurveys))
    setFilter(e.target.value)
  }

  return (
    <div id='SurveysPage'>
      <div id='surveysList'>
        {displayedSurveys.map(function(survey) {
          return(
            <SurveyCard_Pevneva survey={survey}/>
          )
        })}
        <Box sx={{ paddingTop: 20 }} />
      </div>
      <div className='sortPannel'>
        <FormControl>
          <RadioGroup row onChange={sortChange}>
            <FormControlLabel value='byAlphabet' control={<Radio />} label='По алфавиту' />
            <FormControlLabel value='byAuthor' control={<Radio />} label='По автору' />
          </RadioGroup>
        </FormControl>
        <TextField label='Фильтр' value={filter} onChange={filterChanged} />
      </div>
    </div>
  )
}