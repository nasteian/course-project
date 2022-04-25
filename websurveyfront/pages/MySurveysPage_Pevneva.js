import '../App.css'
import React from 'react'
import Cookies from 'js-cookie'
import Cookies_Pevneva from '../settings/Cookies_Pevneva'
import RequestUrls_Pevneva from '../settings/RequestUrls_Pevneva'
import SurveyCard_Pevneva from '../components/SurveyCard_Pevneva'

export default class MySurveysPage_Pevneva extends React.Component {
  constructor(props) {
    super(props)

    this.state = {
      mySurveys: []
    }

    this.loadSurveys()
  }

  loadSurveys = async () => {
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

    this.setState({mySurveys: surveys})
  }
  
  render() {
    return (
      <div id='SurveysPage'>
        <div id='surveysList'>
          {this.state.mySurveys.map(function(survey) {
            return(
              <SurveyCard_Pevneva survey={survey} type='my'/>
            )
          })}
        </div>
      </div>
    )
  }
}